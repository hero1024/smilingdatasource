package cn.edu.moe.smiling.datasource.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author songpeijiang
 * @since 2024/4/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableVo {
    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称", required = true, example = "student")
    @JsonView(RequestVo.class)
    private String name;
    /**
     * 注释
     */
    @ApiModelProperty(value = "表注释", required = true, example = "学生信息表")
    @JsonView(RequestVo.class)
    private String remarks;
    /**
     * 主键字段
     */
    private String primaryKey;

    private List<IndexVo> indexList;
    private List<ColumnVo> columnList;

    public TableVo(String name, String remarks) {
        this.name = name;
        this.remarks = remarks;
    }

}
