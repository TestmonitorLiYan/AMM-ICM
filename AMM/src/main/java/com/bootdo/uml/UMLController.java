package com.bootdo.uml;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import com.bootdo.common.config.Constant;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.domain.*;
import com.bootdo.version.service.*;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.FileService;

@RestController
public class UMLController extends  BaseController{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BaseBranchService baseBranchService;
    @Autowired
    UMLService umlService;

    @Autowired
    private BaseProjectService baseProjectService;

    @Autowired
    private BaseProjectversionService baseProjectversionService;

    @Autowired
    private AsynchronousTask asynchronousTask;

    @Autowired
    private BaseVersionbranchService baseVersionbranchService;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseBranchmethodService baseBranchmethodService;

    @Autowired
    private IndirectInvokeService indirectInvokeService;
    @Autowired
    private DirectInvokeService directInvokeService;
    @Autowired
    private BaseVersionserverService baseVersionserverService;
    /**
     * 发布项目，生成新的版本
     */
    @RequestMapping(value = "/build",method = RequestMethod.POST)
    public Map<String,Object> build(@RequestBody Map<String,Object> map) throws Exception{
        R r = new R();
        long start = System.currentTimeMillis();
        BaseProjectversionDO baseProjectversionDO = new BaseProjectversionDO();
        try {
            //保存版本
            String projectId = map.get("projectId")+"";
            String version = map.get("version")+"";
            String releasePersonid = getUserId()+"";//发布人id
            String responsiblePersonid=getName();//发布人姓名
            String responsiblePerson=map.get("responsiblePerson")+"";//负责人姓名
            Map<String,Object> param = new HashMap<>();
            param.put("projectId",projectId);//项目id
            param.put("version",version);//上个版本号
            List<BaseProjectversionDO> repeatBaseProjectversionDO = baseProjectversionService.list(param);
            /************************************正常****************************************/
            if (repeatBaseProjectversionDO!= null && repeatBaseProjectversionDO.size()>0){
                return r.error(Constant.STATUS_UNCHECKPASS,"项目版本号重复,请重新选择！");
            }
            BaseProjectDO baseProjectDO = baseProjectService.get(projectId);
            String lastVersion = baseProjectDO.getVersion();
            String versionId = UUID.randomUUID().toString().replace("-","");
            baseProjectversionDO.setId(versionId);//设置id
            String versionType = map.get("versionType")+"";
            baseProjectversionDO.setVersionType(versionType);
            baseProjectversionDO.setProjectId(projectId);
            baseProjectversionDO.setVersion(version);
            baseProjectversionDO.setResponsiblePerson(responsiblePerson);//负责人姓名
            baseProjectversionDO.setResponsiblePersonid(responsiblePersonid);//发布人姓名
            baseProjectversionDO.setReleasePersonid(Integer.parseInt(releasePersonid));//发布人id
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            baseProjectversionDO.setReleaseTime(timestamp);
            if (lastVersion != null && !"".equals(lastVersion)){
                //关闭上次版本
                Map<String,Object> map1 = baseProjectversionService.close(projectId);
                if (map1 == null){
                    return r.error("关闭上次版本失败！");
                }
            }
            List<Map<String,Object>> branchList = (ArrayList)map.get("branchList");
            boolean is_check_pass = true;
            List<BaseVersionbranchDO> baseVersionbranchDOS = new ArrayList<>();
            for (int x = 0 ; x < branchList.size(); x ++){

                String versionBranchId = branchList.get(x).get("id")+"";
                String ismodify =  branchList.get(x).get("isModify")+"";
                String remark = branchList.get(x).get("remark")+"";
                String branchName = branchList.get(x).get("branchName")+"";
                String branchId = branchList.get(x).get("branchId")+"";
                if ("1".equals(ismodify)){
                    //更新versionbranch版本
                    param = new HashMap<>();
                    param.put("docid", versionBranchId);
                    param.put("limit", "2");
                    param.put("offset", "1");
                    param.put("sort", "create_date");
                    param.put("order", "desc");
                    List<FileDO> filelist = fileService.list(param);
                    FileDO fileDO = null;
                    if(filelist.size()>0) {
                        fileDO = filelist.get(0);
                        branchList.get(x).put("path",fileDO.getUrl());
                    }else {
                        is_check_pass = false;
                        return r.error("工程【"+branchList.get(x).get("branchName")+"】状态为修改，但未上传war包!");
                    }
                    BaseVersionbranchDO baseVersionbranchDO = new BaseVersionbranchDO();
                    baseVersionbranchDO.setStatus("0");//发布中
                    baseVersionbranchDO.setIsmodify("1");//是否修改 1是 0否
                    baseVersionbranchDO.setVersionid(baseProjectversionDO.getId());
                    baseVersionbranchDO.setRemark(remark);
                    baseVersionbranchDO.setMsg("发布中!");
                    baseVersionbranchDO.setPath(fileDO.getUrl());
                    baseVersionbranchDO.setId(versionBranchId);
                    baseVersionbranchDO.setBranchid(branchId);
                    baseVersionbranchDO.setBranch(branchName);
                    baseVersionbranchDOS.add(baseVersionbranchDO);
                }else{
                    //保存versionbranch版本
                    param = new HashMap<>();
                    param.put("projectId",projectId);
                    param.put("version",lastVersion);
                    List<BaseProjectversionDO> lastBaseProjectversionDOS = baseProjectversionService.list(param);
                    if(lastBaseProjectversionDOS == null || lastBaseProjectversionDOS.size() == 0) {
                        is_check_pass = false;
                        return r.error("工程【"+branchList.get(x).get("branchName")+"】状态为未修改，但找不到历史版本记录!");
                    }
                    BaseProjectversionDO lastBaseProjectversionDO = lastBaseProjectversionDOS.get(0);//上个版本的VO
                    param.clear();
                    param.put("versionid",lastBaseProjectversionDO.getId());
                    List<BaseVersionbranchDO> lastBaseVersionbranchDO = baseVersionbranchService.list(param);
                    if(lastBaseVersionbranchDO == null || lastBaseVersionbranchDO.size() == 0) {
                        is_check_pass = false;
                        return r.error("工程【"+branchList.get(x).get("branchName")+"】状态为未修改，但找不到历史war包!");
                    }
                    BaseVersionbranchDO baseVersionbranchDO = new BaseVersionbranchDO();
                    baseVersionbranchDO.setStatus("0");//发布中
                    baseVersionbranchDO.setPath(lastBaseVersionbranchDO.get(0).getPath());
                    branchList.get(x).put("path",lastBaseVersionbranchDO.get(0).getPath());
                    baseVersionbranchDO.setIsmodify("0");//是否修改 1是 0否
                    baseVersionbranchDO.setVersionid(baseProjectversionDO.getId());
                    baseVersionbranchDO.setId(versionBranchId);
                    baseVersionbranchDO.setMsg("发布中!");
                    baseVersionbranchDO.setBranchid(branchId);
                    baseVersionbranchDO.setBranch(branchName);
                    baseVersionbranchDOS.add(baseVersionbranchDO);
                }
            }
            if (is_check_pass){
                //通过校验保存版本
                baseProjectversionService.save(baseProjectversionDO);
                for (int x = 0 ; x < baseVersionbranchDOS.size(); x++){
                    baseVersionbranchService.save(baseVersionbranchDOS.get(x));
                }
                map.put("versionId",baseProjectversionDO.getId());
                map.put("releasePersonid", releasePersonid);
                baseVersionserverService.save(map);
            }else{
                return r.error(Constant.STATUS_SERVERERROR,"数据校验失败!!!!!!!!!!!!!!!!!!!");
            }
            asynchronousTask.task(map);
        } catch (Exception e) {
        	e.printStackTrace();
            return r.error("发布失败，请联系管理员！");
        }
        long end = System.currentTimeMillis();
        logger.info("controller耗时："+(end-start));
        Map<String,Object> response = new HashMap<>();
        response.put("baseProjectversionDO",baseProjectversionDO);
        return r.ok(response);
    }

