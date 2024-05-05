package org.example.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Represents an error response"
)
public record ErrorResponseDto(
        @Schema(description = "API path")
        String apiPath,

        @Schema(description = "Error code")
        HttpStatus errorCode,

        @Schema(description = "Error message")
        String errorMessage,

        @Schema(description = "Error timestamp")
        LocalDateTime errorTimestamp
) {
}
