package com.adeies.adeies.enterprise.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal (HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse,
                                     FilterChain filterChain) throws ServletException, IOException{
        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(httpServletRequest.getCookies())
                .orElse(new Cookie[0]))
                .filter(cookie -> cookie.getName().equals("Access-Token")).findFirst();
        authCookie.ifPresent(cookie -> SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(cookie.getValue(),null)
        ));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
