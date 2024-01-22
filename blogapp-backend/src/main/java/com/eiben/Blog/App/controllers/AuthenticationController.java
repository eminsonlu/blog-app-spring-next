package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.entities.*;
import com.eiben.Blog.App.repository.RoleRepository;
import com.eiben.Blog.App.requests.LoginRequest;
import com.eiben.Blog.App.requests.RefreshRequest;
import com.eiben.Blog.App.requests.RegisterRequest;
import com.eiben.Blog.App.responses.AuthResponse;
import com.eiben.Blog.App.responses.RegisterResponse;
import com.eiben.Blog.App.security.JwtTokenProvider;
import com.eiben.Blog.App.services.RefreshTokenService;
import com.eiben.Blog.App.services.UserService;
import com.eiben.Blog.App.services.UserVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private RefreshTokenService refreshTokenService;
    private UserVerificationService userVerificationService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RefreshTokenService refreshTokenService, UserVerificationService userVerificationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.refreshTokenService = refreshTokenService;
        this.userVerificationService = userVerificationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        User user = userService.getOneUserByName(loginRequest.getName());
        AuthResponse authResponse = new AuthResponse();
        // check credentials are valid
        if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            authResponse.setMessage("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);


        List<String> roles = user.getRoles().stream().map(role -> role.getName().toString()).toList();
        authResponse.setAccessToken("Bearer "+ jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setId(user.getId());
        authResponse.setName(user.getName());
        authResponse.setRoles(roles);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        if(userService.getOneUserByName(registerRequest.getName()) != null){
            registerResponse.setMessage("Username is already taken");
            return ResponseEntity.badRequest().body(registerResponse);
        }
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
        if (userRole.isEmpty()) {
            registerResponse.setMessage("User role not found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registerResponse);
        }
        roles.add(userRole.get());
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(roles);
        user.setVerified(false);
        userService.saveOneUser(user);
        registerResponse.setMessage("User registered successfully");
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        AuthResponse response = new AuthResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        if(token.getToken().equals(refreshRequest.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token)){
            User user = token.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserName(user.getId());
            List<String> roles = user.getRoles().stream().map(role -> role.getName().toString()).toList();
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken("Bearer "+ jwtToken);
            authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
            authResponse.setId(user.getId());
            authResponse.setName(user.getName());
            authResponse.setRoles(roles);

            return ResponseEntity.ok(authResponse);
        }else {
            response.setMessage("Invalid refresh token");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/activation")
    public ResponseEntity<String> activate(@RequestParam("token") String token){
        UserVerification userVerification = userVerificationService.findByToken(token);

        if(userVerification == null){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        User user = userVerification.getUser();
        if(!user.getVerified()){
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            if(userVerification.getExpires_at().after(currentTimestamp)) {
                user.setVerified(true);
                userService.saveOneUser(user);
                return ResponseEntity.ok("User verified successfully");
            }else {
                return ResponseEntity.badRequest().body("Token expired");
            }
        }else {
            return ResponseEntity.badRequest().body("User already verified");
        }
    }
}
