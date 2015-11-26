package com.mvc.framework.db;
/**
 *
 * @author	pubx <br>
 * @create	2008-12-18 <br>
 */
public abstract class AbstractDialect implements Dialect {

	public boolean supportsLimit() {
		return true;
	}

}
