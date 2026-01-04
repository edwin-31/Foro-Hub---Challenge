package com.foro.hub.presentation.dto.course;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseDTO(
        @NotBlank String name,
        @NotBlank String category
) {
}
