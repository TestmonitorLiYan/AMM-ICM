package com.bootdo.version.service;

import java.util.List;
import java.util.Map;

import com.bootdo.version.domain.BaseVersionbranchDO;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-26
 */
public interface BaseVersionbranchService {

	BaseVersionbranchDO get(String id);

	List<BaseVersionbranchDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(BaseVersionbranchDO baseVersionbranch);

	int update(BaseVersionbranchDO baseVersionbranch);

	int remove(String id);

	int batchRemove(String[] ids);

	BaseVersionbranchDO getPath(String branchId, String versionId);
}
