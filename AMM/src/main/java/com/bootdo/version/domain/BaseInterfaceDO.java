package com.bootdo.version.domain;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-03-19
 */
public class BaseInterfaceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String id;
	//$column.comments
	private String projectId;
	//$column.comments
	private String branchId;
	//$column.comments
	private String methodId;
	//$column.comments
	private String url;

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
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMethodId() {
		return methodId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getUrl() {
		return url;
	}
}
