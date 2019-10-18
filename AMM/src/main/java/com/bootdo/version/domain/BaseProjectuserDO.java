package com.bootdo.version.domain;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-14
 */
public class BaseProjectuserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String projectId;
	//$column.comments
	private Integer userId;

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
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getUserId() {
		return userId;
	}
}
