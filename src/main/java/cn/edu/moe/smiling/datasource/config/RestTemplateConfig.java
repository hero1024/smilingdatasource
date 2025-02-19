package cn.edu.moe.smiling.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Value("${data.file.timeout}")
    private int timeout;

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setHttpClient(httpClientBuilder().build());
        //获取链接超时时间
        httpRequestFactory.setConnectionRequestTimeout(timeout);
        //指客户端和服务器建立连接的timeout
        httpRequestFactory.setConnectTimeout(timeout);
        //读取数据的超时时间
        httpRequestFactory.setReadTimeout(timeout);
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return super.hasError(statusCode);
            }

            @Override
            public void handleError(ClientHttpResponse response) {
                log.info("some error:{}", response);
            }
        });
        return restTemplate;
    }

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(500); // 每个主机的并发
        return poolingConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

}

