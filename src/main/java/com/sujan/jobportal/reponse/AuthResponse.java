package com.sujan.jobportal.reponse;

import com.sujan.jobportal.dto.UserDto;
import com.sujan.jobportal.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private UserDto userDto;
}
