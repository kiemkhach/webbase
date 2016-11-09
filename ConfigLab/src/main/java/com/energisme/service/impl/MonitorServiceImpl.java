package com.energisme.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.MonitorBean;
import com.energisme.service.MonitorService;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.model.Constant;

public class MonitorServiceImpl implements MonitorService {

	@Autowired
	private ConstantDAO constantDao;

	public MonitorBean getMonitor() {
		MonitorBean mBean = new MonitorBean();
		String userName = constantDao.getByLabIdnKey(FrontalKey.MONITOR_LAB_ID,
				FrontalKey.MONITOR_KEY_USERNAME).getValue();
		String passWord = constantDao.getByLabIdnKey(FrontalKey.MONITOR_LAB_ID,
				FrontalKey.MONITOR_KEY_PASSWORD).getValue();
		String timeoutGatewayLcpc = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_LCPC)
				.getValue();
		String timeoutGatewayDevice = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_DEVICE)
				.getValue();
		String timeoutModuleDevice = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_MODULE_DEVICE)
				.getValue();
		mBean.setUserName(userName);
		mBean.setPassWord(passWord);
		mBean.setTimeoutGatewayLcpc(timeoutGatewayLcpc);
		mBean.setTimeoutGatewayDevice(timeoutGatewayDevice);
		mBean.setTimeoutModuleDevice(timeoutModuleDevice);
		return mBean;
	}

	public boolean saveMonitor(MonitorBean monitorBean) {
		List<Constant> listConstant = new ArrayList<Constant>();
		Constant constantUsername = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.MONITOR_KEY_USERNAME);
		Constant constantPassword = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.MONITOR_KEY_PASSWORD);
		Constant cTimeoutGatewayLcpc = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_LCPC);
		Constant cTimeoutGatewayDevice = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_DEVICE);
		Constant cTimeoutModuleDevice = constantDao.getByLabIdnKey(
				FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_MODULE_DEVICE);
		constantUsername.setValue(monitorBean.getUserName());
		constantPassword.setValue(monitorBean.getPassWord());
		cTimeoutGatewayLcpc.setValue(monitorBean.getTimeoutGatewayLcpc());
		cTimeoutGatewayDevice.setValue(monitorBean.getTimeoutGatewayDevice());
		cTimeoutModuleDevice.setValue(monitorBean.getTimeoutModuleDevice());
		listConstant.add(constantUsername);
		listConstant.add(constantPassword);
		listConstant.add(cTimeoutGatewayLcpc);
		listConstant.add(cTimeoutGatewayDevice);
		listConstant.add(cTimeoutModuleDevice);
		if (constantDao.saveAll(listConstant)) {
			return true;
		}
		return false;
	}
}
