package com.farmy.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.farmy.backend.course.Course;
import com.farmy.backend.course.CourseRepository;
import com.farmy.backend.course.Lesson;
import com.farmy.backend.course.enums.Category;

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

			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("P6LbBmahnHM");
			l.setCourse(c);
			c.getLessons().add(l);
			
			courseRepository.save(c);

			Course c1 =  new Course();
			c1.setName("Spring");
			c1.setCategory(Category.BACK_END);

			Lesson l1 = new Lesson();
			l1.setName("Visão geral");
			l1.setYoutubeUrl("EGaj9Stz8Hc");
			l1.setCourse(c1);
			c1.getLessons().add(l1);
			
			courseRepository.save(c1);
			
			Course c2 =  new Course();
			c2.setName("React Native");
			c2.setCategory(Category.FRONT_END);

			Lesson l2 = new Lesson();
			l2.setName("Visão geral");
			l2.setYoutubeUrl("JQ8ehR5tGa4");
			l2.setCourse(c2);
			c2.getLessons().add(l2);

			Lesson l3 = new Lesson();
			l3.setName("REACT em todo lugar");
			l3.setYoutubeUrl("OdmVZ_AjnWU");
			l3.setCourse(c2);
			c2.getLessons().add(l3);
			
			courseRepository.save(c2);
			
			Course c3 =  new Course();
			c3.setName("Python");
			c3.setCategory(Category.BACK_END);

			Lesson l4 = new Lesson();
			l4.setName("Divertido e Arrojado");
			l4.setYoutubeUrl("fKH_hZ4FVPg");
			l4.setCourse(c3);
			c3.getLessons().add(l4);
			
			courseRepository.save(c3);
		};
	}




	

}
