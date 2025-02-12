package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.KnowledgeFileEntity;
import cn.edu.moe.smiling.datasource.model.ConvertMapperStruct;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.service.KnowledgeService;
import cn.edu.moe.smiling.datasource.util.ConvertUtil;
import cn.edu.moe.smiling.datasource.vo.KnowledgeFileVo;
import cn.edu.moe.smiling.datasource.vo.ResponseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanUtils;
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

    /**
     * 分页查询列表
     *
     */
    @ApiOperation("文件列表")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @GetMapping("/file/list")
    public IPage<KnowledgeFileVo> list(@RequestHeader(value = "X-User-ID", required = false) String uid,
                                           @RequestParam(defaultValue = "1") long page,
                                           @RequestParam(defaultValue = "10") long size) {
        //开始查询
        IPage<KnowledgeFileEntity> knowledgeFileEntityPage = knowledgeService.listFile(new Page<>(page, size), uid);
        return knowledgeFileEntityPage.convert(ConvertMapperStruct.INSTANCE::knowledgeFileEntityToVo);
    }


    @ApiOperation("文件删除")
    @DeleteMapping("/file/delete/{id}")
    public Boolean deleteHistory(@ApiParam(value = "文件id", required = true, example = "1") @PathVariable("id") Long id) {
        return knowledgeService.deleteFile(id);
    }


}
