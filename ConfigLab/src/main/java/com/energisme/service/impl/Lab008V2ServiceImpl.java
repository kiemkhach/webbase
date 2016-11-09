package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab008V2;
import com.energisme.service.Lab008V2Service;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab008V2DAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.UserLab;

public class Lab008V2ServiceImpl implements Lab008V2Service {
	@Autowired
	private ConfigLab008V2DAO configLab008V2DAO;

	// private GetCSMDataAction csm = new GetCSMDataAction();
	//
	// private String msg = null;
	@Autowired
	private UserLabDAO userLabDAO;
	@Autowired
	private LabDAO labDAO;

	public ConfigLab008V2 getConfigLab008BySite(Integer siteId) {
		ConfigLab008V2 configLab = configLab008V2DAO.findBySiteId(siteId);
		return configLab;
	}

	@Override
	public String deleteConfig(String id) {
		if (id == null) {
			return "fail";
		}
		Integer idNumber = Utils.getNumber(id);
		if (idNumber == null) {
			return "fail";
		}
		Lab lab = labDAO.findByName(FrontalKey.LAB_NAME_008);
		ConfigLab008V2 configLab = configLab008V2DAO.findById(idNumber);
		if (configLab != null) {
			if (configLab008V2DAO.delete(idNumber)) {
				List<UserLab> userLabLst = userLabDAO.findByLabAndSite(lab.getId(), configLab.getSiteId());
				userLabDAO.deleteAll(userLabLst);
				return "success";
			} else {
				return "fail";
			}
		}
		return "success";

	}

	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB008_LINK);
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
		return configLab008V2DAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab008V2 numConfigLab) {

		ConfigLab008V2 configLab008V2 = new ConfigLab008V2();

		// Set Site ID
		Integer siteId = null;
		// Double precision = null;
		if (numConfigLab.getSiteId() == null || numConfigLab.getSiteId().isEmpty()) { // create
																						// site
			siteId = configLab008V2DAO.getMaxSiteId() + 1;
		} else {
			try {
				siteId = Integer.parseInt(numConfigLab.getSiteId().trim());
			} catch (NumberFormatException nfe) {
				return "Subscription ID must be a number:" + numConfigLab.getSiteId();
			}
			if (siteId == null || siteId <= 0) {
				return "Subscription ID must be a number";
			}

			ConfigLab008V2 ckConfig = configLab008V2DAO.findBySiteId(siteId);
			if (numConfigLab.getId() != null) {
				// update
				configLab008V2 = configLab008V2DAO.findById(numConfigLab.getId());
				if (ckConfig != null && ckConfig.getId() != null && !configLab008V2.getId().equals(ckConfig.getId())) {
					return "Subscription ID : '" + siteId + "' has been duplicated";
				}
			} else {
				// Insert
				if (ckConfig != null && ckConfig.getId() != null) {
					return "Subscription ID : '" + siteId + "' has been duplicated";
				}
			}

		}
		configLab008V2.setSiteId(siteId);

		// Set Site Name
		if (numConfigLab.getSiteName() != null && !numConfigLab.getSiteName().trim().isEmpty()) {
			configLab008V2.setSiteName(numConfigLab.getSiteName().trim());
		} else {
			return "site name must be not null";
		}

		String uriIcon = numConfigLab.getUriIcon();
		if (uriIcon != null) {
			configLab008V2.setUriIcon(uriIcon.trim());
		}

		String logo = numConfigLab.getLogo();
		if (logo != null) {
			configLab008V2.setLogo(logo.trim());
		}
		if (numConfigLab.getTemperatureAmbianteLogementLimit() != null) {
			configLab008V2.setTemperatureAmbianteLogementLimit(numConfigLab.getTemperatureAmbianteLogementLimit());
		}
		if (numConfigLab.getTemperatureProductionChauffageLimit() != null) {
			configLab008V2
					.setTemperatureProductionChauffageLimit(numConfigLab.getTemperatureProductionChauffageLimit());
		}
		if (numConfigLab.getTemperatureProductionECSLimit() != null) {
			configLab008V2.setTemperatureProductionECSLimit(numConfigLab.getTemperatureProductionECSLimit());
		}
		if (numConfigLab.getTemperatureRecyclageECSLimit() != null) {
			configLab008V2.setTemperatureRecyclageECSLimit(numConfigLab.getTemperatureRecyclageECSLimit());
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		String moduleOutsite = numConfigLab.getModuleOutsite();
		if (numConfigLab != null && !numConfigLab.getModuleOutsite().trim().isEmpty()) {
			Integer number = csm.getModuleCSMIDByNumber(moduleOutsite);
			if (number != null) {
				configLab008V2.setModuleOutsite(String.valueOf(number));
			} else {
				return "Module not exists:" + moduleOutsite;
			}

		} else {
			return "module outsite must be not null";
		}
		if (numConfigLab.getUnitWater() != null && !numConfigLab.getUnitWater().isEmpty()) {
			configLab008V2.setUnitWater(numConfigLab.getUnitWater());
		} else {
			configLab008V2.setUnitWater(null);
		}
		if (numConfigLab.getBoilerType() != null) {
			configLab008V2.setBoilerType(numConfigLab.getBoilerType());
		}

		// if (numConfigLab.getEnergyProvider() != null &&
		// !numConfigLab.getEnergyProvider().equalsIgnoreCase("")) {
		configLab008V2.setEnergyProvider(numConfigLab.getEnergyProvider());
		// }
		// if (numConfigLab.getSubscribedPower() != null) {
		configLab008V2.setSubscribedPower(numConfigLab.getSubscribedPower());
		// }
		// if (numConfigLab.getVentilation() != null) {
//		String ventilation = csm.getCalculateModuleByName(numConfigLab.getVentilation());
//		if (ventilation == null) {
//			return "Module number of ventilation not exists:" + numConfigLab.getVentilation();
//		}
//		configLab008V2.setVentilation(ventilation);
		// }
		// if (numConfigLab.getProductionENR() != null) {
		String productionENR = csm.getCalculateModuleByName(numConfigLab.getProductionENR());
		if (productionENR == null) {
			return "Module number of production'ENR not exists:" + numConfigLab.getProductionENR();
		}
		configLab008V2.setProductionENR(productionENR);
		// }
		// if (numConfigLab.getUsedECSproduction() != null) {
		configLab008V2.setUsedECSproduction(numConfigLab.getUsedECSproduction());
		// }
		// if (numConfigLab.getUsedHeatproduction() != null) {
		configLab008V2.setUsedHeatproduction(numConfigLab.getUsedHeatproduction());
		// }

		if (configLab008V2DAO.saveOrUpdate(configLab008V2)) {
			return "success";
		} else {
			return "Error when insert DB";
		}
	}

	@Override
	public List<ConfigLab008V2> getAllConfigLab008V2() {
		List<ConfigLab008V2> listConfigLab = configLab008V2DAO.getAllConfig();
		return listConfigLab;
	}

}
