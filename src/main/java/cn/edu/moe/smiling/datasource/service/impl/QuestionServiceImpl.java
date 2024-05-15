package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.*;
import cn.edu.moe.smiling.datasource.entity.*;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.util.ConvertUtil;
import cn.edu.moe.smiling.datasource.vo.*;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionCaseDao questionCaseDao;
    @Autowired
    private QuestionHistoryDao questionHistoryDao;
    @Autowired
    private DataFeedbackDao dataFeedbackDao;

    @Override
    public IPage<QuestionVo> list(Page<QuestionVo> questionVoPage) {
        return questionHistoryDao.questionPage(questionVoPage);
    }

    @Override
    public List<QuestionCaseEntity> caseList() {
        return questionCaseDao.list(Wrappers.<QuestionCaseEntity>lambdaQuery().orderByAsc(QuestionCaseEntity::getSortNumber));
    }

    @Override
    public Boolean addCase(QuestionCaseVo questionCaseVo) {
        List<QuestionCaseEntity> questionCaseEntityList = questionCaseDao.list();
        QuestionCaseEntity questionCaseEntity = ConvertUtil.entityConvert(questionCaseVo, QuestionCaseEntity.class);
        questionCaseEntity.setUpdateTime(new Date());
        if (questionCaseVo.getSortNumber()<=0){
            questionCaseEntity.setSortNumber(1L);
        } else if (questionCaseVo.getSortNumber() > questionCaseEntityList.size()) {
            questionCaseEntity.setSortNumber(1L + questionCaseEntityList.size());
        }
        questionCaseEntityList.add(questionCaseEntity.getSortNumber().intValue()-1, questionCaseEntity);
        for (int i=0; i<= questionCaseEntityList.size()-1; i++){
            questionCaseEntityList.get(i).setSortNumber((long) (i+1));
        }
        return questionCaseDao.saveOrUpdateBatch(questionCaseEntityList);
    }

    @Override
    public Boolean updateCase(Long id, QuestionCaseVo questionCaseVo) {
        List<QuestionCaseEntity> questionCaseEntityList = questionCaseDao.list();
        Optional<QuestionCaseEntity> optional = questionCaseEntityList.stream().filter(q -> q.getId().equals(id)).findAny();
        if (!optional.isPresent()){
            return false;
        }
        QuestionCaseEntity questionCaseEntity = optional.get();
        questionCaseEntityList.remove(questionCaseEntity);
        questionCaseEntity.setId(id);
        questionCaseEntity.setQuestion(questionCaseVo.getQuestion());
        questionCaseEntity.setType(questionCaseEntity.getType());
        questionCaseEntity.setUpdateTime(new Date());
        if (questionCaseVo.getSortNumber()<=0){
            questionCaseEntity.setSortNumber(1L);
        } else if (questionCaseVo.getSortNumber() > questionCaseEntityList.size()) {
            questionCaseEntity.setSortNumber(1L + questionCaseEntityList.size());
        } else {
            questionCaseEntity.setSortNumber(questionCaseVo.getSortNumber());
        }
        questionCaseEntityList.add(questionCaseEntity.getSortNumber().intValue()-1, questionCaseEntity);
        for (int i=0; i<= questionCaseEntityList.size()-1; i++){
            questionCaseEntityList.get(i).setSortNumber((long) (i+1));
        }
        return questionCaseDao.updateBatchById(questionCaseEntityList);
    }

    @Override
    public Boolean deleteCase(Long id) {
        return questionCaseDao.removeById(id);
    }

    @Override
    public List<QuestionHistoryVo> historyList(Long uid) {
         // 创建QueryWrapper实例
        LambdaQueryWrapper<QuestionHistoryEntity> queryWrapper = Wrappers.lambdaQuery();
         // 降序查询最新20条数据
         queryWrapper.eq(QuestionHistoryEntity::getUserId, uid)
                 .last("LIMIT 20")
                 .orderByDesc(QuestionHistoryEntity::getCreateTime);
         List<QuestionHistoryEntity> entityList = questionHistoryDao.list(queryWrapper);
        List<QuestionHistoryVo> list = ConvertUtil.entityListConvert(entityList, QuestionHistoryVo.class);
                // 进行升序排序
        list.sort(Comparator.comparing(QuestionHistoryVo::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Boolean addHistory(Long uid, String ip, QuestionAndAnswerVo questionAndAnswerVo) {
        Date date = new Date();
        QuestionHistoryEntity questionHistoryEntity = new QuestionHistoryEntity();
        questionHistoryEntity.setQuestion(questionAndAnswerVo.getQuestion());
        questionHistoryEntity.setAnswer(questionAndAnswerVo.getAnswer());
        questionHistoryEntity.setChatNo(StringUtils.hasText(questionAndAnswerVo.getChatNo())?questionAndAnswerVo.getChatNo() : String.valueOf(uid));
        questionHistoryEntity.setUserId(uid);
        questionHistoryEntity.setUserIp(ip);
        questionHistoryEntity.setDelState(0);
        questionHistoryEntity.setCreateTime(date);
        questionHistoryEntity.setUpdateTime(date);
        return questionHistoryDao.save(questionHistoryEntity);
    }

    @Override
    public Boolean deleteHistory(Long id) {
        return questionHistoryDao.removeById(id);
    }

    @Override
    public IPage<QuestionHistoryEntity> historyPages(Page<QuestionHistoryEntity> questionHistoryPage) {
        return questionHistoryDao.page(questionHistoryPage, Wrappers.<QuestionHistoryEntity>lambdaQuery().orderByDesc(QuestionHistoryEntity::getCreateTime));
    }

    @Override
    public Object chatData(String q) {
        String answer = questionHistoryDao.queryAnswer(q);
        if (answer == null){
            return "";
        }
        if (JSONUtil.isTypeJSON(answer)){
            return JSONUtil.parse(answer);
        }
        return answer;
    }

    @Override
    public DataFeedbackEntity addDataFeedback(FeedbackVo feedbackVo) {
        DataFeedbackEntity dataFeedbackEntity = ConvertUtil.entityConvert(feedbackVo, DataFeedbackEntity.class);
        Date date = new Date();
        dataFeedbackEntity.setCreateTime(date);
        dataFeedbackEntity.setUpdateTime(date);
        dataFeedbackDao.save(dataFeedbackEntity);
        return dataFeedbackEntity;
    }

    @Override
    public DataFeedbackEntity updateDataFeedback(Long id, FeedbackVo feedbackVo) {
        DataFeedbackEntity dataFeedbackEntity = dataFeedbackDao.getById(id);
        dataFeedbackEntity.setFeedback(feedbackVo.getFeedback());
        dataFeedbackEntity.setBadReason(feedbackVo.getBadReason());
        dataFeedbackEntity.setUpdateTime(new Date());
        dataFeedbackDao.updateById(dataFeedbackEntity);
        return dataFeedbackEntity;
    }

    @Override
    public Boolean deleteDataFeedback(Long id) {
        return dataFeedbackDao.removeById(id);
    }

}
