package com.arenacesar.arena_api.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilterConfig implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader(
            "Access-Control-Allow-Origin",
            "https://arenacesar-frontend.onrender.com"
        );
        res.setHeader(
            "Access-Control-Allow-Methods",
            "GET,POST,PUT,DELETE,OPTIONS"
        );
        res.setHeader(
            "Access-Control-Allow-Headers",
            "Content-Type, Authorization"
        );
        res.setHeader(
            "Access-Control-Allow-Credentials",
            "true"
        );

        // Preflight request
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}
