package com.example.redunm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RedunmApplication{
	public static void main(String[] args){
		SpringApplication.run(RedunmApplication.class,args);
	}
}