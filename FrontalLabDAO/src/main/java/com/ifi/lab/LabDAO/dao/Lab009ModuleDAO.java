package com.ifi.lab.LabDAO.dao;

import java.util.List;
import java.util.Map;

import com.ifi.lab.LabDAO.model.Lab009Module;

public interface Lab009ModuleDAO {
	List<Lab009Module> getAllData();
	Lab009Module findById(Integer id);
	Integer create(Lab009Module obj);
	boolean update(Lab009Module obj);
	boolean delete(Integer id);
	Map<Integer, Lab009Module> getAllMapLab009ModuleNotWater();
	Map<Integer, Lab009Module> getAllMapLab009Module();
}
