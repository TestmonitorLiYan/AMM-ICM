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
public class DirectInvokeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String invokeMethodid;
	//$column.comments
	private String beinvokeMethodid;
	private String projectId;

	private String branchId;

	/**
	 * 设置：${column.comments}
	 */
	public void setInvokeMethodid(String invokeMethodid) {
		this.invokeMethodid = invokeMethodid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getInvokeMethodid() {
		return invokeMethodid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBeinvokeMethodid(String beinvokeMethodid) {
		this.beinvokeMethodid = beinvokeMethodid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBeinvokeMethodid() {
		return beinvokeMethodid;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}
