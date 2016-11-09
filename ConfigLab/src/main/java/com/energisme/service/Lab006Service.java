package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab006;

public interface Lab006Service {
	ConfigLab006 getConfigLab006BySite(Integer siteId);

	List<ConfigLab006> getAllConfigLab006();

	String saveConfig(NumConfigLab006 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename);

	Integer getMaxSiteId();
}
