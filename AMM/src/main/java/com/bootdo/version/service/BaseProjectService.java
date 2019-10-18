package com.bootdo.version.service;

import com.bootdo.common.utils.R;
import com.bootdo.version.domain.BaseProjectDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-13
 */
public interface BaseProjectService {
	
	BaseProjectDO get(String id);
	
	List<BaseProjectDO> list(Map<String, Object> map);
	List<Map<String, Object>> listProjectVersion();
	int count(Map<String, Object> map);
	
	R save(BaseProjectDO baseProject);
	
	R update(BaseProjectDO baseProject);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	List<Map<String,Object>> getUnPassedProjectList();
}
