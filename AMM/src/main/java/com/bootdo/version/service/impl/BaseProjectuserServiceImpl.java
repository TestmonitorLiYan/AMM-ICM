package com.bootdo.version.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.version.dao.BaseProjectuserDao;
import com.bootdo.version.domain.BaseProjectuserDO;
import com.bootdo.version.service.BaseProjectuserService;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseProjectuserServiceImpl implements BaseProjectuserService {
	@Autowired
	private BaseProjectuserDao baseProjectuserDao;
	
	@Override
	public BaseProjectuserDO get(String projectId){
		return baseProjectuserDao.get(projectId);
	}
	
	@Override
	public List<BaseProjectuserDO> list(Map<String, Object> map){
		return baseProjectuserDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseProjectuserDao.count(map);
	}

	@Transactional
	@Override
	public int save(Map<String,Object> map){
		try {
			String projectId = map.get("projectId")+"";
			List<Integer> ids = (ArrayList)map.get("ids");
			baseProjectuserDao.remove(projectId);
			int count = 1;
			for (int x = 0;x < ids.size();x++){
				Map<String,Object> param = new HashMap<>();
				param.put("projectId",projectId);
				param.put("userId",ids.get(x));
				baseProjectuserDao.save(param);
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int update(BaseProjectuserDO baseProjectuser){
		return baseProjectuserDao.update(baseProjectuser);
	}
	
	@Override
	public int remove(String projectId){
		return baseProjectuserDao.remove(projectId);
	}
	
	@Override
	public int batchRemove(String[] projectIds){
		return baseProjectuserDao.batchRemove(projectIds);
	}
	
}
