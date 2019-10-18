package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseVersionserverDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2019-02-20
 */
@Mapper
public interface BaseVersionserverDao {

	BaseVersionserverDO get(String id);
	
	List<BaseVersionserverDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseVersionserverDO baseVersionserver);
	
	int update(BaseVersionserverDO baseVersionserver);
	
	int remove(String ID);
	
	int batchRemove(String[] ids);
	
	BaseVersionserverDO getByversionIdIp(String versionId,String ip);
}
