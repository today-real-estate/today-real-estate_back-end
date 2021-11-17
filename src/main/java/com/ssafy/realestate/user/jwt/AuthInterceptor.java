package com.ssafy.realestate.user.jwt;


import com.ssafy.realestate.user.annotation.AnnotationHandler;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import com.ssafy.realestate.user.exception.NoTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Optional;

import static com.ssafy.realestate.user.jwt.TokenProvider.AUTHORITIES_KEY;
import static com.ssafy.realestate.user.jwt.TokenProvider.AUTHORITIES_SPLITTER;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_HEADER = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        HandlerMethod method = (HandlerMethod) handler;
        Optional<PreAuthorize> preAuthorize = getPreAuthorize(method);
        Optional<String> jwt = resolveToken(request);

        if (preAuthorize.isPresent()) {
            String token = jwt.orElseThrow(NoTokenException::new);
            tokenProvider.validateToken(token);
            return AnnotationHandler.hasAnyRole(getAuthorities(token), preAuthorize.get());
        }
        return true;
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_HEADER)) {
            return Optional.of(bearerToken.substring(TOKEN_HEADER.length()));
        }
        return Optional.empty();
    }

    private String[] getAuthorities(String token) {
        String authorities = (String) tokenProvider.getData(token).get(AUTHORITIES_KEY);
        return authorities.split(AUTHORITIES_SPLITTER);
    }

    private Optional<PreAuthorize> getPreAuthorize(HandlerMethod method) {
        for (Annotation annotation : method.getMethod().getAnnotations()) {
            if (annotation instanceof PreAuthorize) {
                return Optional.of((PreAuthorize) annotation);
            }
        }
        return Optional.empty();
    }
}