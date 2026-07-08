package com.kenneth.urlshortener.domain.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record CreateShortUrlRequestDto(
        @NotBlank(message = ERROR_MESSAGE_URL_LENGTH)
        @Length(max = 255, message = ERROR_MESSAGE_URL_LENGTH)
        @URL
        String originalUrl
) {
    private static final String ERROR_MESSAGE_URL_LENGTH = "URL must be no longer than 255 characters";
}
