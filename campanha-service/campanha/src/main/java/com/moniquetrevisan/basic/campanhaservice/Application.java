package com.moniquetrevisan.basic.campanhaservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.moniquetrevisan.basic.campanhaservice.repository")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

}
