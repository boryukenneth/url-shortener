package com.kenneth.urlshortener.service.impl;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.exception.ShortUrlNotFoundException;
import com.kenneth.urlshortener.repository.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceImplTest {

    @Mock
    ShortUrlRepository shortUrlRepository;
    @InjectMocks
    ShortUrlServiceImpl shortUrlServiceImpl;

    @Test
        // createShortUrl(CreateShortUrlRequest request)
    void shouldCreateAndSaveShortUrlWhenRequestIsValid() {

        //Arrange
        CreateShortUrlRequest request = new CreateShortUrlRequest("https://github.com/");
        // When method asks if the generated short code exists already, return false
        when(shortUrlRepository.existsByShortCode(anyString()))
                .thenReturn(false);

        // When the method saves any object of short url type, return the created short url
        when(shortUrlRepository.save(any(ShortUrl.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        ShortUrl testShortUrl = shortUrlServiceImpl.createShortUrl(request);

        // Assert
        assertEquals(request.originalUrl(), testShortUrl.getOriginalUrl());
        assertEquals(0L, testShortUrl.getClickCount());
        assertEquals(6, testShortUrl.getShortCode().length());
        assertNotNull(testShortUrl.getShortCode()); // Not null
        assertFalse(testShortUrl.getShortCode().isBlank()); // Not blank

        // Verify
        verify(shortUrlRepository).save(any(ShortUrl.class));

    }

    @Test
        // redirect
    void shouldRedirectIfShortcodeIsValid() {

        // Arrange
        String testShortCode = "a1b2c3";
        ShortUrl dummyShortUrl = new ShortUrl(
                "https://www.google.com/",
                45L,
                testShortCode,
                LocalDateTime.now(),
                0L
        );

        when(shortUrlRepository.findByShortCode(anyString()))
                .thenReturn(Optional.of(dummyShortUrl));
        when(shortUrlRepository.save(any(ShortUrl.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        ShortUrl testShortUrl = shortUrlServiceImpl.redirect(testShortCode);

        // Assert
        assertEquals(testShortCode, testShortUrl.getShortCode());
        assertEquals(1L, testShortUrl.getClickCount());

        // Verify
        verify(shortUrlRepository).save(any(ShortUrl.class));
        verify(shortUrlRepository).findByShortCode(testShortCode);
    }

    @Test
        // redirect
    void shouldThrowNotFoundIfShortcodeIsInvalid() {
        // Arrange
        String testShortCode = "x1y2z3";
        when(shortUrlRepository.findByShortCode(testShortCode))
                .thenReturn(Optional.empty());

        // Act
        ShortUrlNotFoundException e = assertThrowsExactly(ShortUrlNotFoundException.class, () -> {
            shortUrlServiceImpl.redirect(testShortCode);
        });

        // Assert
        assertEquals(String.format("No URL found for short code '%s'", "x1y2z3"), e.getMessage());
        assertEquals(testShortCode, e.getShortCode());

        // Verify
        verify(shortUrlRepository).findByShortCode(any(String.class));
        verify(shortUrlRepository, never()).save(any(ShortUrl.class));

    }
}