package com.veelink.cms.dto.common;

import lombok.Getter;

@Getter
public class MessageResponse {
    private final boolean success;
    private final String message;

    public MessageResponse(String message) {
        this(true, message);
    }

    public MessageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}