package com.bootdo.version.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.version.domain.BaseVersionmethodDO;
import com.bootdo.version.service.BaseVersionmethodService;
@RestController
@RequestMapping("version/baseVersionmethod")
public class BaseVersionmethodController {
    @Autowired
    private BaseVersionmethodService baseVersionmethodService;
    
    @PostMapping("/list")
//	@RequiresPermissions("version:baseBranchmethod:baseBranchmethod")
	public R list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map<String, Object>> baseBranchmethodList = baseVersionmethodService.list(query);
		int total = baseVersionmethodService.count(query);
		PageUtils pageUtils = new PageUtils(baseBranchmethodList, total);
		return new R().put("data", pageUtils);
	}
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public R save(@RequestBody BaseVersionmethodDO baseVersionmethodDO){
        baseVersionmethodService.save(baseVersionmethodDO);
        return R.ok();
    }
    
    /**
     	* 计算两个版本间的绝对覆盖率的接口，参数为开始的版本的id和结束的版本的id
     */
    @PostMapping("/getAbsolutelyCoverage")
    public R getAbsolutelyCoverage(String beginVersionId,String endVersionId){
    	Map<String, Object> result= baseVersionmethodService.getAbsolutelyCoverage(beginVersionId, endVersionId);
        if("-1".equals(result.get("result"))) {
        	return	R.error("数据异常，请联系管理员！");
        }
    	return R.ok(result);
    }
    /**
     	* 计算两个版本间的相对覆盖率的接口，参数为开始的版本的id和结束的版本的id
     */
    @PostMapping("/getRelativeCoverage")
    public R getRelativeCoverage(String beginVersionId,String endVersionId){
    	Map<String, Object> result= baseVersionmethodService.getRelativeCoverage(beginVersionId, endVersionId);
        if("-1".equals(result.get("result"))) {
        	return	R.error("数据异常，请联系管理员！");
        }
    	return R.ok(result);
    }
    /**
     * 添加方法备注
     * @param versionId
     * @param methodId
     * @param remark
     * @return
     */
    @PostMapping("/addRemark")
    public R addRemark(String versionId, String methodId, String remark) {
    	int result=baseVersionmethodService.addRemark(versionId, methodId, remark);
    	if(result>0) {
    		return R.ok("添加成功！");
    	}else {
    		return R.error("添加失败！");
    	}
    }
}
