package com.postify.main.controllers;

import com.postify.main.dto.userDto.UserPartialRequestDto;
import com.postify.main.dto.userDto.UserRequestDto;
import com.postify.main.dto.userDto.UserResponseDto;
import com.postify.main.entities.User;
import com.postify.main.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping
//    public ResponseEntity<Page<UserResponseDto>> getAll(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size,
//            @RequestParam(defaultValue = "ASC") String sortDirection,
//            @RequestParam(defaultValue = "id") String  sortBy
//    ){
//        Page<UserResponseDto> alluser=userService.getAll(page, size, sortDirection, sortBy)
//                .map(user -> userService.convertToUserResponseDto(user));
//        return ResponseEntity.ok().body(alluser);
//    }

//    @PostMapping
//    public  ResponseEntity<UserResponseDto> createuser(@Valid@RequestBody UserRequestDto userRequestDto){
//        User user=userService.convertToUser(userRequestDto);
//        User createdUser=userService.create(user);
//        UserResponseDto userResponse=userService.convertToUserResponseDto(createdUser);
//        return ResponseEntity.status(201).body(userResponse);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getByid(@PathVariable int id){
        User user =userService.getById(id);
        UserResponseDto userResponse=userService.convertToUserResponseDto(user);
        return ResponseEntity.ok(userResponse);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteByid(@PathVariable int id){
//        userService.deleteByid(id);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/{id}")
    public  ResponseEntity<UserResponseDto> updatebyid(@PathVariable int  id, @Valid @RequestBody UserRequestDto userRequestDto){
        User user=userService.convertToUser(userRequestDto);
        User updateduser=userService.updateByid(id,user);
        UserResponseDto userResponse=userService.convertToUserResponseDto(updateduser);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updatePartialUserById(@PathVariable int id,@RequestBody UserPartialRequestDto userPartialRequestDto){
        User user=userService.convertToUser(userPartialRequestDto);
        User updatedUser=userService.updateByid(id,user);
        UserResponseDto userResponse=userService.convertToUserResponseDto(updatedUser);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("")
    public ResponseEntity<Page<UserResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String  sortBy
    ){
        Page<UserResponseDto> alluser=userService.getAll(page, size, sortDirection, sortBy)
                .map(user -> userService.convertToUserResponseDto(user));
        return ResponseEntity.ok().body(alluser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByid(@PathVariable int id){
        userService.deleteByid(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping()
    public ResponseEntity<UserResponseDto> createuser(@Valid @RequestBody UserRequestDto userRequestDto){
        User user=userService.convertToUser(userRequestDto);
        User createdUser=userService.create(user);
        UserResponseDto userResponse=userService.convertToUserResponseDto(createdUser);
        return ResponseEntity.status(201).body(userResponse);
    }
}
