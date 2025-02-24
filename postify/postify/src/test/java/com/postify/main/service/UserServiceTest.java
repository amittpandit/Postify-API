package com.postify.main.service;


import com.postify.main.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class UserServiceTest {

    User user;
    @BeforeEach
    public void initUser(){
        user=new User();
        user.setUsername("sushant");
        user.setEmail("sushant@gmail.com");
        user.setPassword("12121as");
    }
    @Test
    public  void ageTest(){
        int age=1;
        assertTrue(age>=10);
    }
    @Test
    public void emailTest(){
        assertNotNull(user.getEmail());
        assert user.getEmail().isEmpty()!=true;
    }
}
