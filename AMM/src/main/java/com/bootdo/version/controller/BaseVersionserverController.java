package com.bootdo.version.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.common.config.Constant;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseProjectversionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.BaseVersionserverDO;
import com.bootdo.version.service.BaseVersionserverService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2019-02-20
 */
 
@RestController
@RequestMapping("/version/baseVersionserver")
public class BaseVersionserverController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BaseVersionserverService baseVersionserverService;
	@Autowired
    private BaseProjectversionService baseProjectversionService;
	
	
	@GetMapping("/list")
	@RequiresPermissions("version:baseVersionserver:baseVersionserver")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BaseVersionserverDO> baseVersionserverList = baseVersionserverService.list(query);
		int total = baseVersionserverService.count(query);
		PageUtils pageUtils = new PageUtils(baseVersionserverList, total);
		return pageUtils;
	}
	

	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	//@RequiresPermissions("version:baseVersionserver:add")
	public R save(@RequestBody BaseVersionserverDO baseVersionserver){
		if(baseVersionserverService.save(baseVersionserver)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("version:baseVersionserver:edit")
	public R update(@RequestBody BaseVersionserverDO baseVersionserver){
		baseVersionserverService.update(baseVersionserver);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@RequiresPermissions("version:baseVersionserver:remove")
	public R remove( String id){
		if(baseVersionserverService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@RequiresPermissions("version:baseVersionserver:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		baseVersionserverService.batchRemove(ids);
		return R.ok();
	}


    /**
     * 发布时加载上个版本的server列表
     * @return
     */
    @RequestMapping("/lastVersionList")
    //@RequiresPermissions("version:baseVersionserver:lastVersionList")
	public R lastVersionList(@RequestBody Map map){
        R r = new R();
        try {
			logger.info("参数："+map);
            List<BaseVersionserverDO> baseVersionserverDOList = baseVersionserverService.lastVersionList(map);
            Map<String,Object> result = new HashMap<>();
            result.put("serverList",baseVersionserverDOList);
            return r.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return r.error(Constant.STATUS_FAILED,"加载上个版本server列表出错!");
        }
    }
	
}
