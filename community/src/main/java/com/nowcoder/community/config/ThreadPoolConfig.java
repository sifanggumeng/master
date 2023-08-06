package com.nowcoder.community.config;

import com.nowcoder.community.quartz.AlphaJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@EnableScheduling   //启用Spring定时任务线程池
@EnableAsync    //启用@Async（该注解标注方法在多线程环境下,可以被异步的调用.）
public class ThreadPoolConfig {

}
