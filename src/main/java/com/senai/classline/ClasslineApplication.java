package com.senai.classline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClasslineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClasslineApplication.class, args);
	}

}
