package com.bootdo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bootdo.version.dao.BaseKafkaCacheDao;
import com.bootdo.version.dao.BaseProjectversionDao;
import com.bootdo.version.dao.BaseVersionbranchDao;
import com.bootdo.version.dao.BaseVersionmethodDao;
import com.bootdo.version.dao.BaseVersionserverDao;
import com.bootdo.version.domain.BaseProjectversionDO;
import com.bootdo.version.domain.BaseVersionbranchDO;
import com.bootdo.version.domain.BaseVersionserverDO;

import net.sf.json.JSONObject;

@Component
public class KafkaUtils {
	@Autowired
	private BaseVersionserverDao baseVersionserverDao;
	@Autowired
	private BaseVersionmethodDao baseVersionmethodDao;
	@Autowired
	private BaseVersionbranchDao baseVersionbranchDao;
	@Autowired
	private BaseKafkaCacheDao baseKafkaCacheDao;
	@Autowired
	private BaseProjectversionDao baseProjectversionDao;
	private final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
	@KafkaListener(topics = "test0811")
	public void receive(ConsumerRecord<?, ?> consumer) {
		int result = 0;
		JSONObject jsonObject = JSONObject.fromObject(consumer.value());
		Span span = (Span) JSONObject.toBean(jsonObject, Span.class);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(span.getTime());
		Date date = new Date(lt);
		Date now=new Date();
		logger.info("接收到执行时间为："+simpleDateFormat.format(date)+"项目"+span.getApp()+"的"+span.getInst()+"工程的"+span.getVersion()+"版本"+span.getTags().getClazz()+"类的"+span.getTags().getMethod()+"方法");
		//查询解析出来的版本信息
		BaseVersionbranchDO baseVersionbranchDO=baseVersionbranchDao.getStatus(span.getApp(), span.getInst(),span.getVersion());
		//判断版本是否存在
		if(baseVersionbranchDO!=null) {//存在
			//判断该项目该工该版本来自的ip是否存在
			BaseVersionserverDO baseVersionserverDO=baseVersionserverDao.getByversionIdIp(baseVersionbranchDO.getVersionid(), span.getIp());
			if(baseVersionserverDO!=null) {//该服务器存在，更新连通性状态
				BaseVersionserverDO bv=new BaseVersionserverDO();
				bv.setId(baseVersionserverDO.getId());
				bv.setStatus("1");
				baseVersionserverDao.update(bv);
			//判断是否关闭，关闭则不存储，未关闭直接更新方法状态
			BaseProjectversionDO baseProjectversionDO=baseProjectversionDao.getClose(baseVersionbranchDO.getVersionid());
			
			System.out.println("这是一条分割线--------------------------------------------------------------------------------------------------------------");
			
			if(baseProjectversionDO.getCloseTime()==null) {//未关闭
				//判断项目状态，若是解析成功，直接消费，若是发布中、解析失败则进行存储
				if("1".equals(baseVersionbranchDO.getStatus())) {//解析成功
					logger.info("对执行时间为："+simpleDateFormat.format(date)+"项目"+span.getApp()+"的"+span.getInst()+"工程的"+span.getVersion()+"版本"+span.getTags().getClazz()+"类的"+span.getTags().getMethod()+"方法进行状态记录");
					try {
						//记录执行
						result = baseVersionmethodDao.updateMethodStatus(span.getApp(), span.getInst(), span.getTags().getClazz(),
								span.getTags().getMethod(), span.getTags().getMsign(), date,span.getVersion());
						if(result>0) {
							logger.info("记录成功！");
						}else {
							logger.info("未找到该方法");
						}
					} catch (Exception e) {
						// TODO: handle exception
						logger.info("修改过程出现异常");
						logger.info(e.toString());
					}
				}else {
					//进行存储
					Integer num=baseKafkaCacheDao.repeat(span.getApp(), span.getInst(), span.getTags().getClazz(), span.getTags().getMethod(), span.getTags().getMsign(),  span.getVersion(),span.getIp());
					if(num==0) {//不存在，则进行存储，存在，则不再存储
						logger.info("对执行时间为："+simpleDateFormat.format(date)+"项目"+span.getApp()+"的"+span.getInst()+"工程的"+span.getVersion()+"版本"+span.getTags().getClazz()+"类的"+span.getTags().getMethod()+"方法,由于未解析成功，进行存储");
						String id=UUIDUtils.getUUID();
						System.out.println(id);
						baseKafkaCacheDao.save(id, span.getApp(), span.getInst(), span.getTags().getClazz(), span.getTags().getMethod(), span.getTags().getMsign(), date, span.getVersion(),now,span.getIp());
					}
				}
			}
			}
		}else {//不存在
			//不存在，（不论何种原因造成），直接存储信息后进行定时任务
			Integer num=baseKafkaCacheDao.repeat(span.getApp(), span.getInst(), span.getTags().getClazz(), span.getTags().getMethod(), span.getTags().getMsign(),  span.getVersion(),span.getIp());
			if(num==0) {//不存在，则进行存储，存在，则不再存储
				logger.info("对执行时间为："+simpleDateFormat.format(date)+"项目"+span.getApp()+"的"+span.getInst()+"工程的"+span.getVersion()+"版本"+span.getTags().getClazz()+"类的"+span.getTags().getMethod()+"方法,由于版本不存在，进行存储");
				String id=UUIDUtils.getUUID();
				System.out.println(id);
				baseKafkaCacheDao.save(id, span.getApp(), span.getInst(), span.getTags().getClazz(), span.getTags().getMethod(), span.getTags().getMsign(), date, span.getVersion(),now,span.getIp());
			}
		}
	}
}
