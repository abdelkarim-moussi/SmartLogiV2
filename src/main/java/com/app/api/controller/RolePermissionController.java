package com.app.api.controller;

import com.app.api.dto.PermissionRequest;
import com.app.api.dto.RolePermissionsRequest;
import com.app.api.dto.RoleRequest;
import com.app.api.service.RolePermissionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionManager rolePermissionManager;

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
}

