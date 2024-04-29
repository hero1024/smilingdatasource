package cn.edu.moe.smiling.datasource.vo;

import cn.edu.moe.smiling.datasource.model.EnumString;
import cn.edu.moe.smiling.datasource.model.ValidGroup;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class DataBaseSourceVo {
    @ApiModelProperty(value = "连接名", required = true, example = "mysql01")
    @NotBlank(groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class}, message = "datasourceName为必填项")
    private String datasourceName;
    @ApiModelProperty(value = "连接url", required = true, example = "jdbc:mysql://localhost:3306/netty?characterEncoding=utf-8&useSSl=false&serverTimezone=GMT%2B8&autoReconnect=true&useSSL=false")
    @NotBlank(message = "url为必填项")
    private String url;
    @ApiModelProperty(value = "用户名", required = true, example = "root")
    @NotBlank(message = "username为必填项")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "root")
    @NotBlank(message = "password为必填项")
    private String password;
    @ApiModelProperty(value = "驱动类", required = true, example = "com.mysql.jdbc.Driver")
    @NotBlank(message = "driverClass为必填项")
    private String driverClass;
    @ApiModelProperty(value = "类别", required = true, example = "mysql")
    @EnumString(value = {"mysql","postgresql","oracle"}, message="类别只允许为mysql、postgresql、oracle")
    private String databaseType;
}
