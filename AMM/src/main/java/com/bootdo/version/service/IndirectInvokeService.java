package com.bootdo.version.service;

import com.bootdo.version.domain.IndirectInvokeDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
public interface IndirectInvokeService {
	int save(IndirectInvokeDO indirectInvoke);

	int delete(Map<String,Object> map);

	List<Map<String,Object>> getLowerList(String methodId);

	List<Map<String,Object>> getUpperList(String methodId);

	List<Map<String,Object>> getTotalList(String endVersionId);
	
	Integer getInvokeNum(Map<String,Object> map);
}
