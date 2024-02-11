package com.nexus.security.model.dto;

import lombok.Builder;

@Builder
public record Error(String timestamp, Integer status, String error, String message, String path) {}