package com.files.SpringRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRegistryApplication.class, args);
	}

}
