package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseProjectDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-13
 */
@Mapper
public interface BaseProjectDao {

	BaseProjectDO get(String id);
	BaseProjectDO getByProjectCode(String projectCode);
	List<BaseProjectDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseProjectDO baseProject);
	
	int update(BaseProjectDO baseProject);
	
	int remove(String ID);
	
	int batchRemove(String[] ids);
	
	List<Map<String, Object>> listProjectVersion(Map<String, Object> map);
}
