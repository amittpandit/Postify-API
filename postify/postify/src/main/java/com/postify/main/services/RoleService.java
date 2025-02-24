package com.postify.main.services;

import com.postify.main.dto.roleDto.RoleRequestDto;
import com.postify.main.entities.Role;
import com.postify.main.entities.User;
import com.postify.main.repository.RoleRepository;
import com.postify.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private  final RoleRepository roleRepository;
    private  final UserRepository userRepository;

    @Autowired
    public  RoleService(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    public  void createAdminRole(){
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()){
            Role adminRole=new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }

    public  Role getRoleById(int id){
        return roleRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Role  with id: "+id+" not found"));
    }

    public  Role findByRoleName(String name){
        return  roleRepository.findByName(name).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found "+name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Set<Role> getAllRoles(){
        return roleRepository.findAll().stream().map(role -> roleRepository.save(role)).collect(Collectors.toSet());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Role createRole(Role role){
        if (roleRepository.findByName(role.getName()).isPresent()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role : "+role.getName()+" Already Exits");
        }
        return roleRepository.save(role);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Set<Role> setRoles(RoleRequestDto roleRequestDto){
      User user = userRepository.findById(roleRequestDto.getUserId())
              .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found: "));
      Role role=roleRepository.findById(roleRequestDto.getRoleId())
              .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found"));
      if(user.getRoles().contains(role)){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Already have same role");
      }
      user.getRoles().add(role);
      userRepository.save(user);
      return user.getRoles();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Set<Role> romoveRolesFromUser(RoleRequestDto roleRequestDto){
        User user=userRepository.findById(roleRequestDto.getUserId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));
        Role rolesToRemove = roleRepository.findById(roleRequestDto.getRoleId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user role not found"));
        boolean hasRolesToRemove=user.getRoles().remove(rolesToRemove);
        if(!hasRolesToRemove){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user does not have the specific roles");
        }
        userRepository.save(user);
        return user.getRoles();

    }









}
