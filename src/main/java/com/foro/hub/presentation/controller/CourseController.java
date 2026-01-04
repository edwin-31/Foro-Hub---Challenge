package com.foro.hub.presentation.controller;

import com.foro.hub.presentation.dto.course.CreateCourseDTO;
import com.foro.hub.presentation.dto.course.CourseDTO;
import com.foro.hub.service.interfaces.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAll(@PageableDefault(size = 10, sort = {"category"}) Pageable pageable) {
        Page<CourseDTO> courses = courseService.findAll(pageable);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable Long id) {
        Optional<CourseDTO> course = courseService.findById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        CourseDTO courseEntity = courseService.create(createCourseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id, @RequestBody @Valid CreateCourseDTO createCourseDTO) {
        CourseDTO updatedCourse = courseService.update(id, createCourseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
