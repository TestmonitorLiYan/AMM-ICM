package com.bootdo.version.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.domain.DirectInvokeDO;
import com.bootdo.version.service.DirectInvokeService;
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
@RequestMapping("/version/directInvoke")
public class DirectInvokeController {
//	@Autowired
//	private DirectInvokeService directInvokeService;
//
//	/**
//	 * 保存
//	 */
//	@PostMapping("/save")
//	//@RequiresPermissions("version:directInvoke:add")
//	public R save( @RequestBody  DirectInvokeDO directInvoke){
//		if(directInvokeService.save(directInvoke)>0){
//			return R.ok();
//		}
//		return R.error();
//	}
}
