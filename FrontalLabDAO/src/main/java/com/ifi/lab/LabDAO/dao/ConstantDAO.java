package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Constant;

public interface ConstantDAO {
	boolean save(Constant obj);
	boolean saveAll(List<Constant> listObj);
	boolean delete(int labId, String key);
	List<Constant> getByLabId(int labId);
	Constant getByLabIdnKey(int labId, String key);
	boolean getByKeyValue(String key, String value);
	boolean updateValue(int labId, String key, String value);
}
