package com.netcracker.CoffeeShopApplication.authenticationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    AuthenticationTokenProvider authenticationTokenProvider;

    public AuthenticationFilter(AuthenticationTokenProvider authenticationTokenProvider) {
        this.authenticationTokenProvider = authenticationTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = authenticationTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && authenticationTokenProvider.validateToken(token)) {
            Authentication auth = token != null ? authenticationTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
