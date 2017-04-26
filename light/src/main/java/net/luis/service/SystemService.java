package net.luis.service;

import java.util.List;
import java.util.Map;

import net.luis.entity.Menu;

public interface SystemService {
	List<Menu> selectTopMenu();
	
	List<Menu> selectAll();
	
	int selectCountByParam(Map<String, Object> map);
	
	List<Menu> selectByParam(Map<String, Object> map);
	
	void insertValidation(Map<String, Object> map,Menu menu) throws Exception;
	
	void insert(Map<String, Object> map,Menu menu) throws Exception;
	
	Menu selectByPrimaryKey(Long id);
	
	void updateValidation(Map<String, Object> map,Menu menu) throws Exception;
	
	void update(Map<String, Object> map,Menu menu) throws Exception;
	
	void deleteValidation(Map<String, Object> map,Long id) throws Exception;
	
	void delete(Map<String, Object> map,Long id) throws Exception;
	
	List<Menu> selectByParentId(Long parentId);
	
	List<Menu> selectTopByAdminId(Long adminId);
	
	List<Menu> selectByParentIdAndAdminId(Long parentId,Long adminId);
}
