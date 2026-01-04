package com.foro.hub.service.interfaces;

import com.foro.hub.persistence.entity.CourseEntity;
import com.foro.hub.presentation.dto.course.CreateCourseDTO;
import com.foro.hub.presentation.dto.course.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICourseService {
    Page<CourseDTO> findAll(Pageable pageable);
    Optional<CourseDTO> findById(Long id);
    CourseDTO create(CreateCourseDTO createCourseDTO);
    CourseDTO update(Long id, CreateCourseDTO createCourseDTO);
    void deleteById(Long id);
}
