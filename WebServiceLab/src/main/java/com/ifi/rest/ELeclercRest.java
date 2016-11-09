package com.ifi.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.ELeclercBean;
import com.ifi.common.bean.EleclercDriveBean;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesData;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.common.ws.GetDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab003DAO;
import com.ifi.lab.LabDAO.model.ConfigLab003;
import com.reports.util.Utils;

@Controller
@RequestMapping("eLeclerc")
public class ELeclercRest {

	public static String SUCCESS = "success";

	@Autowired
	private ConfigLab003DAO configLab003DAO;

	@RequestMapping(value = "getAllConfig", method = RequestMethod.GET)
	public @ResponseBody String getAllConfig() {
		List<ConfigLab003> listConfigLab = configLab003DAO.getAllConfig();
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(listConfigLab);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "getELeclercDriveBySite", method = RequestMethod.GET)
	public @ResponseBody EleclercDriveBean getELeclercDriveBySite(@RequestParam String siteId) {
		int site = 0;
		try {
			site = Integer.parseInt(siteId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		EleclercDriveBean driveBean = new EleclercDriveBean();

		ConfigLab003 configLab = configLab003DAO.getConfigBySite(site);
		if (configLab == null) {
			return null;
		}

		Double totalCurrentYear = getConsumptionByModuleByYear(configLab.getModuleDrive(), 0, 1);

		Integer totalPastYear = configLab.getModuleDrivePreviousYear();

		Integer superficies = configLab.getSiteSuperficies();
		if (totalCurrentYear != null && superficies != null && superficies != 0) {
			driveBean.setCurrentYearEnergy((int)(totalCurrentYear / superficies));
		}
		if (totalPastYear != null && superficies != null && superficies != 0) {
			driveBean.setPastYearEnergy(totalPastYear / superficies);
		}
		if (totalPastYear != null && totalPastYear != 0 && totalCurrentYear != null) {
			int percent = (int) (((totalCurrentYear - totalPastYear) / (float) totalPastYear) * 100);
			driveBean.setDiscount(percent);
		} else {
			driveBean.setDiscount(null);
		}

		driveBean.setSiteName(configLab.getSiteName());

		driveBean.setSiteStr(configLab.getSiteInfo());

		driveBean.setCurrentYear("2015");
		driveBean.setPastYear("2014");

		// ElectOut
		Double drive = getConsumptionByModule(configLab.getModuleDrive());
		driveBean.setDrive(drive);

		// Temperate In
		Integer temperateOut = getTemperatureByModule(configLab.getModuleTemperatureOut());
		driveBean.setTemperateOut(temperateOut);

		return driveBean;
	}

	/**
	 * Get infomation by siteid
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "getELeclercBySite", method = RequestMethod.GET)
	public @ResponseBody String getELeclercBySite(@RequestParam String siteId) {

		// @SuppressWarnings({ "unused", "resource" })
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "constantConfig.xml");

		// GetCSMDataAction csm = new GetCSMDataAction();
		// SiteCSM site = csm.getSiteBySiteID(0);

		int site = 0;
		try {
			site = Integer.parseInt(siteId);
		} catch (Exception e) {
			e.printStackTrace();
			return "siteId not a number";
		}

		// Create Bean
		ELeclercBean eLeclercBean = new ELeclercBean();
		eLeclercBean.setSiteId(site);

		// // save database
		// ServiceFactory serviceFactory = new ServiceFactory();
		// @SuppressWarnings("static-access")
		// ApplicationContext context = serviceFactory.getAppCtx();
		// ConfigLab003DAO configLab003DAO =
		// context.getBean(ConfigLab003DAO.class);

		ConfigLab003 configLab = configLab003DAO.getConfigBySite(site);
		if (configLab == null) {
			return "This site is not config";
		}

		// PropertiesData pro = new PropertiesData();
		//
		// Map<String, String> mapData = pro.loadDataConfig(site);

		eLeclercBean.setSiteName(configLab.getSiteName());

		// Froid
		Double neg = null;
		if (configLab.getModuleNeg() != null || !"".equals(configLab.getModuleNeg())){
			neg = getConsumptionByModule(configLab.getModuleNeg());
			eLeclercBean.setFroidNeg(neg);
		}
		Double pos = null;
		if (configLab.getModulePos() != null || !"".equals(configLab.getModulePos())){
			pos = getConsumptionByModule(configLab.getModulePos());
			eLeclercBean.setFroidPos(pos);
		}
		Double res = null;
		if (configLab.getModuleReg() != null || !"".equals(configLab.getModuleReg())){
			res = getConsumptionByModule(configLab.getModuleReg());
			eLeclercBean.setFroidRes(res);
		}
		Double froid = null;
		if (neg != null) {
			froid = neg;
		}
		if (pos != null) {
			if (froid == null) {
				froid = pos;
			} else {
				froid += pos;
			}
		}
		if (res != null) {
			if (froid == null) {
				froid = res;
			} else {
				froid += res;
			}
		}
		if (froid != null) {
			eLeclercBean.setFroid(froid);
		}

		// Bureau
		if (configLab.getModuleBureau() != null || !"".equals(configLab.getModuleBureau())){
			Double bureau = getConsumptionByModule(configLab.getModuleBureau());
			Map<String, Double> mapBureauCap = new HashMap<String, Double>();
			mapBureauCap.put("Bureau", bureau);
			eLeclercBean.setMapBureauCap(mapBureauCap);
			eLeclercBean.setBureau(bureau);
		}

		// Eclairage
		if (configLab.getModuleEclairage() != null || !"".equals(configLab.getModuleEclairage())){
			Double eclairage = getConsumptionByModule(configLab.getModuleEclairage());
			Map<String, Double> mapEclairageCap = new HashMap<String, Double>();
			mapEclairageCap.put("Eclairage", eclairage);
			eLeclercBean.setEclairage(eclairage);
		}

		// Boulangerie
		if (configLab.getModuleBoulangerie() != null || !"".equals(configLab.getModuleBoulangerie())){
			Double boulangerie = getConsumptionByModule(configLab.getModuleBoulangerie());
			Map<String, Double> mapBoulangerieCap = new HashMap<String, Double>();
			mapBoulangerieCap.put("Boulangerie", boulangerie);
			eLeclercBean.setMapBoulangerieCap(mapBoulangerieCap);
			eLeclercBean.setBoulangerie(boulangerie);
		}

		// CVC
		if (configLab.getModuleCvc() != null || !"".equals(configLab.getModuleCvc())){
			Double cvc = getConsumptionByModule(configLab.getModuleCvc());
			Map<String, Double> mapCvcCap = new HashMap<String, Double>();
			mapCvcCap.put("Cvc", cvc);
			eLeclercBean.setMapCvcCap(mapCvcCap);
			eLeclercBean.setCvc(cvc);
		}

		// ElectOut
		if (configLab.getModuleDrive() != null || !"".equals(configLab.getModuleDrive())){
			Double drive = getConsumptionByModule(configLab.getModuleDrive());
			eLeclercBean.setDrive(drive);
		}

		// Compteur elec
		if (configLab.getModuleElectric() != null || !"".equals(configLab.getModuleElectric())){
			Double comElec = getConsumptionByModule(configLab.getModuleElectric());
			eLeclercBean.setCompteurElec(comElec);
		}

		// Compteur gaz
		if (configLab.getModuleGaz() != null || !"".equals(configLab.getModuleGaz())){
			Double comGaz = getConsumptionByModule(configLab.getModuleGaz());
			eLeclercBean.setCompteurGaz(comGaz);
		}

		eLeclercBean.setLogoPath(PropertiesReader.getValue(ConfigEnum.LOGO_ELECLERC_PATH));
		eLeclercBean.setSiteStr(configLab.getSiteInfo());

		// Temperate In
		if (configLab.getModuleTemperatureIn() != null || !"".equals(configLab.getModuleTemperatureIn())){
			Integer temperateIn = getTemperatureByModule(configLab.getModuleTemperatureIn());
			eLeclercBean.setTemperateIn(temperateIn);
		}

		// Temperate In
		if (configLab.getModuleTemperatureOut() != null || !"".equals(configLab.getModuleTemperatureOut())){
			Integer temperateOut = getTemperatureByModule(configLab.getModuleTemperatureOut());
			eLeclercBean.setTemperateOut(temperateOut);
		}

		// Energy
		eLeclercBean.setCurrentYear("2015");
		eLeclercBean.setPastYear("2014");
		Double totalCurrentYear = null;

		Double totalElectric = null;
		if (configLab.getModuleElectric() != null || !"".equals(configLab.getModuleElectric())){
			totalElectric = getConsumptionByModuleByYear(configLab.getModuleElectric(), 0, 1);
		}
		Double totalGaz = null;
		if (configLab.getModuleGaz() != null || !"".equals(configLab.getModuleGaz())){
			totalGaz = getConsumptionByModuleByYear(configLab.getModuleGaz(), 0, 1);
		}
		if (totalElectric != null) {
			totalCurrentYear = totalElectric;
		}
		if (totalGaz != null) {
			if (totalCurrentYear == null) {
				totalCurrentYear = totalGaz;
			} else {
				totalCurrentYear += totalGaz;
			}

		}
		// Integer totalCurrentYear = mapData
		// .getInt(ConfigEnum.number_energy_current_year);
		// int totalPastYear = getConsumptionByModuleByYear(
		// mapData
		// .get(ConfigEnum.agregat_module_index_electric),
		// 1, 2)
		// + getConsumptionByModuleByYear(
		// mapData
		// .get(ConfigEnum.agregat_module_index_gaz),
		// 1, 2);
		Integer totalPastYear = configLab.getModuleEnergyPreviousYear();

		Integer superficies = configLab.getSiteSuperficies();
		if (totalCurrentYear != null && superficies != null && superficies != 0) {
			eLeclercBean.setCurrentYearEnergy((int)(totalCurrentYear / superficies));
		}
		if (totalPastYear != null && superficies != null && superficies != 0) {
			eLeclercBean.setPastYearEnergy(totalPastYear / superficies);
		}
		if (totalPastYear != null && totalPastYear != 0 && totalCurrentYear != null) {
			int percent = (int) (((totalCurrentYear - totalPastYear) / (float) totalPastYear) * 100);
			eLeclercBean.setDiscount(percent);
		} else {
			eLeclercBean.setDiscount(null);
		}

		// Cau hinh dai du lieu trong file config
		// PropertiesData pro = new PropertiesData();
		// Map<String, Integer> mapRange = pro.loadRangeConfigInt();
		// eLeclercBean.setIndexPastYear(getIndexOfEnergy(
		// eLeclercBean.getPastYearEnergy(), mapRange));
		// eLeclercBean.setIndexCurrentYear(getIndexOfEnergy(
		// eLeclercBean.getCurrentYearEnergy(), mapRange));
		//
		// // set range value
		// eLeclercBean.setRange_APlus_Min(mapRange.get(ConfigEnum.Range_APlus_Min
		// .toString()));
		// eLeclercBean.setRange_APlus_Max(mapRange.get(ConfigEnum.Range_APlus_Max
		// .toString()));
		//
		// eLeclercBean.setRange_A_Min(mapRange.get(ConfigEnum.Range_A_Min
		// .toString()));
		// eLeclercBean.setRange_A_Max(mapRange.get(ConfigEnum.Range_A_Max
		// .toString()));
		//
		// eLeclercBean.setRange_B_Min(mapRange.get(ConfigEnum.Range_B_Min
		// .toString()));
		// eLeclercBean.setRange_B_Max(mapRange.get(ConfigEnum.Range_B_Max
		// .toString()));
		//
		// eLeclercBean.setRange_C_Min(mapRange.get(ConfigEnum.Range_C_Min
		// .toString()));
		// eLeclercBean.setRange_C_Max(mapRange.get(ConfigEnum.Range_C_Max
		// .toString()));
		//
		// eLeclercBean.setRange_D_Min(mapRange.get(ConfigEnum.Range_D_Min
		// .toString()));
		// eLeclercBean.setRange_D_Max(mapRange.get(ConfigEnum.Range_D_Max
		// .toString()));
		//
		// eLeclercBean.setRange_E_Min(mapRange.get(ConfigEnum.Range_E_Min
		// .toString()));
		// eLeclercBean.setRange_E_Max(mapRange.get(ConfigEnum.Range_E_Max
		// .toString()));
		//
		// eLeclercBean.setRange_F_Min(mapRange.get(ConfigEnum.Range_F_Min
		// .toString()));
		// eLeclercBean.setRange_F_Max(mapRange.get(ConfigEnum.Range_F_Max
		// .toString()));
		//
		// eLeclercBean.setRange_G_Min(mapRange.get(ConfigEnum.Range_G_Min
		// .toString()));
		// eLeclercBean.setRange_G_Max(mapRange.get(ConfigEnum.Range_G_Max
		// .toString()));
		//
		// eLeclercBean.setRange_H_Min(mapRange.get(ConfigEnum.Range_H_Min
		// .toString()));
		// eLeclercBean.setRange_H_Max(mapRange.get(ConfigEnum.Range_H_Max
		// .toString()));
		//
		// eLeclercBean.setRange_I_Min(mapRange.get(ConfigEnum.Range_I_Min
		// .toString()));

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(eLeclercBean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Service to update config module id and site infomation
	 * 
	 * @param siteInfo
	 * @param superficies
	 * @param froidNeg
	 * @param froidPos
	 * @param froidRes
	 * @param eclairage
	 * @param bureau
	 * @param boulangerie
	 * @param cvc
	 * @param temperatureInt
	 * @param temperatureOut
	 * @param drive
	 * @param computerElectric
	 * @param computerGaz
	 * @param franis
	 * @param energyPastYear
	 * @return
	 */
	@RequestMapping(value = "configELeclerc", method = RequestMethod.GET)
	public @ResponseBody String configELeclerc(@RequestParam String siteId,
			@RequestParam(required = false) String siteInfo, @RequestParam(required = false) String siteName,
			@RequestParam(required = false) String superficies, @RequestParam(required = false) String froidNeg,
			@RequestParam(required = false) String froidPos, @RequestParam(required = false) String froidRes,
			@RequestParam(required = false) String eclairage, @RequestParam(required = false) String bureau,
			@RequestParam(required = false) String boulangerie, @RequestParam(required = false) String cvc,
			@RequestParam(required = false) String temperatureInt,
			@RequestParam(required = false) String temperatureOut, @RequestParam(required = false) String drive,
			@RequestParam(required = false) String computerElectric, @RequestParam(required = false) String computerGaz,
			@RequestParam(required = false) String franis, @RequestParam(required = false) String energyPastYear,
			@RequestParam(required = false) String logo, @RequestParam(required = false) String uriIcon) {

		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab003 configLab = configLab003DAO.findBySite(siteNumber);
		configLab.setSiteId(siteNumber);
		if (siteInfo != null && !siteInfo.isEmpty()) {
			configLab.setSiteInfo(siteInfo);
		}
		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(siteName);
		}
		if (superficies != null && !superficies.trim().equals("")) {
			Integer number = WSCommon.getNumber(superficies);
			if (number != null) {
				configLab.setSiteSuperficies(number);
			} else {
				return "superficies not a number";
			}
		}
		if (froidNeg != null && !froidNeg.trim().equals("")) {
			Integer number = WSCommon.getNumber(froidNeg);
			if (number != null) {
				configLab.setModuleNeg(number.toString());
			} else {
				return "froidNeg not a number";
			}
		}
		if (froidPos != null && !froidPos.trim().equals("")) {
			Integer number = WSCommon.getNumber(froidPos);
			if (number != null) {
				configLab.setModulePos(number.toString());
			} else {
				return "froidPos not a number";
			}
		}
		if (froidRes != null && !froidRes.trim().equals("")) {
			Integer number = WSCommon.getNumber(froidRes);
			if (number != null) {
				configLab.setModuleReg(number.toString());
			} else {
				return "froidRes not a number";
			}
		}
		if (eclairage != null && !eclairage.trim().equals("")) {
			Integer number = WSCommon.getNumber(eclairage);
			if (number != null) {
				configLab.setModuleEclairage(number.toString());
			} else {
				return "eclairage not a number";
			}
		}
		if (bureau != null && !bureau.trim().equals("")) {
			Integer number = WSCommon.getNumber(bureau);
			if (number != null) {
				configLab.setModuleBureau(number.toString());
			} else {
				return "bureau not a number";
			}
		}
		if (boulangerie != null && !boulangerie.trim().equals("")) {
			Integer number = WSCommon.getNumber(boulangerie);
			if (number != null) {
				configLab.setModuleBoulangerie(number.toString());
			} else {
				return "boulangerie not a number";
			}
		}

		if (cvc != null && !cvc.trim().equals("")) {
			Integer number = WSCommon.getNumber(cvc);
			if (number != null) {
				configLab.setModuleCvc(number.toString());
			} else {
				return "cvc not a number";
			}
		}
		if (temperatureInt != null && !temperatureInt.trim().equals("")) {
			Integer number = WSCommon.getNumber(temperatureInt);
			if (number != null) {
				configLab.setModuleTemperatureIn(number.toString());
			} else {
				return "temperatureInt not a number";
			}
		}
		if (temperatureOut != null && !temperatureOut.trim().equals("")) {
			Integer number = WSCommon.getNumber(temperatureOut);
			if (number != null) {
				configLab.setModuleTemperatureOut(number.toString());
			} else {
				return "temperatureOut not a number";
			}
		}
		if (drive != null && !drive.trim().equals("")) {
			Integer number = WSCommon.getNumber(drive);
			if (number != null) {
				configLab.setModuleDrive(number.toString());
			} else {
				return "drive not a number";
			}
		}
		if (computerElectric != null && !computerElectric.trim().equals("")) {
			Integer number = WSCommon.getNumber(computerElectric);
			if (number != null) {
				configLab.setModuleElectric(number.toString());
			} else {
				return "computerElectric not a number";
			}
		}
		if (computerGaz != null && !computerGaz.trim().equals("")) {
			Integer number = WSCommon.getNumber(computerGaz);
			if (number != null) {
				configLab.setModuleGaz(number.toString());
			} else {
				return "computerGaz not a number";
			}
		}
		if (franis != null && !franis.trim().equals("")) {
			Integer number = WSCommon.getNumber(franis);
			if (number != null) {
				configLab.setModuleTemperatureFranis(number.toString());
			} else {
				return "franis not a number";
			}
		}
		if (energyPastYear != null && !energyPastYear.trim().equals("")) {
			Integer number = WSCommon.getNumber(energyPastYear);
			if (number != null) {
				configLab.setModuleEnergyPreviousYear(number);
			} else {
				return "energyPastYear not a number";
			}
		}

		if (uriIcon != null && !uriIcon.trim().isEmpty()) {
			configLab.setUriIcon(uriIcon);
		}

		if (logo != null && !logo.trim().isEmpty()) {
			configLab.setLogo(logo);
		}

		if (configLab003DAO.save(configLab)) {
			return "Success";
		} else {
			return "Fail";
		}
	}

	/**
	 * Config lab by number module(not id)
	 * 
	 * @param siteId
	 * @param siteInfo
	 * @param superficies
	 * @param froidNeg
	 * @param froidPos
	 * @param froidRes
	 * @param eclairage
	 * @param bureau
	 * @param boulangerie
	 * @param cvc
	 * @param temperatureInt
	 * @param temperatureOut
	 * @param drive
	 * @param computerElectric
	 * @param computerGaz
	 * @param franis
	 * @param energyPastYear
	 * @return
	 */
	@RequestMapping(value = "configLab003", method = RequestMethod.GET)
	public @ResponseBody String configLab003(@RequestParam String siteId,
			@RequestParam(required = false) String siteInfo, @RequestParam(required = false) String siteName,
			@RequestParam(required = false) String superficies, @RequestParam(required = false) String froidNeg,
			@RequestParam(required = false) String froidPos, @RequestParam(required = false) String froidRes,
			@RequestParam(required = false) String eclairage, @RequestParam(required = false) String bureau,
			@RequestParam(required = false) String boulangerie, @RequestParam(required = false) String cvc,
			@RequestParam(required = false) String temperatureInt,
			@RequestParam(required = false) String temperatureOut, @RequestParam(required = false) String drive,
			@RequestParam(required = false) String computerElectric, @RequestParam(required = false) String computerGaz,
			@RequestParam(required = false) String franis, @RequestParam(required = false) String energyPastYear,
			@RequestParam(required = false) String logo, @RequestParam(required = false) String uriIcon) {

		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab003 configLab = configLab003DAO.findBySite(siteNumber);
		configLab.setSiteId(siteNumber);
		if (siteInfo != null && !siteInfo.isEmpty()) {
			configLab.setSiteInfo(siteInfo);
		}
		if (configLab.getSiteInfo() == null) {
			return "siteInfo required";
		}
		if (siteName != null && !siteName.isEmpty()) {
			configLab.setSiteName(siteName);
		}
		if (configLab.getSiteName() == null) {
			return "siteName required";
		}
		if (superficies != null && !superficies.trim().equals("")) {
			Integer number = WSCommon.getNumber(superficies);
			if (number != null) {
				configLab.setSiteSuperficies(number);
			} else {
				return "superficies not a number";
			}
		}
		if (configLab.getSiteSuperficies() == null) {
			return "superficies required";
		}
		GetCSMDataAction csm = new GetCSMDataAction();
		if (froidNeg != null) {
			if (!froidNeg.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(froidNeg);
				if (number != null) {
					configLab.setModuleNeg(number.toString());
				} else {
					return "froidNeg not exists module: " + froidNeg;
				}
			} else {
				configLab.setModuleNeg(null);
			}
		}
		if (froidPos != null) {
			if (!froidPos.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(froidPos);
				if (number != null) {
					configLab.setModulePos(number.toString());
				} else {
					return "froidPos not exists module: " + froidPos;
				}
			} else {
				configLab.setModulePos(null);
			}
		}
		if (froidRes != null) {
			if (!froidRes.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(froidRes);
				if (number != null) {
					configLab.setModuleReg(number.toString());
				} else {
					return "froidRes not exists module: " + froidRes;
				}
			} else {
				configLab.setModulePos(null);
			}
		}
		if (eclairage != null) {
			if (!eclairage.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(eclairage);
				if (number != null) {
					configLab.setModuleEclairage(number.toString());
				} else {
					return "eclairage not exists module: " + eclairage;
				}
			} else {
				configLab.setModuleEclairage(null);
			}
		}
		if (bureau != null) {
			if (!bureau.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(bureau);
				if (number != null) {
					configLab.setModuleBureau(number.toString());
				} else {
					return "bureau not exists module: " + bureau;
				}
			} else {
				configLab.setModuleBureau(null);
			}
		}
		if (boulangerie != null) {
			if (!boulangerie.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(boulangerie);
				if (number != null) {
					configLab.setModuleBoulangerie(number.toString());
				} else {
					return "boulangerie not exists module: " + boulangerie;
				}
			} else {
				configLab.setModuleBoulangerie(null);
			}
		}
		if (cvc != null) {
			if (!cvc.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(cvc);
				if (number != null) {
					configLab.setModuleCvc(number.toString());
				} else {
					return "cvc not exists module: " + cvc;
				}
			} else {
				configLab.setModuleCvc(null);
			}
		}
		if (temperatureInt != null) {
			if (!temperatureInt.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(temperatureInt);
				if (number != null) {
					configLab.setModuleTemperatureIn(number.toString());
				} else {
					return "temperatureInt not exists module: " + temperatureInt;
				}
			} else {
				configLab.setModuleTemperatureIn(null);
			}
		}
		if (temperatureOut != null) {
			if (!temperatureOut.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(temperatureOut);
				if (number != null) {
					configLab.setModuleTemperatureOut(number.toString());
				} else {
					return "temperatureOut not exists module: " + temperatureOut;
				}
			} else {
				configLab.setModuleTemperatureOut(null);
			}
		}
		if (drive != null) {
			if (!drive.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(drive);
				if (number != null) {
					configLab.setModuleDrive(number.toString());
				} else {
					return "drive not exists module: " + drive;
				}
			} else {
				configLab.setModuleDrive(null);
			}
		}
		if (computerElectric != null) {
			if (!computerElectric.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(computerElectric);
				if (number != null) {
					configLab.setModuleElectric(number.toString());
				} else {
					return "computerElectric not exists module: " + computerElectric;
				}
			} else {
				configLab.setModuleElectric(null);
			}
		}
		if (computerGaz != null) {
			if (!computerGaz.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(computerGaz);
				if (number != null) {
					configLab.setModuleGaz(number.toString());
				} else {
					return "computerGaz not exists module: " + computerGaz;
				}
			} else {
				configLab.setModuleGaz(null);
			}
		}
		if (franis != null) {
			if (!franis.trim().isEmpty()) {
				Integer number = csm.getModuleCSMIDByNumber(franis);
				if (number != null) {
					configLab.setModuleTemperatureFranis(number.toString());
				} else {
					return "franis not exists module: " + franis;
				}
			} else {
				configLab.setModuleTemperatureFranis(null);
			}
		}
		if (energyPastYear != null && !energyPastYear.trim().equals("")) {
			Integer number = WSCommon.getNumber(energyPastYear);
			if (number != null) {
				configLab.setModuleEnergyPreviousYear(number);
			} else {
				return "energyPastYear not a number: " + energyPastYear;
			}
		}

		if (uriIcon != null && !uriIcon.trim().isEmpty()) {
			configLab.setUriIcon(uriIcon);
		}

		if (logo != null && !logo.trim().isEmpty()) {
			configLab.setLogo(logo);
		}

		if (configLab003DAO.save(configLab)) {
			return "Success";
		} else {
			return "Fail";
		}
	}

	@RequestMapping(value = "configRangeEnergy", method = RequestMethod.GET)
	public @ResponseBody String configRangeEnergy(@RequestParam String aPlusMax, @RequestParam String aMin,
			@RequestParam String aMax, @RequestParam String bMin, @RequestParam String bMax, @RequestParam String cMin,
			@RequestParam String cMax, @RequestParam String dMin, @RequestParam String dMax, @RequestParam String eMin,
			@RequestParam String eMax, @RequestParam String fMin, @RequestParam String fMax, @RequestParam String gMin,
			@RequestParam String gMax, @RequestParam String hMin, @RequestParam String hMax,
			@RequestParam String iMin) {
		Integer apMax = WSCommon.getNumber(aPlusMax);
		if (apMax == null) {
			return "aPlusMax not a number";
		}
		if (apMax < 0) {
			return "aPlusMax less than 0";
		}
		// A Min - Max
		Integer aMi = WSCommon.getNumber(aMin);
		if (aMi == null) {
			return "aMin not a number";
		}
		if (aMi <= apMax) {
			return "aMin less than or equal apMax";
		}
		Integer aMa = WSCommon.getNumber(aMax);
		if (aMa == null) {
			return "aMax not a number";
		}
		if (aMa <= aMi) {
			return "aMax less than or equal aMin";
		}
		// B Min - Max
		Integer bMi = WSCommon.getNumber(bMin);
		if (bMi == null) {
			return "bMin not a number";
		}
		if (bMi <= aMa) {
			return "bMin less than or equal aMax";
		}
		Integer bMa = WSCommon.getNumber(bMax);
		if (bMa == null) {
			return "bMax not a number";
		}
		if (bMa <= bMi) {
			return "bMax less than or equal bMin";
		}
		// C Min - Max
		Integer cMi = WSCommon.getNumber(cMin);
		if (cMi == null) {
			return "cMin not a number";
		}
		if (cMi <= bMa) {
			return "cMin less than or equal bMax";
		}
		Integer cMa = WSCommon.getNumber(cMax);
		if (cMa == null) {
			return "cMax not a number";
		}
		if (cMa <= cMi) {
			return "cMax less than or equal cMin";
		}
		// D Min - Max
		Integer dMi = WSCommon.getNumber(dMin);
		if (dMi == null) {
			return "dMin not a number";
		}
		if (dMi <= cMa) {
			return "dMin less than or equal cMax";
		}
		Integer dMa = WSCommon.getNumber(dMax);
		if (dMa == null) {
			return "dMax not a number";
		}
		if (dMa <= dMi) {
			return "dMax less than or equal dMin";
		}
		// E Min - Max
		Integer eMi = WSCommon.getNumber(eMin);
		if (eMi == null) {
			return "eMin not a number";
		}
		if (eMi <= dMa) {
			return "eMin less than or equal dMax";
		}
		Integer eMa = WSCommon.getNumber(eMax);
		if (eMa == null) {
			return "eMax not a number";
		}
		if (eMa <= eMi) {
			return "eMax less than or equal eMin";
		}
		// F Min - Max
		Integer fMi = WSCommon.getNumber(fMin);
		if (fMi == null) {
			return "fMin not a number";
		}
		if (fMi <= eMa) {
			return "fMin less than or equal eMax";
		}
		Integer fMa = WSCommon.getNumber(fMax);
		if (fMa == null) {
			return "fMax not a number";
		}
		if (fMa <= fMi) {
			return "fMax less than or equal fMix";
		}
		// G Min - Max
		Integer gMi = WSCommon.getNumber(gMin);
		if (gMi == null) {
			return "gMin not a number";
		}
		if (gMi <= fMa) {
			return "gMin less than or equal fMax";
		}
		Integer gMa = WSCommon.getNumber(gMax);
		if (gMa == null) {
			return "gMax not a number";
		}
		if (gMa <= gMi) {
			return "gMax less than or equal gMin";
		}
		// H Min - Max
		Integer hMi = WSCommon.getNumber(hMin);
		if (hMi == null) {
			return "hMin not a number";
		}
		if (hMi <= gMa) {
			return "hMin less than or equal gMax";
		}
		Integer hMa = WSCommon.getNumber(hMax);
		if (hMa == null) {
			return "hMax not a number";
		}
		if (hMa <= hMi) {
			return "hMax less than or equal hMin";
		}
		// I Min - Max
		Integer iMi = WSCommon.getNumber(iMin);
		if (iMi == null) {
			return "iMin not a number";
		}
		if (iMi <= hMa) {
			return "iMin less than or equal hMax";
		}

		PropertiesData pro = new PropertiesData();
		Map<String, String> mapData = pro.loadRangeConfig();
		mapData.put(ConfigEnum.Range_APlus_Min.toString(), "0");
		mapData.put(ConfigEnum.Range_APlus_Max.toString(), aPlusMax);
		mapData.put(ConfigEnum.Range_A_Min.toString(), aMin);
		mapData.put(ConfigEnum.Range_A_Max.toString(), aMax);
		mapData.put(ConfigEnum.Range_B_Min.toString(), bMin);
		mapData.put(ConfigEnum.Range_B_Max.toString(), bMax);
		mapData.put(ConfigEnum.Range_C_Min.toString(), cMin);
		mapData.put(ConfigEnum.Range_C_Max.toString(), cMax);
		mapData.put(ConfigEnum.Range_D_Min.toString(), dMin);
		mapData.put(ConfigEnum.Range_D_Max.toString(), dMax);
		mapData.put(ConfigEnum.Range_E_Min.toString(), eMin);
		mapData.put(ConfigEnum.Range_E_Max.toString(), eMax);
		mapData.put(ConfigEnum.Range_F_Min.toString(), fMin);
		mapData.put(ConfigEnum.Range_F_Max.toString(), fMax);
		mapData.put(ConfigEnum.Range_G_Min.toString(), gMin);
		mapData.put(ConfigEnum.Range_G_Max.toString(), gMax);
		mapData.put(ConfigEnum.Range_H_Min.toString(), hMin);
		mapData.put(ConfigEnum.Range_H_Max.toString(), hMax);
		mapData.put(ConfigEnum.Range_I_Min.toString(), iMin);
		pro.saveRangeConfig(mapData);
		return "success";
	}

	/**
	 * register user in pro file
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "registerUser", method = RequestMethod.GET)
	public @ResponseBody String registerUser(@RequestParam String username) {
		String result = null;
		if (username == null || username.isEmpty()) {
			return "[ERROR]User name is empty";
		}
		PropertiesData pro = new PropertiesData();
		int ok = pro.addUser(username);
		if (ok == PropertiesData.STATUS_SUCCESS) {
			result = "success";
		} else if (ok == PropertiesData.STATUS_EXIST) {
			result = "existed";
		} else {
			result = "error";
		}
		return result;
	}

	/**
	 * remove user from properties file
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "removeUser", method = RequestMethod.GET)
	public @ResponseBody String removeUser(@RequestParam String username) {
		String result = null;
		if (username == null || username.isEmpty()) {
			return "[ERROR]User name is empty";
		}
		PropertiesData pro = new PropertiesData();
		int ok = pro.removeUser(username);
		if (ok == PropertiesData.STATUS_SUCCESS) {
			result = "success";
		} else if (ok == PropertiesData.STATUS_EXIST) {
			result = "existed";
		} else {
			result = "error";
		}
		return result;
	}

	/**
	 * return link of lock file in config(pdf file)
	 * 
	 * @return
	 */
	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab003 configLab = configLab003DAO.findBySite(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getReportName() == null || configLab.getReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.PDF_BACKLOG_LINK_ELECLERC) + FrontalKey.WINDOWS
				+ siteId + FrontalKey.WINDOWS + configLab.getReportName();
		return reportLink;
	}

