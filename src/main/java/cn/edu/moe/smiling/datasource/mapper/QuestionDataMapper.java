package cn.edu.moe.smiling.datasource.mapper;

import cn.edu.moe.smiling.datasource.entity.QuestionDataEntity;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionDataMapper extends BaseMapper<QuestionDataEntity> {
    IPage<QuestionVo> questionPage(Page<QuestionVo> questionVoPage);
}
