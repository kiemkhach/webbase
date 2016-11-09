package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab001;
import com.energisme.service.CarreFourService;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab001DAO;
import com.ifi.lab.LabDAO.model.ConfigLab001;

public class CarreFourServiceImpl implements CarreFourService {
	@Autowired
	private ConfigLab001DAO labDAO;

	public ConfigLab001 getConfigLab001BySite(Integer siteId) {
		ConfigLab001 configLab = labDAO.getConfigBySite(siteId);
		return configLab;
	}

	@Override
	public List<ConfigLab001> getAllConfigLab001() {
		List<ConfigLab001> listConfigLab = labDAO.getAllConfig();
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
		if (labDAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

	}

	@Override
	public String uploadFile(String siteId, File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.PDF_BACKLOG_LINK);
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
		return labDAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab001 numConfigLab) {
		String siteInfo = numConfigLab.getSiteInfo();
		String siteName = numConfigLab.getSiteName();
		String superficies = numConfigLab.getSiteSuperficies();
		String froidNeg = numConfigLab.getModuleNeg();
		String froidPos = numConfigLab.getModulePos();
		String froidRes = numConfigLab.getModuleReg();
		String eclairage = numConfigLab.getModuleEclairage();
		String bureau = numConfigLab.getModuleBureau();
		String boulangerie = numConfigLab.getModuleBoulangerie();
		String cvc = numConfigLab.getModuleCvc();
		String temperatureInt = numConfigLab.getModuleTemperatureIn();
		String temperatureOut = numConfigLab.getModuleTemperatureOut();
		String drive = numConfigLab.getModuleDrive();
		String computerElectric = numConfigLab.getModuleElectric();
		String computerGaz = numConfigLab.getModuleGaz();
		String franis = numConfigLab.getModuleTemperatureFranis();
		String energyPastYear = numConfigLab.getModuleEnergyPreviousYear();
		String uriIcon = numConfigLab.getUriIcon();
		String logo = numConfigLab.getLogo();
		
		
		String listFroidNeg = "";
		String listFroidPos = "";
		String listFroidRes = "";
		String listEclairage = "";
		String listBureau = "";
		String listBoulangerie = "";
		String listCvc = "";
		String listTemperatureInt = "";
		String listTemperatureOut = "";
		String listDrive = "";
		String listComputerElectric = "";
		String listComputerGaz = "";
		String listFranis = "";
		String listEnergyPastYear = "";
		
		Utils util = new Utils();
		ConfigLab001 configLab = new ConfigLab001();
		Integer siteNumber = null;
		if (numConfigLab.getSiteId() == null) { // create site
			siteNumber = labDAO.getMaxSiteId() + 1;
		} else {
			siteNumber = util.getNumber(numConfigLab.getSiteId());
			if (siteNumber == null || siteNumber <= 0) {
				return "Subscription ID must be a number";
			}
			configLab = labDAO.findById(numConfigLab.getId());

			ConfigLab001 ckConfig = labDAO.findBySite(siteNumber);
			if (ckConfig != null && ckConfig.getId() != null && configLab.getSiteId().intValue() != siteNumber.intValue()) {
				return "Subscription ID : '" + siteNumber + "' has been duplicated";
			}

		}
		configLab.setSiteId(siteNumber);
		if (siteInfo != null && !siteInfo.isEmpty()) {
			configLab.setSiteInfo(numConfigLab.getSiteInfo());
		}
		if (configLab.getSiteInfo() == null) {
			return "Info required";
		}
		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(numConfigLab.getSiteName());
		}
		if (configLab.getSiteName() == null) {
			return "Name required";
		}
		if (superficies != null && !superficies.trim().equals("")) {
			Integer number = util.getNumber(superficies);
			if (number != null) {
				configLab.setSiteSuperficies(number);
			} else {
				return "Superficies: '" + superficies + "' is not a number";
			}
		} else {
			return "Superficies required";
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		if (froidNeg != null) {
			if (!froidNeg.trim().isEmpty()) {
				/*Integer number = csm.getModuleCSMIDByNumber(froidNeg);
				if (number != null) {
					configLab.setModuleNeg(number.toString());
				} else {
					return "Froid Neg: Module '" + froidNeg + "' does not exist";
				}*/
				String[] arr = froidNeg.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listFroidNeg += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Froid Neg: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleNeg(null);
			}
		}
		if (froidPos != null) {
			if (!froidPos.trim().isEmpty()) {
				String[] arr = froidPos.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listFroidPos += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Froid Pos: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModulePos(null);
			}
		}
		if (froidRes != null) {
			if (!froidRes.trim().isEmpty()) {
				String[] arr = froidRes.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listFroidRes += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Froid Res: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModulePos(null);
			}
		}
		if (eclairage != null) {
			if (!eclairage.trim().isEmpty()) {
				String[] arr = eclairage.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listEclairage += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Eclairage: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleEclairage(null);
			}
		}
		if (bureau != null) {
			if (!bureau.trim().isEmpty()) {
				String[] arr = bureau.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listBureau += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Bureau: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleBureau(null);
			}
		}
		if (boulangerie != null) {
			if (!boulangerie.trim().isEmpty()) {
				
				String[] arr = boulangerie.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listBoulangerie += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Boulangerie: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleBoulangerie(null);
			}
		}
		if (cvc != null) {
			if (!cvc.trim().isEmpty()) {
				String[] arr = cvc.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						listCvc += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Cvc: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleCvc(null);
			}
		}
		if (temperatureInt != null) {
			if (!temperatureInt.trim().isEmpty()) {
				String[] arr = temperatureInt.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listTemperatureInt += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Temperature Int: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleTemperatureIn(null);
			}
		}
		if (temperatureOut != null) {
			if (!temperatureOut.trim().isEmpty()) {
				String[] arr = temperatureOut.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listTemperatureOut += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Temperature Out: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleTemperatureOut(null);
			}
		}
		if (drive != null) {
			if (!drive.trim().isEmpty()) {
				String[] arr = drive.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listDrive += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Drive: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleDrive(null);
			}
		}
		if (computerElectric != null) {
			if (!computerElectric.trim().isEmpty()) {
				String[] arr = computerElectric.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listComputerElectric += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Computer Electric: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleElectric(null);
			}
		}
		if (computerGaz != null) {
			if (!computerGaz.trim().isEmpty()) {
				String[] arr = computerGaz.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listComputerGaz += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Computer Gaz: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleGaz(null);
			}
		}
		if (franis != null) {
			if (!franis.trim().isEmpty()) {
				String[] arr = franis.split(FrontalKey.PLUS_SPECIAL);
				for (int i = 0; i < arr.length; i++) {
					Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
					if (number != null) {
						/*listGruesModuleId += number.toString() + FrontalKey.COMMA;*/
						listFranis += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
					} else {
						return "Franis: Module '" + arr[i] + "' does not exist";
					}
				}
			} else {
				configLab.setModuleTemperatureFranis(null);
			}
		}
		if (energyPastYear != null) {
			Integer number = util.getNumber(energyPastYear);
			if (number != null) {
				configLab.setModuleEnergyPreviousYear(number.toString());
			} else {
				return "Energy Past Year: '" + energyPastYear + "' is not a number";
			}
		}
		

		if (listFroidNeg != null && !"".equals(listFroidNeg)) {
			if (FrontalKey.PLUS.equals(listFroidNeg.charAt(listFroidNeg.length()-1))) {
				listFroidNeg = listFroidNeg.substring(0,listFroidNeg.length()-1);
			}
		}
		if (listFroidPos != null && !"".equals(listFroidPos)) {
			if (FrontalKey.PLUS.equals(listFroidPos.charAt(listFroidPos.length()-1))) {
				listFroidPos = listFroidPos.substring(0,listFroidPos.length()-1);
			}
		}
		if (listFroidRes != null && !"".equals(listFroidRes)) {
			if (FrontalKey.PLUS.equals(listFroidRes.charAt(listFroidRes.length()-1))) {
				listFroidRes = listFroidRes.substring(0,listFroidRes.length()-1);
			}		
		}
		if (listEclairage != null && !"".equals(listEclairage)) {
			if (FrontalKey.PLUS.equals(listEclairage.charAt(listEclairage.length()-1))) {
				listEclairage = listEclairage.substring(0,listEclairage.length()-1);
			}
		}
		if (listBureau != null && !"".equals(listBureau)) {
			if (FrontalKey.PLUS.equals(listBureau.charAt(listBureau.length()-1))) {
				listBureau = listBureau.substring(0,listBureau.length()-1);
			}
		}
		if (listBoulangerie != null && !"".equals(listBoulangerie)) {
			if (FrontalKey.PLUS.equals(listBoulangerie.charAt(listBoulangerie.length()-1))) {
				listBoulangerie = listBoulangerie.substring(0,listBoulangerie.length()-1);
			}
		}
		if (listCvc != null && !"".equals(listCvc)) {
			if (FrontalKey.PLUS.equals(listCvc.charAt(listCvc.length()-1))) {
				listCvc = listCvc.substring(0,listCvc.length()-1);
			}
		}
		if (listTemperatureInt != null && !"".equals(listTemperatureInt)) {
			if (FrontalKey.PLUS.equals(listTemperatureInt.charAt(listTemperatureInt.length()-1))) {
				listTemperatureInt = listTemperatureInt.substring(0,listTemperatureInt.length()-1);
			}
		}
		if (listTemperatureOut != null && !"".equals(listTemperatureOut)) {
			if (FrontalKey.PLUS.equals(listTemperatureOut.charAt(listTemperatureOut.length()-1))) {
				listTemperatureOut = listTemperatureOut.substring(0,listTemperatureOut.length()-1);
			}
		}
		if (listDrive != null && !"".equals(listDrive)) {
			if (FrontalKey.PLUS.equals(listDrive.charAt(listDrive.length()-1))) {
				listDrive = listDrive.substring(0,listDrive.length()-1);
			}
		}
		if (listComputerElectric != null && !"".equals(listComputerElectric)) {
			if (FrontalKey.PLUS.equals(listComputerElectric.charAt(listComputerElectric.length()-1))) {
				listComputerElectric = listComputerElectric.substring(0,listComputerElectric.length()-1);
			}
		}
		if (listComputerGaz != null && !"".equals(listComputerGaz)) {
			if (FrontalKey.PLUS.equals(listComputerGaz.charAt(listComputerGaz.length()-1))) {
				listComputerGaz = listComputerGaz.substring(0,listComputerGaz.length()-1);
			}
		}
		if (listFranis != null && !"".equals(listFranis)) {
			if (FrontalKey.PLUS.equals(listFranis.charAt(listFranis.length()-1))) {
				listFranis = listFranis.substring(0,listFranis.length()-1);
			}
		}
		
		
		configLab.setModuleNeg(listFroidNeg);
		configLab.setModulePos(listFroidPos);
		configLab.setModuleReg(listFroidRes);
		configLab.setModuleEclairage(listEclairage);
		configLab.setModuleBureau(listBureau);
		configLab.setModuleBoulangerie(listBoulangerie);
		configLab.setModuleCvc(listCvc);
		configLab.setModuleTemperatureIn(listTemperatureInt);
		configLab.setModuleTemperatureOut(listTemperatureOut);
		configLab.setModuleDrive(listDrive);
		configLab.setModuleElectric(listComputerElectric);
		configLab.setModuleGaz(listComputerGaz);
		configLab.setModuleTemperatureFranis(listFranis);
		
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

		if (labDAO.save(configLab)) {
			return "success";
		} else {
			return "fail";
		}
	}

}
