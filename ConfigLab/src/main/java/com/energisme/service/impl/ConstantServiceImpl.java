package com.energisme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.ConstantService;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.model.Constant;

public class ConstantServiceImpl implements ConstantService {

	@Autowired
	private ConstantDAO constantDao;

	public String getConstantValue(Integer labId, String key) {
		return constantDao.getByLabIdnKey(labId, key).getValue();
	}

	@Override
	public Boolean updateValue(int labId, String key, String value) {
		// TODO Auto-generated method stub
		return constantDao.updateValue(labId, key, value);
	}

	@Override
	public List<Constant> getConstantByLabId(int labId) {
		// TODO Auto-generated method stub
		List<Constant> lst = constantDao.getByLabId(labId);
		return lst;
	}

	@Override
	public Boolean save(Constant obj) {
		// TODO Auto-generated method stub
		return constantDao.save(obj);
	}

	@Override
	public Constant getConstant(Integer labId, String key) {
		// TODO Auto-generated method stub
		return constantDao.getByLabIdnKey(labId, key);
	}

}
