package com.mvc.framework.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Basic entity
 * The primary key is generated automatically
 * @author Bob.Pu
 *
 */
@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.NONE)
@AccessType("field")
public class BaseEntity extends AbstractEntity implements Identifiable {

	private static final long serialVersionUID = -348284149697647226L;

	/**
	 * Primary key
	 * Generated by table <code>id_sequences</code>
	 */
	@GenericGenerator(
			strategy="org.hibernate.id.MultipleHiLoPerTableGenerator",
			name="hiloTable",
			parameters = { @Parameter(name = "max_lo", value = "20"),@Parameter(name = "table", value = "id_sequences")} )
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="hiloTable")
	private Long objectId;

	@Override
	public Long getObjectId() {
		return objectId;
	}

	@Override
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
}