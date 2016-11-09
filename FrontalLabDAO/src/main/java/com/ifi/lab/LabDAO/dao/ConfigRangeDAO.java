package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigRange;

public interface ConfigRangeDAO {
	List<ConfigRange> getRangeByType(String type);
	ConfigRange getRangeById(int id);
	boolean save(ConfigRange obj);
	boolean delete(int id);
	List<ConfigRange> getRangeByLabIdnPageIdnType(int labId, int pageId, String type);
}
