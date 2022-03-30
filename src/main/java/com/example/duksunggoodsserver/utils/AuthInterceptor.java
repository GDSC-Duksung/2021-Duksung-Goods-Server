package com.example.duksunggoodsserver.utils;

import com.example.duksunggoodsserver.exception.CustomException;
import com.example.duksunggoodsserver.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CustomException {
        final String token = request.getHeader("Authorization");
        log.info("preHandle: " + token);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException("권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }
        return true;
    }
}
