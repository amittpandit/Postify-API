package com.postify.main.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "username cannot be blank")
    @Size(min = 4,max = 20,message = "username must contain or between 4 to 20 character")
    private  String username;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6,max =15,message = "password must be  between 6 to 15 character")
    private String password;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "email should not be blank")
    private  String email;
}
