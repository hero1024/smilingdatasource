package cn.edu.moe.smiling.datasource.dao;

import cn.edu.moe.smiling.datasource.entity.QuestionDataEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QuestionDataDao extends IService<QuestionDataEntity> {
    IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage);
}
