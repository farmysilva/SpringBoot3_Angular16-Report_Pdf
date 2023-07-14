package com.farmy.backend.course;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.farmy.backend.course.dto.CourseDTO;
import com.farmy.backend.course.dto.CoursePageDTO;
import com.farmy.backend.course.dto.CourseRequestDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import net.sf.jasperreports.engine.JRException;

/**
 * Represents the REST API for the Course resource.
 */
@Validated
@RestController
@RequestMapping("api/courses")
public class CourseController {

    
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public CoursePageDTO findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return courseService.findAll(page, pageSize);
    }

    @GetMapping("/searchByName")
    public List<CourseDTO> findByName(@RequestParam @NotNull @NotBlank String name) {
        return courseService.findByName(name);
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @Positive @NotNull Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid CourseRequestDTO course) {
        return courseService.create(course);
    }

    @PutMapping(value = "/{id}")
    public CourseDTO update(@PathVariable @Positive @NotNull Long id,
            @RequestBody @Valid CourseRequestDTO course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive @NotNull Long id) {
        courseService.delete(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(path = "/reportById/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public void courseReportById(@PathVariable @Positive @NotNull Long id, HttpServletResponse httpServletResponse) throws JRException, IOException {
        courseService.exportCourseReportById(id, httpServletResponse);        
    }
}