package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionHistoryDao;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.mapper.QuestionHistoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionHistoryDaoImpl extends ServiceImpl<QuestionHistoryMapper, QuestionHistoryEntity> implements QuestionHistoryDao {
}
