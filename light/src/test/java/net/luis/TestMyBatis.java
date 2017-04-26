package net.luis;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import net.luis.entity.User;
import net.luis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })

public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	@Resource
	private UserService userService;

	@Test
	public void test1() {
		User user = new User();
		user.setUsername("测试用户");
		user.setPassword("123456");
		try {
			userService.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(JSON.toJSONString(user));
	}
}