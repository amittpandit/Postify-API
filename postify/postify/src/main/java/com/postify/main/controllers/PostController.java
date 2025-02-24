package com.postify.main.controllers;

import com.postify.main.dto.postdto.PostPartialRequestDto;
import com.postify.main.dto.postdto.PostRequestDto;
import com.postify.main.dto.postdto.PostResponseDto;
import com.postify.main.entities.Post;
import com.postify.main.entities.Tag;
import com.postify.main.services.PostServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServices postServices;

    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto>> getPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3")int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        Page<PostResponseDto> posts=
                postServices
                        .getall(page,size,sortDirection,sortBy)
                        .map(post->postServices.converttoPostResponseDto(post));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3")int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(name = "value",defaultValue = "") String search
    ){
        Page<PostResponseDto> posts=
                postServices
                        .searchPosts(page,size,sortDirection,sortBy,search)
                        .map(post->postServices.converttoPostResponseDto(post));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getpostbyid(@PathVariable int id){
        return new ResponseEntity<PostResponseDto>(
                postServices.converttoPostResponseDto(postServices.getpost(id)),
                HttpStatus.OK);

    }

    @PostMapping("user/{userid}")
    public ResponseEntity<PostResponseDto> createpost(@PathVariable int userid,@Valid @RequestBody PostRequestDto postRequestDto){
        Post post=postServices.converttoPost(postRequestDto);
        Post createdPost=postServices.create(post,userid);
        return new ResponseEntity<PostResponseDto>(postServices.converttoPostResponseDto(createdPost),HttpStatus.CREATED);
    }

    @PutMapping("/{id}/users/{userId}")
    public  ResponseEntity<PostResponseDto> updatepostbyid(@PathVariable int id ,@Valid@RequestBody PostRequestDto postRequestDto,
                                                           @PathVariable int userId){
        Post updatedpost=postServices.update(id,postServices.converttoPost(postRequestDto),userId);
        return ResponseEntity.ok(postServices.converttoPostResponseDto(updatedpost));
//        Post postResponse=postServices.update(id,postServices.converttoPost(postRequestDto));
//        return new ResponseEntity<PostResponseDto>(postServices.converttoPostResponseDto(postResponse),HttpStatus.CREATED);
    }

    @GetMapping("/title/{title}")
    public  ResponseEntity<Page<PostResponseDto>> getbytitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3")int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ){
       return  new ResponseEntity<>(
               postServices
                       .getpostbytitle(title,page,size,sortDirection,sortBy)
                       .map(post -> postServices.converttoPostResponseDto(post))
               ,HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public  ResponseEntity<Page<PostResponseDto>> getpostsbytags(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3")int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return  new ResponseEntity<>(
                postServices
                        .getpostbyTagName(tag,page,size,sortDirection,sortBy)
                        .map(post -> postServices.converttoPostResponseDto(post))
                ,HttpStatus.OK);
    }


    @DeleteMapping("/{id}/users/{userId}")
    public ResponseEntity<?> deletepost(@PathVariable int id,@PathVariable int userId){
        postServices.delete(id,userId);
        return  new ResponseEntity<>("post deleted successfully",HttpStatus.NO_CONTENT);
    }


    @PatchMapping("{id}/users/{userId}")
    public ResponseEntity<PostResponseDto> updatepostpartial(@PathVariable int id,@Valid@RequestBody PostPartialRequestDto postPartialRequestDto,
                                                             @PathVariable int userId){
        Post post=postServices.converttoPost(postPartialRequestDto);
        Post updatedPost=postServices.update(id,post,userId);
        return ResponseEntity.ok(postServices.converttoPostResponseDto(updatedPost));

//        Post oldpost=postServices.getpost(id);
//        oldpost.setTitle(postRequestDto.getTitle()!=null?postRequestDto.getTitle():oldpost.getTitle());
//        oldpost.setDescription(postRequestDto.getDescription()!=null?postRequestDto.getDescription():oldpost.getDescription());
//        oldpost.setTags(!postRequestDto.getTags().isEmpty() ?
//                postRequestDto.getTags().stream().map(name-> new Tag(name)).
//                        collect(Collectors.toSet()) : oldpost.getTags());
//        Post postResponse=postServices.update(id,oldpost);
//        return  new ResponseEntity<PostResponseDto>(postServices.converttoPostResponseDto(postResponse),HttpStatus.OK);
    }

    @GetMapping("/user/{userid}")
    public  ResponseEntity<List<PostResponseDto>> getuserPostByid(@PathVariable int userid){
        List<Post> userposts=postServices.getUserPosts(userid);
        List<PostResponseDto>  posts=userposts.stream().map(post -> postServices.converttoPostResponseDto(post)).toList();
        return ResponseEntity.ok(posts);
    }



}
