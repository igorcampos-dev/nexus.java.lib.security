package com.nexus.security.dto;

import org.springframework.http.HttpMethod;

public record Routes(String route, HttpMethod method){}
