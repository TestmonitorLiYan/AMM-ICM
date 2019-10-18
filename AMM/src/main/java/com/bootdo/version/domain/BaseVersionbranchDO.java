package com.bootdo.version.domain;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-27
 */
public class BaseVersionbranchDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String id;
	//$column.comments
	private String versionid;
	//$column.comments
	private String ismodify;
	//$column.comments
	private String status;
	//$column.comments
	private String msg;
	//$column.comments
	private String branch;
	//$column.comments
	private String branchid;
	//$column.comments
	private String path;
	//$column.comments
	private String remark;

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
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getVersionid() {
		return versionid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIsmodify(String ismodify) {
		this.ismodify = ismodify;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIsmodify() {
		return ismodify;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBranchid() {
		return branchid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getPath() {
		return path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
