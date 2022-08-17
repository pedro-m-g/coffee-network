package com.coffee.network.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffee.network.models.User;
import com.coffee.network.repositories.UsersRepository;
import com.coffee.network.requests.LoginRequest;
import com.coffee.network.requests.SignUpRequest;
import com.coffee.network.responses.LoginResponse;
import com.coffee.network.services.JwtService;

@RestController
@CrossOrigin
public class AuthController {

  private AuthenticationManager authenticationManager;
  private JwtService jwtService;
  private PasswordEncoder passwordEncoder;
  private UsersRepository usersRepository;

  public AuthController(
    @Autowired AuthenticationManager authenticationManager,
    @Autowired JwtService jwtService,
    @Autowired PasswordEncoder passwordEncoder,
    @Autowired UsersRepository usersRepository
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.usersRepository = usersRepository;
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );
    String token = jwtService.generateToken(
      request.getUsername()
    );
    LoginResponse response = new LoginResponse(token);
    return response;
  }

  @PostMapping("/signup")
  public void signUp(@RequestBody SignUpRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(
      passwordEncoder.encode(request.getPassword())
    );
    usersRepository.save(user);
  }

}
