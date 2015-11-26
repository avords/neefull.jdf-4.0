package com.mvc.framework.model;

/**
 * Tree date interface
 * See@TreeUtils
 * @author pubx
 *
 */
public interface BaseTree {
	public static final long ROOT = -1;
	public static final int FOLDER = 1;
	public static final int FILE = 2;
	/**
	 * @return parent ID
	 */
	public Long getParentId();
	/**
	 * @return Object ID
	 */
	public Long getObjectId();
}
