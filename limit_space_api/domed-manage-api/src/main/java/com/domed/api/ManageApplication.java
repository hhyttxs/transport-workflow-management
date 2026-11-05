package com.domed.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName MarketManageApplication
 * @Description TODO
 * @Author 高杨
 * @Date 2018/5/21 21:30
 * @Version 1.0
 **/
@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.domed"})
@EnableJpaRepositories(basePackages = {"com.domed"})
@EntityScan(basePackages = {"com.domed"})
public class ManageApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ManageApplication.class).run(args);
    }
}
