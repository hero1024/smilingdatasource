package cn.edu.moe.smiling.datasource.service.impl;

import cn.edu.moe.smiling.datasource.config.DBContextHolder;
import cn.edu.moe.smiling.datasource.config.DynamicDataSource;
import cn.edu.moe.smiling.datasource.dao.DataBaseSourceDao;
import cn.edu.moe.smiling.datasource.entity.DataBaseSourceEntity;
import cn.edu.moe.smiling.datasource.mapper.DbCommentMapper;
import cn.edu.moe.smiling.datasource.service.DBChangeService;
import cn.edu.moe.smiling.datasource.util.ConvertUtil;
import cn.edu.moe.smiling.datasource.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Slf4j
@Service
public class DBChangeServiceImpl implements DBChangeService {

    private DataBaseSourceDao dataBaseSourceDao;

    private DynamicDataSource dynamicDataSource;

    private DbCommentMapper dbCommentMapper;

    @Autowired
    public DBChangeServiceImpl(DataBaseSourceDao dataBaseSourceDao,
                               DynamicDataSource dynamicDataSource,
                               DbCommentMapper dbCommentMapper){

        this.dataBaseSourceDao = dataBaseSourceDao;
        this.dynamicDataSource = dynamicDataSource;
        this.dbCommentMapper = dbCommentMapper;
    }

    @Override
    public boolean changeDb(DataBaseSourceEntity source) {
        if(source == null){
            return false;
        }
        try {
            //默认切换到主数据源，进行整体资源的查找
            DBContextHolder.clearDataSource();
            System.out.println("需要使用的数据源已经找到，datasourceName是："+source.getDatasourceName());
            //创建数据源连接&检查，若存在则不需要重新创建
            dynamicDataSource.createDataSourceWithCheck(source);
            //切换到该数据源
            DBContextHolder.setDataSource(source.getDatasourceName());
            return true;
        } catch (Exception e) {
            log.error("changeDb error", e);
            DBContextHolder.clearDataSource();
            return false;
        }

    }

    @Override
    public List<DataBaseSourceEntity> listDbSource() {
        //切换到主数据源
        DBContextHolder.clearDataSource();
        return dataBaseSourceDao.list();
    }

    @Override
    public boolean addDbSource(DataBaseSourceVo dataBaseSource) {
        return dataBaseSourceDao.save(ConvertUtil.entityToVo(dataBaseSource, DataBaseSourceEntity.class));
    }

    @Override
    public boolean updateDbSource(Long id, DataBaseSourceVo dataBaseSource) {
        DataBaseSourceEntity dataBaseSourceEntity = ConvertUtil.entityToVo(dataBaseSource, DataBaseSourceEntity.class);
        dataBaseSourceEntity.setId(id);
        return dataBaseSourceDao.updateById(dataBaseSourceEntity);
    }

    @Override
    public boolean deleteDbSource(Long id) {
        return dataBaseSourceDao.removeById(id);
    }

    @Override
    public boolean testDbSource(DataBaseSourceVo dataBaseSource) {
        return dynamicDataSource.testDatasource(dataBaseSource.getDatasourceName(), dataBaseSource.getDriverClass(), dataBaseSource.getUrl(), dataBaseSource.getUsername(), dataBaseSource.getPassword());
    }

