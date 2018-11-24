package com.major.app;

import com.major.AgManageServerApplication;
import com.major.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AgManageServerApplication.class)

public class RedisTest {
	
	@Autowired
    RedisTemplate redisTemplate;
	
	@Autowired
	RedisService redisService;
	
	// 简单计数
	@Test
	public void test1() {
		redisService.set("aatest", "aa");
	}
	
}
