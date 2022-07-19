package com.vfa.service.interfaces;

import com.vfa.dto.request.AuthenticationRequest;
import com.vfa.dto.request.ReAuthenticationRequest;
import com.vfa.exception.AccessDeniedException;
import com.vfa.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthenticationService {

    Map<String, Object> authenticate(AuthenticationRequest request, String token, HttpServletRequest httpServletRequest)
            throws NotFoundException, AccessDeniedException;

    void loggedOut(ReAuthenticationRequest request);
}
