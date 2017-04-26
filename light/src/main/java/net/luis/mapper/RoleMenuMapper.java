package net.luis.mapper;

import java.util.List;

import net.luis.entity.RoleMenu;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleMenu record);

    RoleMenu selectByPrimaryKey(Long id);

    List<RoleMenu> selectAll();

    int updateByPrimaryKey(RoleMenu record);
    
    int deleteByRoleId(Long roleId);
    
    List<Long> selectMenusByroleId(Long roleId);
    
    int selectRoleMenuCountByMenuId(Long menuId);
}