package cn.edu.moe.smiling.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
//@Schema(description = "数据源信息")
@Data
@TableName("database_source")
public class DataBaseSourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String datasourceName;
    private String url;
    private String username;
    private String password;
    private String driverClass;
    private String databaseType;
    private String dbSource;

}
