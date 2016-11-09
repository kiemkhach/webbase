package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab003;
import com.ifi.lab.LabDAO.model.ConfigLab003;

public interface ELeclercService {
	ConfigLab003 getConfigLab003BySite(Integer siteId);

	List<ConfigLab003> getAllConfigLab003();

	String saveConfig(NumConfigLab003 numConfigLab);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file, String filename, File driveFile, String driveFilename);

	Integer getMaxSiteId();
}
