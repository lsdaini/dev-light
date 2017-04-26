package net.luis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.luis.entity.Role;

public interface RoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Role record);

	Role selectByPrimaryKey(Long id);

	List<Role> selectAll();

	int updateByPrimaryKey(Role record);

	int selectCountByParam(@Param("param") Map<String, Object> map);

	List<Role> selectByParam(@Param("param") Map<String, Object> map);

	Role selectByName(@Param("name") String name, @Param("id") Long id);
}