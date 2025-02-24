package com.postify.main.dto.postdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPartialRequestDto {
    @Size(min = 3,max = 100,message = "title must be between 3 to 100 character")
    private String title;
    @Size(min = 3 , max = 255,message = "description  must be between 3 to 255 character")
    private  String description;
    private Set<String> tags=new HashSet<>();
}
