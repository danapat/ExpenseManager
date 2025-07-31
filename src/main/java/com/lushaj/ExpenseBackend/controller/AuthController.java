package com.lushaj.ExpenseBackend.controller;

import com.lushaj.ExpenseBackend.dto.ProfileDTO;
import com.lushaj.ExpenseBackend.io.AuthRequest;
import com.lushaj.ExpenseBackend.io.AuthResponse;
import com.lushaj.ExpenseBackend.io.ProfileRequest;
import com.lushaj.ExpenseBackend.io.ProfileResponse;
import com.lushaj.ExpenseBackend.service.ProfileService;
import com.lushaj.ExpenseBackend.service.impl.CustomUserDetailsService;
import com.lushaj.ExpenseBackend.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    public static final String AUTH_TOKEN_HEADER = "Authorization";

    private final ModelMapper modelMapper;

    private final ProfileService profileService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsService userDetailsService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        log.info("API / register is called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        return mapToProfileResponse(profileDTO);
    }

    @PostMapping("/login")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest authRequest) {
        log.info("API / login is called {}", authRequest);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    private ProfileDTO mapToProfileDTO(@Valid ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }

    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }
}
