package com.bootdo.version.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.common.utils.*;
import com.bootdo.version.domain.MethodUrlDO;
import com.bootdo.version.domain.MethodUrlDOtwo;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bootdo.version.dao.BaseProjectversionDao;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseProjectversionService;
import com.bootdo.version.service.BaseVersionmethodService;

import javax.servlet.http.HttpServletResponse;

/**
 * ${comments}
 * 
 * @author monitor
 * @email monitor@163.com
 * @date 2018-12-17
 */
 
@RestController
@RequestMapping("/version/baseProjectversion")
public class BaseProjectversionController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BaseProjectversionService baseProjectversionService;
	
	@Autowired
    private BaseVersionmethodService baseVersionmethodService;
	
	@Autowired
	private BaseProjectversionDao baseProjectversionDao;
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	//@RequiresPermissions("version:baseProjectversion:baseProjectversion")
	public R list(@RequestBody Map<String, Object> params){
		R r=new R();
		if(params.get("firstTime")!=null&&params.get("firstTime")!="") {
				params.put("firstTime", (String)params.get("firstTime"));
		}
		//查询列表数据
        Query query = new Query(params);
        String projectId=(String)params.get("projectId");
		if(projectId!=null) {
			BaseProjectversionDO lastFormalVersion =baseProjectversionService.getLastFormalVersion(projectId);
			BaseProjectversionDO lastVersion=baseProjectversionService.getLastVersion(projectId);
			r.put("lastFormalVersion", lastFormalVersion);
			r.put("lastVersion", lastVersion);
			//若最后一个版本未关闭且正在测试,计算覆盖率相关信息
		if(lastVersion!=null && lastVersion.getState()==3) {
				//绝对覆盖率信息
				Map<String,Object> absoluteCoverageMap = baseVersionmethodService.getAbsolutelyCoverage(lastVersion.getId(),lastVersion.getId());
				//相对覆盖率信息
				Map<String,Object> relativeCoverageMap = baseVersionmethodService.getRelativeCoverage(lastVersion.getId(),lastVersion.getId());
				Double absoluteCoverage = Double.parseDouble(absoluteCoverageMap.get("absolutelyCoverage")+"");//绝对覆盖率
				Double relativeCoverage = Double.parseDouble(relativeCoverageMap.get("relativeCoverage")+"");//相对覆盖率
				Integer executeNum = Integer.parseInt(absoluteCoverageMap.get("exeMethodNum")+"");//已执行喊叔叔
				lastVersion.setAbsolutelyCoverage(absoluteCoverage);
				lastVersion.setRelativeCoverage(relativeCoverage);
				lastVersion.setExecuteMethod(executeNum);
				baseProjectversionDao.update(lastVersion);
			}
		}
		List<BaseProjectversionDO> baseProjectversionList = baseProjectversionService.list(query);
		int total = baseProjectversionService.count(query);
		PageUtils pageUtils = new PageUtils(baseProjectversionList, total);
		r.put("pageUtils", pageUtils);
		return r;
	}
	

	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	//@RequiresPermissions("version:baseProjectversion:add")
	public R save(BaseProjectversionDO baseProjectversion){
		System.out.println(baseProjectversion.getResponsiblePersonid());
		if(baseProjectversionService.save(baseProjectversion)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("version:baseProjectversion:edit")
	public R update(@RequestBody BaseProjectversionDO baseProjectversion){
		baseProjectversionService.update(baseProjectversion);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@RequiresPermissions("version:baseProjectversion:remove")
	public R remove( String projectId){
		if(baseProjectversionService.remove(projectId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@RequiresPermissions("version:baseProjectversion:batchRemove")
	public R remove(@RequestParam("ids[]") String[] projectIds){
		baseProjectversionService.batchRemove(projectIds);
		return R.ok();
	}


	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	//@RequiresPermissions("version:baseProjectversion:edit")
	public R list(@PathVariable("id") String id){
		BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(id);
		return new R().put("baseProjectversionDO", baseProjectversionDO);
	}

	@RequestMapping(value = "/getDefaultVersion/{id}",method = RequestMethod.GET)
	//@RequiresPermissions("version:baseProjectversion:getDefaultVersion")
	public R getDefaultVersion(@PathVariable("id") String id){
		try {
			String defaultVersion = baseProjectversionService.getDefaultVersion(id);
			return new R().put("defaultVersion", defaultVersion);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}
	/**
	 * 获得指定项目的最新版本的状态
	 * @param projectId
	 * @return
	 */
	@GetMapping( "/getLastVersionState")
	//@RequiresPermissions("version:baseProjectversion:getLastVersionState")
	public R getLastVersionState(String projectId){
		BaseProjectversionDO baseProjectversionDO = baseProjectversionService.getLastVersionState(projectId);
		return new R().put("baseProjectversionDO", baseProjectversionDO);
	}


	@RequestMapping("/getChangeUrlbyVersionId")
	//@RequiresPermissions("version:baseProjectversion:getChangeUrlbyVersionId")
	public R getChangeUrlbyVersionId(@RequestBody Map map){
		Query query = new Query(map);
		R r = new R();
		List<Map<String,Object>> list = baseProjectversionService.getChangeUrlbyVersionId(map);
		int total = baseProjectversionService.getChangeUrlbyVersionIdCount(map);
		PageUtils pageUtils = new PageUtils(list, total);
		r.put("pageUtils", pageUtils);
		return r;
	}
    @RequestMapping("/downLoadChangeUrlbyVersionId/{versionId}")
  //  @RequiresPermissions("version:baseProjectversion:getChangeUrlbyVersionId")
    public void downLoadChangeUrlbyVersionId(@PathVariable("versionId") String versionId, HttpServletResponse response) throws Exception{
        String    mimetype = "application/x-msdownload";
        response.setContentType(mimetype);
        String downFileName = "data.xls";
        String inlineType = "attachment"; // 是否内联附件
        response.setHeader("Content-Disposition", inlineType
                + ";filename=\"" + downFileName + "\"");
        OutputStream out=response.getOutputStream();
        Map<String,Object> map = new HashMap<>();
        map.put("versionId",versionId);
        List<Map<String,Object>> list = baseProjectversionService.getChangeUrlbyVersionId(map);
        logger.info("list:"+list);
        ExcelUtil excelUtil = new ExcelUtil();
        MethodUrlDOtwo methodUrlDOtwo = new MethodUrlDOtwo();
        excelUtil.create(methodUrlDOtwo,list);
        HSSFWorkbook workbook = excelUtil.workbook;
        workbook.write(out);
        out.flush();
        out.close();
    }
}
