package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;

public interface KnowledgeService {
    /**
     * 文件上传
     */
    ResultData<String> uploadFile(MultipartFile file, String uid);
    /**
     * 向量化
     */
    ResultData<String> uploadFileWithInputStream(KnowledgeFileEntity knowledgeFileEntity);
    /**
     * 文件分页查询
     */
    IPage<KnowledgeFileEntity> listFile(Page<KnowledgeFileEntity> page, String uid);
    /**
     * 文件删除
     */
    JSONObject deleteFile(Long id) throws ValidationException;
}
