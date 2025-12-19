package com.app.api.security.service;

import com.app.api.dto.user.AuthRequest;
import com.app.api.entity.User;
import com.app.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public String authenticate(AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserEmail(),authRequest.getPassword())
        );

        if(authentication.isAuthenticated()){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        String userEmail = auth.getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String getCurrentUserId(){
        return getCurrentUser().getId();
    }

    public boolean hasRole(String role){
        return getCurrentUser().getRoles()
                .stream().anyMatch(r -> r.getName().equals(role));
    }

}
