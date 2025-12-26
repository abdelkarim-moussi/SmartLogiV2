package com.app.api.security.service;

import com.app.api.entity.Role;
import com.app.api.entity.User;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.RoleRepository;
import com.app.api.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Role role = roleRepository.findByName("CLIENT")
                        .orElseThrow(
                                () -> new NotFoundException("role not found")
                        );

        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setRoles(Set.of(role));
            return userRepository.save(newUser);
        });

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

        String token = jwtService.generateToken(userDetails);

        String targetUrl = "http://localhost:3000/oauth2/redirect?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
