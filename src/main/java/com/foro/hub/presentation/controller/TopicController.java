package com.foro.hub.presentation.controller;

import com.foro.hub.presentation.dto.reply.ReplyDTO;
import com.foro.hub.presentation.dto.topic.CreateTopicDTO;
import com.foro.hub.presentation.dto.topic.TopicDTO;
import com.foro.hub.service.interfaces.IReplyService;
import com.foro.hub.service.interfaces.ITopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private ITopicService topicService;

    @Autowired
    private IReplyService replyService;

    @GetMapping
    public ResponseEntity<Page<TopicDTO>> findAll(@PageableDefault(size = 10, sort = {"creationDate"}) Pageable pageable) {
        Page<TopicDTO> topics = topicService.findAll(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> findById(@PathVariable Long id) {
        Optional<TopicDTO> topic = topicService.findById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicDTO> create(@RequestBody @Valid CreateTopicDTO createTopicDTO) {
        TopicDTO topicEntity = topicService.create(createTopicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid CreateTopicDTO createTopicDTO) {
        TopicDTO updatedTopic = topicService.update(id, createTopicDTO);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        System.out.println("enter");
        topicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<ReplyDTO>> findRepliesByTopicId(@PathVariable Long id) {
        List<ReplyDTO> replies = replyService.findByTopicId(id);
        return ResponseEntity.ok(replies);
    }
}
