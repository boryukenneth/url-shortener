package com.kenneth.urlshortener.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ShortUrl {

    public ShortUrl() {
    }

    public ShortUrl(String originalUrl, Long id, String shortCode, LocalDateTime createdAt, Long clickCount) {
        this.originalUrl = originalUrl;
        this.id = id;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.clickCount = clickCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "click_count")
    private Long clickCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrl shortUrl = (ShortUrl) o;
        return Objects.equals(id, shortUrl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
