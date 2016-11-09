package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab006Client;


public interface ConfigLab006ClientDAO {
	ConfigLab006Client getClientById(int id);
	boolean saveOrUpdate(ConfigLab006Client obj);
	boolean delete(int id);
	List<ConfigLab006Client> getBySubscription(int id);
	ConfigLab006Client getNewestClient();
}
