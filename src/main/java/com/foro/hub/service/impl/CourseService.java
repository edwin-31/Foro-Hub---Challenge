package com.foro.hub.service.impl;

import com.foro.hub.persistence.entity.CourseEntity;
import com.foro.hub.persistence.repository.ICourseRepository;
import com.foro.hub.presentation.dto.course.CreateCourseDTO;
import com.foro.hub.presentation.dto.course.CourseDTO;
import com.foro.hub.presentation.dto.reply.ReplyDTO;
import com.foro.hub.service.interfaces.ICourseService;
import com.foro.hub.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    private final ICourseRepository courseRepository;

    @Autowired
    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<CourseDTO> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(course -> new CourseDTO(course.getId(), course.getName(), course.getCategory()));
    }

    @Override
    public Optional<CourseDTO> findById(Long id) {
        return courseRepository.findById(id)
                .map(course -> new CourseDTO(course.getId(), course.getName(), course.getCategory()));
    }

    @Override
    public CourseDTO create(CreateCourseDTO createCourseDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(createCourseDTO.name());
        courseEntity.setCategory(createCourseDTO.category());

        var course = courseRepository.save(courseEntity);
        return new CourseDTO(course.getId(), course.getName(), course.getCategory());
    }

    @Override
    public CourseDTO update(Long id, CreateCourseDTO createCourseDTO) {
        CourseEntity courseEntity = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));

        courseEntity.setName(createCourseDTO.name());
        courseEntity.setCategory(createCourseDTO.category());

        courseRepository.save(courseEntity);

        return new CourseDTO(courseEntity.getId(), courseEntity.getName(), courseEntity.getCategory());
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Course not found with this id"));

        courseRepository.deleteById(id);
    }
}
