package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab002;
import com.energisme.service.BouyguesService;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.BouyguesDAO;
import com.ifi.lab.LabDAO.model.Bouygues;

public class BouyguesServiceImpl implements BouyguesService {
	@Autowired
	private BouyguesDAO bouyguesDAO;

	public Bouygues getBouyguesBySite(Integer siteId) {
		Bouygues configLab = bouyguesDAO.findBySiteId(siteId);
		return configLab;
	}

	@Override
	public List<Bouygues> getAllBouygues() {
		List<Bouygues> listConfigLab = bouyguesDAO.getAllBouygues();
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
		if (bouyguesDAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}

	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.PDF_REPORT_BOUYGUES_LINK);
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return "folder not exist - failed";
				}
			}
			String pathSiteId = path + FrontalKey.WINDOWS + siteId;
			File subFolder = new File(pathSiteId);
			if (!subFolder.exists()) {
				if (!subFolder.mkdir()) {
					return "subFolder not exist - failed";
				}
			}
			File newFile = new File(pathSiteId + FrontalKey.WINDOWS + filename);
			FileUtils.copyFile(file, newFile);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "uploadFile failed";
	}

	@Override
	public Integer getMaxSiteId() {
		return bouyguesDAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab002 numConfigLab) {
		String siteName = numConfigLab.getSiteName();
		String module1 = numConfigLab.getGruesModuleId();
		String module2 = numConfigLab.getPiedModuleId();
		String module3 = numConfigLab.getCantonModuleId();
		String startDate = numConfigLab.getStartDate();
		String numberPermit = numConfigLab.getNumberPermit();
		String uriIcon = numConfigLab.getUriIcon();
		String logo = numConfigLab.getLogo();
		String numberGrues = numConfigLab.getNumberGrues();
		String numberPied = numConfigLab.getNumberPied();
		String numberCanton = numConfigLab.getNumberCanton();

		String listGruesModuleId = "";
		String listPiedModuleId = "";
		String listCantonModuleId = "";

		Utils util = new Utils();
		Bouygues bouygues = new Bouygues();
		Integer siteNumber = null;
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = bouyguesDAO.getMaxSiteId() + 1;
		} else {
			siteNumber = util.getNumber(numConfigLab.getSiteId());
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			bouygues = bouyguesDAO.findById(numConfigLab.getId());
			
			Bouygues ckConfig = bouyguesDAO.findBySiteId(siteNumber);
			if ((ckConfig != null) && (ckConfig.getId() != null) && (bouygues.getSiteId().intValue() != siteNumber.intValue())) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}
//			if (ckConfig != null) {
//				if (ckConfig.getId() != null) {
//					if (bouygues.getSiteId() != siteNumber) {
//						return "Subscription ID : '" + siteNumber + "' has been duplicated";
//					}
//				}
//			}
		}		
		bouygues.setSiteId(siteNumber);
		if (siteName != null) {
			bouygues.setSiteName(siteName);
		}
		if (startDate != null) {
			bouygues.setStartDate(startDate);
		}
		if (numberPermit != null) {
			bouygues.setNumberPermit(numberPermit);
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		if (module1 != null) {
			if (!module1.trim().isEmpty()) {
				String[] arr = module1.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listGruesModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Grues: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (module2 != null) {
			if (!module2.trim().isEmpty()) {
				String[] arr = module2.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listPiedModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Pied: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (module3 != null) {
			if (!module3.trim().isEmpty()) {
				String[] arr = module3.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listCantonModuleId += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Canton: Module '" + arr[i] + "' does not exist";
					}
				}
			}
		}
		if (listGruesModuleId != null && !"".equals(listGruesModuleId)) {
			if (FrontalKey.PLUS.equals(listGruesModuleId.charAt(listGruesModuleId.length()-1))) {
				listGruesModuleId = listGruesModuleId.substring(0,listGruesModuleId.length()-1);
			}
		}
		if (listPiedModuleId != null && !"".equals(listPiedModuleId)) {
			if (FrontalKey.PLUS.equals(listPiedModuleId.charAt(listPiedModuleId.length()-1))) {
				listPiedModuleId = listPiedModuleId.substring(0,listPiedModuleId.length()-1);
			}		
		}
		if (listCantonModuleId != null && !"".equals(listCantonModuleId)) {
			if (FrontalKey.PLUS.equals(listCantonModuleId.charAt(listCantonModuleId.length()-1))) {
				listCantonModuleId = listCantonModuleId.substring(0,listCantonModuleId.length()-1);
			}
		}
		
		bouygues.setGruesModuleId(listGruesModuleId);
		bouygues.setPiedModuleId(listPiedModuleId);
		bouygues.setCantonModuleId(listCantonModuleId);

		if (numberGrues != null) {
			Integer number = util.getNumber(numberGrues);
			if (number != null) {
				bouygues.setNumberGrues(number);
			} else {
				return "Number Grues: '" + numberGrues + "' is not a number";
			}
		}
		if (numberPied != null) {
			Integer number = util.getNumber(numberPied);
			if (number != null) {
				bouygues.setNumberPied(number);
			} else {
				return "Number Pied: '" + numberPied + "' is not a number";
			}
		}
		if (numberCanton != null) {
			Integer number = util.getNumber(numberCanton);
			if (number != null) {
				bouygues.setNumberCanton(number);
			} else {
				return "Number Canton: '" + numberCanton + "' is not a number";
			}
		}

		if (uriIcon != null) {
			bouygues.setUriIcon(uriIcon);
		}
		if (logo != null) {
			bouygues.setLogo(logo);
		}
		String reportName = numConfigLab.getReportName();
		if (reportName != null) {
			bouygues.setReportName(reportName);
		}

		if (bouyguesDAO.saveOrUpdate(bouygues)) {
			return "success";
		} else {
			return "save fail";
		}
	}

}
