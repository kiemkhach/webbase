package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab004;

public interface ConfigLab004Dao {
	ConfigLab004 getConfigByType(int type);

	boolean save(ConfigLab004 obj);

	ConfigLab004 getConfigBySite(Integer siteId);

	ConfigLab004 findBySite(Integer siteId);

	List<ConfigLab004> getAllConfig();
	public Integer getMaxSiteId();
	public ConfigLab004 findById(int id);
	public boolean delete(int id);

}
