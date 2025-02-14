package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.KnowledgeFileDao;
import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.FileStatus;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.model.ReturnCode;
import cn.edu.moe.smiling.datasource.model.ValidException;
import cn.edu.moe.smiling.datasource.service.KnowledgeService;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    private KnowledgeFileDao knowledgeFileDao;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${data.file.storagePath}")
    private String storagePath;
    @Value("${data.file.storageHold}")
    private long storageHold;
    @Value("${data.file.datasetidUrl}")
    private String datasetidUrl;
    @Value("${data.file.vectorizeDeleteUrl}")
    private String vectorizeDeleteUrl;
    @Value("${data.file.vectorizationUrl}")
    private String vectorizationUrl;
    @Value("${data.file.authorization}")
    private String authorization;
    @Value("${data.file.form.data}")
    private String formData;
    @Value("${data.file.form.type}")
    private String formType;

    /**
     * 文件上传
     */
    @Override
    public ResultData<String> uploadFile(MultipartFile file, String uid) {

        String fileName = file.getOriginalFilename();

        String filePathName = Paths.get(storagePath, uid, fileName).toString();

        //拼接文件存放地址
        File dest=new File(filePathName);

        //判断文件路径是否存在  如果不存在就创建文件路径
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        if (FileUtil.size(dest.getParentFile()) > storageHold * 1024 * 1024) {
            return ResultData.fail("文件目录大于" + storageHold + "MB，请清理文件后重试！");
        }

        LambdaQueryWrapper<KnowledgeFileEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(KnowledgeFileEntity::getPath, filePathName);
        lambdaQueryWrapper.last("limit 1");
        KnowledgeFileEntity fileMap = knowledgeFileDao.getOne(lambdaQueryWrapper);
        if (fileMap == null) {
            fileMap = new KnowledgeFileEntity();

            //创建时间
            fileMap.setCreateTime(new Date());
        }
        fileMap.setUpdateTime(new Date());
        //用户ID
        fileMap.setUserId(uid);

        //文件name
        fileMap.setName(fileName);

        //文件大小
        long fileSize = file.getSize();
        fileMap.setSize(fileSize);

        //文件类型
        String fileType = null;
        if (fileName != null) {
            fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        }
        fileMap.setType(fileType);

        //文件path
        fileMap.setPath(filePathName);

        //文件保存
        try {
            FileUtil.writeBytes(file.getBytes(),  filePathName);
            fileMap.setStatus(FileStatus.SUCCESS.name());
            knowledgeFileDao.saveOrUpdate(fileMap);
            // 向量化
            return uploadFileWithInputStream(fileMap);
        } catch (Exception e) {
            log.error("write File fail!", e);
            fileMap.setStatus(FileStatus.FAIL.name());
            fileMap.setStatusDesc(e.getMessage());
            knowledgeFileDao.saveOrUpdate(fileMap);
            return ResultData.fail();
        }
    }

    /**
     * 向量化
     */
    @Override
    public ResultData<String> uploadFileWithInputStream(KnowledgeFileEntity knowledgeFileEntity) {
        String datasetid;
        //datasetid = "e90437ea-87a8-4ef7-88ad-da48a720cad6";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(datasetidUrl, null, String.class, knowledgeFileEntity.getUserId());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("getdatasetid response: {}" , responseEntity.getBody());
            // 5、请求结果处理
            JSONObject datasetResult = JSONObject.parseObject(responseEntity.getBody());
            datasetid = datasetResult.getString("datasetid");
            if (StringUtils.isEmpty(datasetid)) {
                knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
                knowledgeFileEntity.setStatusDesc(responseEntity.getBody());
                knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
                return ResultData.fail();
            }
            knowledgeFileEntity.setDatasetId(datasetid);
            knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
        } else {
            log.error("Failed to get datasetid: {}" , responseEntity.getStatusCode());
            knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
            knowledgeFileEntity.setStatusDesc(responseEntity.getStatusCode().toString());
            knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
            return ResultData.fail();
        }
        // 1、封装请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.setContentLength(knowledgeFileEntity.getSize());
        headers.setContentDispositionFormData("media", knowledgeFileEntity.getName());
        headers.setBearerAuth(authorization);
        // 2、封装请求体
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(knowledgeFileEntity.getPath());
        param.add("file", resource);
        param.add("data", formData);
        param.add("type", formType);
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(vectorizationUrl)
                .build()
                .expand(datasetid)
                .encode();
        URI uri = uriComponents.toUri();
        // 3、封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(param, headers);
        // 4、发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(uri, formEntity, String.class);
        // 检查响应状态和内容
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("File uploaded successfully: {}" , response.getBody());
            // 5、请求结果处理
            JSONObject weChatResult = JSONObject.parseObject(response.getBody());
            JSONObject document = weChatResult.getJSONObject("document");
            if (Objects.isNull(document)) {
                knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
                knowledgeFileEntity.setStatusDesc(response.getBody());
                knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
                return ResultData.fail();
            } else {
                // 6、返回结果
                String documentId = document.getString("id");
                knowledgeFileEntity.setDocumentId(documentId);
                knowledgeFileEntity.setStatus(FileStatus.SUCCESS.name());
                knowledgeFileEntity.setStatusDesc(response.getBody());
                knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
                return ResultData.success(knowledgeFileEntity.getId());
            }
        } else {
            log.error("Failed to upload file: {}" , response.getStatusCode());
            knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
            knowledgeFileEntity.setStatusDesc(response.getStatusCode().toString());
            knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
            return  ResultData.fail();
        }
    }

    @Override
    public IPage<KnowledgeFileEntity> listFile(Page<KnowledgeFileEntity> page, String uid) {
        LambdaQueryWrapper<KnowledgeFileEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(uid), KnowledgeFileEntity::getUserId, uid);
        queryWrapper.orderByDesc(KnowledgeFileEntity::getUpdateTime);
        return knowledgeFileDao.page(page, queryWrapper);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public JSONObject deleteFile(Long id) throws ValidException {
        KnowledgeFileEntity knowledgeFileEntity = knowledgeFileDao.getById(id);
        log.info("删除数据：{}", knowledgeFileEntity);
        if (knowledgeFileDao.removeById(knowledgeFileEntity)) {
            try {
                log.info("删除向量化datasetId：{}，documentId：{}", knowledgeFileEntity.getDatasetId(), knowledgeFileEntity.getDocumentId());
                // 1、封装请求头
                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.APPLICATION_JSON;
                headers.setContentType(type);
                headers.setBearerAuth(authorization);
                // 2、封装请求体
                Map<String, String> uriVariables = new HashMap<>();
                uriVariables.put("dataset_id", knowledgeFileEntity.getDatasetId());
                uriVariables.put("document_id", knowledgeFileEntity.getDocumentId());
                // 4、发送请求
                ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(vectorizeDeleteUrl, HttpMethod.DELETE, new HttpEntity<>(headers), JSONObject.class, uriVariables);
                log.info("vectorizeDelete response: {}", responseEntity.getBody());
                if ("success".equalsIgnoreCase(Objects.requireNonNull(responseEntity.getBody()).getString("result"))) {
                    log.info("删除文件：{}", knowledgeFileEntity.getPath());
                    FileUtil.del(knowledgeFileEntity.getPath());
                    return responseEntity.getBody();
                }
            } catch (Exception e) {
                log.error("删除向量化失败", e);
                throw new ValidException(ReturnCode.RC2003.getCode(), e.getMessage());
            }
        }
        log.info("删除数据失败: {}" , knowledgeFileEntity);
        throw new ValidException(ReturnCode.INVALID_PARAM);
    }

}
