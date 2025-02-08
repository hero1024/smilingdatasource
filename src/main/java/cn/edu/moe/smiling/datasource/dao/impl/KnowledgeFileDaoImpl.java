package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.KnowledgeFileDao;
import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.mapper.KnowledgeFileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class KnowledgeFileDaoImpl extends ServiceImpl<KnowledgeFileMapper, KnowledgeFileEntity> implements KnowledgeFileDao {
}
