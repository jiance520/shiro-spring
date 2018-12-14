package cn.sm1234.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.sm1234.dao.UserMapper;
import cn.sm1234.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {
	@Resource
	private UserMapper userMapper;
	@Test
	public void testFindByName() {
		User user = userMapper.findByName("eric");
		System.out.println(user);
	}
}
