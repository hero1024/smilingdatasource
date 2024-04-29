package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.DataFeedbackDao;
import cn.edu.moe.smiling.datasource.entity.DataFeedbackEntity;
import cn.edu.moe.smiling.datasource.mapper.DataFeedbackMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DataFeedbackDaoImpl extends ServiceImpl<DataFeedbackMapper, DataFeedbackEntity> implements DataFeedbackDao {
}
