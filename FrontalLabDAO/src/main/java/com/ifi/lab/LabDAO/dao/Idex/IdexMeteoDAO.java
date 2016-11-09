package com.ifi.lab.LabDAO.dao.Idex;

import java.util.Date;
import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexMeteo;

public interface IdexMeteoDAO {

	Integer sumByInstallation(Integer installationId,Date fromDate,Date toDate);
	List<IdexMeteo> findByInstallation(Integer installationId);
	List<IdexMeteo> getAll();
	boolean deleteByInstallation(Integer installationId);
	boolean saveMeteoList(List<IdexMeteo> idexMeteoList);
	
}
