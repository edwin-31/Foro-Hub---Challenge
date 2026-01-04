package com.foro.hub.service.interfaces;

import com.foro.hub.presentation.dto.reply.CreateReplyDTO;
import com.foro.hub.presentation.dto.reply.ReplyDTO;

import java.util.List;
import java.util.Optional;

public interface IReplyService {
    List<ReplyDTO> findByTopicId(Long topicId);
    Optional<ReplyDTO> findById(Long id);
    ReplyDTO create(CreateReplyDTO createReplyDTO);
    ReplyDTO update(Long id, CreateReplyDTO createReplyDTO);
    void deleteById(Long id);
}
