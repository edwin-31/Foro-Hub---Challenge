package com.foro.hub.presentation.controller;

import com.foro.hub.presentation.dto.reply.CreateReplyDTO;
import com.foro.hub.presentation.dto.reply.ReplyDTO;
import com.foro.hub.service.interfaces.IReplyService;
import com.foro.hub.util.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/replies")
@SecurityRequirement(name = "bearer-key")
public class ReplyController {

    @Autowired
    private IReplyService replyService;

    @GetMapping("/{id}")
    public ResponseEntity<ReplyDTO> findById(@PathVariable Long id) {
        Optional<ReplyDTO> reply = replyService.findById(id);
        return reply.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReplyDTO> create(@RequestBody @Valid CreateReplyDTO createReplyDTO) {
        ReplyDTO replyEntity = replyService.create(createReplyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(replyEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyDTO> update(@PathVariable Long id, @RequestBody @Valid CreateReplyDTO createReplyDTO) {
        ReplyDTO updatedReply = replyService.update(id, createReplyDTO);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<ReplyDTO> reply = replyService.findById(id);
        if(!reply.isPresent()) {
            throw new ResourceNotFoundException("Reply not found with this id");
        }
        replyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
