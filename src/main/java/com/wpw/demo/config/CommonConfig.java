package com.wpw.demo.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @auther wupeiwen
 * @description
 * @date 2021/3/5 0005
 */
@Configuration
public class CommonConfig {
    /**
     * http
     */
    @Bean
    public RestTemplate restTemplate(OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        return new RestTemplate(factory);
    }

    /**
     * OKHttp请求
     */
    @Bean
    public OkHttpClient okHttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .build();
    }

    /**
     * 线程池的配置
     */
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setKeepAliveSeconds(300);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadNamePrefix("payment_");
        taskExecutor.setDaemon(false);
        return taskExecutor;
    }
}
