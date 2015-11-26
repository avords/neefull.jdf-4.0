package com.mvc.portal.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import com.mvc.framework.util.DomainUtils;
import com.mvc.portal.model.MenuImage;
import com.mvc.security.model.Menu;

public class BaseMenuUtils {

	public static Menu getMenuByMenuName(List<Menu> menuList, String menuName) {
		for (Menu menu : menuList) {
			if (menuName.equals(menu.getName())) {
				return menu;
			}
		}
		return null;
	}

	public static Menu getMenuByMenuId(List<Menu> menuList, long menuId) {
		for (Menu menu : menuList) {
			if (menuId == menu.getObjectId()) {
				return menu;
			}
		}
		return null;
	}

	public static List<Menu> getFirstLevelMenu(List<Menu> menuList) {
		List<Menu> menus = new ArrayList<Menu>();
		if (null != menuList) {
			for (Menu menu : menuList) {
				if (menu.getParentId().equals(Menu.ROOT.getObjectId())) {
					if (Menu.TYPE_MENU == menu.getType()) {
						menus.add(menu);
					} else {
						List<Menu> sub = getSubMenusByParentMenuId(menuList, menu.getObjectId());
						if (sub.size() > 0) {
							menus.add(menu);
						}
					}
				}
			}
		}
		return menus;
	}

	public static List<Menu> getDirectSubMenusByParentMenuId(List<Menu> menuList, Long parentMenuId) {
		List<Menu> menus = new ArrayList<Menu>();
		if (null != menuList) {
			for (Menu menu : menuList) {
				if (parentMenuId.equals(menu.getParentId()) && isNeedDisplayInSystemMenu(menu)) {
					menus.add(menu);
				}
			}
		}
		return menus;
	}

	public static List<Menu> getSubMenusByParentPath(List<Menu> menuList, String parentPath) {
		List<Menu> menus = new ArrayList<Menu>();
		if (null != menuList) {
			for (Menu menu : menuList) {
				if (menu.getObjectId() != Menu.ROOT_FOLDER_ID && menu.getPath().startsWith(parentPath)
				        && isNeedDisplayInSystemMenu(menu)) {
					menus.add(menu);
				}
			}
		}
		return menus;
	}

	public static List<Menu> getSubMenusByParentMenuId(List<Menu> meunList, long parentMenuId) {
		List<Menu> menus = new ArrayList<Menu>();
		if (null != meunList) {
			for (Menu menu : meunList) {
				if (parentMenuId == menu.getParentId() && isNeedDisplayInSystemMenu(menu)) {
					if (Menu.TYPE_FOLDER == menu.getType()) {
						menus.add(menu);
						List<Menu> sub = getSubMenusByParentMenuId(meunList, menu.getObjectId());
						if (sub.size() > 0) {
							menus.addAll(sub);
						}
					} else {
						menus.add(menu);
					}
				}
			}
		}
		return menus;
	}

