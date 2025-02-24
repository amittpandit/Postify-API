package com.postify.main;

import com.postify.main.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PostifyApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(PostifyApplication.class, args);
	}

	@PostConstruct
	public void init(){
		try{
			System.out.println("creating super user.....");
			userService.createSuperUser("postify","postify1","postify@gmail.com");
			System.out.println("successfully created super user!!");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error creating  super user: "+e.getMessage());
		}
	}

}
