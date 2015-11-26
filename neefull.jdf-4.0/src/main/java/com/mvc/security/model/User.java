package com.mvc.security.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mvc.framework.model.BaseEntity;
import com.sun.istack.NotNull;

@Entity(name = "F_USER")
@Audited
public class User extends BaseEntity {
	public static final int	STATUS_VALID	= 1;
	public static final int	STATUS_INVALID	= 0;
	
	@NotEmpty
	@Column(unique = true, length = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String loginName;
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	@NotEmpty
	private String userName;
	@NotNull
	private Integer status;
	private Integer type;
	@NotEmpty
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String email;
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String password;

	private Long departmentId;
	@Length(max=64)
	private String departmentLayer;

	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String phone;
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String mobilePhone;
	@Length(max = com.mvc.Constants.ModelDefine.LARGE_LENGTH)
	private String address;

	private Integer birthday;
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String city;
	private Integer sex;
	@Length(max = com.mvc.Constants.ModelDefine.LARGER_LENGTH)
	private String remark;

	@NotNull
	private Date createDate;
	
	private Integer reserved1;

	private Integer reserved2;

	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String reserved3;
	@Length(max = com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String reserved4;

	private Boolean reserved5;
	
	private Double reserved6;
	private Double reserved7;
	
	@Length(max=com.mvc.Constants.ModelDefine.MEDIUM_LENGTH)
	private String photo;
	
	@Transient
	private Department department;
	@Transient
	private List<Menu> menus = new ArrayList<Menu>();
	@Transient
	private List<Operation> operations = new ArrayList<Operation>();
	@Transient
	private List<DimLocation> dimLocations = new ArrayList<DimLocation>();
	@Transient
	private List<Role> roles = new ArrayList<Role>();
	@Transient
	private String fullName;
	@Transient
	private String photoUrl;
	@Transient
	private String photoExtension;

	@Transient
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getBirthday() {
		return birthday;
	}

	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public List<DimLocation> getDimLocations() {
		return dimLocations;
	}

	public void setDimLocations(List<DimLocation> dimLocations) {
		this.dimLocations = dimLocations;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public Integer getReserved2() {
		return reserved2;
	}

	public void setReserved2(Integer reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDepartmentLayer() {
		return departmentLayer;
	}

	public void setDepartmentLayer(String departmentLayer) {
		this.departmentLayer = departmentLayer;
	}

	public Boolean isReserved5() {
		return reserved5;
	}

	public void setReserved5(Boolean reserved5) {
		this.reserved5 = reserved5;
	}

	public Boolean getReserved5() {
		return reserved5;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
		this.photoUrl = null;
		this.photoExtension = null;
	}

	public String getPhotoUrl() {
		if(photoUrl==null ){
			if(StringUtils.isNotBlank(photo)&&photo.trim().length()>0&&photo.indexOf(".")!=-1){
				photoUrl= photo.substring(0,photo.lastIndexOf("."));
			}else{
				photoUrl= "/picture/user_profile";
			}
		}
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoExtension() {
		if(photoExtension==null){
			if(StringUtils.isNotBlank(photo)&&photo.trim().length()>0&&photo.indexOf(".")!=-1){
				photoExtension = photo.substring(photo.lastIndexOf("."));
			}else{
				photoExtension = ".png";
			}
		}
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}

	public Double getReserved6() {
		return reserved6;
	}

	public void setReserved6(Double reserved6) {
		this.reserved6 = reserved6;
	}

	public Double getReserved7() {
		return reserved7;
	}

	public void setReserved7(Double reserved7) {
		this.reserved7 = reserved7;
	}
}
