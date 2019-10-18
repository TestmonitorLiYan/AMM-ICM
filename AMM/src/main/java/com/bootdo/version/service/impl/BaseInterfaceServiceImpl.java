package com.bootdo.version.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.version.dao.BaseInterfaceDao;
import com.bootdo.version.domain.BaseInterfaceDO;
import com.bootdo.version.service.BaseInterfaceService;



@Service
public class BaseInterfaceServiceImpl implements BaseInterfaceService {
	@Autowired
	private BaseInterfaceDao baseInterfaceDao;
	
	@Override
	public BaseInterfaceDO get(String id){
		return baseInterfaceDao.get(id);
	}
	
	@Override
	public List<Map<String,Object>> list(Map<String, Object> map){
		return baseInterfaceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseInterfaceDao.count(map);
	}
	
	@Override
	public int save(BaseInterfaceDO baseInterface){
		return baseInterfaceDao.save(baseInterface);
	}
	
	@Override
	public int update(BaseInterfaceDO baseInterface){
		return baseInterfaceDao.update(baseInterface);
	}
	
	@Override
	public int remove(String id){
		return baseInterfaceDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return baseInterfaceDao.batchRemove(ids);
	}
	
}
