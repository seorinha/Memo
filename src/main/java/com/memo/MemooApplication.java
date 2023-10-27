package com.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class MemooApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemooApplication.class, args);
	}

}
