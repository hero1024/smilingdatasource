package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionDataDao;
import cn.edu.moe.smiling.datasource.entity.QuestionDataEntity;
import cn.edu.moe.smiling.datasource.mapper.QuestionDataMapper;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDataDaoImpl extends ServiceImpl<QuestionDataMapper, QuestionDataEntity> implements QuestionDataDao {
    @Override
    public IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage) {
        return baseMapper.questionPage(questionVoPage);
    }

    @Override
    public String queryAnswer(String q) {
        return baseMapper.queryAnswer(q);
    }

}
