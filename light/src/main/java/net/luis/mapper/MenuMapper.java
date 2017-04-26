package net.luis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.luis.entity.Menu;


public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);
    
    List<Menu> selectTopMenu();
    
    int selectCountByParam(@Param("param")Map<String, Object> map);
    
    List<Menu> selectByParam(@Param("param")Map<String, Object> map);
    
    Menu selectByName(@Param("name")String name,@Param("id")Long id);
    
    int selectChildCount(Long id);
    
    List<Menu> selectByParentId(Long parentId);
    
    List<Menu> selectTopByAdminId(Long adminId);
    
    List<Menu> selectByParentIdAndAdminId(@Param("parentId")Long parentId,@Param("adminId")Long adminId);
    
}