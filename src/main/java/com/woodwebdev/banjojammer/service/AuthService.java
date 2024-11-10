package com.woodwebdev.banjojammer.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.woodwebdev.banjojammer.controller.AuthenticationRequest;
import com.woodwebdev.banjojammer.controller.AuthenticationResponse;
import com.woodwebdev.banjojammer.controller.RegisterRequest;
import com.woodwebdev.banjojammer.entity.User;
import com.woodwebdev.banjojammer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
  
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(request.getRole())
      .build();
    try {
      repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      logger.info("User registered: " + user.getEmail());
      return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    } catch (Exception e) {
      logger.error("Error registering user: " + user.getEmail());
      throw e;
    }
  }

  public AuthenticationResponse login(AuthenticationRequest request) {
      try {
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getEmail(),
                  request.getPassword()
              )
          );
          
          var user = repository.findByEmail(request.getEmail()).orElseThrow();
          String jwt = jwtService.generateToken(user);
          logger.info("User logged in: " + user.getEmail());
          return AuthenticationResponse.builder()
              .token(jwt)
              .build();
      } catch (AuthenticationException e) {
          throw new BadCredentialsException("Invalid username or password");
      }
  }
}
