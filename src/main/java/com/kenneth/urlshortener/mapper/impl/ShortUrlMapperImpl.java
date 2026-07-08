package com.kenneth.urlshortener.mapper.impl;

import com.kenneth.urlshortener.mapper.ShortUrlMapper;
import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.domain.dto.CreateShortUrlRequestDto;
import com.kenneth.urlshortener.domain.dto.ShortUrlDto;
import org.springframework.stereotype.Component;


@Component
public class ShortUrlMapperImpl implements ShortUrlMapper {

    @Override // REQUEST
    public CreateShortUrlRequest fromDto(CreateShortUrlRequestDto dto) {
        return new CreateShortUrlRequest(
                dto.originalUrl()
        );
    }

    @Override //RESPONSE
    public ShortUrlDto toDto(ShortUrl shortUrl) {
        return new ShortUrlDto(
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode()
        );
    }
}
