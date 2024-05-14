package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.AnswerDataDao;
import cn.edu.moe.smiling.datasource.entity.AnswerDataEntity;
import cn.edu.moe.smiling.datasource.mapper.AnswerDataMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDataDaoImpl extends ServiceImpl<AnswerDataMapper, AnswerDataEntity> implements AnswerDataDao {

}
