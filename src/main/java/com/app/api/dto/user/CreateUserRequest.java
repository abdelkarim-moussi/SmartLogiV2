package com.app.api.dto.user;

import com.app.api.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String email;
    private String password;
    private Set<Role> roles;
}
