package com.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication /* (exclude= {DataSourceAutoConfiguration.class}) */
public class GymApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);

	}

}
