package net.luis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.luis.mapper.RoleMenuMapper;
import net.luis.service.RoleMenuService;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Resource
	private RoleMenuMapper roleMenuMapper;

	@Override
	public List<Long> selectMenusByroleId(Long roleId) {
		return roleMenuMapper.selectMenusByroleId(roleId);
	}

}
