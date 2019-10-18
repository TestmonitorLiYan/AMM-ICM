package com.bootdo.version.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.BaseInterfaceDO;
import com.bootdo.version.service.BaseInterfaceService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-03-19
 */
 
@RestController
@RequestMapping("/version/baseInterface")
public class BaseInterfaceController {
	@Autowired
	private BaseInterfaceService baseInterfaceService;
	
	
	@GetMapping("/list")
	@RequiresPermissions("version:baseInterface:baseInterface")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map<String,Object>> baseInterfaceList = baseInterfaceService.list(query);
		int total = baseInterfaceService.count(query);
		PageUtils pageUtils = new PageUtils(baseInterfaceList, total);
		return pageUtils;
	}
	

	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("version:baseInterface:add")
	public R save( BaseInterfaceDO baseInterface){
		if(baseInterfaceService.save(baseInterface)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("version:baseInterface:edit")
	public R update( BaseInterfaceDO baseInterface){
		baseInterfaceService.update(baseInterface);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@RequiresPermissions("version:baseInterface:remove")
	public R remove( String id){
		if(baseInterfaceService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@RequiresPermissions("version:baseInterface:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		baseInterfaceService.batchRemove(ids);
		return R.ok();
	}
	
}