    /**
     * 单独发布branch
     */
    @RequestMapping(value = "/reBuild",method = RequestMethod.POST)
    public Map<String,Object> singleBuild(@RequestBody Map<String,Object> map) throws Exception{
        R r = new R();
        String versionBranchId=map.get("versionBranchId") + "";
		Map<String,Object> param = new HashMap<>();
        param.put("docid", versionBranchId);
        param.put("limit", "2");
        param.put("offset", "1");
        param.put("sort", "create_date");
        param.put("order", "desc");
        List<FileDO> filelist = fileService.list(param);
        FileDO fileDO = null;
        if(filelist.size()>0) {
        	fileDO = filelist.get(0);
        	map.put("path",fileDO.getUrl());
        }else {
            return r.error("工程状态为修改，但未上传war包!");
        }
        asynchronousTask.reBuildTask(map);
        Map<String,Object> resultmap = new HashMap<>();
        resultmap.put("code",0);
        resultmap.put("msg","branch已经重新发布，请关注状态！");
        return r.ok("版本已保存，待解析，请留意版本状态");

    }

    /**
     * 关闭版本
     */
    @RequestMapping(value = "/closeCurrentVersion",method = RequestMethod.POST)
    public Map<String,Object> closeCurrentVersion(@RequestBody Map<String,Object> map) throws Exception{
        R r = new R();
        String versionId = map.get("versionId")+"";
        BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(versionId);
        String projectId = baseProjectversionDO.getProjectId();
        Map<String,Object> map1 = baseProjectversionService.close(projectId);
        if (map1 == null){
            return r.error("关闭上次版本失败！");
        }
        return r.ok("版本已关闭!");

    }

