package org.springframework.security.samples.springSecurityWithBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringSecurityWithBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityWithBootApplication.class, args);
	}
}
