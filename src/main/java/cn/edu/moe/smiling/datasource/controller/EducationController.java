package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.DataFeedbackEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionCaseEntity;
import cn.edu.moe.smiling.datasource.entity.QuestionHistoryEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.QuestionService;
import cn.edu.moe.smiling.datasource.vo.FeedbackVo;
import cn.edu.moe.smiling.datasource.vo.QuestionAndAnswerVo;
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

    @ApiOperation("新增个人提问记录")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PostMapping("/add/history")
    public QuestionHistoryEntity addHistory(@RequestHeader("X-User-ID") Long uid,
                                            @RequestHeader("X-Forwarded-For") String ip,
                                            @ApiParam(value = "提问历史记录对象", required = true) @Validated @RequestBody QuestionAndAnswerVo questionAndAnswerVo) {
        return questionService.addHistory(uid, ip, questionAndAnswerVo);
    }

    @ApiOperation("个人提问记录列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/list/history")
    public List<QuestionHistoryVo> historyList(@RequestHeader("X-User-ID") Long uid, @ApiParam(value = "会话编号", required = true) @RequestParam("chat_no") String chatNo) {
        //开始查询
        return questionService.historyList(uid, chatNo);
    }

    @ApiOperation("问答缓存查询")
    @PostMapping("/chat")
    public Object chatData(@ApiParam(value = "问题", required = true) @RequestParam String q) {
        return questionService.chatData(q);
    }

    @ApiOperation("新增用户反馈")
    @PostMapping("/add/feedback")
    public DataFeedbackEntity addFeedback(@ApiParam(value = "问题", required = true) @Validated @RequestBody FeedbackVo feedbackVo) {
        return questionService.addDataFeedback(feedbackVo);
    }

    @ApiOperation("修改用户反馈")
    @PutMapping("/update/feedback/{id}")
    public DataFeedbackEntity updateFeedback(@ApiParam(value = "用户反馈id", required = true, example = "1") @PathVariable("id") Long id,
                                             @ApiParam(value = "用户反馈对象", required = true) @Validated @RequestBody FeedbackVo feedbackVo) {
        return questionService.updateDataFeedback(id, feedbackVo);
    }

    @ApiOperation("删除用户反馈")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @DeleteMapping("/delete/feedback/{id}")
    public Boolean deleteHistory(@ApiParam(value = "用户反馈id", required = true, example = "1") @PathVariable("id") Long id) {
        return questionService.deleteDataFeedback(id);
    }

}
