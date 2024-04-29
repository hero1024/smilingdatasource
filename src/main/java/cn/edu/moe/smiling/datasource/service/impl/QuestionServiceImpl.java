package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionDataDao;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDataDao questionDataDao;

    @Override
    public IPage<QuestionVo> list(Page<QuestionVo> questionVoPage) {
        return questionDataDao.questionPage(questionVoPage);
    }
}
