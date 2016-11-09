package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.MView;

public interface MViewDAO {
	boolean save(MView obj);

	boolean delete(MView obj);

	boolean deleteAll(List<MView> listObj);

	MView findById(Integer id);

	List<MView> findAll();

}