    /**
       * 生成调用关系
     */
    @RequestMapping(value = "/getCurrentRelationList",method = RequestMethod.POST)
    public Map getCurrentRelationList(@RequestBody Map<String,Object> map) throws Exception{
        R r = new R();
        //传来的参数包括开始versionId和结束versionID
        //查询所有包含在结束版本中的方法信息以及执行状态、x、y、
        List<Map<String,Object>> nodes=baseBranchmethodService.getCurrentRelationList((String)map.get("beginVersionId"), (String)map.get("endVersionId"));
        List<Map<String,Object>> links = directInvokeService.getTotalList((String)map.get("endVersionId"));
        Map<String,Object> response = new HashMap<>();
        response.put("nodes",nodes);
        response.put("links",links);
        return r.ok(response);

    }
    /**
     * 生成调用关系
     */
    @RequestMapping(value = "/getCurrentRelationList1",method = RequestMethod.POST)
    public Map getCurrentRelationList1(@RequestBody Map<String,Object> map) throws Exception{
        R r = new R();
        //传来的参数包括开始versionId和结束versionID
        //查询所有包含在结束版本中的方法信息以及执行状态、x、y、
        List<Map<String,Object>> nodes=baseBranchmethodService.getCurrentRelationList1((String)map.get("beginVersionId"), (String)map.get("endVersionId"));
        List<Map<String,Object>> links = directInvokeService.getTotalList((String)map.get("endVersionId"));
        Map<String,Object> response = new HashMap<>();
        response.put("nodes",nodes);
        response.put("links",links);
        return r.ok(response);

    }

    /**
     * 生成调用关系
     */
   

    public List<Map<String,Object>> distinct(List<Map<String,Object>> collisionList ){


        List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
        Set<String> keysSet = new HashSet<String>();
        for(Map<String, Object> collisionMap : collisionList){
            String keys = (String) collisionMap.get("name");//根据class_name字段去重
            int beforeSize = keysSet.size();
            keysSet.add(keys);
            int afterSize = keysSet.size();
            if(afterSize == beforeSize + 1){
                tmpList.add(collisionMap);
            }
        }
        return tmpList;
    }

}
