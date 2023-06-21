package com.pp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.pp.mapper")
public class DepositApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DepositApplication.class);
        springApplication.setAllowCircularReferences(Boolean.TRUE);
        springApplication.run(args);
    
    }
}
