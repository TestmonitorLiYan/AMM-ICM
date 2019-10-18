package com.bootdo.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.domain.BaseProjectDO;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.domain.BaseVersionbranchDO;
import com.bootdo.version.service.BaseProjectService;
import com.bootdo.version.service.BaseProjectversionService;
import com.bootdo.version.service.BaseVersionbranchService;

/**
 * 后台版本发布异步线程
 */
@Component
public class AsynchronousTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UMLService umlService;

	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private BaseProjectService baseProjectService;

	@Autowired
	private BaseProjectversionService baseProjectversionService;

	@Autowired
	private BaseVersionbranchService baseVersionbranchService;
	@Autowired
	private BaseVersionmethodDao baseVersionmethodDao;

	/**
	 * 版本发布
	 * 
	 * @param map
	 * @throws InterruptedException
	 */
	@Async("executer")
	public void task(Map<String, Object> map) throws InterruptedException {
		// 线程安全
		logger.info("开始执行task........................");
		Thread.currentThread().setPriority(1);
		synchronized (this) {
			long start = System.currentTimeMillis();
			String projectId = map.get("projectId") + "";
			String releasePersonid = map.get("releasePersonid") + "";
			String responsiblePersonid = map.get("responsiblePersonid") + "";
			List<Map<String, Object>> branchList = (ArrayList) map.get("branchList");
			BaseProjectDO baseProjectDO = baseProjectService.get(projectId);
			String versionId = map.get("versionId") + "";
			int count = 0;// 统计所有的branch是否成功
			for (int x = 0; x < branchList.size(); x++) {
				try {
					String branchId = branchList.get(x).get("branchId") + "";
					String versionBranchId = branchList.get(x).get("id") + "";
					String path = branchList.get(x).get("path") + "";
					Map<String, Object> result = umlService.reverse( bootdoConfig.getUploadPath() + path,
							projectId, versionId, branchId, Integer.parseInt(releasePersonid + ""), responsiblePersonid,
							versionBranchId);
					logger.info("result:" + result);
					if (result != null) {
						count = count + 1;
					} else {
						throw new Exception("异步线程出错!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("branch处理失败：" + branchList.get(x).get("branchId") + ",将处理下一个branch！");
					continue;// 发布失败继续处理下一个branch
				}
			}
			BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(versionId);
			umlService.updateProject(baseProjectDO, baseProjectversionDO.getVersion());// 更新项目版本号
			if (count == branchList.size()) {
				baseProjectversionDO.setState(3);// 已同步
			} else {
				baseProjectversionDO.setState(2);// 同步失败
			}
			Map<String, Object> c = new HashMap<>();
			c.put("versionId", versionId);
			c.put("changeCount", "changeCount");
			List<BaseVersionbranchDO> doc = baseVersionbranchService.list(c);
			baseProjectversionDO.setChangeMethod(baseVersionmethodDao.count(c));// 变更方法总数
			c.put("changeCount", null);
			baseProjectversionDO.setMethodNumber(baseVersionmethodDao.count(c));// 方法总数
			baseProjectversionService.update(baseProjectversionDO);
			long end = System.currentTimeMillis();
			logger.info("task耗时：" + (end - start));
			logger.info("结束执行task........................");
		}
	}

	/**
	 * 版本重新发布
	 * 
	 * @param map
	 * @throws Exception
	 */
	@Async
	public void reBuildTask(Map<String, Object> map) throws Exception {
		logger.info("开始执行reBuildTask........................");
		Thread.currentThread().setPriority(1);
		synchronized (this) {
			try {
				String path = map.get("path") + "";
				String projectId = map.get("projectId") + "";
				String versionId = map.get("versionId") + "";
				String branchId = map.get("branchId") + "";
				Map<String, Object> result = umlService.singleReverse( bootdoConfig.getUploadPath() + path,
						projectId, versionId, branchId);
				if (result == null) {
					throw new Exception("异步线程出错!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				Map<String, Object> c = new HashMap<>();
				c.put("versionId", versionId);
				c.put("changeCount", "changeCount");
				Map<String, Object> e = new HashMap<>();
				e.put("versionid", versionId);
				List<BaseVersionbranchDO> doc = baseVersionbranchService.list(e);
				BaseProjectversionDO baseProjectversionDO = baseProjectversionService.get(versionId);
				baseProjectversionDO.setChangeMethod(baseVersionmethodDao.count(c));// 变更方法总数
				c.put("changeCount", null);
				baseProjectversionDO.setMethodNumber(baseVersionmethodDao.count(c));// 方法总数
				baseProjectversionService.update(baseProjectversionDO);
				boolean flx = true;
				for (BaseVersionbranchDO bvh : doc) {
					if (!"1".equals(bvh.getStatus())) {
						flx = false;
						break;
					}
				}
				BaseProjectversionDO bv = new BaseProjectversionDO();
				if (flx) {
					bv.setState(3);
				} else {
					bv.setState(2);
				}
				bv.setId(versionId);
				bv.setProjectId(projectId);
				baseProjectversionService.update(bv);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("结束执行reBuildTask........................");

		}
	}

}
