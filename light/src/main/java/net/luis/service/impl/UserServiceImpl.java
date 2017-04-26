package net.luis.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.luis.entity.User;
import net.luis.mapper.UserMapper;
import net.luis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public int insert(User record) throws Exception{
		int result = userMapper.insert(record);
		if(result <= 0){
			throw new Exception();
		}
		return result;
	}

	@Override
	public User selectByPrimaryKey(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

}
