package com.nexus.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecurityContextInjector {

    void injectContext(String token);

    void exception(Exception e, HttpServletRequest request, HttpServletResponse httpServletResponse);

}
