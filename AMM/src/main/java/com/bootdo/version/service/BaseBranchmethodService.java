package com.bootdo.version.service;

import com.bootdo.version.domain.BaseBranchmethodDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
public interface BaseBranchmethodService {
	
	BaseBranchmethodDO get(String projectId);
	
	List<BaseBranchmethodDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseBranchmethodDO baseBranchmethod);
	
	int update(BaseBranchmethodDO baseBranchmethod);
	
	int remove(String projectId);
	
	int batchRemove(String[] projectIds);

	List<Map<String,Object>> checkMethod(Map<String,Object> param);

	int deleteFailedBranchMethod(Map<String,Object> param);
	
	List<Map<String,Object>> getCurrentRelationList(String beginVersionId,String endVersionId);
	List<Map<String,Object>> getCurrentRelationList1(String beginVersionId,String endVersionId);
}
