package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab005;
import com.energisme.service.Lab005Service;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConfigLab005Dao;
import com.ifi.lab.LabDAO.model.ConfigLab005;

public class Lab005ServiceImpl implements Lab005Service {
	@Autowired
	private ConfigLab005Dao configLab005DAO;

	public ConfigLab005 getConfigLab005BySite(Integer siteId) {
		ConfigLab005 configLab = configLab005DAO.getConfigBySite(siteId);
		return configLab;
	}

	@Override
	public List<ConfigLab005> getAllConfigLab005() {
		List<ConfigLab005> listConfigLab = configLab005DAO.getAllConfig();
		return listConfigLab;
	}
	
	@Override
	public String deleteConfig(String id) {
		if (id == null) {
			return "fail";
		}
		Utils util = new Utils();
		Integer idNumber = util.getNumber(id);
		if (idNumber == null) {
			return "fail";
		}
		if (configLab005DAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}
	
	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB005_LINK);
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return "failed";
				} 
			}	
			String pathSiteId = path + FrontalKey.WINDOWS + siteId;			
			File subFolder = new File(pathSiteId);
			if (!subFolder.exists()) {
				if (!subFolder.mkdir()) {
					return "failed";
				}
			}			
			File newFile = new File(pathSiteId + FrontalKey.WINDOWS + filename);
			FileUtils.copyFile(file, newFile);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Integer getMaxSiteId() {
		return configLab005DAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab005 numConfigLab) {
		String siteName = numConfigLab.getSiteName();
		String uriIcon = numConfigLab.getUriIcon();
		ConfigLab005 configLab = new ConfigLab005();
		Integer siteNumber = null;
		Utils util = new Utils();
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = configLab005DAO.getMaxSiteId() + 1;
		} else {
			siteNumber = util.getNumber(numConfigLab.getSiteId());
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			configLab = configLab005DAO.findById(numConfigLab.getId());
			
			ConfigLab005 ckConfig = configLab005DAO.findBySite(siteNumber);
			if (ckConfig != null && ckConfig.getId() != null && configLab.getSiteId() != siteNumber) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}			
			
		}
		configLab.setSiteId(siteNumber);

		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(numConfigLab.getSiteName());
		}

		if (uriIcon != null) {
			configLab.setUriIcon(uriIcon);
		}
		String reportName = numConfigLab.getReportName();
		if (reportName != null) {
			configLab.setReportName(reportName);
		}

		if (configLab005DAO.save(configLab)) {
			return "success";
		} else {
			return "fail";
		}
	}
}
