package com.bootdo.version.domain;

import com.bootdo.common.utils.TableTitle;

import java.io.Serializable;
public class MethodUrlDOtwo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableTitle(name = "访问地址")
    private String url;
    @TableTitle(name = "方法信息")
    private String methodinfo;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethodInfo() {
		return methodinfo;
	}
	public void setMethodInfo(String methodinfo) {
		this.methodinfo = methodinfo;
	}

   

  
}
