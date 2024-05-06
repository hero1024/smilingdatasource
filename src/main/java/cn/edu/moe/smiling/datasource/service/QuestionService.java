package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionCaseVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface QuestionService {
    IPage<QuestionVo> list(Page<QuestionVo> questionVoPage);

    List<QuestionCaseEntity> caseList();

    Boolean addCase(QuestionCaseVo questionCaseVo);

    Boolean updateCase(Long id, QuestionCaseVo questionCaseVo);

    Boolean deleteCase(Long id);

}
