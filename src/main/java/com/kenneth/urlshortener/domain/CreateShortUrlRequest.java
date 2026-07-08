package com.kenneth.urlshortener.domain;

public record CreateShortUrlRequest(
        String originalUrl
) {
}
