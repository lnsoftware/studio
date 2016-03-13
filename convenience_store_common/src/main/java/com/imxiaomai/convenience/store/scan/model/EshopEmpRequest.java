package com.imxiaomai.convenience.store.scan.model;

import java.util.Date;

/**
 * EshopEmpRequest：用户请求参数
 * 
 * @author zhc2054
 * @version 2015-8-4 19:16:22
 * 
 */
public class EshopEmpRequest extends BaseRequest {

    /** 序列化标识 */
    private static final long serialVersionUID = 1L;
    
	private Long id;
	
	private String empName;
	
	private String empNo;
	
	private String eshopName;
	
	private String eshopNo;
	
	private String empAccount;
	
	private String empPwd;
	
	private String empTel;
	
	private Integer empType;
    /**  */
	private Date createTime; 
    /**  */
	private String createUser; 
    /**  */
	private Date updateTime; 
    /**  */
	private String updateUser; 
    /** 1 有效 0 无效 */
	private Integer yn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEshopName() {
		return eshopName;
	}
	public void setEshopName(String eshopName) {
		this.eshopName = eshopName;
	}
	public String getEshopNo() {
		return eshopNo;
	}
	public void setEshopNo(String eshopNo) {
		this.eshopNo = eshopNo;
	}
	public String getEmpAccount() {
		return empAccount;
	}
	public void setEmpAccount(String empAccount) {
		this.empAccount = empAccount;
	}
	public String getEmpPwd() {
		return empPwd;
	}
	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd;
	}
	public String getEmpTel() {
		return empTel;
	}
	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}
	public Integer getEmpType() {
		return empType;
	}
	public void setEmpType(Integer empType) {
		this.empType = empType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
}
