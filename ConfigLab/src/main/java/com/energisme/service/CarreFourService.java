package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab001;
import com.ifi.lab.LabDAO.model.ConfigLab001;

public interface CarreFourService {
	ConfigLab001 getConfigLab001BySite(Integer siteId);

	List<ConfigLab001> getAllConfigLab001();

	String saveConfig(NumConfigLab001 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();
}
