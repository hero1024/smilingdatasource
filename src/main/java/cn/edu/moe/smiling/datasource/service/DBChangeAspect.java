package cn.edu.moe.smiling.datasource.service;

import cn.edu.moe.smiling.datasource.config.DBContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author songpeijiang
 * @since 2024/4/12
 */
@Slf4j
@Aspect
@Component
public class DBChangeAspect {

    // 定义一个切点，匹配所有方法执行
    @Pointcut("execution(* cn.edu.moe.smiling.datasource.service..*.*(..))")
    public void serviceMethods() {
    }

    // 在方法执行前执行的操作
    @Before("serviceMethods()")
    public void beforeMethod(JoinPoint joinPoint) {
        log.info("Before method: " + joinPoint.getSignature());
        //切换到主数据源
        DBContextHolder.clearDataSource();
    }

    // 在方法执行后执行的操作
    @After("serviceMethods()")
    public void afterMethod(JoinPoint joinPoint) {
        log.info("After method: " + joinPoint.getSignature());
        //切换到主数据源
        DBContextHolder.clearDataSource();
    }
}

