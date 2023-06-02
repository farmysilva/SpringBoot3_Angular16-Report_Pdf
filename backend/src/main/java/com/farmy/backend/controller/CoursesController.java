package com.farmy.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmy.backend.repository.CourseRepository;

import lombok.AllArgsConstructor;
import com.farmy.backend.model.Course;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CoursesController {

    @Autowired
    private final CourseRepository courseRepository;

    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }
    
}
