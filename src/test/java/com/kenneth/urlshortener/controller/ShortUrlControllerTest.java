package com.kenneth.urlshortener.controller;

import com.kenneth.urlshortener.domain.CreateShortUrlRequest;
import com.kenneth.urlshortener.domain.ShortUrl;
import com.kenneth.urlshortener.domain.dto.CreateShortUrlRequestDto;
import com.kenneth.urlshortener.domain.dto.ShortUrlDto;
import com.kenneth.urlshortener.mapper.ShortUrlMapper;
import com.kenneth.urlshortener.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortUrlController.class)
class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ShortUrlService shortUrlService;
    @MockitoBean
    private ShortUrlMapper shortUrlMapper;


    @Test
    void shouldReturnCreatedWhenRequestIsValid() throws Exception {
        // Arrange
        String requestBody = """
                {
                    "originalUrl":"https://github.com/"
                }
                """;
        CreateShortUrlRequestDto dummyRequestDto = new CreateShortUrlRequestDto("https://github.com/");
        CreateShortUrlRequest request = new CreateShortUrlRequest(dummyRequestDto.originalUrl());
        ShortUrl dummyShortUrl = new ShortUrl(
                "https://github.com/",
                0L,
                "a1b2c3",
                LocalDateTime.now(),
                0L
        );
        ShortUrlDto dummyResponserDto = new ShortUrlDto("https://github.com/", "a1b2c3");

        when(shortUrlMapper.fromDto(any(CreateShortUrlRequestDto.class)))
                .thenReturn(request);
        when(shortUrlService.createShortUrl(any(CreateShortUrlRequest.class)))
                .thenReturn(dummyShortUrl);
        when(shortUrlMapper.toDto(any(ShortUrl.class)))
                .thenReturn(dummyResponserDto);

        // Act + Assert
        mockMvc.perform(post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl").value("https://github.com/"))
                .andExpect(jsonPath("$.shortCode").value("a1b2c3")
                );

        // Verify
        verify(shortUrlMapper).fromDto(any(CreateShortUrlRequestDto.class));
        verify(shortUrlService).createShortUrl(any(CreateShortUrlRequest.class));
        verify(shortUrlMapper).toDto(any(ShortUrl.class));

    }
}