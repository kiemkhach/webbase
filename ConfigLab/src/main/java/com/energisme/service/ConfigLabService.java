package com.energisme.service;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab;

public interface ConfigLabService {
	Lab getConfigLabById(Integer id);
	List<Lab> getAllConfigLabs();
	boolean save(Lab obj);
	boolean update(Lab obj);
	boolean delete(Integer id);
}
