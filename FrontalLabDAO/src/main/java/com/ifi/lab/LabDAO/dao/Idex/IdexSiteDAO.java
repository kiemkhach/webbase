package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.common.bean.Idex.NodeInfo;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;


public interface IdexSiteDAO {

	public List<IdexSite> getListSite(Integer siteId);

	public NodeInfo getInstallationById(Integer installationId);

	boolean addInstallation(IdexInstallation installation);
	
	List<IdexSite> findByInstallation(Integer installationId);
	
	List<Integer> getListCompteurBySite(Integer siteId);
	boolean save(IdexSite obj);

	public Lab findByName(String name);

	public List<IdexSite> getAllConfig();
	Long getNumberSite();
	IdexSite getConfigBySite(Integer siteId);
	
	IdexSite findById(Integer idexSiteId);
	
	boolean delete(Integer installationId);
}
