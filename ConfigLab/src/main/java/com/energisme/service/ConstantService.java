package com.energisme.service;

import java.util.List;

import com.ifi.lab.LabDAO.model.Constant;

public interface ConstantService {

	String getConstantValue(Integer labId, String key);
	Boolean updateValue(int labId, String key, String value);
	List<Constant> getConstantByLabId(int labId);
	Constant getConstant(Integer labId, String key);
	Boolean save(Constant obj);
}
