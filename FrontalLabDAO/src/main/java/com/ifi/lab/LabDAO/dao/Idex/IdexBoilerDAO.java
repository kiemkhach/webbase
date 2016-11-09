package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.common.bean.Idex.IdexBoilerInfo;
import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;

public interface IdexBoilerDAO {

	List<IdexBoiler> findByInstallation(Integer installationId);

	int addBoiler(IdexBoilerInfo idexBoiler);
	Long getNumberBoiler();
	
	IdexBoiler findById(Integer idexBoilerId);
	
	boolean delete(Integer installationId);
}
