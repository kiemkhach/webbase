package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab007;
import com.energisme.service.Lab007Service;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab007DAO;
import com.ifi.lab.LabDAO.model.ConfigLab007;

public class Lab007ServiceImpl implements Lab007Service{
	@Autowired
	private ConfigLab007DAO configLab007DAO; 
	public ConfigLab007 getConfigLab007BySite(Integer siteId) {
		ConfigLab007 configLab = configLab007DAO.findBySiteId(siteId);
		return configLab;
	}
	
	@Override
	public List<ConfigLab007> getAllConfigLab007() {
		List<ConfigLab007> listConfigLab = configLab007DAO.getAllConfig();
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
		if (configLab007DAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}

	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB007_LINK);
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
		return configLab007DAO.getMaxSiteId();
	}

	

	@Override
	public String saveConfig(NumConfigLab007 numConfigLab) {
		String logo = numConfigLab.getLogo();
		String reportName = numConfigLab.getReportName();
		String siteName = numConfigLab.getSiteName();
		String totalElecModuleId = numConfigLab.getTotalElecModuleId();
		String totalGasModuleId = numConfigLab.getTotalGasModuleId();
		String totalPylonesModuleId = numConfigLab.getTotalPylonesModuleId();
		String totalWaterModuleId = numConfigLab.getTotalWaterModuleId();
		String listElecModuleId = "";
		String listGasModuleId = "";
		String listPylonesModuleId = "";
		String listWaterModuleId = "";
		String unitElec = numConfigLab.getUnitElec();
		String unitGas = numConfigLab.getUnitGas();
		String unitPylones = numConfigLab.getUnitPylones();
		String unitWater = numConfigLab.getUnitWater();
		String uriIcon = numConfigLab.getUriIcon();
		
	
		ConfigLab007 configLab007 = new ConfigLab007();
		Integer siteNumber = null;
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = configLab007DAO.getMaxSiteId() + 1;
		} else {
			siteNumber = numConfigLab.getSiteId();
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			configLab007 = configLab007DAO.findById(numConfigLab.getId());
			
			ConfigLab007 ckConfig = configLab007DAO.findBySiteId(siteNumber);
			if (ckConfig != null && ckConfig.getId() != null && configLab007.getSiteId() != siteNumber) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}			
		}	
		configLab007.setSiteId(siteNumber);
		if (siteName != null) {
			configLab007.setSiteName(siteName);
		}
		if (logo != null) {
			configLab007.setLogo(logo);
		}
		if (reportName != null) {
			configLab007.setReportName(reportName);
		}
		if (uriIcon != null) {
			configLab007.setUriIcon(uriIcon);
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		if (totalElecModuleId != null) {
			if (!totalElecModuleId.trim().isEmpty()) {
				String[] arr = totalElecModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listElecModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Elec: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (totalGasModuleId != null) {
			if (!totalGasModuleId.trim().isEmpty()) {
				String[] arr = totalGasModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listGasModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Gas: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (totalPylonesModuleId != null) {
			if (!totalPylonesModuleId.trim().isEmpty()) {
				String[] arr = totalPylonesModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listPylonesModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Pylones: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (totalWaterModuleId != null) {
			if (!totalWaterModuleId.trim().isEmpty()) {
				String[] arr = totalWaterModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listWaterModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Water: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (FrontalKey.PLUS.equals(listElecModuleId.charAt(listElecModuleId.length()-1))) {
			listElecModuleId = listElecModuleId.substring(0,listElecModuleId.length()-1);
		}
		if (FrontalKey.PLUS.equals(listGasModuleId.charAt(listGasModuleId.length()-1))) {
			listGasModuleId = listGasModuleId.substring(0,listGasModuleId.length()-1);
		}
		if (FrontalKey.PLUS.equals(listPylonesModuleId.charAt(listPylonesModuleId.length()-1))) {
			listPylonesModuleId = listPylonesModuleId.substring(0,listPylonesModuleId.length()-1);
		}
		if (FrontalKey.PLUS.equals(listWaterModuleId.charAt(listWaterModuleId.length()-1))) {
			listWaterModuleId = listWaterModuleId.substring(0,listWaterModuleId.length()-1);
		}
		configLab007.setTotalElecModuleId(listElecModuleId);
		configLab007.setTotalGasModuleId(listGasModuleId);
		configLab007.setTotalPylonesModuleId(listPylonesModuleId);
		configLab007.setTotalWaterModuleId(listWaterModuleId);
		if (unitElec != null) {
			configLab007.setUnitElec(unitElec);
		}
		if (unitGas != null) {
			configLab007.setUnitGas(unitGas);
		}
		if (unitPylones != null) {
			configLab007.setUnitPylones(unitPylones);			
		}
		if (unitWater != null) {
			configLab007.setUnitWater(unitWater);
		}
		if (configLab007DAO.saveOrUpdate(configLab007)) {
			return "success";
		} else {
			return "fail";
		}
	}

	
}
