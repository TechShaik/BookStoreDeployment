package com.Files.UserEntity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.Files", "Mapper", "Jms","com.Files.UserEntity"})
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class UserApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
