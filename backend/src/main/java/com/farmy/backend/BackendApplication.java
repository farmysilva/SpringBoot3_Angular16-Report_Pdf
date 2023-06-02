package com.farmy.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.farmy.backend.repository.CourseRepository;

import com.farmy.backend.model.Course;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDataBase(CourseRepository courseRepository){

		return args -> {
			courseRepository.deleteAll();

			Course c =  new Course();
			c.setName("Spring com Algular");
			c.setCategory("Frontend");
			
			courseRepository.save(c);
		};

	}

}
