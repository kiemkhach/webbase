package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexConfig;

public interface IdexConfigDAO {

	IdexConfig getByInstalltion(Integer installationId);
	List<IdexConfig> getAll();
	public boolean saveOrUpdate(IdexConfig idexConfig);
}
