package com.bootdo.version.domain;

import com.bootdo.common.utils.TableTitle;
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
 * @date 2018-12-17
 */
public class BaseProjectversionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	//excel添加注解
	//$column.comments
	@TableTitle(name = "项目名称")
	private String name;
	//$column.comments
	@TableTitle(name = "相对覆盖率(状态)")
	private Double absolutelyCoverage;
	//$column.comments
	@TableTitle(name = "绝对覆盖率(状态)")
	private Double relativeCoverage;
	//$column.comments
	@TableTitle(name = "变更方法数")
	private Integer changeMethod;
	//$column.comments
	@TableTitle(name = "已测变更数")
	private Integer executeMethod;
	//$column.comments
	@TableTitle(name = "方法数")
	private Integer methodNumber;
	@TableTitle(name = "已测方法数")
	private String executeMethodA;
	private String projectId;
	//$column.comments
	private String id;
	//$column.comments
	private String version;
	//$column.comments
	private Integer releasePersonid;
	//$column.comments
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp releaseTime;
	//$column.comments
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp closeTime;
	//$column.comments
	private String responsiblePerson;
	//$column.comments
	private String responsiblePersonid;
	//$column.comments
	//private Integer methodNumber;
	//$column.comments
	//private Integer changeMethod;
	//$column.comments
	private Integer influenceMethod;
	//$column.comments
	//private Integer executeMethod;
	//$column.comments
	//private Double absolutelyCoverage;
	//$column.comments
	//private Double relativeCoverage;
	//$column.comments
	private String errorInfo;
	//$column.comments
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;
	//$column.comments
	private Integer state;
	//$column.comments
	private String versionType;

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
	public void setReleasePersonid(Integer releasePersonid) {
		this.releasePersonid = releasePersonid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getReleasePersonid() {
		return releasePersonid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Timestamp getReleaseTime() {
		return releaseTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Timestamp getCloseTime() {
		return closeTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setResponsiblePersonid(String responsiblePersonid) {
		this.responsiblePersonid = responsiblePersonid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getResponsiblePersonid() {
		return responsiblePersonid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMethodNumber(Integer methodNumber) {
		this.methodNumber = methodNumber;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getMethodNumber() {
		return methodNumber;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setChangeMethod(Integer changeMethod) {
		this.changeMethod = changeMethod;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getChangeMethod() {
		return changeMethod;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setInfluenceMethod(Integer influenceMethod) {
		this.influenceMethod = influenceMethod;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getInfluenceMethod() {
		return influenceMethod;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getExecuteMethod() {
		return executeMethod;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAbsolutelyCoverage(Double absolutelyCoverage) {
		this.absolutelyCoverage = absolutelyCoverage;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Double getAbsolutelyCoverage() {
		return absolutelyCoverage;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setRelativeCoverage(Double relativeCoverage) {
		this.relativeCoverage = relativeCoverage;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Double getRelativeCoverage() {
		return relativeCoverage;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getErrorInfo() {
		return errorInfo;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
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
	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
}
