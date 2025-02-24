package com.postify.main.repository;

import com.postify.main.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @EntityGraph(value = "post.tags_user")
    List<Post> findByTitle(String title);

    @EntityGraph(value = "post.tags_user")
    Page<Post> findByTagsName(String tag,Pageable pageable);

    @Override
    @EntityGraph(value = "post.tags_user")
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(value = "post.tags_user")
    @Query("SELECT DISTINCT p FROM posts p LEFT JOIN p.tags t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Post> searchPosts(@Param("search") String search,Pageable pageable);

    @EntityGraph(value = "post.tags_user")
    @Query("Select p from posts p where p.title LIKE %:title%")
    Page<Post> findByTitleContaining(@Param("title") String title,Pageable pageable);
}
