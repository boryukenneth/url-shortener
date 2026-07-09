package com.kenneth.urlshortener.service;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;

public interface ShortUrlService {

    ShortUrl createShortUrl(CreateShortUrlRequest request);

    ShortUrl redirect (String shortCode);

}
