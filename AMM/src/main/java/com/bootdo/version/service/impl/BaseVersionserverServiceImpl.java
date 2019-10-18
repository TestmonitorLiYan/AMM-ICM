package com.bootdo.version.service.impl;

import com.bootdo.common.utils.UUIDUtils;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseProjectversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.version.dao.BaseVersionserverDao;
import com.bootdo.version.domain.BaseVersionserverDO;
import com.bootdo.version.service.BaseVersionserverService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BaseVersionserverServiceImpl implements BaseVersionserverService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BaseVersionserverDao baseVersionserverDao;
	@Autowired
	private BaseProjectversionService baseProjectversionService;
	
	@Override
	public BaseVersionserverDO get(String id){
		return baseVersionserverDao.get(id);
	}
	
	@Override
	public List<BaseVersionserverDO> list(Map<String, Object> map){
		return baseVersionserverDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baseVersionserverDao.count(map);
	}
	
	@Override
	public int save(BaseVersionserverDO baseVersionserver){
		String id = UUIDUtils.getUUID();
		baseVersionserver.setId(id);
		baseVersionserver.setStatus("0");//网络未联通
		return baseVersionserverDao.save(baseVersionserver);
	}
	
	@Override
	public int update(BaseVersionserverDO baseVersionserver){
		return baseVersionserverDao.update(baseVersionserver);
	}
	
	@Override
	public int remove(String id){
		return baseVersionserverDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return baseVersionserverDao.batchRemove(ids);
	}

	@Override
	public List<BaseVersionserverDO> lastVersionList(Map map) throws Exception{
		try {
			List<BaseVersionserverDO> baseVersionserverDOList = null;
			List<BaseProjectversionDO> baseProjectversionDOList = baseProjectversionService.list(map);
			if (baseProjectversionDOList == null || baseProjectversionDOList.size() == 0){
				return new ArrayList<BaseVersionserverDO>();
			}else{
				map.put("versionid", baseProjectversionDOList.get(0).getId());
				return baseVersionserverDao.list(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询上个版本server出错!!!!!!!!!");
		}
	}

	/**
	 * 项目解析时保存服务器信息
	 * @return
	 */
	//@Transactional
	@Override
	public Map<String, Object> save(Map map) throws Exception {
		try {
			String versionId = map.get("versionId")+"";//当前版本id
			int count = 0;
			List<Map<String,Object>> baseVersionserverDOList = (List<Map<String,Object>>)map.get("serverList");
			for (int x = 0 ; x < baseVersionserverDOList.size(); x++){
				Map temp = baseVersionserverDOList.get(x);
				BaseVersionserverDO baseVersionserverDO = new BaseVersionserverDO();
				baseVersionserverDO.setVersionid(versionId);
				baseVersionserverDO.setBasepackage(temp.get("basepackage")+"");
				baseVersionserverDO.setBranch(temp.get("branch")+"");
				baseVersionserverDO.setIp(temp.get("ip")+"");
				baseVersionserverDO.setRemark(temp.get("remark")+"");
				baseVersionserverDO.setPath(temp.get("path")+"");
				int result = 0;
				if (temp.containsKey("id") && temp.get("id") != null && !"".equals(temp.get("id")+"")){
					result = this.update(baseVersionserverDO);
				}else{
					result = this.save(baseVersionserverDO);
				}
				count += result;
				logger.info("count:"+count);
			}
			if (count == baseVersionserverDOList.size()){
				return new HashMap<>();
			}else{
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
