package com.wroclawroutes.security.services.impl;

import com.wroclawroutes.security.dto.JwtResponse;
import com.wroclawroutes.security.dto.LoginRequest;
import com.wroclawroutes.security.token.JwtUtils;
import com.wroclawroutes.security.userdetails.UserDetailsImpl;
import com.wroclawroutes.security.services.AuthenticationService;
import com.wroclawroutes.security.services.components.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JwtMapper jwtMapper;
    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtMapper.map(jwt, userDetails);
    }
}
