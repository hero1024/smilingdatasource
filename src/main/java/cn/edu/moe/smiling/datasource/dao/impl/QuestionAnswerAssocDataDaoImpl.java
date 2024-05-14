package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionAnswerAssocDataDao;
import cn.edu.moe.smiling.datasource.entity.QuestionAnswerAssocDataEntity;
import cn.edu.moe.smiling.datasource.mapper.QuestionAnswerAssocDataMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionAnswerAssocDataDaoImpl extends ServiceImpl<QuestionAnswerAssocDataMapper, QuestionAnswerAssocDataEntity> implements QuestionAnswerAssocDataDao {
}
