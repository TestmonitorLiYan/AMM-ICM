package com.bootdo.version.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.version.dao.BaseVersionbranchDao;
import com.bootdo.version.domain.BaseVersionbranchDO;
import com.bootdo.version.service.BaseVersionbranchService;

@Service
public class BaseVersionbranchServiceImpl implements BaseVersionbranchService {
	@Autowired
	private BaseVersionbranchDao baseVersionbranchDao;

	@Override
	public BaseVersionbranchDO get(String id) {
		return baseVersionbranchDao.get(id);
	}

	@Override
	public List<BaseVersionbranchDO> list(Map<String, Object> map) {
		return baseVersionbranchDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return baseVersionbranchDao.count(map);
	}

	@Override
	public int save(BaseVersionbranchDO baseVersionbranch) {
		return baseVersionbranchDao.save(baseVersionbranch);
	}

	@Override
	public int update(BaseVersionbranchDO baseVersionbranch) {
		return baseVersionbranchDao.update(baseVersionbranch);
	}

	@Override
	public int remove(String id) {
		return baseVersionbranchDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return baseVersionbranchDao.batchRemove(ids);
	}

	@Override
	public BaseVersionbranchDO getPath(String branchId, String versionId) {
		return baseVersionbranchDao.getPath(branchId, versionId);
	}

}
