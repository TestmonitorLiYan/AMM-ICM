package com.bootdo.version.dao;

import com.bootdo.version.domain.IndirectInvokeDO;

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
public interface IndirectInvokeDao {

	IndirectInvokeDO get(String invokeMethodid);
	
	List<IndirectInvokeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IndirectInvokeDO indirectInvoke);
	
	int update(IndirectInvokeDO indirectInvoke);
	
	int remove(String INVOKE_METHODID);
	
	int batchRemove(String[] invokeMethodids);

	int delete(Map<String, Object> map);

	List<Map<String,Object>> getLowerList(String methodId);

	List<Map<String,Object>> getUpperList(String methodId);

	List<Map<String,Object>> getTotalList(String endVersionId);

	Integer getInvokeNum(Map<String, Object> map);
}
