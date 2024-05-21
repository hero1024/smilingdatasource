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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public IPage<QuestionVo> list(@RequestParam(defaultValue = "1") long page,
                                  @RequestParam(defaultValue = "20") long size,
                                  @RequestParam(required = false) String username,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(name = "start_time", required = false) Date startTime,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(name = "end_time", required = false) Date endTime) {
        //开始查询
        return questionService.list(new Page<>(page, size), username, startTime, endTime);
    }

    @ApiOperation("新增示例问题")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PostMapping("/add/case")
    public Boolean addCase(@ApiParam(value = "示例问题对象", required = true) @Validated @RequestBody QuestionCaseVo questionCaseVo) {
        return questionService.addCase(questionCaseVo);
    }

    @ApiOperation("修改示例问题")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PutMapping("/update/case/{id}")
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

    @ApiOperation("删除问题记录")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @DeleteMapping("/delete/history/{id}")
    public Boolean deleteHistory(@ApiParam(value = "提问历史记录id", required = true, example = "1") @PathVariable("id") Long id) {
        return questionService.deleteHistory(id);
    }

}
