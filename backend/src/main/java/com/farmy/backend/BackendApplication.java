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
			c.setName("Algular");
			c.setCategory("Frontend");
			
			courseRepository.save(c);

			Course c1 =  new Course();
			c1.setName("Spring");
			c1.setCategory("Backend");
			
			courseRepository.save(c1);
			
			Course c2 =  new Course();
			c2.setName("React Native");
			c2.setCategory("Frontend");
			
			courseRepository.save(c2);
			
			Course c3 =  new Course();
			c3.setName("Python");
			c3.setCategory("Backend");
			
			courseRepository.save(c3);
		};
	}




	

}
