package com.foro.hub.presentation.dto.reply;

import java.time.LocalDateTime;

public record ReplyDTO(
        Long id,
        String message,
        LocalDateTime creationDate,
        Long authorId,
        Long topicId,
        Boolean solution
) {
}
