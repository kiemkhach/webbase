package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab008Simulation;

public interface Lab008SimulationDAO {
	Lab008Simulation findById(Integer id);
	public boolean deleteById(Integer id);
	public boolean saveOrUpdate(Lab008Simulation obj);
	List<Lab008Simulation> findSimulationLimitBySite (Integer siteId);
}
