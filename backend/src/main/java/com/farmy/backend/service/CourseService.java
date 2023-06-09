package com.farmy.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.farmy.backend.dto.CourseDTO;
import com.farmy.backend.dto.mapper.CourseMapper;
import com.farmy.backend.exception.RecordNotFoundException;
import com.farmy.backend.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid  @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid  @NotNull CourseDTO course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.name());
                    recordFound.setCategory(courseMapper.convertCategory(course.category()));
                    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public boolean delete(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }
}
