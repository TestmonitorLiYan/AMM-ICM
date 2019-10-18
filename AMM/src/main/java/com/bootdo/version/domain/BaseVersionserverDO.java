package com.bootdo.version.domain;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-02-20
 */
public class BaseVersionserverDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String id;
	//$column.comments
	private String versionid;
	//$column.comments
	private String branch;
	//$column.comments
	private String remark;
	//$column.comments
	private String ip;
	//$column.comments
	private String path;
	//$column.comments
	private String status;
	//$column.comments
	private String basepackage;

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
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIp() {
		return ip;
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
	public void setBasepackage(String basepackage) {
		this.basepackage = basepackage;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getBasepackage() {
		return basepackage;
	}
}
