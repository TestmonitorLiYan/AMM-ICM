package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseProjectuserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-14
 */
@Mapper
public interface BaseProjectuserDao {

	BaseProjectuserDO get(String projectId);
	
	List<BaseProjectuserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Map<String,Object> map);
	
	int update(BaseProjectuserDO baseProjectuser);
	
	int remove(String value);
	
	int batchRemove(String[] projectIds);
}
