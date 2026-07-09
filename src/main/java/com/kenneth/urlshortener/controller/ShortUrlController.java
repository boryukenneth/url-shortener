package com.kenneth.urlshortener.controller;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.domain.dto.CreateShortUrlRequestDto;
import com.kenneth.urlshortener.domain.dto.ShortUrlDto;
import com.kenneth.urlshortener.mapper.ShortUrlMapper;
import com.kenneth.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/urls")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;
    private final ShortUrlMapper shortUrlMapper;

    public ShortUrlController(ShortUrlService shortUrlService, ShortUrlMapper shortUrlMapper) {
        this.shortUrlService = shortUrlService;
        this.shortUrlMapper = shortUrlMapper;
    }

    @PostMapping // HTTP Post
    public ResponseEntity<ShortUrlDto> createShortUrl(
            @RequestBody
            @Valid
            CreateShortUrlRequestDto createShortUrlRequestDto
    ) {
        // CreateShortUrlRequestDto -> CreateShortUrlRequest -> ShortUrl -> ShortUrlDto
        CreateShortUrlRequest createShortUrlRequest = shortUrlMapper.fromDto(createShortUrlRequestDto);
        ShortUrl shortUrl = shortUrlService.createShortUrl(createShortUrlRequest);
        ShortUrlDto createdShortUrlDto = shortUrlMapper.toDto(shortUrl);
        return new ResponseEntity<>(createdShortUrlDto, HttpStatus.CREATED);
    }
}

