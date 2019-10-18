package com.bootdo.version.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.version.domain.BaseBranchDO;

/**
 * BaseBranch的Dao层接口，（项目工程/分支）。
 * @author Administrator
 *
 */
@Mapper
public interface BaseBranchDao {
	//根据ID获得工程信息
	BaseBranchDO get(String id);
	//根据项目ID添加工程
	int save(BaseBranchDO baseBranchDo);
	//根据ID删除工程
	int delete(String id);
	//更改工程信息
	int update(BaseBranchDO baseBranchDo);
	//根据项目ID获取工程信息
	List<BaseBranchDO> getByProjectId(String projectId);
	//根据项目ID查询工程name是否存在
	BaseBranchDO getName(String projectId,String name);
	/**
	 * 本方法，根据项目id，删除该项目下的所有工程。用于项目删除功能
	 */
	int deleteByProjectId(String projectId);

	List<Map<String,Object>> getBranchListByVersionId(String versionId);
}
