package com.app.api.security.service;

import com.app.api.entity.Permission;
import com.app.api.entity.Role;
import com.app.api.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class UserDetailsImpl implements UserDetails {
    private User user;
    private final String userEmail;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userEmail = user.getEmail(); // Use email as username
        this.password = user.getPassword();
        Set<GrantedAuthority> auths = new HashSet<>();

        for (Role role : user.getRoles()){
            auths.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            for (Permission permission : role.getPermissions()){
                auths.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }

        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }
}