	@RequestMapping(value = "getDriveReportLink", method = RequestMethod.GET)
	public @ResponseBody String getDriveReportLink(@RequestParam String siteId) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		ConfigLab003 configLab = configLab003DAO.findBySite(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		if (configLab.getDriveReportName() == null || configLab.getDriveReportName().trim().equals("")) {
			return "Site Id - Report File not exist";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.PDF_BACKLOG_LINK_ELECLERC) + FrontalKey.WINDOWS
				+ siteId + FrontalKey.WINDOWS + FrontalKey.DRIVE + FrontalKey.WINDOWS + configLab.getDriveReportName();
		return reportLink;
	}

	/**
	 * get token for login from csm
	 * 
	 * @return
	 */
	@RequestMapping(value = "getToken", method = RequestMethod.GET)
	public @ResponseBody String getToken(@RequestParam String username, @RequestParam String password) {
		GetCSMDataAction csm = new GetCSMDataAction();
		UserCSM user = csm.checkLogin(username, password);
		if (user == null) {
			return "failed";
		}
		return user.getToken();
	}

	/**
	 * consumption by 7 days
	 *
	 * @param moduleArr
	 * @return
	 */
	private Double getConsumptionByModule(String moduleArr) {

		if (moduleArr == null || "".equals(moduleArr)) {
			return null;
		}
		String[] moduleIdArr = moduleArr.split(FrontalKey.PLUS_SPECIAL);
		// Date time before 7 days
		Double result = 0d;
		for (int i = 0; i < moduleIdArr.length; i++) {
			Integer[] result_tmp = getConsumptionByTime(moduleIdArr[i], Calendar.DAY_OF_YEAR, 0,
					PropertiesReader.getValueInt(ConfigEnum.DAY_GET_CONSUMPTION));
			if (result_tmp != null && result_tmp.length > 1) {
				result += Utils.convertToKWh(moduleIdArr[i], result_tmp[0]);
			}
		}
		return result;
	}

