package com.postify.main.services;

import com.postify.main.dto.tagDto.TagRequestDto;
import com.postify.main.dto.tagDto.TagResponseDto;
import com.postify.main.entities.Post;
import com.postify.main.entities.Tag;
import com.postify.main.repository.PostRepository;
import com.postify.main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostRepository postRepository;

    public Tag updateByname(String name, TagRequestDto tagRequestDto) {
        String newname = tagRequestDto.getName();
        Tag tag = tagRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tag with name " + name + " not found"));

        tag.setName(newname);
        return tagRepository.save(tag);
    }

    public void deletTag(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tag with name " + name + " not found"));
        Page<Post> posts = postRepository.findByTagsName(name, Pageable.unpaged());
        if (!posts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tag with name " + name + " have post refrence so you cant delete");
        }
        tagRepository.deleteByName(name);
    }

    public List<Tag> getall(){
        return tagRepository.findAll();
    }

    public  Tag create(Tag tag){
        if(tagRepository.findByName(tag.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"tag with  name "+tag.getName()+" already exits");
        }
        return tagRepository.save(tag);
    }

    public void deletetagbyid(int id){
        Tag tag=tagRepository
                .findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"tag with id  "+id+" deleted"));
        tagRepository.deleteById(id);
    }

    public Tag converToTag(TagRequestDto tagRequestDto){
        return  new Tag(0,tagRequestDto.getName());
    }

    public TagResponseDto converToTagResponseDto(Tag tag){
        return new TagResponseDto(tag.getId(),tag.getName());
    }
}
