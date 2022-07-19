package com.vfa.security;

import com.vfa.enums.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider implements Serializable {

    @Value("${jwt.access.token.expired}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.refresh.token.expired}")
    private long REFRESH_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String JWT_TOKEN_SECRET;

    @Value("${jwt.header}")
    private String AUTHORIZATION_HEADER;

    @PostConstruct
    protected void init() {
        JWT_TOKEN_SECRET = Base64.getEncoder().encodeToString(JWT_TOKEN_SECRET.getBytes());
    }

    public String createJwt(int userId, String username, int authorityId, String role, JwtTokenType tokenType) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("userId", userId);
        claims.put("authorityId", authorityId);

        Date now = new Date();
        Date validity;

        if (tokenType == JwtTokenType.ACCESS_TOKEN) {
            validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY);
        } else {
            validity = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY);
        }

        claims.put("tokenType", tokenType.name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET)
                .compact();
    }

    public Authentication getAuthentication(String token) {

        String username = getClaimFromToken(token, Claims::getSubject);
        String role = this.getClaimFromToken(token, key -> key.get("role", String.class));

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        grantedAuthorities.add(grantedAuthority);

        UserDetails userDetails = new User(username, "[PROTECTED]", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token, HttpServletRequest servletRequest) {
        try {
            return this.getClaimFromToken(token, Claims::getExpiration).after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            servletRequest.setAttribute("expired", e.getMessage());
            return false;
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =
                Jwts
                        .parser()
                        .setSigningKey(JWT_TOKEN_SECRET)
                        .parseClaimsJws(token)
                        .getBody();
        return claimsResolver.apply(claims);
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return header == null ? null : header.replaceAll("Bearer", "").strip();
    }
}
