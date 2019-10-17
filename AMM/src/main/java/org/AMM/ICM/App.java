package org.amm.icm;

import javax.servlet.MultipartConfigElement;

import org.amm.icm.system.pojo.GirlFriend;
import org.amm.icm.utils.CustomMultipartResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;

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
    	SpringApplication.run(App.class, args);
    }
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CustomMultipartResolver customMultipartResolver = new CustomMultipartResolver();
        return customMultipartResolver;
    }
   /* @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory config = new MultipartConfigFactory();
        config.setMaxFileSize("9000MB");
        config.setMaxRequestSize("9000MB");
        return config.createMultipartConfig();
    }*/
}
