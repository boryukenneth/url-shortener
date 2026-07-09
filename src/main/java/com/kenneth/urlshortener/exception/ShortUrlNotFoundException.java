package com.kenneth.urlshortener.exception;

public class ShortUrlNotFoundException extends RuntimeException {

    private final String shortCode;

    public ShortUrlNotFoundException(String shortCode) {
        super(String.format("No URL found for short code %s", shortCode));
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }
}
