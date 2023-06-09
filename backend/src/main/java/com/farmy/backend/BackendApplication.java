package com.farmy.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.farmy.backend.repository.CourseRepository;
import com.farmy.backend.enums.Category;
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
			c.setCategory(Category.FRONT_END);
			
			courseRepository.save(c);

			Course c1 =  new Course();
			c1.setName("Spring");
			c1.setCategory(Category.BACK_END);
			
			courseRepository.save(c1);
			
			Course c2 =  new Course();
			c2.setName("React Native");
			c2.setCategory(Category.FRONT_END);
			
			courseRepository.save(c2);
			
			Course c3 =  new Course();
			c3.setName("Python");
			c3.setCategory(Category.BACK_END);
			
			courseRepository.save(c3);
		};
	}




	

}
