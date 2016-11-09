package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab007;


public interface ConfigLab007DAO {
	public boolean save(ConfigLab007 obj);
	public boolean update(ConfigLab007 obj);
	public boolean delete(int id);
	public List<ConfigLab007> getAllConfig();
	public ConfigLab007 findById(int id);
	public ConfigLab007 findBySiteId(int siteId);
	public boolean saveOrUpdate(ConfigLab007 obj);
	public Integer getMaxSiteId();
}
