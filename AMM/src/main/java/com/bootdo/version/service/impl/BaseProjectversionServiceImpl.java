package com.bootdo.version.service.impl;

import com.bootdo.common.utils.DateUtils;
import com.bootdo.version.service.BaseVersionmethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import com.bootdo.version.dao.BaseProjectversionDao;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseProjectversionService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BaseProjectversionServiceImpl implements BaseProjectversionService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BaseProjectversionDao baseProjectversionDao;

	@Autowired
	private BaseVersionmethodService baseVersionmethodService;
	
	@Override
	public BaseProjectversionDO get(String projectId){
		return baseProjectversionDao.get(projectId);
	}
	
	@Override
	public BaseProjectversionDO getByVersion(String versionId) {
		return baseProjectversionDao.getByVersion(versionId);
	}
	
	@Override
	public List<BaseProjectversionDO> list(Map<String, Object> map){
		return baseProjectversionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseProjectversionDao.count(map);
	}
	
	@Override
	public int save(BaseProjectversionDO baseProjectversion){

		baseProjectversion.setState(1);//未同步
		return baseProjectversionDao.save(baseProjectversion);
	}
	
	@Override
	public int update(BaseProjectversionDO baseProjectversion){
		return baseProjectversionDao.update(baseProjectversion);
	}
	
	@Override
	public int remove(String projectId){
		return baseProjectversionDao.remove(projectId);
	}
	
	@Override
	public int batchRemove(String[] projectIds){
		return baseProjectversionDao.batchRemove(projectIds);
	}

	@Override
	public BaseProjectversionDO getLastFormalVersion(String projectId) {
		// TODO Auto-generated method stub
		return baseProjectversionDao.getLastFormalVersion(projectId);
	}

	@Override
	public BaseProjectversionDO getLastVersion(String projectId) {
		// TODO Auto-generated method stub
		return baseProjectversionDao.getLastVersion(projectId);
	}

	/**
	 * 关闭版本
	 * @param projectId
	 * @param version
	 * @return
	 */
	@Override
	@Transactional
	public Map<String, Object> close(String projectId) {
		Map<String,Object> map = new HashMap<>();
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("projectId",projectId);
			//获取上个版本（当前版本此时还没有创建）
			List<BaseProjectversionDO> baseProjectversionDOList = baseProjectversionDao.list(param);
			BaseProjectversionDO lastBaseProjectversionDO = baseProjectversionDOList.get(0);
			String lastVersionId = lastBaseProjectversionDO.getId();//上个版本id
			Map<String,Object> absoluteCoverageMap = baseVersionmethodService.getAbsolutelyCoverage(lastVersionId,lastVersionId);
			Map<String,Object> relativeCoverageMap = baseVersionmethodService.getRelativeCoverage(lastVersionId,lastVersionId);
			Double absoluteCoverage = Double.parseDouble(absoluteCoverageMap.get("absolutelyCoverage")+"");
			Double relativeCoverage = Double.parseDouble(relativeCoverageMap.get("relativeCoverage")+"");
			Integer executeNum = Integer.parseInt(absoluteCoverageMap.get("exeMethodNum")+"");
			BaseProjectversionDO baseProjectversionDO = baseProjectversionDao.get(lastVersionId);
			baseProjectversionDO.setAbsolutelyCoverage(absoluteCoverage);
			baseProjectversionDO.setRelativeCoverage(relativeCoverage);
			baseProjectversionDO.setExecuteMethod(executeNum);
			baseProjectversionDO.setState(4);//关闭
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			baseProjectversionDO.setCloseTime(timestamp);//设置关闭时间
			baseProjectversionDao.update(baseProjectversionDO);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public String getDefaultVersion(String projectId) throws Exception{
		try {
			Date date = new Date();
			String releaseDate = DateUtils.format(date,"yyyy-MM-dd");
			Map<String,Object> map = new HashMap<>();
			map.put("releaseDate",releaseDate);
			map.put("projectId",projectId);
			List list = baseProjectversionDao.list(map);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("V");
			stringBuffer.append(releaseDate.replace("-",""));
			stringBuffer.append(".");
			stringBuffer.append(list.size()+1);
			return stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取默认版本号出错!!!!!!!!");
		}
	}

	@Override
	public List<Map<String, Object>> getChangeUrlbyVersionId(Map map) {
		return baseProjectversionDao.getChangeUrlbyVersionId(map);
	}

	@Override
	public int getChangeUrlbyVersionIdCount(Map map) {
		return baseProjectversionDao.getChangeUrlbyVersionIdCount(map);
	}

	@Override
	public BaseProjectversionDO getLastVersionState(String projectId) {
		// TODO Auto-generated method stub
		return baseProjectversionDao.getLastVersionState(projectId);
	}

}
