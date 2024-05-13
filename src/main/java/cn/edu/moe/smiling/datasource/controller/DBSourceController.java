package cn.edu.moe.smiling.datasource.controller;

import cn.edu.moe.smiling.datasource.entity.DataBaseSourceEntity;
import cn.edu.moe.smiling.datasource.model.ResultData;
import cn.edu.moe.smiling.datasource.model.ValidGroup;
import cn.edu.moe.smiling.datasource.service.DBChangeService;
import cn.edu.moe.smiling.datasource.vo.ColumnVo;
import cn.edu.moe.smiling.datasource.vo.DataBaseSourceVo;
import cn.edu.moe.smiling.datasource.vo.RequestVo;
import cn.edu.moe.smiling.datasource.vo.TableVo;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Api(tags = "数据库配置")
@RestController
@RequestMapping("/dbsource")
public class DBSourceController {

    @Autowired
    private DBChangeService dbChangeService;

    @ApiOperation(value = "查询数据库配置信息列表", response = ResultData.class)
    @GetMapping("/list")
    public List<DataBaseSourceEntity> listDbSource() { return dbChangeService.listDbSource(); }

    @ApiOperation("新增数据库配置信息")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PutMapping("/add")
    public Boolean addDbSource(@ApiParam(value = "数据库配置对象", required = true) @Validated(value = ValidGroup.Crud.Create.class) @RequestBody DataBaseSourceVo dataBaseSource) {
        return dbChangeService.addDbSource(dataBaseSource);
    }

    @ApiOperation("修改数据库配置信息")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PostMapping("/{id}/update")
    public Boolean updateDbSource(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id,
                                  @ApiParam(value = "数据库配置对象", required = true) @Validated @RequestBody DataBaseSourceVo dataBaseSource) {
        return dbChangeService.updateDbSource(id, dataBaseSource);
    }

    @ApiOperation("删除数据库配置信息")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @DeleteMapping("/{id}/delete")
    public Boolean deleteDbSource(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id) {
        return dbChangeService.deleteDbSource(id);
    }

    @ApiOperation("测试连通性")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    @PostMapping("/test")
    public Boolean testDbSource(@ApiParam(value = "数据库配置对象", required = true) @Validated(value = ValidGroup.Crud.Create.class) @RequestBody DataBaseSourceVo dataBaseSource) {
        return dbChangeService.testDbSource(dataBaseSource);
    }

    @GetMapping("/{id}/databases")
    @ApiOperation("获取数据库")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class, type = "array")))
    public List<String> getDatabases(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id)  {
        return dbChangeService.getDatabases(id);

    }

    @GetMapping("/{id}/tables/{catalog}/{schema}")
    @ApiOperation("获取表")
    public List<TableVo> getTables(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id,
                                   @PathVariable(value = "catalog", required = false) String catalog,
                                   @PathVariable(value = "schema", required = false) String schema)  {
        return dbChangeService.getTables(id, catalog, schema);
    }

    @PostMapping("/{id}/comment/table/update/{catalog}/{schema}")
    @ApiOperation("修改表注释")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    public Boolean updateTableComment(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id,
                                      @PathVariable(value = "catalog", required = false) String catalog,
                                      @PathVariable(value = "schema", required = false) String schema,
                                      @Valid @RequestBody @JsonView(RequestVo.class) TableVo table)  {
        return dbChangeService.updateTableComment(id, catalog, schema, table);
    }

    @PostMapping("/{id}/comment/{table}/column/update/{catalog}/{schema}")
    @ApiOperation("修改字段注释")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultData.class)))
    public Boolean updateColumnComment(@ApiParam(value = "数据库配置id", required = true, example = "1") @PathVariable("id") Long id,
                                       @ApiParam(value = "表名", required = true) @PathVariable(value = "table") String table,
                                       @PathVariable(value = "catalog", required = false) String catalog,
                                      @PathVariable(value = "schema", required = false) String schema,
                                             @Valid @RequestBody @JsonView(RequestVo.class) ColumnVo column)  {
        return dbChangeService.updateColumnComment(id, catalog, schema, table, column);
    }


}
