package com.postify.main.dto.postdto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private int id;
    private String title;
    private  String description;
    private Set<String> tags=new HashSet<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    String author;

}
