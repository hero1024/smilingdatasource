package cn.edu.moe.smiling.datasource.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author songpeijiang
 * @since 2024/4/10
 */
@Slf4j
public class DBContextHolder {

    //对当前线程的操作-线程安全的
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //调用此方法，切换数据源
    public static void setDataSource(String dataSource){
        contextHolder.set(dataSource);
        log.info("已切换到数据源:{}",dataSource);
    }

    //获取数据源
    public static String getDataSource(){
        return contextHolder.get();
    }

    //删除数据源
    public static void clearDataSource(){
        contextHolder.remove();
        log.info("已切换到主数据源");
    }

}

