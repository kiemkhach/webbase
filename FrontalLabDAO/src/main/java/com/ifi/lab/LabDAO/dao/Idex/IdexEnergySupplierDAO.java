package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergyType;

public interface IdexEnergySupplierDAO {
	List<IdexEnergySupplier> findByInstallation(Integer installationId);
	boolean save(IdexEnergySupplier obj);

	public Lab findByName(String name);

	public List<IdexEnergySupplier> getAllConfig();
	IdexEnergySupplier getConfigBySite(Integer IdexEnergySupplierId);
	
	List<IdexEnergyType> getAllEnergyType();
	
	IdexEnergySupplier findById(Integer idexEnergySupplierId);
	boolean delete(Integer installationId);
}
