package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.QuestionCaseDao;
import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.mapper.QuestionCaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionCaseDaoImpl extends ServiceImpl<QuestionCaseMapper, QuestionCaseEntity> implements QuestionCaseDao {
}
