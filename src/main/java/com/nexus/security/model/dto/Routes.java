package com.nexus.security.model.dto;

import org.springframework.http.HttpMethod;

public record Routes(String route, HttpMethod method){}
