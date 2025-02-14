package cn.edu.moe.smiling.datasource.mapper;

import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionHistoryVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionHistoryMapper extends BaseMapper<QuestionHistoryEntity> {
    IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage, String username, Date startTime, Date endTime);

    String queryAnswer(String q, Integer hour);

    List<QuestionHistoryVo> historyList(Long uid, String chatNo);

}
