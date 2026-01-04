package com.foro.hub.service.impl;

import com.foro.hub.persistence.entity.CourseEntity;
import com.foro.hub.persistence.entity.TopicEntity;
import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.persistence.repository.ICourseRepository;
import com.foro.hub.persistence.repository.ITopicRepository;
import com.foro.hub.persistence.repository.IUserRepository;
import com.foro.hub.presentation.dto.reply.ReplyDTO;
import com.foro.hub.presentation.dto.topic.CreateTopicDTO;
import com.foro.hub.presentation.dto.topic.TopicDTO;
import com.foro.hub.presentation.dto.topic.TopicResponseDTO;
import com.foro.hub.service.interfaces.ITopicService;
import com.foro.hub.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService implements ITopicService {

    private final ITopicRepository topicRepository;
    private final IUserRepository userRepository;
    private final ICourseRepository courseRepository;

    @Autowired
    public TopicService(ITopicRepository topicRepository, IUserRepository userRepository, ICourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<TopicDTO> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable)
                .map(topic -> new TopicDTO(
                        topic.getId(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreationDate(),
                        topic.getStatus(),
                        topic.getAuthor().getId(),
                        topic.getCourseEntity().getId()
                ));
    }

    @Override
    public Optional<TopicResponseDTO> findById(Long id) {
        return topicRepository.findById(id)
                .map(topic -> new TopicResponseDTO(
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreationDate(),
                        topic.getStatus(),
                        topic.getAuthor().getName(),
                        topic.getCourseEntity().getName()
                ));
    }

    @Override
    public TopicDTO create(CreateTopicDTO createTopicDTO) {
        UserEntity author = userRepository.findById(createTopicDTO.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(createTopicDTO.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTitle(createTopicDTO.title());
        topicEntity.setMessage(createTopicDTO.message());
        topicEntity.setAuthor(author);
        topicEntity.setCourseEntity(courseEntity);

        var topic = topicRepository.save(topicEntity);

        return new TopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthor().getId(),
                topic.getCourseEntity().getId()
        );
    }

    @Override
    public TopicDTO update(Long id, CreateTopicDTO createTopicDTO) {
        TopicEntity topicEntity = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        UserEntity author = userRepository.findById(createTopicDTO.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CourseEntity courseEntity = courseRepository.findById(createTopicDTO.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        topicEntity.setTitle(createTopicDTO.title());
        topicEntity.setMessage(createTopicDTO.message());
        topicEntity.setAuthor(author);
        topicEntity.setCourseEntity(courseEntity);

        topicRepository.save(topicEntity);

        return new TopicDTO(
                topicEntity.getId(),
                topicEntity.getTitle(),
                topicEntity.getMessage(),
                topicEntity.getCreationDate(),
                topicEntity.getStatus(),
                topicEntity.getAuthor().getId(),
                topicEntity.getCourseEntity().getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        topicRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Topic not found with this id"));

        topicRepository.deleteById(id);
    }
}
