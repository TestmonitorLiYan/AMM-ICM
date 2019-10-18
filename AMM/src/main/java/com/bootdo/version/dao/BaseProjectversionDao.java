package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseProjectversionDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
@Mapper
public interface BaseProjectversionDao {
	BaseProjectversionDO getByVersion(String versionId);
	BaseProjectversionDO get(String id);
	
	List<BaseProjectversionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseProjectversionDO baseProjectversion);
	
	int update(BaseProjectversionDO baseProjectversion);
	
	int remove(String PROJECT_ID);
	
	int batchRemove(String[] projectIds);
	
	BaseProjectversionDO getLastFormalVersion(String projectId);
	BaseProjectversionDO getLastVersion(String projectId);
	BaseProjectversionDO getClose(String id);
	//获得指定项目的最新状态
	BaseProjectversionDO getLastVersionState(String projectId);

	List<Map<String,Object>> getChangeUrlbyVersionId(Map map);

	int getChangeUrlbyVersionIdCount(Map map);

}
