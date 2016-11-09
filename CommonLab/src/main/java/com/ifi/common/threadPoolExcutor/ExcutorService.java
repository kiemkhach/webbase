package com.ifi.common.threadPoolExcutor;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ifi.common.util.PropertiesLoader;

/**
 * 
 * @author nhanh
 *
 */
public class ExcutorService {

	private static ThreadPoolExecutor executorPool;
	// private ThreadFactory threadFactory;
	private RejectedExecutionHandlerImpl rejectionHandler;
	// private GetLab011ResidentThread getinfoThread;

	private ArrayList<Thread> lstThread = new ArrayList<Thread>();

	public ExcutorService() {
		if (executorPool == null) {
			rejectionHandler = new RejectedExecutionHandlerImpl();
			// threadFactory = Executors.defaultThreadFactory();
			executorPool = new ThreadPoolExecutor(PropertiesLoader.threadCorePoolSize,
					PropertiesLoader.threadMaxPoolSize, PropertiesLoader.threadKeepAlive, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(2), rejectionHandler);
		}
	}

	public void excute(Thread thread) {
		executorPool.execute(thread);
	}

	public void shutdown() {
		if (executorPool != null) {
			executorPool.shutdown();

		}
	}

	public static ThreadPoolExecutor getExecutorPool() {
		return executorPool;
	}

	public static void setExecutorPool(ThreadPoolExecutor executorPool) {
		ExcutorService.executorPool = executorPool;
	}

	public ArrayList<Thread> getLstThread() {
		return lstThread;
	}

	public void setLstThread(ArrayList<Thread> lstThread) {
		this.lstThread = lstThread;
	}

	public void removeArrayThread() {
		this.lstThread.clear();
	}
}
