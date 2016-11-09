package com.ifi.lab.LabDAO.dao;

import java.util.Date;
import java.util.List;

import com.ifi.lab.LabDAO.model.MonitorModule;

public interface MonitorModuleDAO {
	boolean save(MonitorModule obj);

	boolean saveAll(List<MonitorModule> listObj);

	MonitorModule findById(Integer id);

	MonitorModule findByModuleNo(String moduleNo);

	MonitorModule findByModuleAndDate(String moduleNo, Date dateData);

	List<MonitorModule> getModuleByDay(Integer gatewayId);

	boolean delete(MonitorModule obj);

	boolean deleteAll(List<MonitorModule> listObj);

}
