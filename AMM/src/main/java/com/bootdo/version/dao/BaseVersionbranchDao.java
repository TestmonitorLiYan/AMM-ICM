package com.bootdo.version.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.version.domain.BaseVersionbranchDO;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-26
 */
@Mapper
public interface BaseVersionbranchDao {

	BaseVersionbranchDO get(String id);

	List<BaseVersionbranchDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(BaseVersionbranchDO baseVersionbranch);

	int update(BaseVersionbranchDO baseVersionbranch);

	int remove(String ID);

	int batchRemove(String[] ids);

	// 查询最新版本的发布状态
	BaseVersionbranchDO getStatus(String projectCode, String branchName, String version);

	BaseVersionbranchDO getPath(String branchId, String versionId);
}
