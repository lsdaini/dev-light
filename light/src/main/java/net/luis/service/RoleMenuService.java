package net.luis.service;

import java.util.List;

public interface RoleMenuService {

	List<Long> selectMenusByroleId(Long roleId);

}
