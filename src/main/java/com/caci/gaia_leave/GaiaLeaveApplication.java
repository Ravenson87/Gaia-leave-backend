package com.caci.gaia_leave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GaiaLeaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaiaLeaveApplication.class, args);
	}

}
