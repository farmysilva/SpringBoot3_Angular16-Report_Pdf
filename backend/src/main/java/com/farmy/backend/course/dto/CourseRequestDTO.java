package com.farmy.backend.course.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.farmy.backend.course.enums.Category;
import com.farmy.backend.shared.Validation.ValueOfEnum;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Used as request object that represents a Course.
 */
public record CourseRequestDTO(
        @NotBlank @NotNull @Length(min = 5, max = 200) String name,
        @NotBlank @NotNull @ValueOfEnum(enumClass = Category.class) String category,
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons) {
}
