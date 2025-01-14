package com.sujan.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
        private Integer id;
        private String email;
        private String address;
        private String phoneNo;
        private String role;
}
