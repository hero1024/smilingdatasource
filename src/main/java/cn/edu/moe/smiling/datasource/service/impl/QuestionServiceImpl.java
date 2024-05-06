package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionCaseDao;
import cn.edu.moe.smiling.datasource.dao.QuestionDataDao;
import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.util.ConvertUtil;
import cn.edu.moe.smiling.datasource.vo.QuestionCaseVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDataDao questionDataDao;
    @Autowired
    private QuestionCaseDao questionCaseDao;

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
        QuestionCaseEntity questionCaseEntity = ConvertUtil.entityToVo(questionCaseVo, QuestionCaseEntity.class);
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

}
