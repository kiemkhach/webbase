package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.MViewGateway;

public interface MViewGatewayDAO {

	boolean save(MViewGateway obj);
	
	boolean saveAll(List<MViewGateway> listObj);

	boolean delete(MViewGateway obj);

	MViewGateway findById(Integer id);
	
	MViewGateway findByGatewayIdAndViewId(Integer gatewayId, Integer viewId);
	
	MViewGateway findByViewId(Integer viewId);
	
	Integer countByViewId(Integer viewId);
	
	int deleteGateWayOfView(Integer viewId,List<Integer> gatewayIdLst);

}
