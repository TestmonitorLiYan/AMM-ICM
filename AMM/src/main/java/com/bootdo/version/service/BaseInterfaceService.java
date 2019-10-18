package com.bootdo.version.service;

import com.bootdo.version.domain.BaseInterfaceDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-03-19
 */
public interface BaseInterfaceService {
	
	BaseInterfaceDO get(String id);
	
	List<Map<String,Object>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseInterfaceDO baseInterface);
	
	int update(BaseInterfaceDO baseInterface);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
