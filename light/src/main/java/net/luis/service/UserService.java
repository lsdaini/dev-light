package net.luis.service;

import net.luis.entity.User;

public interface UserService {
	
	int insert(User record) throws Exception;
	
	User selectByPrimaryKey(Long id);
}