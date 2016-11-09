package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab007;
import com.ifi.lab.LabDAO.model.ConfigLab007;

public interface Lab007Service {
	ConfigLab007 getConfigLab007BySite(Integer siteId);

	List<ConfigLab007> getAllConfigLab007();

	String saveConfig(NumConfigLab007 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();
}
