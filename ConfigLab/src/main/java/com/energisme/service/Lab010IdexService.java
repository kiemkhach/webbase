package com.energisme.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexConfig;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public interface Lab010IdexService {
	List<IdexInstallation> getAllIdexInstallation();
	IdexConfig getIdexConfigByInstallationId(Integer installationId);
	boolean saveIdexConfig(IdexConfig obj);
	List<IdexEnergySupplier> getAllIdexEnergySupplier();
	List<IdexCost> getAllIdexCost();
	List<IdexSite> getAllIdexSite();
	List<IdexCounter> getGroupIdexCounterByInstallation(int idexInstallation);
	List<IdexCost> getGroupIdexCostByInstallation(int idexInstallation);
	List<IdexEnergySupplier> getGroupIdexEnergySupplierByInstallation(int idexInstallation);
	List<IdexSite> getGroupIdexSiteByInstallation(int idexInstallation);
	boolean saveIdexCost(IdexCost obj);
	Boolean uploadFile(String path, String filename,File file);
	InputStream exportLab010CostDetailByIdToCSV(String[] dataHeader, int idexCostId);
	InputStream exportAllLab010CostDetailByIdToCSV(String[] dataHeader);
	String importSubCategoryByCSV (File importFile, int idexCostId);
	Boolean deleteFile(String path,String filename);
	boolean deleteIdexCost(int idexCostId);
	String importRelevesByCSV (File importFile, int idexCounterId);
	InputStream exportLab010RelevesByIdToCSV(String[] dataHeader, int idexCounterId);
	InputStream exportAllLab010RelevesToCSV(String[] dataHeader);
	String importMeteoCSVByInstallation (File importFile, int idexInstallationId);
	InputStream exportIdexMeteoByInstallationToCSV(String[] dataHeader, int idexInstallationId);
	InputStream exportAllIdexMeteoToCSV(String[] dataHeader);
}
