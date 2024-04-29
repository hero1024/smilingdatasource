package cn.edu.moe.smiling.datasource.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author songpeijiang
 * @since 2024/4/11
 */
@Data
@Accessors(chain = true)
public class IndexVo {

    /**
     * 索引名称
     */
    private String name;
    /**
     * 是否唯一索引
     */
    private boolean unique;
    /**
     * 索引列 逗号分隔
     */
    private String fields;

}
