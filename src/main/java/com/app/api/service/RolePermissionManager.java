package com.app.api.service;

import com.app.api.dto.RolePermissionsRequest;
import com.app.api.entity.Permission;
import com.app.api.entity.Role;
import com.app.api.exception.InvalidDataException;
import com.app.api.exception.ResourceNotFoundException;
import com.app.api.repository.PermissionRepository;
import com.app.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolePermissionManager {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Object createNewRole(String roleName){
        if(roleName == null || roleName.isEmpty()) throw new InvalidDataException("role name is required");

        Role role = Role.builder()
                .name(roleName.toUpperCase()).build();

        return roleRepository.save(role);
    }

    public Object createNewPermission(String permissionName){
        if(permissionName == null || permissionName.isEmpty()) throw new InvalidDataException("permission name is required");

        Permission permission = Permission.builder()
                .name(permissionName).build();

        return permissionRepository.save(permission);
    }

    public Object assignPermissionsToRole(RolePermissionsRequest request){
        if(request.role() == null || request.role().isEmpty()) throw new InvalidDataException("role is required");
        if(request.permissions() == null) throw new InvalidDataException("permissions is required");

        Role existingRole = roleRepository.findByName(request.role()).orElseThrow(
                () -> new ResourceNotFoundException("role not found with name "+request.role())
        );

        Set<Permission> managedPermissions = new HashSet<>();
        for (String permissionName : request.permissions()){
            Permission existingPermission = permissionRepository.findByName(permissionName).orElseThrow(
                    () -> new ResourceNotFoundException("permission not found with name "+permissionName)
            );

            managedPermissions.add(existingPermission);
        }

        existingRole.setPermissions(managedPermissions);
        return roleRepository.save(existingRole);

    }
}
