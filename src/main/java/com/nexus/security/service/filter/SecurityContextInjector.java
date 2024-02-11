package com.nexus.security.service.filter;

public interface SecurityContextInjector {

    void injectContext(String token);

}
