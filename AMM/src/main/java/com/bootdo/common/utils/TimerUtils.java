package com.bootdo.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * 指定时间执行定时任务的Timer管理类
 * @author liyan
 *
 */
public class TimerUtils {
	Timer timer;
    
    public TimerUtils(){
        Date time = getTime();
        System.out.println("指定时间time=" + time);
        timer = new Timer();
        timer.schedule(new KafkaCacheUtils(), time);
    }
    
    public Date getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 39);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        
        return time;
    }
    
    public static void main(String[] args) {
        new TimerUtils();
    }
}
