package com.app.api.dto;

import java.util.List;

public record UserRolesRequest(String userId, List<String> roles){}
