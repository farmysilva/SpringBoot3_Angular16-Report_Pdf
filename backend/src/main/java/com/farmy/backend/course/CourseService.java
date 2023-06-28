package com.farmy.backend.course;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.farmy.backend.course.dto.CourseDTO;
import com.farmy.backend.course.dto.CoursePageDTO;
import com.farmy.backend.course.dto.CourseRequestDTO;
import com.farmy.backend.course.dto.mapper.CourseMapper;
import com.farmy.backend.course.enums.Status;
import com.farmy.backend.exception.BusinessException;
import com.farmy.backend.exception.RecordNotFoundException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
@Validated
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(@PositiveOrZero int page, @Positive @Max(1000) int pageSize) {
        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> list = coursePage.getContent().stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
        return new CoursePageDTO(list, coursePage.getTotalElements(), coursePage.getTotalPages());
    }

    public List<CourseDTO> findByName(@NotNull @NotBlank String name) {
        return courseRepository.findByName(name).stream().map(courseMapper::toDTO).collect(Collectors.toList());
    }

    public CourseDTO findById(@Positive @NotNull Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid CourseRequestDTO courseRequestDTO) {
        courseRepository.findByName(courseRequestDTO.name()).stream()
                .filter(c -> c.getStatus().equals(Status.ACTIVE))
                .findAny().ifPresent(c -> {
                    throw new BusinessException("A course with name " + courseRequestDTO.name() + " already exists.");
                });
        Course course = courseMapper.toModel(courseRequestDTO);
        course.setStatus(Status.ACTIVE);
        return courseMapper.toDTO(courseRepository.save(course));
    }

    public CourseDTO update(@Positive @NotNull Long id, @Valid CourseRequestDTO courseRequestDTO) {
        return courseRepository.findById(id).map(actual -> {
            actual.setName(courseRequestDTO.name());
            actual.setCategory(courseMapper.convertCategoryValue(courseRequestDTO.category()));
            return courseMapper.toDTO(courseRepository.save(actual));
        })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@Positive @NotNull Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }

    public Document exportCourseReportById(@Positive @NotNull Long id, HttpServletResponse httpServletResponse) throws DocumentException, IOException {        
        
        GenerateCoursePdfReport generateCoursePdfReport = new GenerateCoursePdfReport();
        return generateCoursePdfReport.generatePdf(httpServletResponse, this.findById(id));
    }
}
