package com.bootdo.version.service.impl;

import com.bootdo.system.dao.UserDao;
import com.bootdo.version.dao.BaseProjectversionDao;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.domain.BaseVersionmethodDO;
import com.bootdo.version.service.BaseVersionmethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BaseVersionmethodServiceImpl implements BaseVersionmethodService {
    @Autowired
    private BaseVersionmethodDao baseVersionmethodDao;
    @Autowired
    private BaseProjectversionDao baseProjectversionDao;
    
    @Override
    public int save(BaseVersionmethodDO baseVersionmethodDO) {
//        String id = UUID.randomUUID().toString().replace("-","");
//        String method_id = UUID.randomUUID().toString().replace("-","");
//        baseVersionmethodDO.setId(id);
//        baseVersionmethodDO.setMethodId(method_id);//后期注意2个表id不要重复
        return baseVersionmethodDao.save(baseVersionmethodDO);
    }
	@Override
	public Map<String, Object> getAbsolutelyCoverage(String beginVersionId,String endVersionId) {
		// TODO Auto-generated method stub
		Map<String, Object> result=new HashMap<String, Object>();
		//1、查询出结束时间，若为空，则取当前时间
		Date end=baseVersionmethodDao.getEndTime(endVersionId);
		if(end==null) {
			end=new Date();
		}
		//2、查询出实际执行方法个数
		String number=baseVersionmethodDao.exeMethodNum(beginVersionId, end,endVersionId);
		//3、查询出结束版本的总方法个数
		BaseProjectversionDO baseProjectversionDO=baseProjectversionDao.get(endVersionId);
		//4、计算出绝对覆盖率
			Integer exeMethodNum = 0;
			if(number != null){
				exeMethodNum = Integer.parseInt(number);
			}
			String absolutelyCoverage="";
			if(baseProjectversionDO!=null) {
			if(!(baseProjectversionDO.getMethodNumber()==null||0==baseProjectversionDO.getMethodNumber())) {
				absolutelyCoverage = new BigDecimal(Double.parseDouble(exeMethodNum+"")*100/baseProjectversionDO.getMethodNumber()).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
			}else {
				absolutelyCoverage="100";
			}
			result.put("methodNum", baseProjectversionDO.getMethodNumber());
			}else {
				result.put("methodNum", 0);
			}
			result.put("absolutelyCoverage",absolutelyCoverage );
			result.put("exeMethodNum", exeMethodNum);
			return result;
	}
	@Override
	public Map<String, Object> getRelativeCoverage(String beginVersionId, String endVersionId) {
		// TODO Auto-generated method stub
		Map<String, Object> result=new HashMap<String, Object>();
		//1、查询出结束时间，若为空，则取当前时间
		Date end=baseVersionmethodDao.getEndTime(endVersionId);
		if(end==null) {
			end=new Date();
		}
		//2、查询出实际执行方法个数
		String number=baseVersionmethodDao.getExeMethodNum(beginVersionId, end, endVersionId);
		String all=baseVersionmethodDao.getMethodNum(beginVersionId, end, endVersionId);
					Integer exeMethodNum = 0;
					if(number != null){
						exeMethodNum = Integer.parseInt(number);
				}
				String relativeCoverage="";
					if(!(all==null||"0".equals(all))) {
						relativeCoverage = new BigDecimal(Double.parseDouble(exeMethodNum+"")*100/Double.parseDouble(all)).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
					}else {
						relativeCoverage="100";
					}
					result.put("relativeCoverage", relativeCoverage);
					result.put("exeMethodNum", exeMethodNum);
					result.put("methodNum", all);
					return result;
	}

	@Override
	public int deleteFailedVersionMethod(Map<String, Object> map) {
		return baseVersionmethodDao.deleteFailedVersionMethod(map);
	}
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseVersionmethodDao.list(map);
	}
	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseVersionmethodDao.count(map);
	}
	@Override
	public int addRemark(String versionId, String methodId, String remark) {
		// TODO Auto-generated method stub
		return baseVersionmethodDao.addRemark(versionId, methodId, remark);
	}
}
