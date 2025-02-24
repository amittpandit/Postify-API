package com.postify.main.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

// project -LomBok : - Project Lombok is a popular Java library that helps reduce boilerplate code in Java applications
// by providing a set of annotations that automate repetitive tasks. It integrates seamlessly with most IDEs and build tools,
// simplifying the development process while maintaining code readability.
//Example : - Data classes (tostring , getter,setter) , constructor classes(noarsconstructor,Allargsconstructor,requiredargsconstructor)
//and etc

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "post.tags_user",attributeNodes = {@NamedAttributeNode("tags"),@NamedAttributeNode("user")})
public class  Post {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    private String title;
    private  String description;


    @ManyToMany(cascade =CascadeType.ALL)
    @JoinTable(
            name = "posts_tags",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags=new HashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private User user;
}
