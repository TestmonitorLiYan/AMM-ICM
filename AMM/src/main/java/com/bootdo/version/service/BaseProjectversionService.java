package com.bootdo.version.service;

import com.bootdo.version.domain.BaseProjectversionDO;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
public interface BaseProjectversionService {
	BaseProjectversionDO getByVersion(String versionId);
	BaseProjectversionDO get(String projectId);
	
	List<BaseProjectversionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseProjectversionDO baseProjectversion);
	
	int update(BaseProjectversionDO baseProjectversion);
	
	int remove(String projectId);
	
	int batchRemove(String[] projectIds);
	BaseProjectversionDO getLastFormalVersion(String projectId);
	BaseProjectversionDO getLastVersion(String projectId);
	/**
	 * 关闭版本
	 * @param projectId
	 * @param
	 * @return
	 */
	Map<String,Object> close(String projectId);
	//获得指定项目的最新状态
	BaseProjectversionDO getLastVersionState(String projectId);

	String getDefaultVersion(String projectId)throws Exception;

	List<Map<String,Object>> getChangeUrlbyVersionId(Map map);

	int getChangeUrlbyVersionIdCount(Map map);

}
