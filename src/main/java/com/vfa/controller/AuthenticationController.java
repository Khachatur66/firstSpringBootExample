package com.vfa.controller;

import com.vfa.dto.request.AuthenticationRequest;
import com.vfa.dto.request.ReAuthenticationRequest;
import com.vfa.exception.AccessDeniedException;
import com.vfa.exception.NotFoundException;
import com.vfa.service.interfaces.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/crm/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request,
                                          HttpServletRequest httpServletRequest) throws NotFoundException, AccessDeniedException {
        return ResponseEntity.ok(authenticationService.authenticate(request, null, httpServletRequest));

    }

    @PostMapping("/re-login")
    public ResponseEntity<?> reAuthenticate(@Valid @RequestBody ReAuthenticationRequest request,
                                            HttpServletRequest httpServletRequest) throws NotFoundException, AccessDeniedException {
        return ResponseEntity.ok(authenticationService.authenticate(null, request.getRefreshToken(), httpServletRequest));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody ReAuthenticationRequest request) {
        authenticationService.loggedOut(request);
        return ResponseEntity.ok().build();
    }
}
