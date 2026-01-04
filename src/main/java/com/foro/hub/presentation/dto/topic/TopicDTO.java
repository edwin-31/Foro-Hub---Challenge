package com.foro.hub.presentation.dto.topic;

import com.foro.hub.util.TopicStatus;

import java.time.LocalDateTime;

public record TopicDTO(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        TopicStatus status,
        Long authorId,
        Long courseId
) {
}
