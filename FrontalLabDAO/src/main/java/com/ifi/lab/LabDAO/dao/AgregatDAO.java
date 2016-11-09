package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Agregat;

public interface AgregatDAO {
	/**
	 * Method insert Agregat
	 * @param obj
	 * @return true if insert success, false if transaction get an error
	 */
	public boolean save(Agregat obj);
	
	public boolean saveList(List<Agregat> lst);
}
