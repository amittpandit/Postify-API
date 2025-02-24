package com.postify.main.controllers;

import com.postify.main.dto.roleDto.RoleRequestDto;
import com.postify.main.entities.Role;
import com.postify.main.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @PostMapping("/set")
    public ResponseEntity<Set<Role>> setRole(@RequestBody RoleRequestDto roleRequestDto){
        return ResponseEntity.ok(roleService.setRoles(roleRequestDto));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteRole(@RequestBody RoleRequestDto roleRequestDto){
        roleService.romoveRolesFromUser(roleRequestDto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getall")
    public ResponseEntity<Set<Role>> getallroles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

}
