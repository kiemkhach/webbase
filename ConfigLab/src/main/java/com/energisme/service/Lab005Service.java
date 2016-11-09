package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab005;
import com.ifi.lab.LabDAO.model.ConfigLab005;

public interface Lab005Service {
	ConfigLab005 getConfigLab005BySite(Integer siteId);

	List<ConfigLab005> getAllConfigLab005();

	String saveConfig(NumConfigLab005 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();
}
