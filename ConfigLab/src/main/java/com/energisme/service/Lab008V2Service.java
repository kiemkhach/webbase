package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab008V2;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;

public interface Lab008V2Service {
	ConfigLab008V2 getConfigLab008BySite(Integer siteId);

	List<ConfigLab008V2> getAllConfigLab008V2();

	String saveConfig(NumConfigLab008V2 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();
}
