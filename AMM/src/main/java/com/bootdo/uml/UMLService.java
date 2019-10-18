package com.bootdo.uml;

import com.bootdo.common.utils.UUIDUtils;
import com.bootdo.version.dao.BaseBranchmethodDao;
import com.bootdo.version.dao.IndirectInvokeDao;
import com.bootdo.version.domain.*;
import com.bootdo.version.service.*;
import oracle.sql.CLOB;

import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UMLService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BaseBranchmethodService baseBranchmethodService;

    @Autowired
    private BaseVersionmethodService baseVersionmethodService;

    @Autowired
    private BaseProjectversionService baseProjectversionService;

    @Autowired
    private BaseProjectService baseProjectService;

    @Autowired
    private BaseBranchmethodDao baseBranchmethodDao;

    @Autowired
    private DirectInvokeService directInvokeService;

    @Autowired
    private IndirectInvokeService indirectInvokeService;

    @Autowired
    private BaseVersionbranchService baseVersionbranchService;
    
    @Autowired
    private IndirectInvokeDao indirectInvokeMapper;

    @Autowired
    private BaseBranchService baseBranchService;

    @Autowired
    private BaseInterfaceService baseInterfaceService;

    /**
     *
     * @param isModify
     * @param path
     * @param projectId
     * @param versionId
     * @param branchId
     * @param releasePersonid
     * @param responsiblePersonid
     * @param versionBranchId
     * @return
     * @throws Exception
     */
    public Map<String,Object> reverse(String path,String projectId,String versionId,String branchId,Integer releasePersonid,String responsiblePersonid,String versionBranchId)throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        logger.info("+++++++++++++++++++reverse start+++++++++++++++++++");
        Map<String,Object> param = new HashMap<>();
        param.put("projectId",projectId);
        param.put("branchId",branchId);
        //清空以往调用关系
        directInvokeService.delete(param);
        indirectInvokeService.delete(param);
        //清空以往地址存储
        baseInterfaceService.remove(branchId);
        List<Map<String,Object>> classReferences = new ArrayList<>();//method集合

        /******************正式******************/
        BaseVersionbranchDO baseVersionbranchDO = baseVersionbranchService.get(versionBranchId);
        try {

        classReferences = analysis(branchId,path);

        if (classReferences.size() == 0){
            throw new Exception("逆向工程出错...........");
        }
        /**
         * 更新版本状态为解析成功
         */
        baseVersionbranchDO.setMsg("文件解析成功,待比对!");
        baseVersionbranchDO.setStatus("0");
        baseVersionbranchService.update(baseVersionbranchDO);
        Map<String,Object> response = new HashMap<>();
        response = this.saveMathod(projectId,branchId,versionId,classReferences);//Method保存
        if (response == null){
            throw new Exception("保存方法出错.................................");
        }
        this.saveRelate(projectId,branchId,versionId,classReferences);//保存直接调用关系
        param.clear();
        param.put("projectId",projectId);
        param.put("branchId",branchId);
        List<Map<String,Object>> relationList  = directInvokeService.getRelationList(param);

        for (int k = 0 ; k < relationList.size() ; k++) {
            String pid = relationList.get(k).get("INVOKE_METHODID") + "";//目标方法id
            //生成间接关系
            getRelation(projectId, branchId,pid,pid);
        }
        /**
         * 更新版本状态为解析发布成功
         */
        baseVersionbranchDO.setMsg("发布成功！");
        baseVersionbranchDO.setStatus("1");
        baseVersionbranchService.update(baseVersionbranchDO);
        logger.info("+++++++++++++++++++reverse end+++++++++++++++++++");
        return returnMap;
        } catch (Exception e) {
            baseVersionbranchDO.setMsg("发布失败！");
            if (classReferences == null || classReferences.size() == 0){
                baseVersionbranchDO.setMsg("发布失败,基本包名配置错误，请检查！");
            }
            baseVersionbranchDO.setStatus("2");
            baseVersionbranchService.update(baseVersionbranchDO);
            e.printStackTrace();
            logger.info("+++++++++++++++++++reverse end+++++++++++++++++++");
            return null;
        }
    }

    /**
     * 保存versionMethod
     * @param list
     * @return
     */
    public Map<String,Object> saveMathod(String projectId,String branchId,String versionId,List<Map<String,Object>> list){
        Map<String,Object> result = new HashMap<>();
        try {
            int len = list.size();
            List<String> modified = new ArrayList<>();
            BaseProjectDO baseProjectDO = baseProjectService.get(projectId);
            BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(versionId);
            //保存branchMethod
            for (int x= 0 ; x < len; x++){

                Map<String,Object> p_method = list.get(x);
                Map<String,Object> byte_code_map = (Map<String,Object>)p_method.get("byte_code");
                Map<String,Object> call_method_name_map = (Map<String,Object>)p_method.get("call_method_name");
                Map<String,Object> call_class_name_map = (Map<String,Object>)p_method.get("call_class_name");
                Map<String,Object> call_method_param_map = (Map<String,Object>)p_method.get("call_method_param");
                Map<String,Object> return_type_map = (Map<String,Object>)p_method.get("return_type");
                Map<String,Object> pair_map = (Map<String,Object>)p_method.get("pair");
                //JSONArray jsonArray = JSONArray.fromObject(list);
                String byte_code = byte_code_map.get("byte_code")+"";
                String call_method_name = call_method_name_map.get("call_method_name")+"";
                String call_class_name = call_class_name_map.get("call_class_name")+"";
                String call_method_param = call_method_param_map.get("call_method_param")+"";
                String return_type = return_type_map.get("return_type")+"";
                Map<String,Object> param = new HashMap<>();
                param.put("projectId",projectId);
                param.put("branchId",branchId);
                param.put("methodName",call_method_name);
                param.put("className",call_class_name);
                param.put("methodParameter",call_method_param);
                param.put("returnType",return_type);
                List<Map<String,Object>> checkList = baseBranchmethodService.checkMethod(param);
                if (checkList == null || checkList.size() == 0){
                    logger.info("++++++++++method 不存在++++++++++");
                    String id = UUID.randomUUID().toString().replace("-","");//主键id
                    String method_id = UUID.randomUUID().toString().replace("-","");//方法id
                    BaseBranchmethodDO baseBranchmethodDO = new BaseBranchmethodDO();
                    baseBranchmethodDO.setId(method_id);
                    baseBranchmethodDO.setBranchId(branchId);
                    baseBranchmethodDO.setProjectId(projectId);
                    baseBranchmethodDO.setVersion(baseProjectversionDO.getVersion());
                    baseBranchmethodDO.setPackageName("");
                    baseBranchmethodDO.setClassName(call_class_name);
                    baseBranchmethodDO.setMethodName(call_method_name);
                    baseBranchmethodDO.setMethodParameter(call_method_param);
                    baseBranchmethodDO.setMethodReturn(return_type);
                    baseBranchmethodDO.setMethodCode(byte_code);
                    baseBranchmethodService.save(baseBranchmethodDO);
                    BaseVersionmethodDO baseVersionmethodDO = new BaseVersionmethodDO();
                    baseVersionmethodDO.setMethodId(method_id);
                    baseVersionmethodDO.setId(id);
                    baseVersionmethodDO.setBranchId(branchId);
                    baseVersionmethodDO.setProjectId(projectId);
                    baseVersionmethodDO.setState(0);
                    baseVersionmethodDO.setVersionId(versionId);
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    baseVersionmethodDO.setUpdateTime(time);
                    baseVersionmethodDO.setVersionTime(baseProjectversionDO.getReleaseTime());
                    baseVersionmethodService.save(baseVersionmethodDO);
                    //用来计算影响方法个数了
                    modified.add(method_id);
                    saveUrl(pair_map,projectId,branchId,method_id);
                }else{
                    logger.info("++++++++++method 存在++++++++++");
                    //比较已存在的方法是否上个版本
                    Map<String,Object> method = null;
                    boolean change = false;
                    boolean have = false;
                    String lastVersion = baseProjectversionDO.getVersion();
                    for (int y  = 0 ; y < checkList.size(); y ++){//实际只有一条才对
                    	method = checkList.get(y);
                    	 if(lastVersion.equals(method.get("VERSION"+""))) {
                    		 have=true;//只要找到最新的版本存在该方法，就不用再处理
                    		 break;
                         }
                            CLOB clob = (CLOB) method.get("METHOD_CODE");
                            String clobString = this.ClobToString(clob);
                            String pattern = "\\([0-9]+\\)";	//括号内
                            byte_code = byte_code.replaceAll(pattern, "");
                            clobString=clobString.replaceAll(pattern, "");
                            if (replaceBlank(byte_code).equals(replaceBlank(clobString))) {
                            	logger.info("++++++++++method 未变更++++++++++");
                                break;
                            }else {
                            	change=true;
                            	logger.info("++++++++++method 变更++++++++++");
                            }
                    }
                    if(!have) {//找到的方法不是最新版本
                        //方法是上个版本且未变更
                        String id = UUID.randomUUID().toString().replace("-","");//主键id
                        BaseBranchmethodDO baseBranchmethodDO = baseBranchmethodService.get(method.get("ID")+"");
                        baseBranchmethodDO.setVersion(lastVersion);
                        baseBranchmethodDO.setMethodCode(byte_code);//不管有没有变更，存最新的都是对的
                        baseBranchmethodService.update(baseBranchmethodDO);
                        BaseVersionmethodDO baseVersionmethodDO = new BaseVersionmethodDO();
                        baseVersionmethodDO.setId(id);
                        baseVersionmethodDO.setProjectId(projectId);
                        baseVersionmethodDO.setBranchId(branchId);
                        baseVersionmethodDO.setMethodId(baseBranchmethodDO.getId());
                        baseVersionmethodDO.setVersionId(versionId);
                        baseVersionmethodDO.setState(0);
                        if(change==true) {//变更
                        	modified.add(method.get("ID")+"");
                        	 Timestamp time = new Timestamp(System.currentTimeMillis());
                        	baseVersionmethodDO.setUpdateTime(time);
                        }
                        baseVersionmethodDO.setVersionTime(baseProjectversionDO.getReleaseTime());
                        baseVersionmethodService.save(baseVersionmethodDO);
                        saveUrl(pair_map,projectId,branchId,method.get("ID")+"");
                    }
                }
            }
            result.put("total",len);
            result.put("modified",modified);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
            return result;
        }
    }

    /**
     * 保存调用关系
     */
    public void saveRelate(String projectId,String branchId,String versionId,List<Map<String,Object>> list){
        BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(versionId);
        for (int x = 0 ; x < list.size(); x ++){
            Map<String,Object> parent = list.get(x);
            Map<String,Object> call_class_name_map = (Map<String,Object>)parent.get("call_class_name");
            Map<String,Object> call_method_name_map = (Map<String,Object>)parent.get("call_method_name");
            Map<String,Object> call_method_param_map = (Map<String,Object>)parent.get("call_method_param");
            Map<String,Object> call_return_type_map = (Map<String,Object>)parent.get("return_type");
            String call_class_name = call_class_name_map.get("call_class_name")+"";
            String call_method_name = call_method_name_map.get("call_method_name")+"";
            String call_method_param = call_method_param_map.get("call_method_param")+"";
            Map<String,Object> param = new HashMap<>();
            param.put("projectId",projectId);
            param.put("branchId",branchId);
            param.put("methodName",call_method_name);
            param.put("className",call_class_name);
            param.put("methodParameter",call_method_param);
            param.put("returnType",call_return_type_map.get("return_type")+"");
            String p_method_id = "";
            List<Map<String,Object>> parentList = baseBranchmethodService.checkMethod(param); 
            for (int x1 = 0 ; x1 < parentList.size() ; x1 ++) {
            	if (baseProjectversionDO.getVersion().equals(parentList.get(x1).get("VERSION")+"")) {
            		p_method_id = parentList.get(x1).get("ID")+"";
            	}
            }
            List<Map<String,Object>> childMethodList = (List<Map<String,Object>>)parent.get("sub");
            for (int y = 0 ; y < childMethodList.size(); y++){
                Map<String,Object> child = childMethodList.get(y);
                Map<String,Object> called_class_name_map = (Map<String,Object>)child.get("called_class_name");
                Map<String,Object> called_method_name_map = (Map<String,Object>)child.get("called_method_name");
                Map<String,Object> called_method_param_map = (Map<String,Object>)child.get("called_method_param");
                String called_class_name = called_class_name_map.get("called_class_name")+"";
                String called_method_name = called_method_name_map.get("called_method_name")+"";
                String called_method_param = called_method_param_map.get("called_method_param")+"";
                Map<String,Object> called_return_type_map = (Map<String,Object>)child.get("return_type");
                String called_return_type = called_return_type_map.get("return_type")+"";
                param.clear();
                param.put("projectId",projectId);
                param.put("branchId",branchId);
                param.put("methodName",called_method_name);
                param.put("className",called_class_name);
                param.put("methodParameter",called_method_param);
                param.put("returnType",called_return_type);
                List<Map<String,Object>> checkList = baseBranchmethodService.checkMethod(param);
                if (checkList != null && checkList.size() > 0){

                    String c_method_id = "";
                    for (int x1 = 0 ; x1 < checkList.size() ; x1 ++) {
                    	if (baseProjectversionDO.getVersion().equals(checkList.get(x1).get("VERSION")+"")) {
                    		c_method_id = checkList.get(x1).get("ID")+"";
                    	}
                    }
                    //屏蔽递归调用关系
                    if (!c_method_id.equals(p_method_id)){
                        param.clear();
                        param.put("projectId",projectId);
                        param.put("branchId",branchId);
                        param.put("p_method_id",p_method_id);
                        param.put("c_method_id",c_method_id);
                        List<DirectInvokeDO> directInvokeList = directInvokeService.list(param);
                        if (directInvokeList == null || directInvokeList.size() == 0) {
                        	//屏蔽一个方法多次调用同一个方法
                            DirectInvokeDO directInvokeDO = new DirectInvokeDO();
                            directInvokeDO.setBeinvokeMethodid(c_method_id);
                            directInvokeDO.setInvokeMethodid(p_method_id);
                            directInvokeDO.setBranchId(branchId);
                            directInvokeDO.setProjectId(projectId);
                            directInvokeService.save(directInvokeDO);
                        }else {

                        }
                    }
                }
            }

        }
    }

    public List<Map<String,Object>> getRelation(String projectid,String branchId ,String methodId,String pmethodid){
        Map<String,Object> p = new HashMap<>();
        p.put("projectId",projectid);
        p.put("branchId",branchId);
        p.put("methodId",methodId);
        List<Map<String,Object>> submethodList = directInvokeService.getRelationList(p);
        if(submethodList!=null) {
            for (int j = 0; j < submethodList.size(); j++) {
            		//判断方法是否存在
                    if (!hasIndirectInvoke(pmethodid, submethodList.get(j).get("BEINVOKE_METHODID")+"")){
                        IndirectInvokeDO IndirectInvokeDO = new IndirectInvokeDO();
                        IndirectInvokeDO.setInvokeMethodid(pmethodid);
                        IndirectInvokeDO.setBranchId(branchId);
                        IndirectInvokeDO.setProjectId(projectid);
                        IndirectInvokeDO.setBeinvokeMethodid(submethodList.get(j).get("BEINVOKE_METHODID")+"");
                        indirectInvokeService.save(IndirectInvokeDO);
                        getRelation(projectid, branchId, submethodList.get(j).get("BEINVOKE_METHODID")+"",pmethodid);
                    }
            }
        }
        return submethodList;
    }
    
    /*
     	* 判断关系记录是否存在
     * */
    public Boolean hasIndirectInvoke(String pId,String cId) {
    	 Map<String,Object> p = new HashMap<>();
         p.put("pId",pId);
         p.put("cId",cId);
         int count = indirectInvokeMapper.count(p);
         if(count>0) {
        	 return true; 
         }
         return false;
    }

    /**
     * 保存方法变更记录和被影响记录到monitor_version_modify
     * @param methodList 直接修改的方法id集合
     */
    public List<String> getAffectedList(List<String> methodList){
        List<String> total = new ArrayList<>();
        for (String m:methodList){
            List<Map<String,Object>> lower = indirectInvokeService.getLowerList(m);//所有下级方法
            List<Map<String,Object>> upper = indirectInvokeService.getUpperList(m);//所有上级方法
            if (lower != null && lower.size() > 0){
                for (Map l:lower){
                    if (!total.contains(l.get("BEINVOKE_METHODID")+"")){
                        total.add(l.get("BEINVOKE_METHODID")+"");//去重添加下级方法
                    }
                }
            }
            if (upper != null && upper.size() > 0){
                for (Map u:upper){
                    if (!total.contains(u.get("INVOKE_METHODID")+"")){
                        total.add(u.get("INVOKE_METHODID")+"");//去重添加上级方法
                    }
                }
            }
        }
        logger.info("total:"+total);
        return total;
    }

    public Map<String,Object> updateProject(BaseProjectDO baseProjectDO,String version){
        try {
            baseProjectDO.setVersion(version);
            baseProjectService.update(baseProjectDO);
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param isModify
     * @param path
     * @param projectId
     * @param versionId
     * @param branchId
     * @param versionBranchId
     * @return
     * @throws Exception
     */
    public Map<String,Object> singleReverse(String path,String projectId,String versionId,String branchId)throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        ClassParser cp;
        logger.info("+++++++++++++++++++singleReverse start+++++++++++++++++++");
        Map<String,Object> p = new HashMap<>();
        p.put("projectId",projectId);
        p.put("branchId",branchId);
        //清空以往调用关系
        directInvokeService.delete(p);
        indirectInvokeService.delete(p);
        //清空branchmethod和versionmethod
        BaseProjectversionDO baseProjectversionDO = baseProjectversionService.getByVersion(versionId);
        p.put("version",baseProjectversionDO.getVersion());
        //?怎么会把branchMethod的删了呢？不应该是对比完后更新吗？
        //baseBranchmethodService.deleteFailedBranchMethod(p);
        p.put("versionId",versionId);
        baseVersionmethodService.deleteFailedVersionMethod(p);
        BaseVersionbranchDO baseVersionbranchDO = baseVersionbranchService.getPath(branchId, versionId);
        List<Map<String,Object>> classReferences = new ArrayList<>();//method集合
        try {
            classReferences = analysis(branchId,path);
            if (classReferences.size() == 0){
                throw new Exception("逆向工程出错...........");
            }
            /**
             * 更新版本状态为解析成功
             */
            baseVersionbranchDO.setMsg("文件解析成功,待比对!");
            baseVersionbranchDO.setStatus("0");
            baseVersionbranchService.update(baseVersionbranchDO);
            int len = classReferences.size();
            Map<String,Object> response = new HashMap<>();
            response = this.saveMathod(projectId,branchId,versionId,classReferences);//Method保存
            if (response == null){
                throw new Exception("保存方法出错.................................");
            }
            List<String> modifiedList = (List<String>)response.get("modified");
            /******************************************方法统计********************************************/
            this.saveRelate(projectId,branchId,versionId,classReferences);//保存直接调用关系
            p.clear();
            p.put("projectId",projectId);
            p.put("branchId",branchId);
            List<Map<String,Object>> relationList  = directInvokeService.getRelationList(p);

            for (int k = 0 ; k < relationList.size() ; k++) {
                String pid = relationList.get(k).get("INVOKE_METHODID") + "";//目标方法id
                String cid = relationList.get(k).get("BEINVOKE_METHODID") + "";//目标方法id
                //生成间接关系
                getRelation(projectId, branchId,pid,pid);
            }
            logger.info("size:"+len);
            List<String> affectedList = this.getAffectedList(modifiedList);
            int affected = affectedList.size();
            returnMap.put("affected",affected);
            /**
             * 更新版本状态为解析发布成功
             */
            baseVersionbranchDO.setMsg("发布成功！");
            baseVersionbranchDO.setStatus("1");
            baseVersionbranchService.update(baseVersionbranchDO);
            logger.info("+++++++++++++++++++singleReverse end+++++++++++++++++++");
            return returnMap;
        } catch (Exception e) {
            /**
             * 更新版本状态为解析发布成功
             */
            baseVersionbranchDO.setMsg("发布失败！");
            baseVersionbranchDO.setMsg("发布失败！");
            if (classReferences == null || classReferences.size() == 0){
                baseVersionbranchDO.setMsg("发布失败,基本包名配置错误，请检查！");
            }
            baseVersionbranchDO.setStatus("2");
            baseVersionbranchService.update(baseVersionbranchDO);
            return null;
        }
    }
    
    /**
     *oracle.sql.Clob类型转换成String类型
     */
     
    public String ClobToString(CLOB clob) throws SQLException, IOException {
     
     String reString = "";
     if (clob != null){
         Reader is = clob.getCharacterStream();// 得到流
         BufferedReader br = new BufferedReader(is);
         String s = br.readLine();
         StringBuffer sb = new StringBuffer();
         while (s != null) {
             sb.append(s);
             sb.append("\r\n");
             s = br.readLine();
         }
         reString = sb.toString();
     }
     return reString;
    }
    
    
    public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

    /**
     * 解析工程
     * @param branchId
     * @param path
     * @return
     * @throws Exception
     */
	public List<Map<String,Object>> analysis(String branchId,String path) throws Exception{
	    BaseBranchDO baseBranchDO = baseBranchService.get(branchId);
	    logger.info("baseBranchDO:"+baseBranchDO);
        ClassParser cp;
        List<Map<String,Object>> classReferences = new ArrayList<>();//method集合
        //根据路径获取war对象
        //JarFile jar = new JarFile(path);
        JarFile jar = new JarFile(new File(path));
        Enumeration<?> entries = jar.entries();
        Map<String, JavaClass> allClass=new HashMap<String, JavaClass>();
        while (entries.hasMoreElements()) {
            JarEntry entry =(JarEntry)entries.nextElement();
            if (entry.isDirectory()){
                continue;
            }
            if(entry.getName().endsWith(".MF")){
            	continue;
            }
            if (!entry.getName().endsWith(".class")){
                continue;
            }
            if(entry.getName().startsWith("WEB-INF/classes")||entry.getName().startsWith("BOOT-INF/classes")) {
            	cp = new ClassParser(path,entry.getName());
            	ClassVisitor visitor = new ClassVisitor(cp.parse());
            	allClass.put(cp.parse().getClassName(), cp.parse());
            	visitor.start();
            	classReferences.addAll(visitor.classReferences);
            }
        }
        for(String key:allClass.keySet()) {
        	JavaClass now=allClass.get(key);
        	String[] name=now.getInterfaceNames();
        	for(String na:name) {
        		//排除jdk和jar包里的接口,不为空说明是解析过的，解析时已经加了限制
        		if(allClass.get(na)!=null) {
        			Method[] m=allClass.get(na).getMethods();
        			for(Method me:m) {
        				List<Map<String,Object>> totalList = new ArrayList<Map<String,Object>>();
        		        Map<String,Object> sub = new HashMap<String,Object>();//方法map对象
        				Map<String,Object> call_class_name = new HashMap<String,Object>(); //class
			            Map<String,Object> call_method_name = new HashMap<String,Object>();//方法名称
			            Map<String,Object> call_method_param = new HashMap<String,Object>();//方法参数类型
			            Map<String,Object> byte_code = new HashMap<String,Object>();//由于获得的都是接口方法，所以必然没有方法体，不用存储
			            Map<String,Object> return_type = new HashMap<String,Object>();//返回类型
			            byte_code.put("byte_code","");
			            call_class_name.put("call_class_name", allClass.get(na).getClassName());
			            call_method_name.put("call_method_name", me.getName());
			            call_method_param.put("call_method_param", argumentList(me.getArgumentTypes()));
			            return_type.put("return_type",me.getReturnType().toString());
			            sub.put("call_class_name", call_class_name);
			            sub.put("call_method_name", call_method_name);
			            sub.put("call_method_param", call_method_param);
			            sub.put("return_type", return_type);
			            sub.put("byte_code", byte_code);
        				for(Method met:now.getMethods()) {
        					if(me.getName().equals(met.getName())&&argumentList(me.getArgumentTypes()).equals(argumentList(met.getArgumentTypes()))) {
        						Map<String,Object> called_class_name = new HashMap<String,Object>(); //被调用class
        				    	Map<String,Object> called_method_name = new HashMap<String,Object>();//被调用方法名称
        				        Map<String,Object> called_method_param = new HashMap<String,Object>();//被调用方法参数类型
        				        called_method_name.put("called_method_name", met.getName());
        				    	called_method_param.put("called_method_param", argumentList(met.getArgumentTypes()));    	
        				    	called_class_name.put("called_class_name", now.getClassName());
        				        return_type.put("return_type",met.getReturnType().toString());
        				        Map<String,Object> sub1 = new HashMap<String,Object>();//被调用方法map对象
        				    	sub1.put("called_class_name", called_class_name);
        				    	sub1.put("called_method_name", called_method_name);
        				    	sub1.put("called_method_param", called_method_param);
        				        sub1.put("return_type", return_type);
        				    	totalList.add(sub1);
        					}
        				}
        				sub.put("sub", totalList);
        				classReferences.add(sub);
        			}
        		}
        	}
        	String superName=now.getSuperclassName();
        	if(allClass.get(superName)!=null) {
        		Method[] m=allClass.get(superName).getMethods();
        		for(Method me:m) {
    				List<Map<String,Object>> totalList = new ArrayList<Map<String,Object>>();
    		        Map<String,Object> sub = new HashMap<String,Object>();//方法map对象
    				Map<String,Object> call_class_name = new HashMap<String,Object>(); //class
		            Map<String,Object> call_method_name = new HashMap<String,Object>();//方法名称
		            Map<String,Object> call_method_param = new HashMap<String,Object>();//方法参数类型
		            Map<String,Object> byte_code = new HashMap<String,Object>();//由于获得的都是接口方法，所以必然没有方法体，不用存储
		            Map<String,Object> return_type = new HashMap<String,Object>();//返回类型
		            if (me.getCode() != null){
	                    String byte_code_str = me.getCode().toString();
	                    byte_code.put("byte_code",byte_code_str.substring(0, byte_code_str.indexOf("Attribute")).trim());//存储字节码指令
	                }else{
	                    byte_code.put("byte_code","");//存储字节码指令
	                }
		            call_class_name.put("call_class_name", allClass.get(superName).getClassName());
		            call_method_name.put("call_method_name", me.getName());
		            call_method_param.put("call_method_param", argumentList(me.getArgumentTypes()));
		            return_type.put("return_type",me.getReturnType().toString());
		            sub.put("call_class_name", call_class_name);
		            sub.put("call_method_name", call_method_name);
		            sub.put("call_method_param", call_method_param);
		            sub.put("return_type", return_type);
		            sub.put("byte_code", byte_code);
    				for(Method met:now.getMethods()) {
    					if(me.getName().equals(met.getName())&&argumentList(me.getArgumentTypes()).equals(argumentList(met.getArgumentTypes()))) {
    						Map<String,Object> called_class_name = new HashMap<String,Object>(); //被调用class
    				    	Map<String,Object> called_method_name = new HashMap<String,Object>();//被调用方法名称
    				        Map<String,Object> called_method_param = new HashMap<String,Object>();//被调用方法参数类型
    				        called_method_name.put("called_method_name", met.getName());
    				    	called_method_param.put("called_method_param", argumentList(met.getArgumentTypes()));    	
    				    	called_class_name.put("called_class_name", now.getClassName());
    				        return_type.put("return_type",met.getReturnType().toString());
    				        Map<String,Object> sub1 = new HashMap<String,Object>();//被调用方法map对象
    				    	sub1.put("called_class_name", called_class_name);
    				    	sub1.put("called_method_name", called_method_name);
    				    	sub1.put("called_method_param", called_method_param);
    				        sub1.put("return_type", return_type);
    				    	totalList.add(sub1);
    					}
    				}
    				sub.put("sub", totalList);
    				classReferences.add(sub);
    			}
        	}
        }
        return classReferences;
    }
	//转换参数所需
	private String argumentList(Type[] arguments) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(arguments[i].toString());
        }
        return sb.toString();
    }
	
    public void saveUrl(Map pairMap,String projectId,String branchId,String methodId) throws Exception{
        try {
            if (pairMap != null && pairMap.size() > 0){
                logger.info("pair:"+pairMap);
                String pair = pairMap.get("pair")+"";
                BaseInterfaceDO baseInterfaceDO =new BaseInterfaceDO();
                baseInterfaceDO.setId(UUIDUtils.getUUID());
                baseInterfaceDO.setProjectId(projectId);
                baseInterfaceDO.setBranchId(branchId);
                baseInterfaceDO.setMethodId(methodId);
                baseInterfaceDO.setUrl(pairMap.get("pair")+"");
                baseInterfaceService.save(baseInterfaceDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("保存地址出错！！！！！！！！！！");
        }
    }
}
