package com.bootdo.version.dao;

import com.bootdo.version.domain.DirectInvokeDO;

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
public interface DirectInvokeDao {

	int save(DirectInvokeDO directInvoke);

	List<Map<String,Object>> getRelationList(Map<String,Object> map);

	int delete(Map<String,Object> map);
	List<Map<String,Object>> getTotalList(String endVersionId);
	List<DirectInvokeDO> list(Map<String,Object> map);


}
