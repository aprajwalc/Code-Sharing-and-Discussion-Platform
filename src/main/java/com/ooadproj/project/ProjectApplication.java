package com.ooadproj.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@ComponentScan(basePackages = "com.ooadproj.project")
@SpringBootApplication
public class ProjectApplication {
		public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}

