package com.nexus.security.model.dto;

import org.springframework.http.HttpMethod;

public record RoutesDTO(String route, HttpMethod method){}
