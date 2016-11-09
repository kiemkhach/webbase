package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab006;
import com.energisme.service.Lab006Service;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab006Dao;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;

public class Lab006ServiceImpl implements Lab006Service {
	@Autowired
	private ConfigLab006Dao configLab006DAO;

	public ConfigLab006 getConfigLab006BySite(Integer siteId) {
		ConfigLab006 configLab = configLab006DAO.findBySite(siteId);
		return configLab;
	}

	@Override
	public List<ConfigLab006> getAllConfigLab006() {
		List<ConfigLab006> listConfigLab = configLab006DAO.getAllConfig();
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
		if (configLab006DAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}

	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB006_LINK);
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
		return configLab006DAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab006 numConfigLab) {
		String siteName = numConfigLab.getSiteName();
		String uriIcon = numConfigLab.getUriIcon();
		String logo = numConfigLab.getLogo();
		String commonPortionModuleId = numConfigLab.getCommonPortionModuleId();
		String energyConsumptionModuleId = numConfigLab.getEnergyConsumptionModuleId();
		String summerStartDate = numConfigLab.getSummerStartDate();
		String winterStartDate = numConfigLab.getWinterStartDate();
		Integer startHourHC = numConfigLab.getStartHourHC();
		Integer startHourHP = numConfigLab.getStartHourHP();
		ConfigLab006 configLab = new ConfigLab006();
		
		Integer siteNumber = null;
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = configLab006DAO.getMaxSiteId() + 1;
		} else {
			siteNumber = Utils.getNumber(numConfigLab.getSiteId());
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			configLab = configLab006DAO.findById(numConfigLab.getId());

			ConfigLab006 ckConfig = configLab006DAO.findBySite(siteNumber);
			if (ckConfig != null && ckConfig.getId() != null && configLab.getSiteId().intValue() != siteNumber.intValue()) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}

		}
		configLab.setSiteId(siteNumber);

		if (summerStartDate != null && !"".equals(summerStartDate)){
			configLab.setSummerStartDate(summerStartDate);
		}
		if (winterStartDate != null && !"".equals(winterStartDate)){
			configLab.setWinterStartDate(winterStartDate);
		}

		if (startHourHC != null){
			configLab.setStartHourHC(startHourHC);
		}
		if (startHourHP != null){
			configLab.setStartHourHP(startHourHP);
		}

		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(numConfigLab.getSiteName());
		}

		if (uriIcon != null) {
			configLab.setUriIcon(uriIcon);
		}

		if (logo != null) {
			configLab.setLogo(logo);
		}
		String reportName = numConfigLab.getReportName();
		if (reportName != null) {
			configLab.setReportName(reportName);
		}
		

		GetCSMDataAction csm = new GetCSMDataAction();
		String listCommonPortionModuleId = "";
		String listEnergyConsumptionModuleId = "";

//		if (commonPortionModuleId != null) {
//			if (!commonPortionModuleId.trim().isEmpty()) {
//				String[] arr = commonPortionModuleId.split(FrontalKey.COMMA);
//				for (int i = 0; i < arr.length; i++) {
//					Integer number = csm.getModuleCSMIDByNumber(arr[i]);
//					if (number != null) {
//						listCommonPortionModuleId += number.toString() + FrontalKey.COMMA;
//					} else {
//						return "Common Portion: Module '" + arr[i] + "' does not exist";
//					}
//				}
//			}
//		}
		if (commonPortionModuleId != null && !"".equals(commonPortionModuleId)){
			if (!commonPortionModuleId.trim().isEmpty()) {
				String[] arr = commonPortionModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listCommonPortionModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Common Portion: Module '" + arr[i] + "' does not exist";
					}
				}
			}
//			listCommonPortionModuleId = csm.getModuleCSMIDByNumber(commonPortionModuleId).toString();
		}

		if (energyConsumptionModuleId != null && !"".equals(energyConsumptionModuleId)) {
			if (!energyConsumptionModuleId.trim().isEmpty()) {
				String[] arr = energyConsumptionModuleId.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listEnergyConsumptionModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Energy Consommation: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}

		if (listCommonPortionModuleId != null && !"".equals(listCommonPortionModuleId)) {
			if (FrontalKey.PLUS.equals(listCommonPortionModuleId.charAt(listCommonPortionModuleId.length()-1))) {
				listCommonPortionModuleId = listCommonPortionModuleId.substring(0,listCommonPortionModuleId.length()-1);
			}
		}
		if (listEnergyConsumptionModuleId != null && !"".equals(listEnergyConsumptionModuleId)) {
			if (FrontalKey.PLUS.equals(listEnergyConsumptionModuleId.charAt(listEnergyConsumptionModuleId.length()-1))) {
				listEnergyConsumptionModuleId = listEnergyConsumptionModuleId.substring(0,listEnergyConsumptionModuleId.length()-1);
			}
		}
		configLab.setCommonPortionModuleId(listCommonPortionModuleId);
		configLab.setEnergyConsumptionModuleId(listEnergyConsumptionModuleId);

		if (numConfigLab.getId() != null){
			configLab.setId(numConfigLab.getId());
			if (configLab006DAO.update(configLab)) {
				return "success";
			} else {
				return "fail";
			}
		}else {
			if (configLab006DAO.save(configLab)) {
				return "success";
			} else {
				return "fail";
			}
		}
	}
}
