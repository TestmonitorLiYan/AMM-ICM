package com.bootdo.version.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.IndirectInvokeDO;
import com.bootdo.version.service.IndirectInvokeService;
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
@RequestMapping("/version/indirectInvoke")
public class IndirectInvokeController {
//	@Autowired
//	private IndirectInvokeService indirectInvokeService;
//
//
//	/**
//	 * 保存
//	 */
//	@PostMapping("/save")
//	//@RequiresPermissions("version:indirectInvoke:add")
//	public R save( @RequestBody IndirectInvokeDO indirectInvoke){
//		if(indirectInvokeService.save(indirectInvoke)>0){
//			return R.ok();
//		}
//		return R.error();
//	}
}
