package com.energisme.service;

import com.ifi.common.bean.caloon.CaloonResidentBean;
import com.ifi.common.bean.caloon.CaloonSyndicBean;

public interface CaloonService {
	
	CaloonSyndicBean getCaloonSyndicBean(Integer caloonSyndicId);
	
	CaloonResidentBean getCaloonResidentBean(Integer caloonResidentId);
	
	String getLastDayResident(Integer residentId);
	
	String getLastDaySyndic(Integer syndicId);
}
