package net.luis.service;

import java.util.List;
import java.util.Map;

import net.luis.entity.Role;

public interface RoleService {

	int selectCountByParam(Map<String, Object> map);

	List<Role> selectByParam(Map<String, Object> map);

	List<Role> selectAll();

	Role selectByPrimaryKey(Long id);

	void insertValidation(Map<String, Object> map, Role role) throws Exception;

	void insert(Map<String, Object> map, Role role) throws Exception;

	void updateValidation(Map<String, Object> map, Role role) throws Exception;

	void update(Map<String, Object> map, Role role) throws Exception;

	void deleteValidation(Map<String, Object> map, Long id) throws Exception;

	void delete(Map<String, Object> map, Long id) throws Exception;
}
