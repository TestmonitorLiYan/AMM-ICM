package com.bootdo.version.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.version.dao.DirectInvokeDao;
import com.bootdo.version.domain.DirectInvokeDO;
import com.bootdo.version.service.DirectInvokeService;



@Service
public class DirectInvokeServiceImpl implements DirectInvokeService {
	@Autowired
	private DirectInvokeDao directInvokeDao;
	@Override
	public int save(DirectInvokeDO directInvoke){
		return directInvokeDao.save(directInvoke);
	}

	@Override
	public List<Map<String, Object>> getRelationList(Map<String, Object> map) {
		return directInvokeDao.getRelationList(map);
	}

	@Override
	public int delete(Map<String, Object> map) {
		return directInvokeDao.delete(map);
	}

	@Override
	public List<DirectInvokeDO> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return directInvokeDao.list(map);
	}
	@Override
	public List<Map<String,Object>> getTotalList(String endVersionId) {
		return directInvokeDao.getTotalList(endVersionId);
	}
}
