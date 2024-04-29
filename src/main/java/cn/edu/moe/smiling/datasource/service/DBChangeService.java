package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.entity.DataBaseSourceEntity;
import cn.edu.moe.smiling.datasource.vo.ColumnVo;
import cn.edu.moe.smiling.datasource.vo.DataBaseSourceVo;
import cn.edu.moe.smiling.datasource.vo.TableVo;

import java.util.List;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
public interface DBChangeService {

    List<DataBaseSourceEntity> listDbSource();

    boolean changeDb(DataBaseSourceEntity source);

    boolean addDbSource(DataBaseSourceVo dataBaseSource);

    boolean updateDbSource(Long id, DataBaseSourceVo dataBaseSource);

    boolean deleteDbSource(Long id);

    boolean testDbSource(DataBaseSourceVo dataBaseSource);

    List<String> getDatabases(Long id);

    List<TableVo> getTables(Long id, String catalog, String schema);

    boolean updateTableComment(Long id, String catalog, String schema, TableVo table);

    boolean updateColumnComment(Long id, String catalog, String schema, String table, ColumnVo column);

}