	private Double getConsumptionByModuleByYear(String moduleArr,
												int startYear, int endYear) {
		if (moduleArr == null || "".equals(moduleArr)) {
			return null;
		}
		String[] moduleIdArr = moduleArr.split(FrontalKey.PLUS_SPECIAL);
		Double result = 0d;
		for (String moduleID: moduleIdArr) {
			Integer[] result_tmp = getConsumptionByTime(moduleID, Calendar.YEAR,
					startYear, endYear);
			if (result_tmp != null) {
				int consumption = result_tmp[0];
				int numberDay = result_tmp[1];
				if (numberDay != 0) {
					System.out.println("Number day:" + numberDay + ":"
							+ consumption + ":" + consumption / numberDay * 365);
					result += Utils.convertToKWh(moduleID,(consumption / numberDay * 365));
				}
			}
		}
		return result;
	}

	/**
	 * Get number fo consumption by time
	 *
	 * @param moduleID
	 * @param calendarType
	 * @param fromNumber
	 * @param toNumber
	 * @return
	 */
	private Integer[] getConsumptionByTime(String moduleID, int calendarType,
										   int fromNumber, int toNumber) {

		//
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar cal = Calendar.getInstance();

		// SET TIME
		if (!"YES"
				.equals(PropertiesReader.getValue(ConfigEnum.IS_CURRENT_TIME))) {
			cal.set(Calendar.YEAR,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_YEAR));
			cal.set(Calendar.MONTH,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_MONTH) - 1);
			cal.set(Calendar.DAY_OF_MONTH,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_DAY));
			cal.set(Calendar.HOUR_OF_DAY,
					PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_HOUR));
		}

		// Date time now
		cal.add(calendarType, -fromNumber);
