package com.energisme.service.impl;

import org.springframework.web.client.RestTemplate;

import com.energisme.service.CaloonService;
import com.ifi.common.bean.caloon.CaloonResidentBean;
import com.ifi.common.bean.caloon.CaloonSyndicBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.PropertiesReader;

public class CaloonServiceImpl implements CaloonService {
	private static final String GET_CALOON_SYNDIC_BEAN = "/WebServiceLab/lab011/getCaloonSyndicBean?caloonSyndicId={0}";
	private static final String GET_CALOON_RESIDENT_BEAN = "/WebServiceLab/lab011/getCaloonResidentBean?caloonResidentId={0}";
	private static final String GET_LAST_DAY_RESIDENT = "/WebServiceLab/lab011/getLastDayResident?residentId={0}";
	private static final String GET_LAST_DAY_SYNDIC = "/WebServiceLab/lab011/getLastDaySyndic?syndicId={0}";

	@Override
	public CaloonSyndicBean getCaloonSyndicBean(Integer caloonSyndicId) {
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_CALOON_SYNDIC_BEAN;
		RestTemplate restClient = new RestTemplate();
		CaloonSyndicBean bean = restClient.getForObject(path, CaloonSyndicBean.class, caloonSyndicId);
		return bean;
	}

	@Override
	public CaloonResidentBean getCaloonResidentBean(Integer caloonResidentId) {
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_CALOON_RESIDENT_BEAN;
		RestTemplate restClient = new RestTemplate();
		CaloonResidentBean bean = restClient.getForObject(path, CaloonResidentBean.class, caloonResidentId);
		return bean;
	}

	@Override
	public String getLastDayResident(Integer residentId) {
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_LAST_DAY_RESIDENT;
		RestTemplate restClient = new RestTemplate();
		String date = restClient.getForObject(path, String.class, residentId);
		if(date != null && date.startsWith("\"")){
			date = date.replace("\"", "");
		}
		return date;
	}

	@Override
	public String getLastDaySyndic(Integer syndicId) {
		String path = PropertiesReader.getValue(ConfigEnum.URI_WEB_SERVICE) + GET_LAST_DAY_SYNDIC;
		RestTemplate restClient = new RestTemplate();
		String date = restClient.getForObject(path, String.class, syndicId);
		if(date != null && date.startsWith("\"")){
			date = date.replace("\"", "");
		}
		return date;
	}

}
