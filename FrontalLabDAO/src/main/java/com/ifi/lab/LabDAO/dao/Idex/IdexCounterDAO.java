package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexCounter;

public interface IdexCounterDAO {
	List<IdexCounter> findAllByInstallation(Integer installationId);
	List<IdexCounter> findByInstallation(Integer installationId);
	IdexCounter findById(Integer idexCounterId);
	Long getNumberCounter();
	IdexCounter findParentById(Integer idexCounterId);
	int saveIdexCounter(IdexCounter idexCounter);
	
	boolean updateCoefficient(int counterId, float coefficient);
	boolean delete(Integer installationId);
	boolean deleteByType(Integer type,Integer installationId);
	boolean deleteCounterSite(Integer installationId);
}
