package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.entity.DataFeedbackEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface QuestionService {
    IPage<QuestionVo> list(Page<QuestionVo> questionVoPage);

    List<QuestionCaseEntity> caseList();

    Boolean addCase(QuestionCaseVo questionCaseVo);

    Boolean updateCase(Long id, QuestionCaseVo questionCaseVo);

    Boolean deleteCase(Long id);

    List<QuestionHistoryVo> historyList(Long uid);

    QuestionHistoryEntity addHistory(Long uid, String ip, QuestionAndAnswerVo questionAndAnswerVo);

    Boolean deleteHistory(Long id);

    Object chatData(String q);

    DataFeedbackEntity addDataFeedback(FeedbackVo feedbackVo);

    DataFeedbackEntity updateDataFeedback(Long id, FeedbackVo feedbackVo);

    Boolean deleteDataFeedback(Long id);

}
