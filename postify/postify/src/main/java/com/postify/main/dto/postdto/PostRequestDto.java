package com.postify.main.dto.postdto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    @NotBlank(message = "title cannot be blank")
    @Size(min = 3,max = 100,message = "title must be between 3 to 100 character")
    private String title;

    @NotBlank(message = "description cannot be blank ")
    @Size(min = 3 , max = 255,message = "description  must be between 3 to 255 character")
    private  String description;
    private  Set<String> tags=new HashSet<>();
}
