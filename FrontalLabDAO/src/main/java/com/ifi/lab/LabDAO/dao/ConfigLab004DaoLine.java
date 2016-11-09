package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab004Line;

public interface ConfigLab004DaoLine {
	boolean save(ConfigLab004Line obj);

	boolean saveAll(List<ConfigLab004Line> listObj);

	ConfigLab004Line findByid(Integer id);

	List<ConfigLab004Line> getAllLineByConfigId(Integer configLab004Id);

	ConfigLab004Line getConfigLineByType(Integer type, Integer configLab004Id);
}
