package com.kenneth.urlshortener.domain.dto;

public record ShortUrlDto (
        String originalUrl,
        String shortCode
){

}
