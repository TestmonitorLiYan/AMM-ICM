package com.bootdo.version.domain;
/**
 * BaseBranch的实体类，（项目工程/分支）。对应数据库表BASE_BRANCH
 * @author Liyan
 *
 */
public class BaseBranchDO {
	private String projectId;//项目ID
	private String id;//分支/工程ID
	private String name;//分支/工程名
	private String remarks;//备注
	private String svnAddress;//svn地址
	private String svnNumber;//svn账号
	private String svnPassword;//svn密码
	private String ip;//服务器ip
	private String port;//服务端口号
	private String path;//agent配置文件保存根目录
	private String basePackage;//基本包名
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSvnAddress() {
		return svnAddress;
	}
	public void setSvnAddress(String svnAddress) {
		this.svnAddress = svnAddress;
	}
	public String getSvnNumber() {
		return svnNumber;
	}
	public void setSvnNumber(String svnNumber) {
		this.svnNumber = svnNumber;
	}
	public String getSvnPassword() {
		return svnPassword;
	}
	public void setSvnPassword(String svnPassword) {
		this.svnPassword = svnPassword;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
}
