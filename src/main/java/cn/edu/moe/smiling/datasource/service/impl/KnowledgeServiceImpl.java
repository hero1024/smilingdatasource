package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.KnowledgeFileDao;
import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.FileStatus;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.KnowledgeService;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
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

        ResultData<String> result;

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
        fileMap.setSize(String.valueOf(fileSize));

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
            result = uploadFileWithInputStream(file, fileMap);
        } catch (IOException e) {
            log.error("write File fail!", e);
            fileMap.setStatus(FileStatus.FAIL.name());
            fileMap.setStatusDesc(e.getMessage());
            knowledgeFileDao.saveOrUpdate(fileMap);
            result = ResultData.fail();
        }
        return result;
    }

    /**
     * 向量化
     */
    @Override
    public ResultData<String> uploadFileWithInputStream(MultipartFile file, KnowledgeFileEntity knowledgeFileEntity) throws IOException {
        ResultData<String> result;
        // 1、封装请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.setContentLength(file.getSize());
        headers.setContentDispositionFormData("media", file.getOriginalFilename());
        headers.setBearerAuth(authorization);
        // 2、封装请求体
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        // 将multipartFile转换成byte资源进行传输
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        param.add("file", resource);
        param.add("data", formData);
        param.add("type", formType);
        // 3、封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(param, headers);
        // 4、发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(vectorizationUrl, formEntity, String.class);
        // 检查响应状态和内容
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("File uploaded successfully: {}" , response.getBody());
            // 5、请求结果处理
            JSONObject weChatResult = JSONObject.parseObject(response.getBody());
            JSONObject document = weChatResult.getJSONObject("document");
            if (Objects.nonNull(document) && StringUtils.hasText(document.getString("id"))) {
                // 6、返回结果
                knowledgeFileEntity.setStatus(FileStatus.SUCCESS.name());
                knowledgeFileEntity.setStatusDesc(response.getBody());
                knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
                result = ResultData.success(knowledgeFileEntity.getId());
            } else {
                knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
                knowledgeFileEntity.setStatusDesc(response.getBody());
                knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
                result = ResultData.fail();
            }
        } else {
            log.error("Failed to upload file: {}" , response.getStatusCode());
            knowledgeFileEntity.setStatus(FileStatus.FAIL.name());
            knowledgeFileEntity.setStatusDesc(response.getStatusCode().toString());
            knowledgeFileDao.saveOrUpdate(knowledgeFileEntity);
            result = ResultData.fail();
        }
        return result;
    }

}
