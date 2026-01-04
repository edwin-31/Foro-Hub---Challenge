package com.foro.hub.presentation.dto.reply;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReplyDTO(
        @NotBlank String message,
        @NotNull Long authorId,
        @NotNull Long topicId
) {
}
