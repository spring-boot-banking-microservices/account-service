package org.example.accountservice.dto;

public record ResponseDto(
        String statusCode,
        String statusMessage
) {
}
