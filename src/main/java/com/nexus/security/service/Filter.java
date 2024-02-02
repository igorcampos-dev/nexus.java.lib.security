package com.nexus.security.service;

import com.nexus.security.exception.MethodNotImplementedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Filter {

    public void injectContext(String token){
        throw new MethodNotImplementedException("Implements this method");
    }

    public void exception(Exception e, HttpServletRequest request, HttpServletResponse httpServletResponse){
        throw new MethodNotImplementedException("Implements this method");
    }
}
