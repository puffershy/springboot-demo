package com.puffer.demo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 基于AbstractRoutingDataSource实现的动态数据源切换
 *
 * @author buyi
 * @date 2018年4月5日上午11:47:12
 * @since 1.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 使用ThreadLocal把数据源与当前线程绑定
	 */
	private static final ThreadLocal<DataSourceType> dataSources = new ThreadLocal<DataSourceType>();

	public static void setDataSource(DataSourceType datasource) {
		dataSources.set(datasource);
	}

	public static DataSourceType getDataSource() {
		return dataSources.get();
	}

	/**
	 * 清楚数据源信息
	 *
	 * @author buyi
	 * @date 2018年4月5日上午11:37:40
	 * @since 1.0.0
	 */
	public static void clear() {
		dataSources.remove();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSource();
	}

}
