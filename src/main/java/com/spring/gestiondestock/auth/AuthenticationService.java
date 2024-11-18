package com.spring.gestiondestock.auth;

import com.spring.gestiondestock.model.Users;
import com.spring.gestiondestock.model.enums.RoleUser;
import com.spring.gestiondestock.repositories.InterfaceUser;
import com.spring.gestiondestock.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final InterfaceUser interfaceUser;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .image(request.getImage())
                .address(request.getAddress())
                .contact(request.getContact())
                .role(RoleUser.valueOf(request.getRole()))
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        interfaceUser.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = interfaceUser.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
