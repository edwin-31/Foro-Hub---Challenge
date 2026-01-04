package com.foro.hub.service.impl;

import com.foro.hub.persistence.entity.ReplyEntity;
import com.foro.hub.persistence.entity.TopicEntity;
import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.persistence.repository.IReplyRepository;
import com.foro.hub.persistence.repository.ITopicRepository;
import com.foro.hub.persistence.repository.IUserRepository;
import com.foro.hub.presentation.dto.reply.CreateReplyDTO;
import com.foro.hub.presentation.dto.reply.ReplyDTO;
import com.foro.hub.service.interfaces.IReplyService;
import com.foro.hub.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyService implements IReplyService {

    private final IReplyRepository replyRepository;
    private final IUserRepository userRepository;
    private final ITopicRepository topicRepository;

    @Autowired
    public ReplyService(IReplyRepository replyRepository, IUserRepository userRepository, ITopicRepository topicRepository) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }


    @Override
    public List<ReplyDTO> findByTopicId(Long topicId) {
        return replyRepository.findByTopicId(topicId).stream()
                .map(reply -> new ReplyDTO(
                        reply.getId(),
                        reply.getMessage(),
                        reply.getCreationDate(),
                        reply.getAuthor().getId(),
                        reply.getTopic().getId(),
                        reply.getSolution()
                )).collect(Collectors.toList());
    }

    @Override
    public Optional<ReplyDTO> findById(Long id) {
        return replyRepository.findById(id)
                .map(reply -> new ReplyDTO(
                        reply.getId(),
                        reply.getMessage(),
                        reply.getCreationDate(),
                        reply.getAuthor().getId(),
                        reply.getTopic().getId(),
                        reply.getSolution()
                ));
    }

    @Override
    public ReplyDTO create(CreateReplyDTO createReplyDTO) {
        UserEntity author = userRepository.findById(createReplyDTO.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        TopicEntity topicEntity = topicRepository.findById(createReplyDTO.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setMessage(createReplyDTO.message());
        replyEntity.setAuthor(author);
        replyEntity.setTopic(topicEntity);

        var reply = replyRepository.save(replyEntity);

        return new ReplyDTO(
                reply.getId(),
                reply.getMessage(),
                reply.getCreationDate(),
                reply.getAuthor().getId(),
                reply.getTopic().getId(),
                reply.getSolution()
        );
    }

    @Override
    public ReplyDTO update(Long id, CreateReplyDTO createReplyDTO) {
        ReplyEntity replyEntity = replyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reply not found"));

        UserEntity author = userRepository.findById(createReplyDTO.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        TopicEntity topicEntity = topicRepository.findById(createReplyDTO.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        replyEntity.setMessage(createReplyDTO.message());
        replyEntity.setAuthor(author);
        replyEntity.setTopic(topicEntity);

        replyRepository.save(replyEntity);

        return new ReplyDTO(
                replyEntity.getId(),
                replyEntity.getMessage(),
                replyEntity.getCreationDate(),
                replyEntity.getAuthor().getId(),
                replyEntity.getTopic().getId(),
                replyEntity.getSolution()
        );
    }

    @Override
    public void deleteById(Long id) {
        replyRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Reply not found with this id"));

        replyRepository.deleteById(id);
    }
}
