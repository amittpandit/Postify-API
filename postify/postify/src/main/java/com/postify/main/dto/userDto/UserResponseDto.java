package com.postify.main.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Set<String> roles;
}
