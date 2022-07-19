package com.vfa.service.implemenation;

import com.vfa.dto.request.AuthenticationRequest;
import com.vfa.dto.request.ReAuthenticationRequest;
import com.vfa.enums.EmployeeStatus;
import com.vfa.enums.JwtTokenType;
import com.vfa.exception.JwtAuthenticationException;
import com.vfa.model.Employee;
import com.vfa.security.JwtTokenProvider;
import com.vfa.security.LoggedOutJwtTokenCache;
import com.vfa.service.interfaces.AuthenticationService;
import com.vfa.service.interfaces.EmployeeService;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;

    private final LoggedOutJwtTokenCache jwtTokenCache;

    private final EmployeeService employeeService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(JwtTokenProvider jwtTokenProvider,
                                     LoggedOutJwtTokenCache jwtTokenCache,
                                     EmployeeService employeeService,
                                     PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenCache = jwtTokenCache;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> authenticate(AuthenticationRequest request, String token, HttpServletRequest httpServletRequest) {
        int employeeId, authorityId;
        String email, role;

        if (token == null) {
            email = request.getEmail();
            Employee employee = employeeService
                    .getByEmail(email)
                    .orElseThrow(() -> new JwtAuthenticationException("Incorrect username or password", HttpStatus.UNAUTHORIZED));

            this.checkUserCredentials(employee, request.getPassword());

            employeeId = employee.getId();
            authorityId = employee.getAuthority().getId();
            role = employee.getAuthority().getRole();

            jwtTokenCache.removeFromCache(email);

        } else if (!jwtTokenProvider.validateToken(token, httpServletRequest) ||
                !jwtTokenProvider.getClaimFromToken(token, key -> key.get("tokenType", String.class)).equals(JwtTokenType.REFRESH_TOKEN.name())) {
            log.error("JWT token expired or refresh token not found");
            throw new JwtAuthenticationException("Unauthorized", HttpStatus.UNAUTHORIZED);
        } else {
            email = jwtTokenProvider.getClaimFromToken(token, Claims::getSubject);
            employeeId = jwtTokenProvider.getClaimFromToken(token, key -> key.get("employeeId" +
                    "", Integer.class));
            role = jwtTokenProvider.getClaimFromToken(token, key -> key.get("role", String.class));
            authorityId = jwtTokenProvider.getClaimFromToken(token, key -> key.get("authorityId", Integer.class));
        }

        String accessToken = jwtTokenProvider.createJwt(employeeId, email, authorityId, role, JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtTokenProvider.createJwt(employeeId, email, authorityId, role, JwtTokenType.REFRESH_TOKEN);

        return this.createAuthResponse(employeeId, authorityId, role, accessToken, refreshToken);
    }

    private void checkUserCredentials(Employee employee, String password) {
        if (!employee.getStatus().equals(EmployeeStatus.ACTIVE)) {
            log.info("Your account has not been authenticated because it is not active");
            throw new JwtAuthenticationException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(password, employee.getPassword())) {
            log.info("Password is not valid");
            throw new JwtAuthenticationException("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    private Map<String, Object> createAuthResponse(int userId, int authorityId, String role, String accessToken, String refreshToken) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("userId", userId);
        response.put("authorityId", authorityId);
        response.put("role", role);
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return response;
    }

    @Override
    public void loggedOut(ReAuthenticationRequest request) {
        String refreshToken = request.getRefreshToken();
        jwtTokenCache.markLogoutToken(refreshToken);
    }
}
