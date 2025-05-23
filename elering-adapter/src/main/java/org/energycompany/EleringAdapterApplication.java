package org.energycompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EleringAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EleringAdapterApplication.class, args);
    }
}