package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab010IdexSite;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public interface Lab010IdexSiteService {
	Lab getLab010IdexSiteinfo(String name);
	String uploadFile(String idexSiteId, File file, String filename);
	String saveConfig(NumConfigLab010IdexSite numConfigLab010IdexSite);
	Lab getLabinfo(String name);
	List<IdexSite> getAllIdexSite();
	//List<String> getUsersInLab(String labName, String siteId);
	List<IdexInstallation> getAllIdexInstallation();
	IdexSite getConfigLab010ByIdIndexSite(Integer idIndexSite);
}
