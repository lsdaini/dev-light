package net.luis.service;

import java.util.List;
import java.util.Map;

import net.luis.entity.Admin;

public interface AdminService {

	int selectCountByParam(Map<String, Object> map);

	List<Admin> selectByParam(Map<String, Object> map);

	Admin selectByPrimaryKey(Long id);

	void insertValidation(Map<String, Object> map, Admin admin) throws Exception;

	void insert(Map<String, Object> map, Admin admin) throws Exception;

	void updateValidation(Map<String, Object> map, Admin admin) throws Exception;

	void update(Map<String, Object> map, Admin admin) throws Exception;

	void deleteValidation(Map<String, Object> map, Long id) throws Exception;

	void delete(Map<String, Object> map, Long id) throws Exception;

	void loginValidation(Map<String, Object> map, Admin admin) throws Exception;

	void login(Map<String, Object> map, Admin admin) throws Exception;
}
