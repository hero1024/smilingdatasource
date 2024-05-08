package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionCaseDao;
import cn.edu.moe.smiling.datasource.dao.QuestionDataDao;
import cn.edu.moe.smiling.datasource.dao.QuestionHistoryDao;
import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.util.ConvertUtil;
import cn.edu.moe.smiling.datasource.vo.QuestionCaseVo;
import cn.edu.moe.smiling.datasource.vo.QuestionHistoryVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDataDao questionDataDao;
    @Autowired
    private QuestionCaseDao questionCaseDao;
    @Autowired
    private QuestionHistoryDao questionHistoryDao;

    @Override
    public IPage<QuestionVo> list(Page<QuestionVo> questionVoPage) {
        return questionDataDao.questionPage(questionVoPage);
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
    public List<QuestionHistoryEntity> historyList(Long uid) {
         // 创建QueryWrapper实例
        LambdaQueryWrapper<QuestionHistoryEntity> queryWrapper = Wrappers.lambdaQuery();
         // 降序查询最新20条数据
         queryWrapper.eq(QuestionHistoryEntity::getUserId, uid)
                 .last("LIMIT 20")
                 .orderByDesc(QuestionHistoryEntity::getCreatedAt);
         List<QuestionHistoryEntity> list = questionHistoryDao.list(queryWrapper);
         // 进行升序排序
        list.sort(Comparator.comparing(QuestionHistoryEntity::getCreatedAt));
        return list;
    }

    @Override
    public Boolean addHistory(Long uid, QuestionHistoryVo questionHistoryVo) {
        QuestionHistoryEntity questionHistoryEntity = ConvertUtil.entityConvert(questionHistoryVo, QuestionHistoryEntity.class);
        questionHistoryEntity.setUserId(uid);
        questionHistoryEntity.setCreatedAt(new Date());
        questionHistoryEntity.setDelState(0);
        return questionHistoryDao.save(questionHistoryEntity);
    }

    @Override
    public Boolean deleteHistory(Long id) {
        return questionHistoryDao.removeById(id);
    }

    @Override
    public IPage<QuestionHistoryEntity> historyPages(Page<QuestionHistoryEntity> questionHistoryPage) {
        return questionHistoryDao.page(questionHistoryPage, Wrappers.<QuestionHistoryEntity>lambdaQuery().orderByDesc(QuestionHistoryEntity::getCreatedAt));
    }

}
