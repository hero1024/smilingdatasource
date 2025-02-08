package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface KnowledgeService {
    /**
     * 文件上传
     */
    ResultData<String> uploadFile(MultipartFile file, String uid);
    /**
     * 向量化
     */
    ResultData<String> uploadFileWithInputStream(MultipartFile file, KnowledgeFileEntity knowledgeFileEntity) throws IOException;
}
