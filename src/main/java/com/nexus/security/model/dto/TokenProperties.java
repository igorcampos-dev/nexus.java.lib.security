package com.nexus.security.model.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record TokenProperties(String username, List<String> authorities) {
}
