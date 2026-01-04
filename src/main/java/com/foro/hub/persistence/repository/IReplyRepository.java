package com.foro.hub.persistence.repository;

import com.foro.hub.persistence.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findByTopicId(Long topicId);
}
