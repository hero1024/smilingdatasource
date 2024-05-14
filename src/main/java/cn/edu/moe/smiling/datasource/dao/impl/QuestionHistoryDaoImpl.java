package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionHistoryDao;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.mapper.QuestionHistoryMapper;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionHistoryDaoImpl extends ServiceImpl<QuestionHistoryMapper, QuestionHistoryEntity> implements QuestionHistoryDao {

    @Value("${data.hour}")
    Integer hour;

    @Override
    public IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage) {
        return baseMapper.questionPage(questionVoPage);
    }

    @Override
    public String queryAnswer(String q) {
        return baseMapper.queryAnswer(q, hour);
    }

}
