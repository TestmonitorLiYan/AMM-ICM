package com.bootdo.version.service.impl;

import com.bootdo.common.config.Constant;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.version.dao.BaseProjectuserDao;
import com.bootdo.version.domain.BaseProjectuserDO;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseProjectuserService;
import com.bootdo.version.service.BaseProjectversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import com.bootdo.version.dao.BaseProjectDao;
import com.bootdo.version.domain.BaseProjectDO;
import com.bootdo.version.service.BaseProjectService;
import com.bootdo.version.service.BaseVersionmethodService;

import org.springframework.transaction.annotation.EnableTransactionManagement;


@Service
public class BaseProjectServiceImpl implements BaseProjectService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BaseProjectDao baseProjectDao;
	@Autowired
    private BaseVersionmethodService baseVersionmethodService;
	@Autowired
	private DictService dictService;
	@Autowired
	private BaseProjectversionService baseProjectversionService;
	@Override
	public BaseProjectDO get(String id){
		return baseProjectDao.get(id);
	}
	
	@Override
	public List<BaseProjectDO> list(Map<String, Object> map){
		UserDO userDO = (UserDO)ShiroUtils.getSubjct().getPrincipal();
		map.put("userId", userDO.getUserId());
		if("admin".equals(userDO.getRolesign())) {
			//超级管理员，查询全部
		}else if("Suppliermanager".equals(userDO.getRolesign())) {
			//非超级管理员，并且是供应商经理，查询自己供应商下的所有项目
			map.put("supplierCode",userDO.getSupplier());
			map.put("supplierManagerId",userDO.getUserId());
		}else if("Tester".equals(userDO.getRolesign())) {
			//测试人员，根据项目用户表，查询出自己有权限的项目
		}else {
			//都不是，默认是功能区管理员
			map.put("domainCode", userDO.getRolesign());
		}
		return baseProjectDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseProjectDao.count(map);
	}
	
	@Override
	public R save(BaseProjectDO baseProject){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("projectCode", baseProject.getProjectCode());
		List<BaseProjectDO> checkList = baseProjectDao.list(m);
		if (checkList!=null && checkList.size()> 0) {
				return R.error(Constant.STATUS_UNCHECKPASS,"项目code重复!");
		}
		String id = UUID.randomUUID().toString().replace("-","");
		baseProject.setId(id);//主键
		//baseProject.setState(1);//z状态：测试
		baseProject.setLastReleaseTime(new Timestamp(System.currentTimeMillis()));//最后发布时间
		if(baseProjectDao.save(baseProject)>0){
			return new R().put("id", baseProject.getId());
		}
		return R.error(Constant.STATUS_SERVERERROR,"项目创建失败!");
	}
	
	@Override
	public R update(BaseProjectDO baseProject){
		if (baseProject.getProjectCode() != null && !baseProject.getProjectCode().equals("")) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("projectCode", baseProject.getProjectCode());
			//m.put("projectId", baseProject.getId());
			List<BaseProjectDO> checkList = baseProjectDao.list(m);
			if (checkList!=null && checkList.size()> 0) {
				if (!(baseProject.getId().equals(checkList.get(0).getId()))){
					return R.error(Constant.STATUS_UNCHECKPASS,"项目code重复!");
				}
			}
		}
		if (baseProjectDao.update(baseProject)>0){
			return R.ok();
		}
		return R.error(Constant.STATUS_SERVERERROR,"项目保存失败!");
	}
	
	@Override
	public int remove(String id){
		return baseProjectDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return baseProjectDao.batchRemove(ids);
	}

	/**
	 * 查询未通过覆盖率指标的项目
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUnPassedProjectList() {
		UserDO userDO = (UserDO) ShiroUtils.getSubjct().getPrincipal();
		Map<String,Object> param = new HashMap<>();
		param.put("userId", userDO.getUserId());
		if("admin".equals(userDO.getRolesign())) {
			//超级管理员，查询全部
		}else if("Suppliermanager".equals(userDO.getRolesign())) {
			//非超级管理员，并且是供应商经理，查询自己供应商下的所有项目
			param.put("supplierCode",userDO.getSupplier());
			param.put("supplierManagerId",userDO.getUserId());
		}else if("Tester".equals(userDO.getRolesign())) {
			//测试人员，根据项目用户表，查询出自己有权限的项目
			
		}else {
			//都不是，默认是功能区管理员
			param.put("domainCode", userDO.getRolesign());
		}
		List<BaseProjectDO> baseProjectDOList = baseProjectDao.list(param);
		param.put("type","min_absolute_coverage");
		DictDO absolute = dictService.list(param).get(0);
		param.clear();
		param.put("type","min_relative_coverage");
		DictDO relative = dictService.list(param).get(0);
		int size = baseProjectDOList.size();
		List<Map<String,Object>> response = new ArrayList<>();
		for (int i = 0; i < size ; i++){
			Map<String,Object> temp = new HashMap<>();
			String projectId = baseProjectDOList.get(i).getId();
			BaseProjectversionDO lastVersion = baseProjectversionService.getLastVersion(projectId);
			BaseProjectversionDO lastFormalVersion = baseProjectversionService.getLastFormalVersion(projectId);
			logger.info("lastVersion:"+lastVersion);
			logger.info("lastFormalVersion:"+lastFormalVersion);
			Map<String,Object> absoluteCoverageMap = null;
			Map<String,Object> relativeCoverageMap = null;
			String startVersion = "";
			String endVersion = "";
			if (lastFormalVersion == null){
				if (lastVersion != null){
					startVersion = lastVersion.getId();
					endVersion = lastVersion.getId();
				}
			}else{
				startVersion = lastFormalVersion.getId();
				endVersion = lastVersion.getId();

			}
			if (!"".equals(startVersion) && !"".equals(endVersion)){
				//绝对覆盖率信息
				absoluteCoverageMap = baseVersionmethodService.getAbsolutelyCoverage(startVersion,endVersion);
				//相对覆盖率信息
				relativeCoverageMap = baseVersionmethodService.getRelativeCoverage(startVersion,endVersion);
				logger.info("absoluteCoverageMap:"+absoluteCoverageMap);
				logger.info("relativeCoverage:"+relativeCoverageMap);
				Double absolutelyCoverage = Double.parseDouble(absoluteCoverageMap.get("absolutelyCoverage")+"");
				Double relativeCoverage = Double.parseDouble(relativeCoverageMap.get("relativeCoverage")+"");
				logger.info("absolutelyCoverage:"+absolutelyCoverage);
				logger.info("relativeCoverage:"+relativeCoverage);
				logger.info("min1:"+absolute.getValue());
				logger.info("min2:"+relative.getValue());
				if (Double.parseDouble(absolute.getValue()+"") > absolutelyCoverage || Double.parseDouble(relative.getValue()+"") > relativeCoverage){
					temp.put("project",baseProjectDOList.get(i));
					temp.put("absolutelyCoverage",absolutelyCoverage);
					temp.put("relativeCoverage",relativeCoverage);
					if (Double.parseDouble(absolute.getValue()+"") > absolutelyCoverage){
						temp.put("description","绝对覆盖率未达标");
					}
					if (Double.parseDouble(relative.getValue()+"") > relativeCoverage){
						temp.put("description","相对覆盖率未达标");
					}
					response.add(temp);
				}
			}

		}
		logger.info("response:"+response);
		return response;
	}

	@Override
	public List<Map<String, Object>> listProjectVersion() {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		UserDO userDO = (UserDO)ShiroUtils.getSubjct().getPrincipal();
		map.put("userId", userDO.getUserId());
		if("admin".equals(userDO.getRolesign())) {
			//超级管理员，查询全部
		}else if("Suppliermanager".equals(userDO.getRolesign())) {
			//非超级管理员，并且是供应商经理，查询自己供应商下的所有项目
			map.put("supplierCode",userDO.getSupplier());
			map.put("supplierManagerId",userDO.getUserId());
		}else if("Tester".equals(userDO.getRolesign())) {
			//测试人员，根据项目用户表，查询出自己有权限的项目
		}else {
			//都不是，默认是功能区管理员
			map.put("domainCode", userDO.getRolesign());
		}
		List<Map<String, Object>> list=baseProjectDao.listProjectVersion(map);
		for(Map<String, Object> map1:list) {
			//绝对覆盖率信息
			Map<String,Object> absoluteCoverageMap = baseVersionmethodService.getAbsolutelyCoverage(map1.get("VERSION_ID").toString(),map1.get("VERSION_ID").toString());
			//相对覆盖率信息
			Map<String,Object> relativeCoverageMap = baseVersionmethodService.getRelativeCoverage(map1.get("VERSION_ID").toString(),map1.get("VERSION_ID").toString());
			map1.put("ABSOLUTELY_COVERAGE", absoluteCoverageMap.get("absolutelyCoverage"));
			map1.put("RELATIVE_COVERAGE", relativeCoverageMap.get("relativeCoverage"));
			map1.put("EXECUTE_METHOD_A", absoluteCoverageMap.get("exeMethodNum"));
			map1.put("EXECUTE_METHOD_R", relativeCoverageMap.get("exeMethodNum"));
		}
		return list;
	}
	
}
