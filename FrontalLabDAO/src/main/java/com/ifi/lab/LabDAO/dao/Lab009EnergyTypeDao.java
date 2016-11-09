package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009EnergyType;

public interface Lab009EnergyTypeDao {
	List<Lab009EnergyType> getAll();
	boolean updateEnergyType(Lab009EnergyType lab009Energy);
	Lab009EnergyType findByID(Integer id);
	boolean delete(int id);
	Integer createEnergy(Lab009EnergyType obj);
	List<Lab009EnergyType> getAllConsommation();
}
