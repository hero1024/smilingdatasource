package cn.edu.moe.smiling.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author songpeijiang
 * @since 2024/4/12
 */
@Mapper
public interface DbCommentMapper {
    int updateTableComment(@Param("schema") String schema, @Param("tableName") String tableName, @Param("comment") String comment, @Param("dbType") String dbType);
    int updateColumnComment(@Param("schema") String schema, @Param("tableName") String tableName, @Param("columnName") String columnName, @Param("columnType") String columnType, @Param("columnSize") Integer columnSize, @Param("comment") String comment, @Param("dbType") String dbType);

}
