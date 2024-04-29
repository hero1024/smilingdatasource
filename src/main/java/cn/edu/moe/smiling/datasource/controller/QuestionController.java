package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "问数问题")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 分页查询列表
     * @param
     * @return
     */
    @GetMapping("/list")
    public IPage<QuestionVo> list(@RequestParam long page, long size) {
        //开始查询
        return questionService.list(new Page<>(page, size));
    }

}
