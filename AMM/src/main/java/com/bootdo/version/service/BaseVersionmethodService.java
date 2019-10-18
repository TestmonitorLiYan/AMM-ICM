package com.bootdo.version.service;

import java.util.List;
import java.util.Map;

import com.bootdo.version.domain.BaseVersionmethodDO;

public interface BaseVersionmethodService {
//	//根据ID获得版本方法信息
//	BaseVersionmethodDO get(String id);
	//获取版本方法数据
	List<Map<String, Object>> list(Map<String, Object> map);
	//获取版本方法数量
	int count(Map<String, Object> map);
	
    int save(BaseVersionmethodDO baseVersionmethodDO);
    Map<String, Object> getAbsolutelyCoverage(String beginVersionId,String endVersionId);
    Map<String, Object> getRelativeCoverage(String beginVersionId,String endVersionId);

    int deleteFailedVersionMethod(Map<String,Object> map);
    int addRemark(String versionId,String methodId,String remark);
}
