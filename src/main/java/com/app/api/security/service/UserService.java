package com.app.api.security.service;

import com.app.api.dto.user.CreateUserRequest;
import com.app.api.entity.Role;
import com.app.api.entity.User;
import com.app.api.exception.EmailAlreadyUsedException;
import com.app.api.exception.InvalidDataException;
import com.app.api.mapper.UserMapper;
import com.app.api.repository.RoleRepository;
import com.app.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("user not found with email : "+email)
        );

        return new UserDetailsImpl(user);
    }

    public String addUser(CreateUserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        boolean isEmailExist = userRepository.existsByEmail(user.getEmail());
        if(isEmailExist){
            throw new EmailAlreadyUsedException("email already used");
        }

        Set<Role> userRoles = new HashSet<>();
        for (Role role : user.getRoles()){
            Role existingRole = roleRepository.findByName(role.getName())
                    .orElseThrow(() -> new InvalidDataException("Role "+role.getName() +"does not exist"));
            userRoles.add(existingRole);
        }

        user.setRoles(userRoles);
        userRepository.save(userMapper.toEntity(user));
        return "User added successfully!";
    }
}
