package com.postify.main;

import  org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostifyApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testNumberIspostitive(){
		int number=10;
		assertTrue(number>0,"number should be greater than 0");
	}

}
