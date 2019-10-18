package com.bootdo.version.domain;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
public class BaseBranchmethodDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String projectId;
	//$column.comments
	private String branchId;
	//$column.comments
	private String id;
	//$column.comments
	private String packageName;
	//$column.comments
	private String className;
	//$column.comments
	private String methodName;
	//$column.comments
	private String methodParameter;
	//$column.comments
	private String methodReturn;
	//$column.comments
	private String methodCode;
	//$column.comments
	private String version;

	/**
	 * 设置：${column.comments}
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBranchId() {
		return branchId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMethodParameter(String methodParameter) {
		this.methodParameter = methodParameter;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMethodParameter() {
		return methodParameter;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMethodReturn(String methodReturn) {
		this.methodReturn = methodReturn;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMethodReturn() {
		return methodReturn;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMethodCode() {
		return methodCode;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getVersion() {
		return version;
	}
}
