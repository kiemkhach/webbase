package com.energisme.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.MonitorBean;
import com.energisme.service.MonitorService;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorAction extends AbstractBaseAction implements
		ModelDriven<MonitorBean> {

	private static final long serialVersionUID = 1L;
	private String mes;
	@Autowired
	private MonitorBean monitorBean;
	@Autowired
	private MonitorService monitorService;

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"constantConfig.xml");
		monitorBean = monitorService.getMonitor();
		return SUCCESS;
	}

	public String save() {
		if (monitorBean.getUserName() != null
				&& monitorBean.getPassWord() != null
				&& !monitorBean.getUserName().equals("")
				&& !monitorBean.getPassWord().equals("")) {
			if (monitorService.saveMonitor(monitorBean)) {
				mes = SUCCESS;
			} else {
				mes = ERROR;
			}
		} else {
			mes = ERROR;
		}
		return SUCCESS;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	@Override
	public MonitorBean getModel() {
		return monitorBean;
	}

	public MonitorBean getMonitorBean() {
		return monitorBean;
	}

	public void setMonitorBean(MonitorBean monitorBean) {
		this.monitorBean = monitorBean;
	}

	public MonitorService getMonitorService() {
		return monitorService;
	}

	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

}
