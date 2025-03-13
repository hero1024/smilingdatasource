package cn.edu.moe.smiling.datasource.dao;

import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionHistoryVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface QuestionHistoryDao extends IService<QuestionHistoryEntity> {
    IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage, String username, String chatNo, Date startTime, Date endTime);

    String queryAnswer(String q);

    List<QuestionHistoryVo> historyList(Long uid, String chatNo);

}
