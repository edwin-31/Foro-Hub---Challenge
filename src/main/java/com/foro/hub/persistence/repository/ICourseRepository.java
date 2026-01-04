package com.foro.hub.persistence.repository;

import com.foro.hub.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
}
