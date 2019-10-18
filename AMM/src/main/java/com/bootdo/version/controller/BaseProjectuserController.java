package com.bootdo.version.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.BaseProjectuserDO;
import com.bootdo.version.service.BaseProjectuserService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-14
 */
 
@RestController
@RequestMapping(value = "/version/baseProjectuser",produces = "application/json; charset=UTF-8")
public class BaseProjectuserController {
	@Autowired
	private BaseProjectuserService baseProjectuserService;

	@Autowired
	private UserService userService;

	/**
	 * 查询所有人员以及项目被选取人员
	 * @param id
	 * @return
	 */
	@GetMapping("/list/{id}")
	//@RequiresPermissions("version:baseProjectuser:baseProjectuser")
	public R list(@PathVariable("id") String id){
		//查询列表数据
		PageUtils pageUtils = null;
		Map<String,Object> resultMap = new HashMap<>();
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("projectId",id);
			List<BaseProjectuserDO> baseProjectuserList = baseProjectuserService.list(param);//被选取人员id列表
			param.clear();
			List<UserDO> userList = userService.list(param);//全部人员列表
			List<UserDO> selectedUserList = new ArrayList<UserDO>();
			for (int x = 0 ; x < userList.size();x++){
				UserDO userDO = userList.get(x);
				Long selectedUserId = userDO.getUserId();
				for (int j = 0 ; j < baseProjectuserList.size();j++){
					BaseProjectuserDO baseProjectu = baseProjectuserList.get(j);
					if (selectedUserId == Long.parseLong(baseProjectu.getUserId()+"")){
						//用户被选中
						selectedUserList.add(userDO);
					}
				}
			}
			resultMap.put("userList",userList);
			resultMap.put("selectedUserList",selectedUserList);
			return R.ok(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}
	

	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	//@RequiresPermissions("version:baseProjectuser:add")
	public R save( @RequestBody Map<String,Object> body){

		if(baseProjectuserService.save(body)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("version:baseProjectuser:edit")
	public R update(BaseProjectuserDO baseProjectuser){
		baseProjectuserService.update(baseProjectuser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	//@RequiresPermissions("version:baseProjectuser:remove")
	public R remove( String projectId){
		if(baseProjectuserService.remove(projectId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	//@RequiresPermissions("version:baseProjectuser:batchRemove")
	public R remove(@RequestParam("ids[]") String[] projectIds){
		baseProjectuserService.batchRemove(projectIds);
		return R.ok();
	}
	
}
