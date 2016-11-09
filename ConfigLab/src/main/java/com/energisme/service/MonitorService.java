package com.energisme.service;

import com.energisme.bean.MonitorBean;

public interface MonitorService {

	MonitorBean getMonitor();

	boolean saveMonitor(MonitorBean monitorBean);
}
