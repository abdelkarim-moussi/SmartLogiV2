package com.app.api.controller;

import com.app.api.dto.PermissionRequest;
import com.app.api.dto.RolePermissionsRequest;
import com.app.api.dto.RoleRequest;
import com.app.api.dto.UserRolesRequest;
import com.app.api.entity.Permission;
import com.app.api.entity.Role;
import com.app.api.service.RolePermissionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionManager rolePermissionManager;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getALlRoles(){
        List<Role> roles = rolePermissionManager.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getALlPermissions(){
        List<Permission> permissions = rolePermissionManager.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<Object> createRole(@RequestBody RoleRequest request){
        Object createdRole = rolePermissionManager.createNewRole(request.role());
        return ResponseEntity.ok(createdRole);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/permissions")
    public ResponseEntity<Object> createPermission(@RequestBody PermissionRequest request){
        Object createdPermission = rolePermissionManager.createNewPermission(request.permission());
        return ResponseEntity.ok(createdPermission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/permissions")
    public ResponseEntity<Object> assignPermissionsToRole(@RequestBody RolePermissionsRequest request){
        Object result = rolePermissionManager.assignPermissionsToRole(request);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/roles")
    public ResponseEntity<Object> assignRolesToUser(@RequestBody UserRolesRequest request){
        Object result = rolePermissionManager.assignRolesToUser(request);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/{name}")
    public String deleteRole(@PathVariable(value = "name") String name){
        rolePermissionManager.deleteRole(name);
        return "Role Deleted Successfully";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/permissions/{name}")
    public String deletePermission(@PathVariable(value = "name") String name){
        rolePermissionManager.deletePermission(name);
        return "Permission Deleted Successfully";
    }
}

