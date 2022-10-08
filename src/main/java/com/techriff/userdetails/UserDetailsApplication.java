package com.techriff.userdetails;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class UserDetailsApplication {

	//private static Logger logger = LogManager.getLogger(UserDetailsWithExceptionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserDetailsApplication.class, args);
//		logger.info("Info level log message");
//		logger.debug("Debug level log message");
//		logger.error("Error level log message");

	}

}
