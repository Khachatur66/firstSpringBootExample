package com.vfa.security;

import com.vfa.enums.JwtTokenType;
import com.vfa.exception.JwtAuthenticationException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;

    @Value(value = "${re.login.url}")
    private String reLoginPath;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loggedOutJwtTokenCache = loggedOutJwtTokenCache;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token, (HttpServletRequest) servletRequest) &&
                    loggedOutJwtTokenCache.getLogoutToken(token) == null) {

                String tokenType = jwtTokenProvider.getClaimFromToken(token, key -> key.get("tokenType", String.class));

                if (tokenType.equals(JwtTokenType.REFRESH_TOKEN.name())) {
                    String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
                    if (!servletPath.equals(reLoginPath))
                        throw new JwtAuthenticationException("Unauthorized", HttpStatus.UNAUTHORIZED);
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            servletRequest.setAttribute("expired", e.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
