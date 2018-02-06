package com.puffer.demo.urule.config;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.bstek.urule.URulePropertyPlaceholderConfigurer;

public class PropertiesConfiguration extends URulePropertyPlaceholderConfigurer implements InitializingBean{

	public void afterPropertiesSet() throws Exception {
		Properties props=new Properties();
		props.setProperty("urule.repository.xml", "classpath:mysql.xml");	
		setProperties(props);
	}

}
