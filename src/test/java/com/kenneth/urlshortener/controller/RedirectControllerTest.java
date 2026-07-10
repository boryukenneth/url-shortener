package com.kenneth.urlshortener.controller;

import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.exception.ShortUrlNotFoundException;
import com.kenneth.urlshortener.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RedirectController.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ShortUrlService shortUrlService;

    @Test
    void shouldReturnFoundWhenRequestIsValid() throws Exception {
        // Arrange
        String shortCode = "1a2s3d";
        ShortUrl shortUrl = new ShortUrl(
                "https://github.com/",
                0L,
                "1a2s3d",
                LocalDateTime.now(),
                0L
        );
        // Act Assert
        when(shortUrlService.redirect(shortCode))
                .thenReturn(shortUrl);
        mockMvc.perform(get("/{shortCode}", shortCode))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://github.com/"));
        // Verify
        verify(shortUrlService).redirect(shortCode);

    }

    @Test
    void shouldThrowNotFoundWhenRequestIsInvalid() throws Exception {
        // Arrange
        String shortCode = "1a2s3d";
        // Act Assert
        when(shortUrlService.redirect(shortCode))
                .thenThrow(new ShortUrlNotFoundException(shortCode));

        mockMvc.perform(get("/{shortCode}", shortCode))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("No URL found for short code '%s'".formatted(shortCode)));
        // Verify
        verify(shortUrlService).redirect(shortCode);
        git commit -m "Add more JUnit test for redirect controller"
    }
}