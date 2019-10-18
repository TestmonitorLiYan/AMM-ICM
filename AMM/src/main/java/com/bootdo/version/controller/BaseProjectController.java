package com.bootdo.version.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

import com.bootdo.common.config.Constant;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.ExcelUtil;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.version.domain.*;
import com.bootdo.version.service.BaseBranchService;
import com.bootdo.version.service.BaseBranchmethodService;
import com.bootdo.version.service.BaseInterfaceService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.service.BaseProjectService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-13
 */
 
@RestController
@RequestMapping(value = "/version/baseProject", produces = "application/json; charset=UTF-8")
public class BaseProjectController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BaseProjectService baseProjectService;
	
	@Autowired
	private DictService dictService;

	@Autowired
	private UserService userService;

	@Autowired
	private BaseBranchService baseBranchService;

	@Autowired
	private BaseInterfaceService baseInterfaceService;

	@Autowired
	private BaseBranchmethodService baseBranchmethodService;
	
	
	@PostMapping("/list")
	//@RequiresPermissions("version:baseProject:baseProject")
	public R list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BaseProjectDO> baseProjectList = baseProjectService.list(query);
		int total = baseProjectService.count(query);
		PageUtils pageUtils = new PageUtils(baseProjectList, total);
		return new R().put("data", pageUtils);
	}
	/**
	 * 获得权限内项目信息以及最新版本的覆盖率
	 * @param params
	 * @return
	 */
	@GetMapping("/listProjectVersion")
//	@RequiresPermissions("version:baseProject:baseProject")
	public R listProjectVersion(){
		//查询列表数据
		List<Map<String, Object>> list = baseProjectService.listProjectVersion();
		//System.out.println("=================================>>>>>>>>>>>>>>>>>>>>>>>>>/n/n/n"+list.size());
		JSONArray json = JSONArray.fromObject(list);//将java对象转换为json对象
		return new R().put("str", json);
	}
	

	
	/**
	 * 保存
	 */
	@PostMapping(value = "/save")
	@RequiresPermissions("version:baseProject:add")
	public R save(@RequestBody BaseProjectDO baseProject){
		return baseProjectService.save(baseProject);
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@RequiresPermissions("version:baseProject:edit")
	public R update(@RequestBody BaseProjectDO baseProject){
		return baseProjectService.update(baseProject);
	}
	
	/**
	 * 删除
	 */
	@GetMapping("/remove/{id}")
	R remove(@PathVariable("id") String id) {
		R r =new R();
		if(baseProjectService.remove(id)>0){
		return r.ok();
		}
		return r.error("删除项目出错!");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping( "/batchRemove")
	@RequiresPermissions("version:baseProject:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		baseProjectService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 获取单个项目
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	//@RequiresPermissions("version:baseProject:get")
	public R edit(@PathVariable("id") String id) {
		Map<String, Object> resultmap = new HashMap<>();
		R r =new R();
		try {
			BaseProjectDO baseProjectDO = baseProjectService.get(id);
			resultmap.put("baseProjectDO",baseProjectDO);
			return r.ok(resultmap);
		} catch (Exception e) {
			e.printStackTrace();
			return r.error("获取项目信息出错!");
		}
	}

	/**
		 * 加载功能区+供应商+供应商经理
		 */
		@RequestMapping("/preHandler")
		public R preHandler(){
			Map<String, Object> resultmap = new HashMap<>();
		R r =new R();
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("type","domain");
			List<DictDO> domainList = dictService.list(param);
			param.clear();
			param.put("type","supplier");
			List<DictDO> supplierList = dictService.list(param);
			param.clear();
			param.put("usertype","3");
			List<UserDO> managerList = userService.list(param);
			resultmap.put("domainList",domainList);
			resultmap.put("supplierList",supplierList);
			resultmap.put("managerList",managerList);
			return r.ok(resultmap);
		} catch (Exception e) {
			e.printStackTrace();
			return r.error("加载功能区/供应商/供应商经理出错！");
		}
	}

	/**
	 * @return
	 */
	@RequestMapping("/getUnPassedProjectList")
	public R getUnPassedProjectList(){
		R r =new R();
		try {
			Map<String, Object> resultmap = new HashMap<>();
			resultmap.put("projectList",baseProjectService.getUnPassedProjectList());
			return r.ok(resultmap);
		} catch (Exception e) {
			e.printStackTrace();
			return r.error(Constant.STATUS_SERVERERROR,"加载测试未达标项目出错！");
		}
	}

	/**
	 * 获得权限内项目信息以及最新版本的覆盖率
	 * @param
	 * @return
	 */
	@GetMapping("/download")
	//@RequiresPermissions("version:baseProject:baseProject")
	public void downloadProjectVersion(HttpServletResponse response) throws Exception{
		String    mimetype = "application/x-msdownload";
		response.setContentType(mimetype);
		String downFileName = "data.xls";
		String inlineType = "attachment"; // 是否内联附件
		response.setHeader("Content-Disposition", inlineType
				+ ";filename=\"" + downFileName + "\"");
		OutputStream out=response.getOutputStream();
		List<Map<String,Object>> list = baseProjectService.listProjectVersion();
		logger.info("list:"+list);
		ExcelUtil excelUtil = new ExcelUtil();
		BaseProjectversionDO baseProjectversionDO = new BaseProjectversionDO();
        excelUtil.create(baseProjectversionDO,list);
		HSSFWorkbook workbook = excelUtil.workbook;
		workbook.write(out);
		out.flush();
		out.close();
	}

	/**
	 * 获得权限内项目信息以及最新版本的覆盖率
	 * @param
	 * @return
	 */
	@RequestMapping("/getProjectInterfaceList")
	//@RequiresPermissions("version:baseProject:getProjectUrlList")
	public R getProjectInterfaceList(@RequestBody Map params) throws Exception{
		R r = new R();
		//查询列表数据
		Query query = new Query(params);
		int total = baseInterfaceService.count(params);
		try {
			List<Map<String,Object>> baseInterfaceDOList = baseInterfaceService.list(params);
			PageUtils pageUtils = new PageUtils(baseInterfaceDOList, total);
			return new R().put("data", pageUtils);
		} catch (Exception e) {
			e.printStackTrace();
			return new R().error(Constant.STATUS_SERVERERROR,"服务器错误！！！！");
		}

	}

	@GetMapping("/downLoadChangeUrlbyVersionId/{projectId}")
	//@RequiresPermissions("version:baseProject:baseProject")
	public void downLoadChangeUrlbyVersionId(@PathVariable("projectId") String projectId,HttpServletResponse response) throws Exception{
		String    mimetype = "application/x-msdownload";
		response.setContentType(mimetype);
		String downFileName = "data.xls";
		String inlineType = "attachment"; // 是否内联附件
		response.setHeader("Content-Disposition", inlineType
				+ ";filename=\"" + downFileName + "\"");
		OutputStream out=response.getOutputStream();
		Map<String,Object> params = new HashMap<>();
		params.put("projectId",projectId);
		List<Map<String,Object>> baseInterfaceDOList = baseInterfaceService.list(params);
		logger.info("list:"+baseInterfaceDOList);
		ExcelUtil excelUtil = new ExcelUtil();
		MethodUrlDO methodUrlDO = new MethodUrlDO();
		excelUtil.create(methodUrlDO,baseInterfaceDOList);
        HSSFWorkbook workbook = excelUtil.workbook;
        workbook.write(out);
        out.flush();
        out.close();
	}
}
