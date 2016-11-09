package com.ifi.common.threadPoolExcutor;

import java.util.List;
import java.util.Map;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.ws.GetIndusDataAction;

public class GetLab011ResidentThread extends Thread {
	private ExcutorService exService;
	private int threadType;
	private int seconds;
	private List<String> lstModule;
	private String fromDate;
	private String toDate;
	private Integer typeTime;
	private Map<String, Map<String, Double>> tmp = null;
	private Map<String, Double> mapTmp = null;
	private GetIndusDataAction indus = new GetIndusDataAction();

	private boolean run = true;

	public GetLab011ResidentThread(ExcutorService exService, int delay, List<String> module, String fromDate,
			String toDate, Integer typeTime, int threadType) {
		this.exService = exService;
		this.seconds = delay;
		this.lstModule = module;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.typeTime = typeTime;
		this.threadType = threadType;
	}

	public void shutdown() {
		this.run = false;
	}

	public void run() {

		while (run) {

			switch (threadType) {
			case FrontalKey.THREAD_TYPE_PRECALPULSEBYTYPE:
				tmp = indus.getWS_PreCalPulseByType(lstModule, fromDate, toDate, typeTime);
				if (tmp != null) {
					System.out.println(tmp);
					System.out.println("----------------getWS_PreCalPulseByType" + this.getName());
				}

				break;

			case FrontalKey.THREAD_TYPE_LAST_TEMPEATURE_BYTIME:
				mapTmp = indus.getWS_LastTemperatureByTime(lstModule, fromDate, toDate);
				if (mapTmp != null) {
					System.out.println(mapTmp);
					System.out.println("----------------getWS_LastTemperatureByTime" + this.getName());
				}
				break;
			default:
				break;
			}
			this.shutdown();
			exService.getLstThread().add(this);
			exService.getExecutorPool().remove(this);

			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public Map<String, Map<String, Double>> getTmp() {
		return tmp;
	}

	public void setTmp(Map<String, Map<String, Double>> tmp) {
		this.tmp = tmp;
	}

	public Map<String, Double> getMapTmp() {
		return mapTmp;
	}

	public void setMapTmp(Map<String, Double> mapTmp) {
		this.mapTmp = mapTmp;
	}

	public ExcutorService getExService() {
		return exService;
	}

	public void setExService(ExcutorService exService) {
		this.exService = exService;
	}

}