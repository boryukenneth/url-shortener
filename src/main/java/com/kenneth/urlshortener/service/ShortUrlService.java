package com.kenneth.urlshortener.service;

import com.kenneth.urlshortener.domain.ShortUrl;

public interface ShortUrlService {

    ShortUrl createShortUrl(String originalUrl);

}
