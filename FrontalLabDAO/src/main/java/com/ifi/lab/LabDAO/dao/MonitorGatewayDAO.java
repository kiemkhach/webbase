package com.ifi.lab.LabDAO.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.ifi.lab.LabDAO.model.MonitorGateway;

public interface MonitorGatewayDAO {

	boolean save(MonitorGateway obj);

	boolean saveAll(List<MonitorGateway> listObj);

	MonitorGateway findById(Integer id);

	MonitorGateway findBySerialNo(String serialNo);
	
	MonitorGateway findBySerialAndDate(String serialNo, Date dateData);

	List<MonitorGateway> getAllGateways(Timestamp dateData);
	
	List<MonitorGateway> getGatewaysByDay(Timestamp dateData,int viewId);

	boolean delete(MonitorGateway obj);
	
	boolean deleteAll(List<MonitorGateway> listObj);

}
