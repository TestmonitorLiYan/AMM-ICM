package com.bootdo.version.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.BaseBranchmethodDO;
import com.bootdo.version.service.BaseBranchmethodService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
 
@RestController
@RequestMapping("/version/baseBranchmethod")
public class BaseBranchmethodController {
	@Autowired
	private BaseBranchmethodService baseBranchmethodService;
	
	
	@PostMapping("/list")
//	@RequiresPermissions("version:baseBranchmethod:baseBranchmethod")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BaseBranchmethodDO> baseBranchmethodList = baseBranchmethodService.list(query);
		int total = baseBranchmethodService.count(query);
		PageUtils pageUtils = new PageUtils(baseBranchmethodList, total);
		return new R().put("data", pageUtils);
	}
	

	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	//@RequiresPermissions("version:baseBranchmethod:add")
	public R save( @RequestBody BaseBranchmethodDO baseBranchmethod){
		if(baseBranchmethodService.save(baseBranchmethod)>0){
			return R.ok();
		}
		return R.error();
	}
//	/**
//	 * 修改
//	 */
//	@RequestMapping("/update")
//	@RequiresPermissions("version:baseBranchmethod:edit")
//	public R update( BaseBranchmethodDO baseBranchmethod){
//		baseBranchmethodService.update(baseBranchmethod);
//		return R.ok();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/remove")
//	@RequiresPermissions("version:baseBranchmethod:remove")
//	public R remove( String projectId){
//		if(baseBranchmethodService.remove(projectId)>0){
//		return R.ok();
//		}
//		return R.error();
//	}
//
//	/**
//	 * 删除
//	 */
//	@PostMapping( "/batchRemove")
//	@RequiresPermissions("version:baseBranchmethod:batchRemove")
//	public R remove(@RequestParam("ids[]") String[] projectIds){
//		baseBranchmethodService.batchRemove(projectIds);
//		return R.ok();
//	}
	
}
