package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab003;

public interface ConfigLab003DAO {
	public boolean save(ConfigLab003 obj);
	public ConfigLab003 getConfigBySite(Integer siteId);
	public ConfigLab003 findBySite(Integer siteId);
	public List<ConfigLab003> getAllConfig();
	public Integer getMaxSiteId();
	public ConfigLab003 findById(int id);
	public boolean delete(int id);
}
