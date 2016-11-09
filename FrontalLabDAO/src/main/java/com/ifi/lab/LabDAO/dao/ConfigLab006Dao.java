package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab006;

public interface ConfigLab006Dao {

	boolean update(ConfigLab006 obj);

	boolean save(ConfigLab006 obj);

	Integer getMaxSiteId();

	List<ConfigLab006> getAllConfig();

	ConfigLab006 findBySite(Integer siteId);

	public ConfigLab006 findById(int id);

	public boolean delete(int id);
}
