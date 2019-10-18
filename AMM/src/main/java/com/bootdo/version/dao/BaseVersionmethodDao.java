package com.bootdo.version.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bootdo.version.domain.BaseBranchmethodDO;
import com.bootdo.version.domain.BaseKafkaCache;
import com.bootdo.version.domain.BaseVersionmethodDO;

/**
 * ${comments}
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
@Mapper
public interface BaseVersionmethodDao {


	List<Map<String, Object>> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	/**
	 * BASE_VERSIONMETHOD保存
	 * @param baseVersionmethodDO
	 * @return
	 */
	int save(BaseVersionmethodDO baseVersionmethodDO);


	
//	int update(BaseBranchmethodDO baseBranchmethod);
//
//	int remove(String PROJECT_ID);
//
//	int batchRemove(String[] projectIds);
	//更改方法运行状态和运行时间
	int updateMethodStatus(@Param("projectCode")String projectCode,@Param("branchName")String branchName,@Param("className")String className,@Param("methodName")String methodName,@Param("paramName")String paramName,@Param("time")Date time,@Param("version")String version);
	//计算绝对覆盖率的两个方法
	String exeMethodNum(@Param("beginVersionId")String beginVersionId,@Param("end")Date end,@Param("endVersionId")String endVersionId);
	Date getEndTime(@Param("endVersionId")String endVersionId);
	//计算相对覆盖率的两个方法
	String getMethodNum(@Param("beginVersionId")String beginVersionId,@Param("end")Date end,@Param("endVersionId")String endVersionId);
	String getExeMethodNum(@Param("beginVersionId")String beginVersionId,@Param("end")Date end,@Param("endVersionId")String endVersionId);

	int deleteFailedVersionMethod(Map<String,Object> map);
	int updateStatus(BaseKafkaCache baseKafkaCache);
	int addRemark(String versionId,String methodId,String remark);
}
