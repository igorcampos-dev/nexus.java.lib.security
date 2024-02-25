package com.nexus.security.properties;

import lombok.Builder;
import java.util.List;

@Builder
public record TokenProperties(String username, List<String> authorities) {
}