//		String endHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String endDate = sdf.format(cal.getTime());

		// Date time before 7 days
		cal.add(calendarType, -toNumber);
//		String startHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String startDate = sdf.format(cal.getTime());

		GetDataAction csm = new GetDataAction();
		return csm.getConsommationAndDiffDate(Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)),
				startDate, endDate);
////		String[] moduleArr = moduleID.split(FrontalKey.PLUS_SPECIAL);
//		Integer[] result = null;
////		for (String module : moduleArr) {
//		int moduleId = Integer.parseInt(Utils.getModuleNumberFromArray(moduleID));
//		List<AgregatCSM> agregatLst = csm.getListAgregatByModule(Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)),
//				startDate, endDate, startHour, endHour);
//
//		if (agregatLst.size() > 1) {
//			result = new Integer[2];
//			result[0] = Math.abs(agregatLst.get(0).getIndex()
//					- agregatLst.get(agregatLst.size() - 1).getIndex());
//			result[1] = WSCommon.different2Date(agregatLst.get(0)
//					.getDateHeureAgregat(), agregatLst.get(1)
//					.getDateHeureAgregat());
////				for (AgregatCSM agr : agregatLst) {
////					if (agr.getConsommation() != null) {
////						if(result == null){
////							result.add(0);
////						}
////						result.add(Math.abs(agregatLst.get(0).getIndex()
////								- agregatLst.get(agregatLst.size() - 1).getIndex())) ;
////					}
////				}
//		}
////		}
//
//		return result;
	}

	private Integer getTemperatureByModule(String moduleID) {
		if (moduleID == null || "".equals(moduleID)){
			return null;
		}

		//
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar cal = Calendar.getInstance();

		if (!"YES".equals(PropertiesReader.getValue(ConfigEnum.IS_CURRENT_TIME))) {
			cal.set(Calendar.YEAR, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_YEAR));
			cal.set(Calendar.MONTH, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_MONTH) - 1);
			cal.set(Calendar.DAY_OF_MONTH, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_DAY));
			cal.set(Calendar.HOUR_OF_DAY, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_HOUR));
		}

		// Date time now
		String endHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String endDate = sdf.format(cal.getTime());

		// Date time before 7 days
		cal.add(Calendar.HOUR_OF_DAY, -PropertiesReader.getValueInt(ConfigEnum.HOUR_GET_TEMPERATURE));
		String startHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String startDate = sdf.format(cal.getTime());

		GetDataAction csm = new GetDataAction();
		return csm.getLastTemperatureByTime(Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)),
				startDate, endDate, startHour, endHour);
