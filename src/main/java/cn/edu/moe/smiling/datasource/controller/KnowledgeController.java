package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.ConvertMapperStruct;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.KnowledgeService;
import cn.edu.moe.smiling.datasource.vo.KnowledgeFileVo;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.util.Date;

/**
 * @author songpeijiang
 */
@Api(tags = "知识库")
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 文件上传
     */
    @ApiOperation("文件上传")
    @PostMapping("/file/upload")
    public ResultData<String> uploadFile(@RequestHeader("X-User-ID") String uid,
                                         @ApiParam(value = "知识库文件", required = true) @RequestPart("file") MultipartFile file) {
        return knowledgeService.uploadFile(file, uid);
    }

    /**
     * 分页查询列表
     */
    @ApiOperation("文件列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/file/list")
    public IPage<KnowledgeFileVo> list(@RequestHeader(value = "X-User-ID") String uid,
                                       @RequestParam(defaultValue = "1") long page,
                                       @RequestParam(defaultValue = "10") long size,
                                       @ApiParam("文件名称") @RequestParam(required = false) String name,
                                       @ApiParam(value = "文件类型", allowableValues = "txt,docx,pdf,csv") @RequestParam(required = false) String type,
                                       @ApiParam("开始更新时间") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "start_time", required = false) Date startTime,
                                       @ApiParam("结束更新时间") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "end_time", required = false) Date endTime) {
        //开始查询
        IPage<KnowledgeFileEntity> knowledgeFileEntityPage = knowledgeService.listFile(new Page<>(page, size), uid, name, type, startTime, endTime);
        return knowledgeFileEntityPage.convert(ConvertMapperStruct.INSTANCE::knowledgeFileEntityToVo);
    }


    @ApiOperation("文件删除")
    @DeleteMapping("/file/delete/{id}")
    public JSONObject deleteFile(@ApiParam(value = "文件id", required = true, example = "1") @PathVariable("id") Long id) throws ValidationException {
        return knowledgeService.deleteFile(id);
    }


}
