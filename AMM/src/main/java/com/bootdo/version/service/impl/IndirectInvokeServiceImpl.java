package com.bootdo.version.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.version.dao.IndirectInvokeDao;
import com.bootdo.version.domain.IndirectInvokeDO;
import com.bootdo.version.service.IndirectInvokeService;



@Service
public class IndirectInvokeServiceImpl implements IndirectInvokeService {
	@Autowired
	private IndirectInvokeDao indirectInvokeDao;
	
	@Override
	public int save(IndirectInvokeDO indirectInvoke){
		return indirectInvokeDao.save(indirectInvoke);
	}

	@Override
	public int delete(Map<String, Object> map) {
		return indirectInvokeDao.delete(map);
	}

	@Override
	public List<Map<String, Object>> getLowerList(String methodId) {
		return indirectInvokeDao.getLowerList(methodId);
	}

	@Override
	public List<Map<String, Object>> getUpperList(String methodId) {
		return indirectInvokeDao.getUpperList(methodId);
	}

	@Override
	public List<Map<String,Object>> getTotalList(String endVersionId) {
		return indirectInvokeDao.getTotalList(endVersionId);
	}

	@Override
	public Integer getInvokeNum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return indirectInvokeDao.getInvokeNum(map);
	}


}
