package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab009;

public interface ConfigLab009DAO{
	List<ConfigLab009> getAll();
	boolean saveOrUpdate(ConfigLab009 configLab009);
	ConfigLab009 findByClient(Integer clientID);
}

