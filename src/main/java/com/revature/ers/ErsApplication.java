package com.revature.ers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ErsApplication {

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Autowired
	private static  final Logger logger = LoggerFactory.getLogger(ErsApplication.class);


	public static void main(String[] args) {

		SpringApplication.run(ErsApplication.class, args);
	}
}
