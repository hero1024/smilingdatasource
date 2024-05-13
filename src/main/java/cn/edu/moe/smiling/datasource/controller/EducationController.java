package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.vo.QuestionHistoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问数前台")
@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation("示例问题列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/list/case")
    public List<QuestionCaseEntity> caseList() {
        //开始查询
        return questionService.caseList();
    }

    @ApiOperation("新增个人提问历史记录")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PutMapping("/add/history")
    public Boolean addHistory(@RequestHeader("X-User-ID") Long uid,
                              @ApiParam(value = "提问历史记录对象", required = true) @Validated @RequestBody QuestionHistoryVo questionHistoryVo) {
        return questionService.addHistory(uid, questionHistoryVo);
    }

    @ApiOperation("个人提问历史记录列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/list/history")
    public List<QuestionHistoryEntity> historyList(@RequestHeader("X-User-ID") Long uid) {
        //开始查询
        return questionService.historyList(uid);
    }

    @ApiOperation("删除提问历史记录")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @DeleteMapping("/delete/history/{id}")
    public Boolean deleteHistory(@ApiParam(value = "提问历史记录id", required = true, example = "1") @PathVariable("id") Long id) {
        return questionService.deleteHistory(id);
    }

    @ApiOperation("问答缓存查询")
    @PostMapping("/chat")
    public Object chatData(@ApiParam(value = "问题", required = true) @RequestParam String q) {
        return questionService.chatData(q);
    }

}
