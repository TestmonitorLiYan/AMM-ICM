package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseInterfaceDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2019-03-19
 */
@Mapper
public interface BaseInterfaceDao {

	BaseInterfaceDO get(String id);
	
	List<Map<String,Object>> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseInterfaceDO baseInterface);
	
	int update(BaseInterfaceDO baseInterface);
	
	int remove(String branchId);
	
	int batchRemove(String[] ids);
}
