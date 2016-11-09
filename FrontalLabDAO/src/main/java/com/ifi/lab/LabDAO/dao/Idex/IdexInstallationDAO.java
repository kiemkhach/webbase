package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.common.bean.Idex.NodeInfo;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;

public interface IdexInstallationDAO {
	IdexInstallation findById(Integer installationId);

	public NodeInfo getNode(Integer installationId, Integer nodeId, Integer type);

	public int addNode(NodeInfo nodeInfo, Integer insallationId, Integer type, Integer parentId);
	
	List<IdexInstallation> getAll();

	Integer deleteNode(Integer installationId, Integer nodeId, Integer type);

	boolean delete(Integer installationId);
	
	boolean save(IdexInstallation idexInstallation);
}
