package com.postify.main.services;

import com.postify.main.dto.postdto.PostPartialRequestDto;
import com.postify.main.dto.postdto.PostRequestDto;
import com.postify.main.dto.postdto.PostResponseDto;
import com.postify.main.entities.Post;
import com.postify.main.entities.Tag;
import com.postify.main.entities.User;
import com.postify.main.repository.PostRepository;
import com.postify.main.repository.TagRepository;
import com.postify.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public Post  create(Post post,int userid){
//        Set<Tag> persistedTags=new HashSet<>();
//        if(post.getTags() !=null && !post.getTags().isEmpty()){
//            persistedTags=post.getTags().stream().map(tag -> {
//                return tagRepository.findByName(tag.getName()).orElseGet(()->tagRepository.save(tag));
//            }).collect(Collectors.toSet());
//            User user=userService.getById(userid);
//            post.setUser(user);
//            post.setTags(persistedTags);
//            emailService.sendEmail(user.getEmail(),"Post creation","Your post with title : "+post.getTitle()+" has been created successfully on :"+
//                    post.getCreatedDate());
//        }
//        return postRepository.save(post);
        User user=userService.getById(userid);
        post.setUser(user);
        post.setTags(getpersistedTags(post.getTags()));
        Post savePost=postRepository.save(post);
        emailService.sendEmail(user.getEmail(),"Post creation","Your post with title : "+post.getTitle()+" has been created successfully on :"+
                    post.getCreatedDate());
        return savePost;
    }



    public Post getpost(int id){
        return  postRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"post with id "+id+" not found"));
    }

    public Post update(int id,Post updatedpost,int  userId){
        User user=userService.getById(userId);
        Post userpost=user.getPosts().stream().filter(post -> post.getId()==id)
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"post with id "+id +" not found"));
        if(updatedpost.getTitle()!=null){
            userpost.setTitle(updatedpost.getTitle());
        }
        if (updatedpost.getDescription()!=null){
            userpost.setDescription(updatedpost.getDescription());
        }
        if (!updatedpost.getTags().isEmpty()){
            userpost.setTags(getpersistedTags(updatedpost.getTags()));
        }
        Post savePost= postRepository.save(userpost);
        emailService.sendEmail(user.getEmail(),"Post creation","Your post with title : "+userpost.getTitle()+" has been updated successfully on :"+
                userpost.getLastModifiedDate());
        return savePost;
    }

    private Set<Tag> getpersistedTags(Set<Tag> tags){
        if(tags==null || tags.isEmpty()){
            return new HashSet<>();
        }
        return tags.stream()
                .map(tag -> tagRepository.findByName(tag.getName())
                        .orElseGet(()->tagRepository.save(tag))).collect(Collectors.toSet());

    }

    @Transactional
    public  void delete(int id,int userId){
        User user=userService.getById(userId);

        Post userpost=user.getPosts().stream().filter(post -> post.getId()==id).findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"post with id: "+ id+" not found"));
        userpost.setTags(new HashSet<>());
        user.getPosts().removeIf(post ->post.getId()==id);
        userRepository.save(user);
       postRepository.deleteById(id);
    }

    public Page<Post> getall(int page,int size,String sortDirection, String sortBy) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.fromString(sortDirection),sortBy));
        return postRepository.findAll(pageable);
    }

    public Page<Post> searchPosts(int page,int size,String sortDirection, String sortBy,String search) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.fromString(sortDirection),sortBy));
        return postRepository.searchPosts(search,pageable);
    }

    public Page<Post> getpostbytitle(String title,int page,int size,String sortDirection, String sortBy){
//        return postRepository.findByTitle(title);
        Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(sortDirection),sortBy));
        return  postRepository.findByTitleContaining(title,pageable);
    }


    public Page<Post> getpostbyTagName(String tag,int page,int size,String sortDirection, String sortBy){
        Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(sortDirection),sortBy));
        return  postRepository.findByTagsName(tag,pageable);
    }

    public List<Post> getUserPosts(int userid){
        User user=userService.getById(userid);
        return user.getPosts();
    }

    public PostResponseDto converttoPostResponseDto(Post post){
        PostResponseDto postResponseDto=new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setDescription(post.getDescription());
        postResponseDto.setTags(post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        postResponseDto.setCreatedDate(post.getCreatedDate());
        postResponseDto.setLastModifiedDate(post.getLastModifiedDate());
        postResponseDto.setAuthor(post.getUser().getUsername());

        return postResponseDto;
//        return new PostResponseDto(post.getId(),post.getTitle(),post.getDescription(),
//                post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()),post.getCreatedDate(),post.getLastModifiedDate());
    }

    public  Post converttoPost(PostRequestDto postRequestDto){
        Post post=new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setDescription(postRequestDto.getDescription());
        post.setTags(postRequestDto.getTags().stream().map(name->new Tag(name)).collect(Collectors.toSet()));

        return post;
    }
    public  Post converttoPost(PostPartialRequestDto postPartialRequestDto){
        Post post=new Post();
        post.setTitle(postPartialRequestDto.getTitle());
        post.setDescription(postPartialRequestDto.getDescription());
        post.setTags(postPartialRequestDto.getTags().stream().map(name->new Tag(name)).collect(Collectors.toSet()));

        return post;
    }

}
