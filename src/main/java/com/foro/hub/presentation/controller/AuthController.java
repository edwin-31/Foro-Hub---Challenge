package com.foro.hub.presentation.controller;

import com.foro.hub.config.jwt.JwtUtil;
import com.foro.hub.presentation.dto.auth.AuthRequest;
import com.foro.hub.presentation.dto.user.CreateUserDTO;
import com.foro.hub.presentation.dto.user.UserDTO;
import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.service.interfaces.IUserService;
import com.foro.hub.util.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	private final IUserService userService;

	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          UserDetailsService userDetailsService, IUserService userService) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthRequest request) {
		Optional<UserEntity> optionalUser = userService.findByEmail(request.getEmail());
		UserEntity user = optionalUser.orElseThrow(() ->
				new ResourceNotFoundException("User not found: " + request.getEmail()));

		var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
		authManager.authenticate(auth);

		var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		var jwt = jwtUtil.generateToken(userDetails.getUsername());

		return ResponseEntity.ok(Map.of("token", jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@Valid @RequestBody CreateUserDTO dto) {
		Optional<UserEntity> existing = userService.findByEmail(dto.getEmail());

		if (existing.isPresent()) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of(
                    "error", "You're already registered."
            ));

		}

		UserDTO created = userService.save(dto);

		if(created == null) {
            return ResponseEntity
                .status(500)
                .body(Map.of(
                    "error", "An unexpected error has happend."
                ));
        }

		return ResponseEntity.ok(Map.of(
                "message", "Successful registration."
		));
	}
}