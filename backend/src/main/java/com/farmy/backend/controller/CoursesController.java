package com.farmy.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.farmy.backend.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.farmy.backend.model.Course;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CoursesController {
   
    
    private final CourseService courseService;

    public CoursesController(        
        CourseService courseService
    ) {        
        this.courseService = courseService;
    }

    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public Course findbyId(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course){       
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")    
    public ResponseEntity<Course> delete(@PathVariable @NotNull @Positive Long id){
        Course deleted = courseService.findById(id);
        if (courseService.delete(id)) {
            return ResponseEntity.ok().body(deleted);
        }
        return ResponseEntity.notFound().build();     
    }
}
