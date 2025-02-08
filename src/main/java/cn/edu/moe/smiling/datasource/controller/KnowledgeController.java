package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


}