    @Override
    public List<String> getDatabases(Long id) {
        List<String> list = new ArrayList<>();
        DataBaseSourceEntity source = dataBaseSourceDao.getById(id);
        if(!changeDb(source)){
            return list;
        }
        try (Connection connection = dynamicDataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData == null) {
                return list;
            }
            ResultSet rs = metaData.getCatalogs();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            if (list.isEmpty()) {
                rs = metaData.getSchemas();
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            log.error("getDatabases error", e);
            DBContextHolder.clearDataSource();
            list.clear();
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(Long id, String catalog, String schema) {
        List<TableVo> list = new ArrayList<>();
        DataBaseSourceEntity source = dataBaseSourceDao.getById(id);
        if(!changeDb(source)){
            return list;
        }
        try (Connection connection = dynamicDataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData == null) {
                return list;
            }
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(catalog, schema, "%", types);
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                String tableRemarks = tables.getString("REMARKS");
                TableVo table = new TableVo(tableName, tableRemarks);
                ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);
                while (primaryKeys.next()) {
                    String primaryKey = primaryKeys.getString("COLUMN_NAME");
                    table.setPrimaryKey(primaryKey);
                }
                if (log.isInfoEnabled()) {
                    String a = String.format("tableName: %-30s primaryKey: %-30s remarks: %-1s", table.getName(), table.getPrimaryKey(), table.getRemarks());
                    log.info(a);
                    log.info("======================================================================================================================");
                    String b = String.format("%-30s | %-12s | %-1s", "index_name", "unique", "columns");
                    log.info(b);
                    log.info("----------------------------------------------------------------------------------------------------------------------");
                }
                // 获取索引
                table.setIndexList(getIndex(metaData, table, catalog, schema));
                if (log.isInfoEnabled()) {
                    log.info("======================================================================================================================");
                    String c = String.format("%-30s | %-12s | %-6s | %-6s | %-8s | %-13s | %-18s | %-1s", "name", "type", "size", "digits", "nullable", "autoIncrement", "defaultValue", "remarks");
                    log.info(c);
                    log.info("----------------------------------------------------------------------------------------------------------------------");
                }
                // 获取列
                table.setColumnList(getColumn(metaData, table, catalog, schema));
                log.info("#################################################################################################################################");
                list.add(table);
            }
            return list;
        } catch (Exception e) {
            log.error("getTables error", e);
            DBContextHolder.clearDataSource();
            list.clear();
            return list;
        }
    }

    private static List<IndexVo> getIndex(DatabaseMetaData metaData, TableVo table,String catalog, String schema) {
        String tableName = table.getName();
        List<IndexVo> list = new ArrayList<>();
        if (metaData == null) {
            return list;
        }
        Map<String, List<String>> indexMap = new HashMap<>(16);
        Map<String, IndexVo> map = new HashMap<>(16);
        try {
            ResultSet indexInfo = metaData.getIndexInfo(catalog, schema, tableName, false, false);
            while (indexInfo.next()) {
                String indexName = indexInfo.getString("INDEX_NAME");
                String columnName = indexInfo.getString("COLUMN_NAME");
                boolean unique = !indexInfo.getBoolean("NON_UNIQUE");
                short ordinalPosition = indexInfo.getShort("ORDINAL_POSITION");
                if (!map.containsKey(indexName)) {
                    map.put(indexName, new IndexVo().setName(indexName).setUnique(unique));
                }
                // 暂存索引列
                List<String> indexList = indexMap.computeIfAbsent(indexName, k -> new ArrayList<>());
                indexList.add(ordinalPosition - 1, columnName);
            }

            map.forEach((indexName, index) -> {
                List<String> indexList = indexMap.get(indexName);
                index = index.setFields(StringUtils.join(indexList, ","));
                if (log.isInfoEnabled()) {
                    String a = String.format("%-30s | %-12s | %-1s", index.getName(), index.isUnique(), index.getFields());
                    log.info(a);
                }
                list.add(index);
            });
            return list;
        } catch (Exception e) {
            log.error("getIndex error", e);
            return list;
        }
    }

    private static List<ColumnVo> getColumn(DatabaseMetaData metaData, TableVo table, String catalog, String schema) {
        String tableName = table.getName();
        List<ColumnVo> list = new ArrayList<>();
        if (metaData == null) {
            return list;
        }
        try {
            ResultSet columns = metaData.getColumns(catalog, schema, tableName, "%");
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                int dataSize = columns.getInt("COLUMN_SIZE");
                int digits = columns.getInt("DECIMAL_DIGITS");
                boolean nullable = columns.getBoolean("NULLABLE");
                String autoIncrement = columns.getString("IS_AUTOINCREMENT");
                String defaultValue = columns.getString("COLUMN_DEF");
                if (defaultValue == null) {
                    defaultValue = "";
                }
                String remarks = columns.getString("REMARKS");
                if (log.isInfoEnabled()) {
                    String a = String.format("%-30s | %-12s | %-6s | %-6s | %-8s | %-13s | %-18s | %-1s", columnName, columnType, dataSize, digits, nullable, autoIncrement, defaultValue, remarks);
                    log.info(a);
                }
                list.add(new ColumnVo(columnName, columnType, dataSize, digits, nullable, autoIncrement, defaultValue, remarks));
            }
            return list;
        } catch (Exception e) {
            log.error("getColumn error", e);
            return list;
        }
    }

    @Override
    public boolean updateTableComment(Long id, String catalog, String schema, TableVo table) {
        DataBaseSourceEntity source = dataBaseSourceDao.getById(id);
        if(!changeDb(source)){
            return false;
        }
        try (Connection connection = dynamicDataSource.getConnection()) {
            connection.setCatalog(catalog);
            dbCommentMapper.updateTableComment(schema, table.getName(), table.getRemarks(), source.getDatabaseType().toLowerCase());
            return true;
        } catch (Exception e) {
            log.error("getTables error", e);
            DBContextHolder.clearDataSource();
            return false;
        }
    }

    @Override
    public boolean updateColumnComment(Long id, String catalog, String schema, String table, ColumnVo column) {
        DataBaseSourceEntity source = dataBaseSourceDao.getById(id);
        if(!changeDb(source)){
            return false;
        }
        try (Connection connection = dynamicDataSource.getConnection()) {
            connection.setCatalog(catalog);
            dbCommentMapper.updateColumnComment(schema, table, column.getName(), column.getType(), column.getSize(), column.getRemarks(), source.getDatabaseType().toLowerCase());
            return true;
        } catch (Exception e) {
            log.error("getTables error", e);
            DBContextHolder.clearDataSource();
            return false;
        }
    }


}
