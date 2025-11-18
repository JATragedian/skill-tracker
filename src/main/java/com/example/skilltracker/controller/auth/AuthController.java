package com.example.skilltracker.controller.auth;

import com.example.skilltracker.config.security.JwtService;
import com.example.skilltracker.dto.auth.request.AuthRequest;
import com.example.skilltracker.dto.auth.request.RefreshRequest;
import com.example.skilltracker.dto.auth.request.RegisterUserRequest;
import com.example.skilltracker.dto.auth.response.AuthResponse;
import com.example.skilltracker.dto.auth.response.UserResponse;
import com.example.skilltracker.entity.user.UserEntity;
import com.example.skilltracker.mapper.UserMapper;
import com.example.skilltracker.service.UserService;
import com.example.skilltracker.service.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper mapper;

    public AuthController(
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            UserService userService,
            UserMapper mapper
    ) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "username": "admin@example.com",
                                              "password": "admin"
                                            }
                                            """
                            )
                    )
            )
            @RequestBody AuthRequest request
    ) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = (UserEntity) auth.getPrincipal();

        var accessToken = jwtService.generateToken(user.getEmail());
        var refreshToken = refreshTokenService.create(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        var refresh = refreshTokenService.validate(request.refreshToken());
        var user = refresh.getUser();

        refreshTokenService.delete(refresh);
        var newRefreshToken = refreshTokenService.create(user);
        var newAccessToken = jwtService.generateToken(user.getEmail());

        return new AuthResponse(newAccessToken, newRefreshToken.getToken());
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterUserRequest request) {
        return mapper.toResponse(userService.register(request.name(), request.email(), request.password()));
    }
}
