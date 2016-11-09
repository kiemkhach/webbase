package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab004;
import com.energisme.bean.NumConfigLab004Line;
import com.energisme.service.Lab004Service;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConfigLab004Dao;
import com.ifi.lab.LabDAO.dao.ConfigLab004DaoLine;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab004Line;

public class Lab004ServiceImpl implements Lab004Service {
	@Autowired
	private ConfigLab004Dao configLab004DAO;

	@Autowired
	private ConfigLab004DaoLine configLab004DaoLine;

	public ConfigLab004 getConfigLab004BySite(Integer siteId) {
		ConfigLab004 configLab = configLab004DAO.getConfigBySite(siteId);
		return configLab;
	}

	@Override
	public List<ConfigLab004> getAllConfigLab004() {
		List<ConfigLab004> listConfigLab = configLab004DAO.getAllConfig();
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
		if (configLab004DAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}

	@Override
	public String uploadFile(String siteId, File file1, String file1name, File file2, String file2name, File file3,
			String file3name) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB004_LINK);
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
			String pathType1 = pathSiteId + FrontalKey.WINDOWS + FrontalKey.TYPE1;
			File type1Folder = new File(pathType1);
			if (!type1Folder.exists()) {
				if (!type1Folder.mkdir()) {
					return "failed";
				}
			}
			String pathType2 = pathSiteId + FrontalKey.WINDOWS + FrontalKey.TYPE2;
			File type2Folder = new File(pathType2);
			if (!type2Folder.exists()) {
				if (!type2Folder.mkdir()) {
					return "failed";
				}
			}
			String pathType3 = pathSiteId + FrontalKey.WINDOWS + FrontalKey.TYPE3;
			File type3Folder = new File(pathType3);
			if (!type3Folder.exists()) {
				if (!type3Folder.mkdir()) {
					return "failed";
				}
			}
			if (file1 != null) {
				File newFile1 = new File(pathType1 + FrontalKey.WINDOWS + file1name);
				FileUtils.copyFile(file1, newFile1);
			}
			if (file2 != null) {
				File newFile2 = new File(pathType2 + FrontalKey.WINDOWS + file2name);
				FileUtils.copyFile(file2, newFile2);
			}
			if (file3 != null) {
				File newFile3 = new File(pathType3 + FrontalKey.WINDOWS + file3name);
				FileUtils.copyFile(file3, newFile3);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Integer getMaxSiteId() {
		return configLab004DAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab004 numConfigLab) {
		String siteName = numConfigLab.getSiteName();
		String siteInfo = numConfigLab.getSiteInfo();
		String uriIcon = numConfigLab.getUriIcon();
		String logo = numConfigLab.getLogo();
		String cible = numConfigLab.getCible();
		String unitPrice = numConfigLab.getUnitPrice();

		Utils util = new Utils();
		ConfigLab004 configLab = new ConfigLab004();
		Integer siteNumber = null;
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = configLab004DAO.getMaxSiteId() + 1;
		} else {
			siteNumber = util.getNumber(numConfigLab.getSiteId());
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			configLab = configLab004DAO.findById(numConfigLab.getId());
			
			ConfigLab004 ckConfig = configLab004DAO.findBySite(siteNumber);
			if (ckConfig != null && ckConfig.getId() != null && configLab.getSiteId() != siteNumber) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}			
			
		}
		configLab.setSiteId(siteNumber);

		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(numConfigLab.getSiteName());
		}

		if (siteInfo != null) {
			configLab.setSiteInfo(siteInfo);
		}

		if (uriIcon != null) {
			configLab.setUriIcon(uriIcon);
		}

		if (logo != null) {
			configLab.setLogo(logo);
		}

		if (cible != null) {
			Integer number = util.getNumber(cible);
			if (number != null) {
				configLab.setCible(number);
			} else {
				return "Cible: '" + cible + "' is not a number";
			}
		}

		if (unitPrice != null) {
			Double number = util.getNumberDouble(unitPrice);
			if (number != null) {
				configLab.setUnitPrice(number);
			} else {
				return "Unit Price: '" + unitPrice + "' is not a number";
			}
		}

		if (configLab004DAO.save(configLab)) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public ConfigLab004Line getLineById(Integer id) {
		ConfigLab004Line configLab = configLab004DaoLine.findByid(id);
		return configLab;
	}

	@Override
	public List<ConfigLab004Line> getAllLine(Integer configLab004Id) {
		List<ConfigLab004Line> listConfigLab = configLab004DaoLine.getAllLineByConfigId(configLab004Id);
		return listConfigLab;
	}

	@Override
	public String savaAllLine(List<ConfigLab004Line> listConfig) {
		if (configLab004DaoLine.saveAll(listConfig)) {
			return "success";
		} else {
			return "fail";
		}

	}

}
