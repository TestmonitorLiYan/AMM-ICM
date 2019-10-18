package com.bootdo.common.utils;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bootdo.version.dao.BaseKafkaCacheDao;
import com.bootdo.version.dao.BaseVersionbranchDao;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.dao.BaseVersionserverDao;
import com.bootdo.version.domain.BaseKafkaCache;
import com.bootdo.version.domain.BaseVersionbranchDO;
import com.bootdo.version.domain.BaseVersionserverDO;

public class TestQuartz extends QuartzJobBean{
	private BaseKafkaCacheDao baseKafkaCacheDao=(BaseKafkaCacheDao)SpringContextUtil.getBean("baseKafkaCacheDao");
	private BaseVersionmethodDao baseVersionmethodDao=(BaseVersionmethodDao)SpringContextUtil.getBean("baseVersionmethodDao");
	private BaseVersionbranchDao baseVersionbranchDao=(BaseVersionbranchDao)SpringContextUtil.getBean("baseVersionbranchDao");
	private BaseVersionserverDao baseVersionserverDao=(BaseVersionserverDao)SpringContextUtil.getBean("baseVersionserverDao");
	private final Logger logger = LoggerFactory.getLogger(KafkaCacheUtils.class);
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("-----------------------------+++++++++++++++++++++");
		// TODO Auto-generated method stub
				logger.info("begin----------------》》定时消费kafka缓存消息开始《《------------------");
				//查询出被记录的所有消息
				List<BaseKafkaCache> baseKafkaCacheList=baseKafkaCacheDao.list();
				for(BaseKafkaCache baseKafkaCache:baseKafkaCacheList) {
					//循环执行更新操作
					int result =baseVersionmethodDao.updateStatus(baseKafkaCache);
					if(result>0) {//更新成功的则删除，未成功的不删除
						//成功则说明，必定存在相应服务器。更改对应服务器的状态
						BaseVersionbranchDO baseVersionbranchDO=baseVersionbranchDao.getStatus(baseKafkaCache.getProjectCode(), baseKafkaCache.getBranchName(),baseKafkaCache.getVersion());
						BaseVersionserverDO baseVersionserverDO=baseVersionserverDao.getByversionIdIp(baseVersionbranchDO.getVersionid(), baseKafkaCache.getIp());
						BaseVersionserverDO bv=new BaseVersionserverDO();
						bv.setId(baseVersionserverDO.getId());
						bv.setStatus("1");
						baseVersionserverDao.update(bv);
						//删除
						baseKafkaCacheDao.remove(baseKafkaCache.getId());
					}
					
				}
				//时间超过一个月的信息删除
				logger.info("=============>>>时间超过一个月的信息开始清理<<<==============");
				int removeNum=baseKafkaCacheDao.removeByTimeOut();
				logger.info("清理超过一个月的数据"+removeNum+"条");
	}

}
