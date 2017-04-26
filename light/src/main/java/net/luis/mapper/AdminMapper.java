package net.luis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.luis.entity.Admin;

public interface AdminMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Admin record);

	Admin selectByPrimaryKey(Long id);

	List<Admin> selectAll();

	int updateByPrimaryKey(Admin record);

	int selectCountByRoleId(Long roleId);

	int selectCountByParam(@Param("param") Map<String, Object> map);

	List<Admin> selectByParam(@Param("param") Map<String, Object> map);

	Admin selectByName(@Param("username") String username, @Param("id") Long id);
	
	Admin login(Admin admin);
	
	Admin selectEnable(Admin admin);
}