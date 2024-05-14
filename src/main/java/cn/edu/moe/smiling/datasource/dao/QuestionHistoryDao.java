package cn.edu.moe.smiling.datasource.dao;

import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QuestionHistoryDao extends IService<QuestionHistoryEntity> {
    IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage);

    String queryAnswer(String q);
}
