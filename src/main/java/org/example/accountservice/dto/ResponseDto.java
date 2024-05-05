package org.example.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Response",
        description = "Represents a success response"
)
public record ResponseDto(
        @Schema(description = "Status code")
        String statusCode,

        @Schema(description = "Status message")
        String statusMessage
) {
}
