package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexKey;

public interface IdexKeyDAO {
	List<IdexKey> getAll();
	List<IdexKey> findByType(Integer type);
}
