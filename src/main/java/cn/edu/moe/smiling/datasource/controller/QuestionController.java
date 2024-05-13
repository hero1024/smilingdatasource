package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.vo.QuestionCaseVo;
import cn.edu.moe.smiling.datasource.vo.QuestionVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "问数后管")
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
    @ApiOperation("问题列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/list")
    public IPage<QuestionVo> list(@RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "20") long size) {
        //开始查询
        return questionService.list(new Page<>(page, size));
    }

    @ApiOperation("新增示例问题")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PutMapping("/add/case")
    public Boolean addCase(@ApiParam(value = "示例问题对象", required = true) @Validated @RequestBody QuestionCaseVo questionCaseVo) {
        return questionService.addCase(questionCaseVo);
    }

    @ApiOperation("修改示例问题")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PostMapping("/update/case/{id}")
    public Boolean updateCase(@ApiParam(value = "示例问题id", required = true, example = "1") @PathVariable("id") Long id,
                                  @ApiParam(value = "示例问题对象", required = true) @Validated @RequestBody QuestionCaseVo questionCaseVo) {
        return questionService.updateCase(id, questionCaseVo);
    }

    @ApiOperation("删除示例问题")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @DeleteMapping("/delete/case/{id}")
    public Boolean deleteCase(@ApiParam(value = "示例问题id", required = true, example = "1") @PathVariable("id") Long id) {
        return questionService.deleteCase(id);
    }

    @ApiOperation("所有提问历史记录后管分页列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/pages/history")
    public IPage<QuestionHistoryEntity> historyPages(@RequestParam(defaultValue = "1") long page, @RequestParam(defaultValue = "20") long size) {
        //开始查询
        return questionService.historyPages(new Page<>(page, size));
    }

}
