package com.skillstorm.spyglassapi;

import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import com.skillstorm.spyglassapi.models.dbSet.Role;
import com.skillstorm.spyglassapi.services.interfaces.AuthService;
import com.skillstorm.spyglassapi.services.interfaces.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpyglassApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpyglassApiApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
