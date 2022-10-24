package com.NorbertVarga.SpringBootDemoApp;

import com.NorbertVarga.SpringBootDemoApp.errorHandling.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static final Logger logger = LoggerFactory.getLogger(SpringBootDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
		logger.trace("** APPLICATION STARTUP **");
	}

}
