package com.bootdo.version.service;

import com.bootdo.version.domain.BaseVersionserverDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-02-20
 */
public interface BaseVersionserverService {
	
	BaseVersionserverDO get(String id);
	
	List<BaseVersionserverDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseVersionserverDO baseVersionserver);
	
	int update(BaseVersionserverDO baseVersionserver);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<BaseVersionserverDO> lastVersionList(Map map) throws Exception;

	Map<String,Object> save(Map map) throws Exception;
}
