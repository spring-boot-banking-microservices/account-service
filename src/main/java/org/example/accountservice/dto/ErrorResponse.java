package org.example.accountservice.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String apiPath,
        String errorCode,
        String errorMessage,
        LocalDateTime errorTimestamp
) {
}
