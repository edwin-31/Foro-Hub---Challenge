package com.foro.hub.persistence.repository;

import com.foro.hub.persistence.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicRepository extends JpaRepository<TopicEntity, Long> {
}
