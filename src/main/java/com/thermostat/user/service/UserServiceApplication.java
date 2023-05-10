package com.thermostat.user.service;

import com.thermostat.user.service.model.AppUser;
import com.thermostat.user.service.model.Thermostat;
import com.thermostat.user.service.model.UserRole;
import com.thermostat.user.service.service.ThermostatService;
import com.thermostat.user.service.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class UserServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


@Bean
	CommandLineRunner run(UserService userService, ThermostatService thermostatService){
		return args -> {
			userService.save(new AppUser(null,"sandro","smesh","123", UserRole.USER, null));
		};
	}

}
