package com.vfa.security;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class LoggedOutJwtTokenCache {

    private final ExpiringMap<String, String> tokenEventMap;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoggedOutJwtTokenCache(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.tokenEventMap =
                ExpiringMap
                        .builder()
                        .variableExpiration()
                        .maxSize(1000)
                        .build();
    }

    public void markLogoutToken(String token) {
        String username = tokenProvider.getClaimFromToken(token, Claims::getSubject);

        if (tokenEventMap.containsKey(token)) {
            log.info(String.format("Log out token for user [%s] is already present in the cache", username));
        } else {
            Date tokenExpiryDate = tokenProvider.getClaimFromToken(token, Claims::getExpiration);
            long expiryForToken = this.getExpiryForToken(tokenExpiryDate);
            log.info(String.format("Logout token cache set for [%s] with a expiry of [%s] seconds. Token is due expiry at [%s]", username, expiryForToken, tokenExpiryDate));
            tokenEventMap.put(username, token, expiryForToken, TimeUnit.SECONDS);
        }
    }

    public String getLogoutToken(String token) {
        String username = tokenProvider.getClaimFromToken(token, Claims::getSubject);
        return tokenEventMap.get(username);
    }

    private long getExpiryForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }

    public void removeFromCache(String username) {
        tokenEventMap.remove(username);
    }
}
