package com.nttdata.btc.account.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NttdataBtcAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(NttdataBtcAccountApplication.class, args);
    }
}