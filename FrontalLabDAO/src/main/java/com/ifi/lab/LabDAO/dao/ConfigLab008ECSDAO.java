package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import com.ifi.lab.LabDAO.model.Lab008ECS;


public interface ConfigLab008ECSDAO {
	Lab008ECS getECSById(int id);
	boolean saveOrUpdate(Lab008ECS obj);
	boolean delete(int id);
	List<Lab008ECS> getBySubscription(int id);
	Lab008ECS getNewestECS();
	List<Lab008ECS> getECSByType(int configlab8Id, int type);
}
