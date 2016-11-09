package com.energisme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.ConfigLabService;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.Lab;

public class ConfigLabServiceImpl implements ConfigLabService {
	@Autowired
	private LabDAO labDAO;
	
	@Override
	public Lab getConfigLabById(Integer id) {
		Lab obj = labDAO.findById(id.intValue());
		return obj;
	}

	@Override
	public List<Lab> getAllConfigLabs() {
		List<Lab> list = labDAO.getAllLab();
		return list;
	}

	@Override
	public boolean save(Lab obj) {
		return labDAO.save(obj);
	}

	@Override
	public boolean update(Lab obj) {
		return labDAO.update(obj);
	}

	@Override
	public boolean delete(Integer id) {
		return labDAO.delete(id.intValue());
	}

}
