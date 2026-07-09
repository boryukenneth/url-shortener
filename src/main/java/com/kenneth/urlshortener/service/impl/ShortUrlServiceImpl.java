package com.kenneth.urlshortener.service.impl;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.exception.ShortUrlNotFoundException;
import com.kenneth.urlshortener.repository.ShortUrlRepository;
import com.kenneth.urlshortener.service.ShortUrlService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }


    @Override
    public ShortUrl createShortUrl(CreateShortUrlRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String shortCode;

        do {
            shortCode = createRandomShortCode();
        }
        while (
                shortUrlRepository.existsByShortCode(shortCode)
        );

        ShortUrl shortUrl = new ShortUrl(
                request.originalUrl(),
                null,
                shortCode,
                now,
                0L);

        return shortUrlRepository.save(shortUrl);
    }

    private String createRandomShortCode() {
        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            shortCode.append(
                    CHARACTERS.charAt(
                            RANDOM.nextInt(CHARACTERS.length()) // 62
                    )
            );
        }

        return shortCode.toString();
    }

    @Override
    public ShortUrl redirect(String shortCode) {
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode).orElseThrow(() -> new ShortUrlNotFoundException(shortCode));
        shortUrl.incrementClickCount();
        return shortUrlRepository.save(shortUrl);
    }

}
