package com.ifi.common.util;

public class PropertiesLoader {

	public static final int threadCorePoolSize;
	public static final int threadMaxPoolSize;
	public static final int threadKeepAlive;
	public static final int threadActiveCount;
	public static final int threadConcurentModuleStep;
	public static final int threadTimeOutWaitRestAPI;

	static {
		threadCorePoolSize = PropertiesReader.getValueInt(ConfigEnum.THREAD_CORE_POOL_SIZE);
		threadMaxPoolSize = PropertiesReader.getValueInt(ConfigEnum.THREAD_MAX_POOL_SIZE);
		threadKeepAlive = PropertiesReader.getValueInt(ConfigEnum.THREAD_KEEP_ALIVE);
		threadActiveCount = PropertiesReader.getValueInt(ConfigEnum.THREAD_ACTIVE_COUNT);
		threadConcurentModuleStep = PropertiesReader.getValueInt(ConfigEnum.THREAD_COCURENT_MODULE_STEP);
		threadTimeOutWaitRestAPI = PropertiesReader.getValueInt(ConfigEnum.THREAD_TIMEOUT_WAIT_RESTAPI);
	}

}
