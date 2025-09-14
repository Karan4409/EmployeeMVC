package com.example.EmployeeMvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmployeeMvcApplication {

	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "rony@143";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println("BCrypt hash: " + encodedPassword);


		SpringApplication.run(EmployeeMvcApplication.class, args);
	}

}
