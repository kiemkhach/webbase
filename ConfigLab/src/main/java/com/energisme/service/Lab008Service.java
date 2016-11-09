package com.energisme.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.energisme.bean.NumConfigLab008;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;

public interface Lab008Service {
	ConfigLab008V2 getConfigLab008BySite(Integer siteId);

	List<ConfigLab008V2> getAllConfigLab008();

	String saveConfig(NumConfigLab008 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();

	String updatelab008BoilerInfoFrCsv(File importCsvFile, Integer siteId);

//	String updatelab008TrefFrCsv(File importCsvFile);

	String updatelab008PerformanceFrCsv(File importCsvFile, Integer siteId);

	InputStream exportLab008BoilerInfotoCsv(String[] dataHeader, Integer siteId);

//	InputStream exportLab008TrefInfotoCsv(String[] dataHeader);

	InputStream exportLab008PerformanceToCsv(String[] dataHeader, Integer siteId);
}
