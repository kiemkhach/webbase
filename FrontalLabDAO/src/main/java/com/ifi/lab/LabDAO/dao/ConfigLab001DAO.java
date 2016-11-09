package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab001;

public interface ConfigLab001DAO {
	public boolean save(ConfigLab001 obj);
	public ConfigLab001 getConfigBySite(Integer siteId);
	public ConfigLab001 findBySite(Integer siteId);
	public List<ConfigLab001> getAllConfig();
	public Integer getMaxSiteId();
	public ConfigLab001 findById(int id);
	public boolean delete(int id);
}
