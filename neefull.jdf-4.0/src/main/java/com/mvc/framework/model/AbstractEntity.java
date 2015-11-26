package com.mvc.framework.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Define the base action for entity.
 *
 * @author Bob.Pu
 *
 */
@AccessType("field")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1278542044103959022L;
	@Transient
	private transient String tableName;

	@Override
	public String toString() {
		return String.valueOf(getObjectId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getObjectId() == null) ? 0 : getObjectId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		if (getObjectId() == null) {
			if (other.getObjectId() != null) {
				return false;
			}

		} else if (!getObjectId().equals(other.getObjectId())) {
			return false;
		}
		return true;
	}

	@Transient
	public String getTableName() {
		return tableName;
	}

	public AbstractEntity() {
		Entity table = this.getClass().getAnnotation(Entity.class);
		this.tableName = table.name();
	}

	/**
	 *
	 * @return The entity's primary key
	 */
	public abstract Long getObjectId();

	/**
	 * Set the entity's primary key
	 * @param objectId
	 */
	public abstract void setObjectId(Long objectId);
}
