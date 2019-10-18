package com.bootdo.version.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.bootdo.version.domain.BaseBranchDO;

/**
 * BaseBranch的service层接口，（工程/分支）
 * @author liyan
 *
 */
public interface BaseBranchService {
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
		//根据版本id查询加载页的信息
	    List<Map<String,Object>> getBranchListByVersionId(String versionId);
	    //本方法是用来保存新增的多个工程
	    int saveList(List<BaseBranchDO> params);
}
