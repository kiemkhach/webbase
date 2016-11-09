package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab005;

public interface ConfigLab005Dao {

	boolean save(ConfigLab005 obj);

	ConfigLab005 getConfigBySite(Integer siteId);
	Integer getMaxSiteId();
	
	List<ConfigLab005> getAllConfig();
	
	ConfigLab005 findBySite(Integer siteId);
	public ConfigLab005 findById(int id);
	public boolean delete(int id);
}
