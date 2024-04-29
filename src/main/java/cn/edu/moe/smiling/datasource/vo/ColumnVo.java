package cn.edu.moe.smiling.datasource.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author songpeijiang
 * @since 2024/4/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnVo {

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名", required = true, example = "name")
    @JsonView(RequestVo.class)
    private String name;
    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型", required = true, example = "VARCHAR")
    @JsonView(RequestVo.class)
    private String type;
    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度", required = true, example = "255")
    @JsonView(RequestVo.class)
    private int size;
    /**
     * 小数
     */
    private int digits;
    /**
     * 可否为空
     */
    private boolean nullable;
    /**
     * 是否自增
     */
    private String autoIncrement;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 备注
     */
    @ApiModelProperty(value = "字段备注", required = true, example = "姓名")
    @JsonView(RequestVo.class)
    private String remarks;

}
