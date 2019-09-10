package org.amm.icm;

import org.amm.icm.system.pojo.GirlFriend;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("org.amm.icm.**.dao")
@EnableCaching
@EnableScheduling
@EnableAsync
public class App 
{
    public static void main( String[] args )
    {
    	//SpringApplication.run(App.class, args);
    	GirlFriend girlFriend=new GirlFriend();
    	girlFriend.setName("李岩的女朋友");
    	girlFriend.setAddress("未知");
    	girlFriend.setAge(18);
    	girlFriend.setHobbey("讲道理");
    }
}
