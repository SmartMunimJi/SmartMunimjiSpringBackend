package com.smartmunimji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.smartmunimji")
public class SmartMunimjiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMunimjiApplication.class, args);
	}

}
