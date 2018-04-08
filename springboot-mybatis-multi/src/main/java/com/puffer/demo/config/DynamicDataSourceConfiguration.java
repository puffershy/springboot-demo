package com.puffer.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DynamicDataSourceConfiguration {

	@ConfigurationProperties(prefix = "spring.datasource.master")
	@Bean("masterDatasource")
	private DataSource masterDatasource() {
		return new DruidDataSource();
	}

	@ConfigurationProperties(prefix = "spring.datasource.cluster")
	@Bean("clusterDatasource")
	private DataSource clusterDatasource() {
		return new DruidDataSource();
	}

	@Bean
	@Primary
	private DynamicDataSource dataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.MASTER, masterDatasource());
		targetDataSources.put(DataSourceType.CLUSTER, clusterDatasource());

		DynamicDataSource dataSource = new DynamicDataSource();

		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(masterDatasource());

		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);

		// 加载mybatiss xml文件
		factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/cluster/**/*.xml"));
		// 加载持久化实体对象
		factory.setTypeAliasesPackage("com.puffer.**.entity.cluster");

		return factory.getObject();
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}

}
