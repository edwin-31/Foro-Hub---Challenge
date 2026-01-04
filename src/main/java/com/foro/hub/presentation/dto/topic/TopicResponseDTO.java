package com.foro.hub.presentation.dto.topic;

import com.foro.hub.util.TopicStatus;

import java.time.LocalDateTime;

public record TopicResponseDTO(
        String title,
        String message,
        LocalDateTime creationDate,
        TopicStatus status,
        String authorName,
        String courseName
) {
}