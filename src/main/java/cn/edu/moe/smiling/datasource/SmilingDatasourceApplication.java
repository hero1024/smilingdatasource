package cn.edu.moe.smiling.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SmilingDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmilingDatasourceApplication.class, args);
    }

}
