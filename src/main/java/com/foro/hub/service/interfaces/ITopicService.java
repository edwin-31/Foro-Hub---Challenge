package com.foro.hub.service.interfaces;

import com.foro.hub.persistence.entity.TopicEntity;
import com.foro.hub.presentation.dto.topic.CreateTopicDTO;
import com.foro.hub.presentation.dto.topic.TopicDTO;
import com.foro.hub.presentation.dto.topic.TopicResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ITopicService {
    Page<TopicDTO> findAll(Pageable pageable);
    Optional<TopicResponseDTO> findById(Long id);
    TopicDTO create(CreateTopicDTO createTopicDTO);
    TopicDTO update(Long id, CreateTopicDTO createTopicDTO);
    void deleteById(Long id);
}
