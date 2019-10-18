package com.bootdo.version.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bootdo.version.dao.BaseBranchmethodDao;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.domain.BaseBranchmethodDO;
import com.bootdo.version.service.BaseBranchmethodService;



@Service
public class BaseBranchmethodServiceImpl implements BaseBranchmethodService {
	@Autowired
	private BaseBranchmethodDao baseBranchmethodDao;
	@Autowired
    private BaseVersionmethodDao baseVersionmethodDao;
	@Override
	public BaseBranchmethodDO get(String projectId){
		return baseBranchmethodDao.get(projectId);
	}
	
	@Override
	public List<BaseBranchmethodDO> list(Map<String, Object> map){
		return baseBranchmethodDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseBranchmethodDao.count(map);
	}
	
	@Override
	public int save(BaseBranchmethodDO baseBranchmethod){
//		String id = UUID.randomUUID().toString().replace("-","");
//		baseBranchmethod.setId(id);//设置id
		return baseBranchmethodDao.save(baseBranchmethod);
	}
	
	@Override
	public int update(BaseBranchmethodDO baseBranchmethod){
		return baseBranchmethodDao.update(baseBranchmethod);
	}
	
	@Override
	public int remove(String projectId){
		return baseBranchmethodDao.remove(projectId);
	}
	
	@Override
	public int batchRemove(String[] projectIds){
		return baseBranchmethodDao.batchRemove(projectIds);
	}

	@Override
	public List<Map<String, Object>> checkMethod(Map<String, Object> param) {
		return baseBranchmethodDao.checkMethod(param);
	}

	@Override
	public int deleteFailedBranchMethod(Map<String, Object> param) {
		return baseBranchmethodDao.deleteFailedBranchMethod(param);
	}

	@Override
	public List<Map<String, Object>> getCurrentRelationList(String beginVersionId, String endVersionId) {
		// TODO Auto-generated method stub
		 Date end=baseVersionmethodDao.getEndTime(endVersionId);
			if(end==null) {
				end=new Date();
			}
		List<Map<String,Object>> result=baseBranchmethodDao.getCurrentRelationList(beginVersionId,end, endVersionId);
		return result;
	}
	@Override
	public List<Map<String, Object>> getCurrentRelationList1(String beginVersionId, String endVersionId) {
		// TODO Auto-generated method stub
		 Date end=baseVersionmethodDao.getEndTime(endVersionId);
			if(end==null) {
				end=new Date();
			}
		List<Map<String,Object>> result=baseBranchmethodDao.getCurrentRelationList1(beginVersionId,end, endVersionId);
		return result;
	}

}
