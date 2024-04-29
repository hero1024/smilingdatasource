package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface QuestionService {
    IPage<QuestionVo> list(Page<QuestionVo> questionVoPage);
}
