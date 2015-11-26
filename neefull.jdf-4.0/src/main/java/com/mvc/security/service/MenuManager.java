package com.mvc.security.service;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.mvc.framework.service.BaseService;
import com.mvc.security.model.Menu;
import com.mvc.security.model.RoleMenu;
import com.mvc.security.model.UserRole;

@Service
public class MenuManager extends BaseService<Menu, Long> {

	public void save(Menu entity) {
		boolean updateChildren = false;
		if (null != entity.getObjectId()) {
			updateChildren = true;
		}
		setPath(entity);
		super.save(entity);
		if (updateChildren) {
			String parentPath = entity.getPath();
			updatePathOfDirectChildren(entity.getObjectId(), parentPath);
		}

	}

	private void updatePathOfDirectChildren(Long parentId, String parentPath) {
		List<Menu> needUpdateMenu = getDirectChildren(parentId);
		for (Menu menu : needUpdateMenu) {
			menu.setPath(parentPath + Menu.PATH_SEPARATOR + menu.getName());
			updatePathOfDirectChildren(menu.getObjectId(), menu.getPath());
			String hql = "UPDATE " + Menu.class.getName() + " SET path = ? WHERE objectId = ?";
			Query query = getSession().createQuery(hql).setParameter(0, menu.getPath())
					.setParameter(1, menu.getObjectId());
			query.executeUpdate();
		}
	}

	private void setPath(Menu entity) {
		if (null != entity) {
			if (null != entity.getParentId() && !Menu.ROOT.getObjectId().equals(entity.getParentId())) {
				Menu parentMenu = getParentMenu(entity.getParentId());
				entity.setPath(parentMenu.getPath() + Menu.PATH_SEPARATOR + entity.getName());
			} else {
				entity.setPath(Menu.PATH_SEPARATOR + entity.getName());
			}
		}
	}

	public List<Menu> getMenusByServerId(long serverId) {
		String sql = "select A from " + Menu.class.getName() + " A where serverId=? and type=?";
		return searchBySql(sql, new Object[] { serverId, Menu.TYPE_MENU});
	}

	
	public List<Menu> getOnlineMenusByServerId(long serverId) {
		String sql = "select A from " + Menu.class.getName() + " A where serverId=? and type=? and status=?";
		return searchBySql(sql, new Object[] { serverId, Menu.TYPE_MENU, Menu.MENU_STATUS_ONLINE });
	}

	public List<Menu> getAllOnLineMenus() {
		String sql = "select A from " + Menu.class.getName() + " A where type=? and status=?";
		return searchBySql(sql, new Object[] { Menu.TYPE_MENU, Menu.MENU_STATUS_ONLINE });
	}

	public List<Menu> getAllMenu() {
		String sql = "select A from " + Menu.class.getName() + " A where type=?";
		return searchBySql(sql, new Object[] { Menu.TYPE_MENU });
	}


	public List<Menu> getMenusByServerIdAndUserId(Long serverId, Long userId) {
		String sql = "SELECT  distinct A from  " + Menu.class.getName() + " A, " + RoleMenu.class.getName() + " C, "
				+ UserRole.class.getName() + " D " + "WHERE A.status = " + Menu.MENU_STATUS_ONLINE
				+ " AND A.serverId = " + serverId + " AND A.objectId = C.menuId "
				+ "AND C.roleId = D.roleId AND D.userId = " + userId;
		return searchBySql(sql);
	}

	public List<Menu> getMenusByUserId(Long userId) {
		String sql = "SELECT distinct A from  " + Menu.class.getName() + " A, " + RoleMenu.class.getName() + " C, "
				+ UserRole.class.getName() + " D " + "WHERE A.status = ? AND A.objectId = C.menuId "
				+ "AND C.roleId = D.roleId AND D.userId = ?";
		return searchBySql(sql,new Object[]{Menu.MENU_STATUS_ONLINE,userId});
	}

	public List<Menu> getMenusByRoleId(Long roleId) {
		String sql = "SELECT distinct A FROM  " + Menu.class.getName() + " A, " + RoleMenu.class.getName()
				+ " C WHERE A.status = " + Menu.MENU_STATUS_ONLINE + " AND A.type = " + Menu.TYPE_MENU
				+ " AND A.objectId = C.menuId AND C.roleId = " + roleId;
		return searchBySql(sql);
	}

	public List<Menu> getAllFolder() {
		String sql = "select A from " + Menu.class.getName() + " A where type=?";
		List<Menu> result = searchBySql(sql, new Object[] { Menu.TYPE_FOLDER });
		result.add(Menu.ROOT);
		return result;
	}

	public List<Menu> getDirectChildren(Long parentId) {
		String sql = "select A from " + Menu.class.getName() + " A where A.status = ? and parentId=?";
		return searchBySql(sql, new Object[] { Menu.MENU_STATUS_ONLINE, parentId });
	}

	public List<Menu> getChildren(Long parentId) {
		List<Menu> result = new LinkedList<Menu>();
		String sql = "select A from " + Menu.class.getName() + " A where status=? and parentId=?";
		List<Menu> current = searchBySql(sql, new Object[] { Menu.MENU_STATUS_ONLINE, parentId });
		List<Menu> children = new LinkedList<Menu>();
		result.addAll(children);
		result.addAll(current);
		return result;
	}

	public Menu getParentMenu(Long parentId) {
		Menu menu = getByObjectId(parentId);
		setPath(menu);
		return menu;
	}
}
