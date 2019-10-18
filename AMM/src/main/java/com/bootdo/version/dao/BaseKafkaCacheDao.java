package com.bootdo.version.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.version.domain.BaseKafkaCache;

/**
 * 将暂时不能消费的消息进行存储
 * @author liyan
 *
 */
@Mapper
public interface BaseKafkaCacheDao {
	//存储方法
	int save(String id,String projectCode,String branchName,String className,String methodName,String methodParameter,Date executeTime,String version,Date now,String ip);
	//存储之前进行是否重复的查询
	int repeat(String projectCode,String branchName,String className,String methodName,String methodParameter,String version,String ip);
	//查询所有记录
	List<BaseKafkaCache> list();
	//删除单条记录
	int remove(String id);
	//删除所有时间超过一月的信息
	int removeByTimeOut();
}
