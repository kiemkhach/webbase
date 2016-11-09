package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab002;
import com.ifi.lab.LabDAO.model.Bouygues;

public interface BouyguesService {
	Bouygues getBouyguesBySite(Integer siteId);

	List<Bouygues> getAllBouygues();

	String saveConfig(NumConfigLab002 numConfigLab);
	
	String deleteConfig(String id);
	
	String uploadFile(String siteId, File file, String filename);
	
	Integer getMaxSiteId();
}
