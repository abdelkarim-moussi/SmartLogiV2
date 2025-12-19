package com.app.api.dto;
import java.util.Set;

public record RolePermissionsRequest(String role, Set<String> permissions){}
