package com.nowcoder.community;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;	//定义一个成员变量记录applicationContext

	//重写接口ApplicationContextAware中的方法
	@Override
	//如果一个类实现了ApplicationContextAware接口的setApplicationContext方法，Spring容器会检测到，
	// Spring容器在扫描主键的时候，会调用它的setApplicationContext方法把容器传进来
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {	//ApplicationContext是Spring容器
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);	//打印出接口的实现类
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));	//打印出当前时间2023-07-05 20:05:07
	}

	@Autowired
	private SimpleDateFormat simpleDateFormat;	//让Spring容器将SimpleDateFormat这个Bean注入到simpleDateFormat属性中





}
