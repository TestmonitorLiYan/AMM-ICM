package com.bootdo.version.dao;

import com.bootdo.version.domain.BaseBranchmethodDO;

import java.util.Date;
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
public interface BaseBranchmethodDao {

	BaseBranchmethodDO get(String projectId);
	
	List<BaseBranchmethodDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BaseBranchmethodDO baseBranchmethod);
	
	int update(BaseBranchmethodDO baseBranchmethod);
	
	int remove(String PROJECT_ID);
	
	int batchRemove(String[] projectIds);

	/**
	 * 方法校验
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> checkMethod(Map<String,Object> param);

	/**
	 * 插入调用关系时查询方法id
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMethodByElement(Map<String,Object> param);

	int deleteFailedBranchMethod(Map<String,Object> param);
	
	List<Map<String,Object>> getCurrentRelationList(String beginVersionId,Date end,String endVersionId);
	List<Map<String,Object>> getCurrentRelationList1(String beginVersionId,Date end,String endVersionId);
}
