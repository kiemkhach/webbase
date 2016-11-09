package com.ifi.lab.LabDAO.dao;

import java.util.Date;
import java.util.List;

import com.ifi.lab.LabDAO.model.MGatewayFile;

public interface MGatewayFileDao {
	boolean save(MGatewayFile file);

	MGatewayFile findByID(Integer id);

	List<MGatewayFile> getByGateway(Integer gatewayId);
	
	int deleteBeforeDay(Date beforeDate); 
}
