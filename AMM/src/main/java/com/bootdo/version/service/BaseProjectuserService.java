package com.bootdo.version.service;

import com.bootdo.version.domain.BaseProjectuserDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-14
 */
public interface BaseProjectuserService {
	
	BaseProjectuserDO get(String projectId);
	
	List<BaseProjectuserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Map<String,Object> map);
	
	int update(BaseProjectuserDO baseProjectuser);
	
	int remove(String projectId);
	
	int batchRemove(String[] projectIds);
}
