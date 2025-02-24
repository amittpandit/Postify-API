package com.postify.main.controllers;

import com.postify.main.dto.tagDto.TagRequestDto;
import com.postify.main.dto.tagDto.TagResponseDto;
import com.postify.main.entities.Tag;
import com.postify.main.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @PutMapping("{name}")
    public ResponseEntity<Tag> updateName(@PathVariable String name , @RequestBody TagRequestDto tagRequestDto){
        Tag updateByTag=tagService.updateByname(name,tagRequestDto);
        return new ResponseEntity<>(updateByTag,HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByTagName(@PathVariable String name){
        tagService.deletTag(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public  ResponseEntity<List<TagResponseDto>> getall(){
        return  new ResponseEntity<List<TagResponseDto>>(
                tagService.getall().stream().map(tag -> tagService.converToTagResponseDto(tag))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public  ResponseEntity<TagResponseDto> create(@RequestBody TagRequestDto tagRequestDto){
        return  new ResponseEntity<>(tagService.converToTagResponseDto(
                tagService.create(tagService.converToTag(tagRequestDto))),HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public  ResponseEntity<?> deletetagbyid(@PathVariable int id){
        tagService.deletetagbyid(id);
        return new ResponseEntity<>("tag deleted successfully",HttpStatus.NO_CONTENT);
    }
}