	public static List<Menu> getTabMenus(List<Menu> menuList, String parentPath) {
		List<Menu> result = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if (Menu.TYPE_MENU == menu.getType()
			        && (null != menu.getDisplayPosition() && (Menu.DISPLAY_POSITION_TAB == menu.getDisplayPosition() || Menu.DISPLAY_POSITION_BOTH == menu
			                .getDisplayPosition()))) {
				if ((parentPath + Menu.PATH_SEPARATOR + menu.getName()).equals(menu.getPath())) {
					result.add(menu);
				}
			}
		}
		return result;
	}

	public static boolean isNeedDisplayInSystemMenu(Menu menu) {
		return (null == menu.getDisplayPosition() || Menu.DISPLAY_POSITION_SYSTEM_MENU == menu.getDisplayPosition());
	}

	public static Map<String, String> getMenuImageMap(List<MenuImage> menuImageList) {
		Map<String, String> menuImageMap = new HashMap<String, String>(menuImageList.size());
		for (int i = 0, n = menuImageList.size(); i < n; i++) {
			MenuImage image = menuImageList.get(i);
			menuImageMap.put(image.getModuleId()
			        + (image.getType().equals(MenuImage.MENU_IMAGE_TYPE_MENU) ? "_" + image.getObjectId() : ""),
			        image.getImageName());
		}
		return menuImageMap;
	}

	public static String getImageName(Long moduleId, Menu menu, Map<String, String> menuImageMap) {
		if (moduleId == null) {
			return MenuImage.DEFAULT_IMAGE_NAME;
		}
		StringBuilder key = new StringBuilder(moduleId.toString());
		if (null != menu.getObjectId()) {
			key.append("_" + menu.getObjectId());
		}
		String result = menuImageMap.get(key.toString());
		if (result == null) {
			result = menuImageMap.get(moduleId.toString());
			if (result == null) {
				result = MenuImage.DEFAULT_IMAGE_NAME;
			}
		}
		return result;
	}

	public static String getDefaultImageName(Integer moduleId, Menu menu, Map<String, String> menuImageMap) {
		if (moduleId == null) {
			return MenuImage.DEFAULT_IMAGE_NAME;
		}
		StringBuilder key = new StringBuilder(moduleId.toString());
		if (null != menu.getObjectId()) {
			key.append("_" + menu.getObjectId());
		}
		String result = menuImageMap.get(key.toString());
		if (result == null) {
			result = menuImageMap.get(moduleId.toString());
			if (result == null) {
				if (Menu.TYPE_FOLDER == menu.getType()) {
					result = "folder.gif";
				} else {
					result = "file.gif";
				}
			}
		}
		return result;
	}

	public static String getBigImageName(Long moduleId, Menu menu, Map<String, String> menuImageMap) {
		if (moduleId == null) {
			return MenuImage.DEFAULT_BIG_FOLDER_IMAGE_NAME;
		}
		StringBuilder key = new StringBuilder(moduleId.toString());
		if (null != menu.getObjectId()) {
			key.append("_" + menu.getObjectId());
		}
		String result = menuImageMap.get(key.toString());
		if (result == null) {
			result = menuImageMap.get(moduleId.toString());
			if (result == null) {
				result = MenuImage.DEFAULT_BIG_FOLDER_IMAGE_NAME;
			}
		}
		return result;
	}

	public static String generateDtreeHtml(List<Menu> menus, List<MenuImage> menuImages, Long moduleId,
	        String menuImageRootPath) {
		Map<String, String> menuImageMap = getMenuImageMap(menuImages);
		StringBuilder builder = new StringBuilder(200);
		for (Menu menu : menus) {
			builder.append("d.add(").append(menu.getObjectId()).append(",").append(menu.getParentId()).append(",'")
			        .append(menu.getName()).append("'");
			if (Menu.TYPE_MENU == menu.getType()) {
				builder.append(",'navToMenu(" + menu.getObjectId() + ")','','','','")
				        .append(DomainUtils.getStaticDomain()).append(menuImageRootPath)
				        .append(getImageName(moduleId, menu, menuImageMap)).append("'");
			} else {
				builder.append(",'void(0)'");
			}
			builder.append(");\n");
		}
		return builder.toString();
	}

	public static void generateBsTreeHtml(StringBuilder treeHtml, DefaultMutableTreeNode treeNode, String parent) {
		if (null != treeNode && null != treeNode.getUserObject()) {
			Menu menu = (Menu) treeNode.getUserObject();
			treeHtml.append(parent).append("=new Array;\n");
			parent += "['children'][0]";
			treeHtml.append(parent).append("=new Array;\n");
			treeHtml.append(parent).append("['caption']='").append(menu.getName()).append("';\n");
			treeHtml.append(parent).append("['isOpen']=false;\n");
			treeHtml.append(parent).append("['icon']='0.gif';\n");
			if (Menu.TYPE_MENU == menu.getType()) {
				treeHtml.append(parent).append("['onClickCaption']=moduleSwitch(").append(menu.getObjectId())
				        .append(");\n");
			}
			if (treeNode.getChildCount() > 0) {
				parent += parent + "['children']";
				Enumeration num = treeNode.children();
				int index = 0;
				while (num.hasMoreElements()) {
					generateBsTreeHtml(treeHtml, (DefaultMutableTreeNode) num.nextElement(), parent + "[" + (index++)
					        + "]");
				}
			}
		}
	}


	public static String generateTreeViewHtml(DefaultMutableTreeNode treeNode, List<MenuImage> menuImageList,
	        Long moduleId, String menuImageRootPath) {
		Map<String, String> menuImageMap = getMenuImageMap(menuImageList);
		StringBuilder treeHtml = new StringBuilder(500);
		generateHtml(treeHtml, treeNode, menuImageMap, moduleId, menuImageRootPath);
		return treeHtml.toString();
	}

	private static void generateHtml(StringBuilder treeHtml, DefaultMutableTreeNode treeNode,
	        Map<String, String> menuImageMap, Long moduleId, String menuImageRootPath) {
		if (null != treeNode && null != treeNode.getUserObject()) {
			Menu menu = (Menu) treeNode.getUserObject();
			treeHtml.append("<li>");
			String folder = "";
			if (Menu.TYPE_FOLDER == menu.getType()) {
				folder = "class=\"folder\"";
			}
			treeHtml.append("<span ").append(folder).append(" onclick=\"navToMenu(" + menu.getObjectId() + ")\">");
			treeHtml.append("<img width=\"15\" src=\"").append(menuImageRootPath)
			        .append(getImageName(moduleId, menu, menuImageMap)).append("\"></img>");
			treeHtml.append(menu.getName());
			treeHtml.append("</span>");
			if (treeNode.getChildCount() > 0) {
				treeHtml.append("<ul>");
				Enumeration num = treeNode.children();
				while (num.hasMoreElements()) {
					generateHtml(treeHtml, (DefaultMutableTreeNode) num.nextElement(), menuImageMap, moduleId,
					        menuImageRootPath);
				}
				treeHtml.append("</ul>");
			}
			treeHtml.append("</li>");
		}
	}

}
