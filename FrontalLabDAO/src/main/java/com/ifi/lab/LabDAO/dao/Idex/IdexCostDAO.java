package com.ifi.lab.LabDAO.dao.Idex;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;

public interface IdexCostDAO {

	List<IdexCost> findByInstallation(Integer installationId);
	List<IdexCost> getAll();
	List<IdexCost> findByInstallationId(Integer installationId);
	boolean save(IdexCost obj);
	boolean delete(Integer installationId);
	boolean deleteById(int idexCostId);
	Map<Integer,List<IdexCostDetail>> getDetailByInstallation(Integer installationId,Date fromDate, Date toDate);
}
