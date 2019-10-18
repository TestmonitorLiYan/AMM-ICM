package com.bootdo.version.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.version.domain.BaseVersionserverDO;
import com.bootdo.version.service.BaseVersionserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootdo.common.config.Constant;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.UUIDUtils;
import com.bootdo.version.domain.BaseBranchDO;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.service.BaseBranchService;
import com.bootdo.version.service.BaseProjectService;
import com.bootdo.version.service.BaseProjectversionService;

/**
 * 项目分支/工程 控制器类
 * @author LiYan
 * @email 15717981520@163.com
 * @date 2018-12-14
 */

@RestController
@RequestMapping("/version/baseBranch")
public class BaseBranchController {
		
		@Autowired
		private BaseBranchService baseBranchService;

		@Autowired
		private BaseProjectService baseProjectService;

		@Autowired
		private BaseProjectversionService baseProjectversionService;

		@Autowired
		private BaseVersionserverService baseVersionserverService;
		/**
		 * 本方法是用来保存新增的工程
		 * @param baseBranchDO，是从前端传来的BaseBranchDO的对象，除了备注，其余属性必须全有
		 * @return
		 */
		//@RequiresPermissions("sys:user:add")
		@PostMapping("/save")
		R save(BaseBranchDO baseBranchDO) {
			String name=baseBranchDO.getName();
			String projectId=baseBranchDO.getProjectId();
			BaseBranchDO Branch=baseBranchService.getName(projectId, name);
			if(Branch==null) {//查询同一个项目中没有相同名字的分支
				//进行保存
				String UUID=UUIDUtils.getUUID();
				baseBranchDO.setId(UUID);
				int result=baseBranchService.save(baseBranchDO);
				if(result>0) {//保存成功
					return R.ok();
				}else{//保存失败，影响行数0		
					return  R.error(Constant.STATUS_FAILED,"保存了0条数据");
				}
			}else {//有相同名字的分支，返回信息该工程名已存在
				//R返回相应信息
				return R.error(Constant.STATUS_UNCHECKPASS,"工程名重复");
			}
		}
		/**
		 * 本方法是用来保存新增的多个工程
		 * @param baseBranchDO，是从前端传来的BaseBranchDO的对象，除了备注，其余属性必须全有
		 * @return
		 */
		//@RequiresPermissions("sys:user:add")
		@PostMapping("/saveList")
		R saveList(@RequestBody List<BaseBranchDO> params) {
			int resultAll=baseBranchService.saveList(params);
			if(resultAll>0) {//保存成功
				return R.ok();
			}else if(resultAll==-2){//保存失败，影响行数0		
				return R.error(Constant.STATUS_UNCHECKPASS,"工程名重复");
			}else {
				return  R.error(Constant.STATUS_FAILED,"保存了0条数据");
			}
		}
		/**
		 * 本方法是用来删除
		 * @return
		 */
		@GetMapping("/delete/{id}")
		R delete(@PathVariable("id")String id) {
			int result=baseBranchService.delete(id);
			if(result>0) {//删除成功
				return R.ok();
			}else {//删除失败
				return R.error(Constant.STATUS_FAILED,"删除了0条数据");
			}
		}
		/**
		 * 本方法是用来更新已有的工程信息
		 * @return
		 */
		@PostMapping("/update")
	R update(BaseBranchDO baseBranchDo) {
		String name=baseBranchDo.getName();
		String projectId=baseBranchDo.getProjectId();
		BaseBranchDO Branch=baseBranchService.getName(projectId, name);
		if(Branch==null||Branch.getId().equals(baseBranchDo.getId())) {//查询同一个项目中没有相同名字的分支
			int result=baseBranchService.update(baseBranchDo);
			if(result>0) {//更新成功
				return R.ok();
			}else {//更新失败
				return R.error(Constant.STATUS_FAILED,"更新了0条数据");
			}
		}else {//有相同名字的分支，返回信息该工程名已存在
			//R返回相应信息
			return R.error("工程名重复");
		}
	}
		/**
		 * 本方法是用来根据工程ID获取工程信息
		 * @return
		 */
		@GetMapping("/get/{id}")
		R get(Model model,@PathVariable("id")String id){
			BaseBranchDO result=baseBranchService.get(id);
			return new R().put("branch", result);
		}
		
		/**
		 * 本方法是用来根据项目ID获取工程信息
		 * @return
		 */
		@GetMapping("/getByProjectId/{projectId}")
		R getByProjectId(Model model,@PathVariable("projectId")String projectId){
			List<BaseBranchDO> result=baseBranchService.getByProjectId(projectId);
			for(int i = 0;i<result.size();i++) {
				BaseBranchDO basebranch = result.get(i);
				basebranch.setProjectId(UUIDUtils.getUUID());
			}
			return new R().put("branchList", result);
		}
		
		/**
		 * 本方法是用来根据项目ID、工程名判断是否重名。
		 * @return
		 */
		@PostMapping("/getName")
		R getName(String projectId,String name) {
			BaseBranchDO branch=baseBranchService.getName(projectId, name);
			if(branch==null) {//没有重名
				return R.ok("0");//0来代表没有重名
			}else {//重名
				return R.ok("1");//1来代表重名
			}
		}


	/**
	 * 本方法是用来根据版本id获取展示页信息
	 * @return
	 */
	@RequestMapping(value = "/getBranchListByVersionId/{id}",method = RequestMethod.GET)
	public Map<String,Object> getProjectData(@PathVariable("id") String id){
		R r = new R();
		try {
			Map<String,Object> map = new HashMap<>();
			List<Map<String,Object>> baseVersionBranchList = baseBranchService.getBranchListByVersionId(id);
			for(Map<String,Object> basebranch:baseVersionBranchList) {
				basebranch.put("projectId", UUIDUtils.getUUID());
			}
			map.put("versionBranchList",baseVersionBranchList);
			BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(id);
			map.put("baseProjectversionDO",baseProjectversionDO);
			Map<String,Object> param = new HashMap<>();
			param.put("versionid",id);
			List<BaseVersionserverDO> baseVersionserverDOList = baseVersionserverService.list(param);
//			map.put("code",0);
//			map.put("msg","处理成功！");
			map.put("baseVersionserverDOList",baseVersionserverDOList);
			return r.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			return r.error("获取发布信息出错!");
		}
	}
}
