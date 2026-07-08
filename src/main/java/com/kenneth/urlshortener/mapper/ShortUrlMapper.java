package com.kenneth.urlshortener.mapper;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.domain.dto.CreateShortUrlRequestDto;
import com.kenneth.urlshortener.domain.dto.ShortUrlDto;

public interface ShortUrlMapper {
    CreateShortUrlRequest fromDto(CreateShortUrlRequestDto dto);
    ShortUrlDto toDto(ShortUrl shortUrl);
}