//		List<AgregatCSM> agregatLst = csm.getListAgregatByModule(Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)), startDate, endDate, startHour, endHour);
//		Integer result = null;
//		if (agregatLst.size() > 0) {
//			result = agregatLst.get(0).getTemperature();
//		}
//		if (agregatLst.size() > 1) {
//			if (agregatLst.get(1).getDateHeureAgregat().compareTo(agregatLst.get(1).getDateHeureAgregat()) > 0) {
//				result = agregatLst.get(1).getTemperature();
//			} else if (agregatLst.get(1).getDateHeureAgregat()
//					.compareTo(agregatLst.get(1).getDateHeureAgregat()) == 0) {
//				if (agregatLst.get(1).getHeureAgregat().compareTo(agregatLst.get(1).getHeureAgregat()) > 0) {
//					result = agregatLst.get(1).getTemperature();
//				}
//			}
//		}
//		return result;
	}

	/**
	 * get URI of icon URI return for another system(premium)
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "getURIIcon", method = RequestMethod.GET)
	public @ResponseBody String getURIIcon(@RequestParam Integer siteId) {

		ConfigLab003 configLab = configLab003DAO.getConfigBySite(siteId);

		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getUriIcon();
	}

	/**
	 * get logo- logo display for page
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "getLogo", method = RequestMethod.GET)
	public @ResponseBody String getLogo(@RequestParam Integer siteId) {

		ConfigLab003 configLab = configLab003DAO.getConfigBySite(siteId);

		if (configLab == null) {
			return "This site is not config";
		}
		return configLab.getLogo();
	}

}
