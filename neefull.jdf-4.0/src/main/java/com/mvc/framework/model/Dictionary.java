package com.mvc.framework.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

@Entity(name = "F_DICTIONARY")
@Audited
public class Dictionary extends BaseEntity implements BaseTree {
	
	public static final Dictionary ROOT= new Dictionary();
	static {
		ROOT.setObjectId(BaseTree.ROOT);
		ROOT.setType(BaseTree.FOLDER);
		ROOT.setValue((int)BaseTree.ROOT);
		ROOT.setDictionaryId((int)BaseTree.ROOT);
		ROOT.setName("Dictionary");
	}
	
	private static final int MAX_NAME_SIZE = 128;
	/**
	 * A group dictionary with the same dictionary ID which input by user
	 * If it is the group member,this can be null
	 */
	private Integer dictionaryId;
	@NotNull
	private Long parentId;
	@NotNull
	@Length(min = 1, max = MAX_NAME_SIZE)
	private String name;
	/**
	 * Dictionary value
	 */
	@NotNull
	private Integer value;
	private String remark;
	/**
	 * The Sort value
	 */
	@NotNull
	private Integer sortId;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * status
	 */
	@NotNull
	private Boolean status;
	/**
	 * Reserver 1
	 */
	private String reserver1;
	/**
	 * Reserver 2
	 */
	private String reserver2;
	
	/**
	 * Type:Folder(1),Leaf(2)
	 */
	@NotNull
	private Integer type;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}


    public String toString() {
	    return String.valueOf(getObjectId());
    }

	public String getReserver1() {
    	return reserver1;
    }

	public void setReserver1(String reserver1) {
    	this.reserver1 = reserver1;
    }

	public String getReserver2() {
    	return reserver2;
    }

	public void setReserver2(String reserver2) {
    	this.reserver2 = reserver2;
    }

	public String getCode() {
    	return code;
    }

	public void setCode(String code) {
    	this.code = code;
    }

	public Boolean getStatus() {
    	return status;
    }

	public void setStatus(Boolean status) {
    	this.status = status;
    }

	public Integer getDictionaryId() {
    	return dictionaryId;
    }

	public void setDictionaryId(Integer dictionaryId) {
    	this.dictionaryId = dictionaryId;
    }

	public Integer getType() {
    	return type;
    }

	public void setType(Integer type) {
    	this.type = type;
    }
}
