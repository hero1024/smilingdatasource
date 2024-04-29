package cn.edu.moe.smiling.datasource.dao.impl;

import cn.edu.moe.smiling.datasource.dao.DataBaseSourceDao;
import cn.edu.moe.smiling.datasource.mapper.DataBaseSourceMapper;
import cn.edu.moe.smiling.datasource.entity.DataBaseSourceEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Repository
public class DataBaseSourceDaoImpl extends ServiceImpl<DataBaseSourceMapper, DataBaseSourceEntity> implements DataBaseSourceDao {
}
