package com.bootdo.version.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;



/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-13
 */
public class BaseProjectDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private String id;
	private String projectCode;
	//$column.comments
	private String name;
	//$column.comments
	private String domain;
	//$column.comments
	private String domainCode;
	//$column.comments
	private String supplier;
	//$column.comments
	private String supplierCode;
	//$column.comments
	private String supplierManagerId;
	//$column.comments
	private Integer state;
	//$column.comments
	private String version;
	//$column.comments
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date projectTime;
	//$column.comments
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Timestamp lastReleaseTime;
	//$column.comments
	private String supplierManager;

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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getName() {
		return name;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getDomainCode() {
		return domainCode;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getSupplierCode() {
		return supplierCode;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSupplierManagerId(String supplierManagerId) {
		this.supplierManagerId = supplierManagerId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getSupplierManagerId() {
		return supplierManagerId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getState() {
		return state;
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
	/**
	 * 设置：${column.comments}
	 */
	public void setProjectTime(Date projectTime) {
		this.projectTime = projectTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getProjectTime() {
		return projectTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setLastReleaseTime(Timestamp lastReleaseTime) {
		this.lastReleaseTime = lastReleaseTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Timestamp getLastReleaseTime() {
		return lastReleaseTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSupplierManager(String supplierManager) {
		this.supplierManager = supplierManager;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getSupplierManager() {
		return supplierManager;
	}
	

}
