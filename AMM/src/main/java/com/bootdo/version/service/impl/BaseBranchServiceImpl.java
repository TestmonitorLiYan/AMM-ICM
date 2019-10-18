package com.bootdo.version.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootdo.common.config.Constant;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.UUIDUtils;
import com.bootdo.version.dao.BaseBranchDao;
import com.bootdo.version.domain.BaseBranchDO;
import com.bootdo.version.service.BaseBranchService;

@Transactional
@Service
public class BaseBranchServiceImpl implements BaseBranchService {
	@Autowired
	private BaseBranchDao baseBranchDao;
	@Override
	public BaseBranchDO get(String id) {
		// TODO Auto-generated method stub
		return baseBranchDao.get(id);
	}

	@Override
	public int save( BaseBranchDO baseBranchDo) {
		// TODO Auto-generated method stub
		return baseBranchDao.save(baseBranchDo);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return baseBranchDao.delete(id);
	}

	@Override
	public int update(BaseBranchDO baseBranchDo) {
		// TODO Auto-generated method stub
		return baseBranchDao.update(baseBranchDo);
	}

	@Override
	public List<BaseBranchDO> getByProjectId(String projectId) {
		// TODO Auto-generated method stub
		return baseBranchDao.getByProjectId(projectId);
	}

	@Override
	public BaseBranchDO getName(String projectId, String name) {
		// TODO Auto-generated method stub
		return baseBranchDao.getName(projectId, name);
	}

	@Override
	public int deleteByProjectId(String projectId) {
		// TODO Auto-generated method stub
		return baseBranchDao.deleteByProjectId(projectId);
	}

	@Override
	public List<Map<String, Object>> getBranchListByVersionId(String versionId) {
		return baseBranchDao.getBranchListByVersionId(versionId);
	}

	@Override
	public int saveList(List<BaseBranchDO> params) {
		// TODO Auto-generated method stub
		int resultAll=0;
		for(BaseBranchDO baseBranchDO:params) {
			String name=baseBranchDO.getName();
			String projectId=baseBranchDO.getProjectId();
			BaseBranchDO Branch=baseBranchDao.getName(projectId, name);
			if(Branch!=null) {//查询同一个项目中没有相同名字的分支
				return -2;
			}
			}
		for(BaseBranchDO baseBranchDO:params) {
			String name=baseBranchDO.getName();
			String projectId=baseBranchDO.getProjectId();
			BaseBranchDO Branch=baseBranchDao.getName(projectId, name);
			if(Branch==null) {//查询同一个项目中没有相同名字的分支
				//进行保存
				String UUID=UUIDUtils.getUUID();
				baseBranchDO.setId(UUID);
				int result=baseBranchDao.save(baseBranchDO);
				if(result>0) {//保存成功
					resultAll=1;
				}else{//保存失败，影响行数0		
					resultAll=0;
				}
			}else {//有相同名字的分支，返回信息该工程名已存在
				return -2;
			}
			}
		return resultAll;
	}
}
