package com.bootdo.version.domain;

import com.bootdo.common.utils.TableTitle;

import java.io.Serializable;
public class MethodUrlDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableTitle(name = "序号")
    private Integer num;

    @TableTitle(name = "参数类型")
    private String methodParameter;

    @TableTitle(name = "状态")
    private Integer state;

    @TableTitle(name = "变更时间")
    private String updateTime;

    @TableTitle(name = "类名")
    private String className;

    @TableTitle(name = "访问地址")
    private String url;

    @TableTitle(name = "方法id")
    private String methodId;

    @TableTitle(name = "版本id")
    private String versionId;

    @TableTitle(name = "返回值类型")
    private String methodReturn;

    @TableTitle(name = "地址id")
    private String interfaceId;

    @TableTitle(name = "版本")
    private String version;

    @TableTitle(name = "方法名称")
    private String methodName;

    @TableTitle(name = "项目id")
    private String projectId;

    @TableTitle(name = "工程id")
    private String branchId;


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(String methodParameter) {
        this.methodParameter = methodParameter;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getMethodReturn() {
        return methodReturn;
    }

    public void setMethodReturn(String methodReturn) {
        this.methodReturn = methodReturn;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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
