package com.energisme.util;

import java.text.SimpleDateFormat;

import com.energisme.bean.NumConfigLab001;
import com.energisme.bean.NumConfigLab002;
import com.energisme.bean.NumConfigLab003;
import com.energisme.bean.NumConfigLab004;
import com.energisme.bean.NumConfigLab005;
import com.energisme.bean.NumConfigLab006;
import com.energisme.bean.NumConfigLab006Client;
import com.energisme.bean.NumConfigLab007;
import com.energisme.bean.NumConfigLab008;
import com.energisme.bean.NumConfigLab008ECS;
import com.energisme.bean.NumConfigLab008V2;
import com.energisme.bean.NumConfigLab010IdexSite;
import com.energisme.bean.NumConfigLab010IdexSupplier;
import com.energisme.bean.NumConfigLab011Range;
import com.energisme.bean.NumconfigLab011Resident;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.model.Bouygues;
import com.ifi.lab.LabDAO.model.ConfigLab001;
import com.ifi.lab.LabDAO.model.ConfigLab003;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab005;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import com.ifi.lab.LabDAO.model.ConfigLab007;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab008ECS;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonResident;

public class ConvertObject {

	public NumConfigLab001 convertIdtoMuduleNumber(ConfigLab001 configLab) {
		/*
		 * NumConfigLab001 numConfig = new NumConfigLab001();
		 * numConfig.setId(configLab.getId()); if (configLab.getSiteId() !=
		 * null) { numConfig.setSiteId(configLab.getSiteId().toString()); }
		 * numConfig.setSiteInfo(configLab.getSiteInfo());
		 * numConfig.setSiteName(configLab.getSiteName()); if
		 * (configLab.getSiteSuperficies() != null) {
		 * numConfig.setSiteSuperficies(configLab.getSiteSuperficies().toString(
		 * )); } GetCSMDataAction csm = new GetCSMDataAction();
		 * numConfig.setModuleBoulangerie(csm.getModuleNumberByID(Integer.
		 * parseInt(configLab.getModuleBoulangerie())));
		 * numConfig.setModuleBureau(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleBureau())));
		 * numConfig.setModuleCvc(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleCvc())));
		 * numConfig.setModuleDrive(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleDrive())));
		 * numConfig.setModuleEclairage(csm.getModuleNumberByID(Integer.parseInt
		 * (configLab.getModuleEclairage())));
		 * numConfig.setModuleElectric(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleElectric())));
		 * numConfig.setModuleGaz(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleGaz())));
		 * numConfig.setModuleNeg(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleNeg())));
		 * numConfig.setModulePos(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModulePos())));
		 * numConfig.setModuleReg(csm.getModuleNumberByID(Integer.parseInt(
		 * configLab.getModuleReg())));
		 * numConfig.setModuleTemperatureFranis(csm.getModuleNumberByID(Integer.
		 * parseInt(configLab.getModuleTemperatureFranis())));
		 * numConfig.setModuleTemperatureIn(csm.getModuleNumberByID(Integer.
		 * parseInt(configLab.getModuleTemperatureIn())));
		 * numConfig.setModuleTemperatureOut(csm.getModuleNumberByID(Integer.
		 * parseInt(configLab.getModuleTemperatureOut())));
		 * numConfig.setLogo(configLab.getLogo());
		 * numConfig.setUriIcon(configLab.getUriIcon());
		 * numConfig.setReportName(configLab.getReportName()); if
		 * (configLab.getModuleEnergyPreviousYear() != null) {
		 * numConfig.setModuleEnergyPreviousYear(configLab.
		 * getModuleEnergyPreviousYear().toString()); } return numConfig;
		 */
		NumConfigLab001 numConfig = new NumConfigLab001();
		numConfig.setId(configLab.getId());
		if (configLab.getSiteId() != null) {
			numConfig.setSiteId(configLab.getSiteId().toString());
		}
		numConfig.setSiteInfo(configLab.getSiteInfo());
		numConfig.setSiteName(configLab.getSiteName());
		if (configLab.getSiteSuperficies() != null) {
			numConfig.setSiteSuperficies(configLab.getSiteSuperficies().toString());
		}
		GetCSMDataAction csm = new GetCSMDataAction();
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

		String froidNeg = configLab.getModuleNeg();
		String froidPos = configLab.getModulePos();
		String froidRes = configLab.getModuleReg();
		String eclairage = configLab.getModuleEclairage();
		String bureau = configLab.getModuleBureau();
		String boulangerie = configLab.getModuleBoulangerie();
		String cvc = configLab.getModuleCvc();
		String temperatureInt = configLab.getModuleTemperatureIn();
		String temperatureOut = configLab.getModuleTemperatureOut();
		String drive = configLab.getModuleDrive();
		String computerElectric = configLab.getModuleElectric();
		String computerGaz = configLab.getModuleGaz();
		String franis = configLab.getModuleTemperatureFranis();
		// String energyPastYear = configLab.getModuleEnergyPreviousYear();

		if (froidNeg != null && !froidNeg.trim().equals("")) {
			String[] arrObj = froidNeg.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidNeg += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (froidPos != null && !froidPos.trim().equals("")) {
			String[] arrObj = froidPos.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidPos += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (froidRes != null && !froidRes.trim().equals("")) {
			String[] arrObj = froidRes.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidRes += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (eclairage != null && !eclairage.trim().equals("")) {
			String[] arrObj = eclairage.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listEclairage += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (bureau != null && !bureau.trim().equals("")) {
			String[] arrObj = bureau.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listBureau += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (boulangerie != null && !boulangerie.trim().equals("")) {
			String[] arrObj = boulangerie.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listBoulangerie += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (cvc != null && !cvc.trim().equals("")) {
			String[] arrObj = cvc.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listCvc += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (temperatureInt != null && !temperatureInt.trim().equals("")) {
			String[] arrObj = temperatureInt.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listTemperatureInt += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (temperatureOut != null && !temperatureOut.trim().equals("")) {
			String[] arrObj = temperatureOut.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listTemperatureOut += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (drive != null && !drive.trim().equals("")) {
			String[] arrObj = drive.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listDrive += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (computerElectric != null && !computerElectric.trim().equals("")) {
			String[] arrObj = computerElectric.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listComputerElectric += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (computerGaz != null && !computerGaz.trim().equals("")) {
			String[] arrObj = computerGaz.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listComputerGaz += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (franis != null && !franis.trim().equals("")) {
			String[] arrObj = franis.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFranis += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		/*
		 * if (energyPastYear != null && !energyPastYear.trim().equals("")) {
		 * String[] arrObj = energyPastYear.split(FrontalKey.PLUS_SPECIAL); for
		 * (int i = 0; i < arrObj.length; i++) { if (!arrObj[i].equals("")) {
		 * listEnergyPastYear += Utils.getModuleIndexRatio(arrObj[i]) +
		 * csm.getModuleNumberByID(Integer.parseInt(Utils.
		 * getModuleNumberFromArray(arrObj[i]))) + FrontalKey.PLUS; } } }
		 */
		if (listFroidNeg != null && !"".equals(listFroidNeg)) {
			if (FrontalKey.PLUS.equals(listFroidNeg.charAt(listFroidNeg.length() - 1))) {
				listFroidNeg = listFroidNeg.substring(0, listFroidNeg.length() - 1);
			}
		}
		if (listFroidPos != null && !"".equals(listFroidPos)) {
			if (FrontalKey.PLUS.equals(listFroidPos.charAt(listFroidPos.length() - 1))) {
				listFroidPos = listFroidPos.substring(0, listFroidPos.length() - 1);
			}
		}
		if (listFroidRes != null && !"".equals(listFroidRes)) {
			if (FrontalKey.PLUS.equals(listFroidRes.charAt(listFroidRes.length() - 1))) {
				listFroidRes = listFroidRes.substring(0, listFroidRes.length() - 1);
			}
		}
		if (listEclairage != null && !"".equals(listEclairage)) {
			if (FrontalKey.PLUS.equals(listEclairage.charAt(listEclairage.length() - 1))) {
				listEclairage = listEclairage.substring(0, listEclairage.length() - 1);
			}
		}
		if (listBureau != null && !"".equals(listBureau)) {
			if (FrontalKey.PLUS.equals(listBureau.charAt(listBureau.length() - 1))) {
				listBureau = listBureau.substring(0, listBureau.length() - 1);
			}
		}
		if (listBoulangerie != null && !"".equals(listBoulangerie)) {
			if (FrontalKey.PLUS.equals(listBoulangerie.charAt(listBoulangerie.length() - 1))) {
				listBoulangerie = listBoulangerie.substring(0, listBoulangerie.length() - 1);
			}
		}
		if (listCvc != null && !"".equals(listCvc)) {
			if (FrontalKey.PLUS.equals(listCvc.charAt(listCvc.length() - 1))) {
				listCvc = listCvc.substring(0, listCvc.length() - 1);
			}
		}
		if (listTemperatureInt != null && !"".equals(listTemperatureInt)) {
			if (FrontalKey.PLUS.equals(listTemperatureInt.charAt(listTemperatureInt.length() - 1))) {
				listTemperatureInt = listTemperatureInt.substring(0, listTemperatureInt.length() - 1);
			}
		}
		if (listTemperatureOut != null && !"".equals(listTemperatureOut)) {
			if (FrontalKey.PLUS.equals(listTemperatureOut.charAt(listTemperatureOut.length() - 1))) {
				listTemperatureOut = listTemperatureOut.substring(0, listTemperatureOut.length() - 1);
			}
		}
		if (listDrive != null && !"".equals(listDrive)) {
			if (FrontalKey.PLUS.equals(listDrive.charAt(listDrive.length() - 1))) {
				listDrive = listDrive.substring(0, listDrive.length() - 1);
			}
		}
		if (listComputerElectric != null && !"".equals(listComputerElectric)) {
			if (FrontalKey.PLUS.equals(listComputerElectric.charAt(listComputerElectric.length() - 1))) {
				listComputerElectric = listComputerElectric.substring(0, listComputerElectric.length() - 1);
			}
		}
		if (listComputerGaz != null && !"".equals(listComputerGaz)) {
			if (FrontalKey.PLUS.equals(listComputerGaz.charAt(listComputerGaz.length() - 1))) {
				listComputerGaz = listComputerGaz.substring(0, listComputerGaz.length() - 1);
			}
		}
		if (listFranis != null && !"".equals(listFranis)) {
			if (FrontalKey.PLUS.equals(listFranis.charAt(listFranis.length() - 1))) {
				listFranis = listFranis.substring(0, listFranis.length() - 1);
			}
		}
		/*
		 * if (listEnergyPastYear != null && !"".equals(listEnergyPastYear)) {
		 * if
		 * (FrontalKey.PLUS.equals(listEnergyPastYear.charAt(listEnergyPastYear.
		 * length()-1))) { listEnergyPastYear =
		 * listEnergyPastYear.substring(0,listEnergyPastYear.length()-1); } }
		 */

		numConfig.setModuleNeg(listFroidNeg);
		numConfig.setModulePos(listFroidPos);
		numConfig.setModuleReg(listFroidRes);
		numConfig.setModuleEclairage(listEclairage);
		numConfig.setModuleBureau(listBureau);
		numConfig.setModuleBoulangerie(listBoulangerie);
		numConfig.setModuleCvc(listCvc);
		numConfig.setModuleTemperatureIn(listTemperatureInt);
		numConfig.setModuleTemperatureOut(listTemperatureOut);
		numConfig.setModuleDrive(listDrive);
		numConfig.setModuleElectric(listComputerElectric);
		numConfig.setModuleGaz(listComputerGaz);
		numConfig.setModuleTemperatureFranis(listFranis);

		numConfig.setLogo(configLab.getLogo());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setReportName(configLab.getReportName());
		if (configLab.getModuleEnergyPreviousYear() != null) {
			numConfig.setModuleEnergyPreviousYear(configLab.getModuleEnergyPreviousYear().toString());
		}
		return numConfig;
	}

	public NumConfigLab003 convertIdtoMuduleNumber(ConfigLab003 configLab) {
		/*
		 * NumConfigLab003 numConfig = new NumConfigLab003();
		 * numConfig.setId(configLab.getId()); if (configLab.getSiteId() !=
		 * null) { numConfig.setSiteId(configLab.getSiteId().toString()); }
		 * numConfig.setSiteInfo(configLab.getSiteInfo());
		 * numConfig.setSiteName(configLab.getSiteName()); if
		 * (configLab.getSiteSuperficies() != null) {
		 * numConfig.setSiteSuperficies(configLab.getSiteSuperficies().toString(
		 * )); } GetCSMDataAction csm = new GetCSMDataAction();
		 * numConfig.setModuleBoulangerie(csm.getModuleNumberByID(configLab.
		 * getModuleBoulangerie()));
		 * numConfig.setModuleBureau(csm.getModuleNumberByID(configLab.
		 * getModuleBureau()));
		 * numConfig.setModuleCvc(csm.getModuleNumberByID(configLab.getModuleCvc
		 * ())); numConfig.setModuleDrive(csm.getModuleNumberByID(configLab.
		 * getModuleDrive()));
		 * numConfig.setModuleEclairage(csm.getModuleNumberByID(configLab.
		 * getModuleEclairage()));
		 * numConfig.setModuleElectric(csm.getModuleNumberByID(configLab.
		 * getModuleElectric()));
		 * numConfig.setModuleGaz(csm.getModuleNumberByID(configLab.getModuleGaz
		 * ()));
		 * numConfig.setModuleNeg(csm.getModuleNumberByID(configLab.getModuleNeg
		 * ()));
		 * numConfig.setModulePos(csm.getModuleNumberByID(configLab.getModulePos
		 * ()));
		 * numConfig.setModuleReg(csm.getModuleNumberByID(configLab.getModuleReg
		 * ())); numConfig.setModuleTemperatureFranis(csm.getModuleNumberByID(
		 * configLab.getModuleTemperatureFranis()));
		 * numConfig.setModuleTemperatureIn(csm.getModuleNumberByID(configLab.
		 * getModuleTemperatureIn()));
		 * numConfig.setModuleTemperatureOut(csm.getModuleNumberByID(configLab.
		 * getModuleTemperatureOut())); numConfig.setLogo(configLab.getLogo());
		 * numConfig.setUriIcon(configLab.getUriIcon());
		 * numConfig.setReportName(configLab.getReportName());
		 * numConfig.setDriveReportName(configLab.getDriveReportName()); if
		 * (configLab.getModuleEnergyPreviousYear() != null) {
		 * numConfig.setModuleEnergyPreviousYear(configLab.
		 * getModuleEnergyPreviousYear().toString()); } if
		 * (configLab.getModuleDrivePreviousYear() != null) {
		 * numConfig.setModuleDrivePreviousYear(configLab.
		 * getModuleDrivePreviousYear().toString()); } return numConfig;
		 */

		NumConfigLab003 numConfig = new NumConfigLab003();
		numConfig.setId(configLab.getId());
		if (configLab.getSiteId() != null) {
			numConfig.setSiteId(configLab.getSiteId().toString());
		}
		numConfig.setSiteInfo(configLab.getSiteInfo());
		numConfig.setSiteName(configLab.getSiteName());
		if (configLab.getSiteSuperficies() != null) {
			numConfig.setSiteSuperficies(configLab.getSiteSuperficies().toString());
		}
		GetCSMDataAction csm = new GetCSMDataAction();
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

		String froidNeg = configLab.getModuleNeg();
		String froidPos = configLab.getModulePos();
		String froidRes = configLab.getModuleReg();
		String eclairage = configLab.getModuleEclairage();
		String bureau = configLab.getModuleBureau();
		String boulangerie = configLab.getModuleBoulangerie();
		String cvc = configLab.getModuleCvc();
		String temperatureInt = configLab.getModuleTemperatureIn();
		String temperatureOut = configLab.getModuleTemperatureOut();
		String drive = configLab.getModuleDrive();
		String computerElectric = configLab.getModuleElectric();
		String computerGaz = configLab.getModuleGaz();
		String franis = configLab.getModuleTemperatureFranis();
		// String energyPastYear = configLab.getModuleEnergyPreviousYear();

		if (froidNeg != null && !froidNeg.trim().equals("")) {
			String[] arrObj = froidNeg.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidNeg += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (froidPos != null && !froidPos.trim().equals("")) {
			String[] arrObj = froidPos.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidPos += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (froidRes != null && !froidRes.trim().equals("")) {
			String[] arrObj = froidRes.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFroidRes += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (eclairage != null && !eclairage.trim().equals("")) {
			String[] arrObj = eclairage.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listEclairage += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (bureau != null && !bureau.trim().equals("")) {
			String[] arrObj = bureau.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listBureau += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (boulangerie != null && !boulangerie.trim().equals("")) {
			String[] arrObj = boulangerie.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listBoulangerie += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (cvc != null && !cvc.trim().equals("")) {
			String[] arrObj = cvc.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listCvc += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (temperatureInt != null && !temperatureInt.trim().equals("")) {
			String[] arrObj = temperatureInt.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listTemperatureInt += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (temperatureOut != null && !temperatureOut.trim().equals("")) {
			String[] arrObj = temperatureOut.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listTemperatureOut += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (drive != null && !drive.trim().equals("")) {
			String[] arrObj = drive.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listDrive += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (computerElectric != null && !computerElectric.trim().equals("")) {
			String[] arrObj = computerElectric.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listComputerElectric += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (computerGaz != null && !computerGaz.trim().equals("")) {
			String[] arrObj = computerGaz.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listComputerGaz += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (franis != null && !franis.trim().equals("")) {
			String[] arrObj = franis.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listFranis += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		/*
		 * if (energyPastYear != null && !energyPastYear.trim().equals("")) {
		 * String[] arrObj = energyPastYear.split(FrontalKey.PLUS_SPECIAL); for
		 * (int i = 0; i < arrObj.length; i++) { if (!arrObj[i].equals("")) {
		 * listEnergyPastYear += Utils.getModuleIndexRatio(arrObj[i]) +
		 * csm.getModuleNumberByID(Integer.parseInt(Utils.
		 * getModuleNumberFromArray(arrObj[i]))) + FrontalKey.PLUS; } } }
		 */
		if (listFroidNeg != null && !"".equals(listFroidNeg)) {
			if (FrontalKey.PLUS.equals(listFroidNeg.charAt(listFroidNeg.length() - 1))) {
				listFroidNeg = listFroidNeg.substring(0, listFroidNeg.length() - 1);
			}
		}
		if (listFroidPos != null && !"".equals(listFroidPos)) {
			if (FrontalKey.PLUS.equals(listFroidPos.charAt(listFroidPos.length() - 1))) {
				listFroidPos = listFroidPos.substring(0, listFroidPos.length() - 1);
			}
		}
		if (listFroidRes != null && !"".equals(listFroidRes)) {
			if (FrontalKey.PLUS.equals(listFroidRes.charAt(listFroidRes.length() - 1))) {
				listFroidRes = listFroidRes.substring(0, listFroidRes.length() - 1);
			}
		}
		if (listEclairage != null && !"".equals(listEclairage)) {
			if (FrontalKey.PLUS.equals(listEclairage.charAt(listEclairage.length() - 1))) {
				listEclairage = listEclairage.substring(0, listEclairage.length() - 1);
			}
		}
		if (listBureau != null && !"".equals(listBureau)) {
			if (FrontalKey.PLUS.equals(listBureau.charAt(listBureau.length() - 1))) {
				listBureau = listBureau.substring(0, listBureau.length() - 1);
			}
		}
		if (listBoulangerie != null && !"".equals(listBoulangerie)) {
			if (FrontalKey.PLUS.equals(listBoulangerie.charAt(listBoulangerie.length() - 1))) {
				listBoulangerie = listBoulangerie.substring(0, listBoulangerie.length() - 1);
			}
		}
		if (listCvc != null && !"".equals(listCvc)) {
			if (FrontalKey.PLUS.equals(listCvc.charAt(listCvc.length() - 1))) {
				listCvc = listCvc.substring(0, listCvc.length() - 1);
			}
		}
		if (listTemperatureInt != null && !"".equals(listTemperatureInt)) {
			if (FrontalKey.PLUS.equals(listTemperatureInt.charAt(listTemperatureInt.length() - 1))) {
				listTemperatureInt = listTemperatureInt.substring(0, listTemperatureInt.length() - 1);
			}
		}
		if (listTemperatureOut != null && !"".equals(listTemperatureOut)) {
			if (FrontalKey.PLUS.equals(listTemperatureOut.charAt(listTemperatureOut.length() - 1))) {
				listTemperatureOut = listTemperatureOut.substring(0, listTemperatureOut.length() - 1);
			}
		}
		if (listDrive != null && !"".equals(listDrive)) {
			if (FrontalKey.PLUS.equals(listDrive.charAt(listDrive.length() - 1))) {
				listDrive = listDrive.substring(0, listDrive.length() - 1);
			}
		}
		if (listComputerElectric != null && !"".equals(listComputerElectric)) {
			if (FrontalKey.PLUS.equals(listComputerElectric.charAt(listComputerElectric.length() - 1))) {
				listComputerElectric = listComputerElectric.substring(0, listComputerElectric.length() - 1);
			}
		}
		if (listComputerGaz != null && !"".equals(listComputerGaz)) {
			if (FrontalKey.PLUS.equals(listComputerGaz.charAt(listComputerGaz.length() - 1))) {
				listComputerGaz = listComputerGaz.substring(0, listComputerGaz.length() - 1);
			}
		}
		if (listFranis != null && !"".equals(listFranis)) {
			if (FrontalKey.PLUS.equals(listFranis.charAt(listFranis.length() - 1))) {
				listFranis = listFranis.substring(0, listFranis.length() - 1);
			}
		}
		/*
		 * if (listEnergyPastYear != null && !"".equals(listEnergyPastYear)) {
		 * if
		 * (FrontalKey.PLUS.equals(listEnergyPastYear.charAt(listEnergyPastYear.
		 * length()-1))) { listEnergyPastYear =
		 * listEnergyPastYear.substring(0,listEnergyPastYear.length()-1); } }
		 */

		numConfig.setModuleNeg(listFroidNeg);
		numConfig.setModulePos(listFroidPos);
		numConfig.setModuleReg(listFroidRes);
		numConfig.setModuleEclairage(listEclairage);
		numConfig.setModuleBureau(listBureau);
		numConfig.setModuleBoulangerie(listBoulangerie);
		numConfig.setModuleCvc(listCvc);
		numConfig.setModuleTemperatureIn(listTemperatureInt);
		numConfig.setModuleTemperatureOut(listTemperatureOut);
		numConfig.setModuleDrive(listDrive);
		numConfig.setModuleElectric(listComputerElectric);
		numConfig.setModuleGaz(listComputerGaz);
		numConfig.setModuleTemperatureFranis(listFranis);

		numConfig.setLogo(configLab.getLogo());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setReportName(configLab.getReportName());
		if (configLab.getModuleEnergyPreviousYear() != null) {
			numConfig.setModuleEnergyPreviousYear(configLab.getModuleEnergyPreviousYear().toString());
		}

		numConfig.setDriveReportName(configLab.getDriveReportName());
		if (configLab.getModuleEnergyPreviousYear() != null) {
			numConfig.setModuleEnergyPreviousYear(configLab.getModuleEnergyPreviousYear().toString());
		}
		if (configLab.getModuleDrivePreviousYear() != null) {
			numConfig.setModuleDrivePreviousYear(configLab.getModuleDrivePreviousYear().toString());
		}
		return numConfig;
	}

	public NumConfigLab002 convertIdtoMuduleNumber(Bouygues configLab) {
		NumConfigLab002 numConfig = new NumConfigLab002();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		numConfig.setSiteName(configLab.getSiteName());

		String listGruesModule = "";
		String listPiedModule = "";
		String listCantonModule = "";

		GetCSMDataAction csm = new GetCSMDataAction();
		String listGruesModuleId = configLab.getGruesModuleId();
		String listPiedModuleId = configLab.getPiedModuleId();
		String listCantonModuleId = configLab.getCantonModuleId();
		if (listGruesModuleId != null && !listGruesModuleId.trim().equals("")) {
			String[] arrObj = listGruesModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					// listGruesModule +=
					// csm.getModuleNumberByID(Integer.parseInt(arrObj[i])) +
					// FrontalKey.PLUS;
					listGruesModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}

		if (listPiedModuleId != null && !listPiedModuleId.trim().equals("")) {
			String[] arrObj = listPiedModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listPiedModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listCantonModuleId != null && !listCantonModuleId.trim().equals("")) {
			String[] arrObj = listCantonModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listCantonModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listGruesModule != null && !"".equals(listGruesModule)) {
			if (FrontalKey.PLUS.equals(listGruesModule.charAt(listGruesModule.length() - 1))) {
				listGruesModule = listGruesModule.substring(0, listGruesModule.length() - 1);
			}
		}
		if (listPiedModule != null && !"".equals(listPiedModule)) {
			if (FrontalKey.PLUS.equals(listPiedModule.charAt(listPiedModule.length() - 1))) {
				listPiedModule = listPiedModule.substring(0, listPiedModule.length() - 1);
			}
		}
		if (listCantonModule != null && !"".equals(listCantonModule)) {
			if (FrontalKey.PLUS.equals(listCantonModule.charAt(listCantonModule.length() - 1))) {
				listCantonModule = listCantonModule.substring(0, listCantonModule.length() - 1);
			}
		}

		numConfig.setGruesModuleId(listGruesModule);
		numConfig.setPiedModuleId(listPiedModule);
		numConfig.setCantonModuleId(listCantonModule);
		if (configLab.getNumberGrues() != null) {
			numConfig.setNumberGrues(String.valueOf(configLab.getNumberGrues()));
		}
		if (configLab.getNumberPied() != null) {
			numConfig.setNumberPied(String.valueOf(configLab.getNumberPied()));
		}
		if (configLab.getNumberCanton() != null) {
			numConfig.setNumberCanton(String.valueOf(configLab.getNumberCanton()));
		}
		numConfig.setLogo(configLab.getLogo());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setStartDate(configLab.getStartDate());
		numConfig.setNumberPermit(configLab.getNumberPermit());
		numConfig.setReportName(configLab.getReportName());
		return numConfig;
	}

	public NumConfigLab004 convertIdtoMuduleNumber(ConfigLab004 configLab) {
		NumConfigLab004 numConfig = new NumConfigLab004();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		numConfig.setSiteName(configLab.getSiteName());
		numConfig.setLogo(configLab.getLogo());
		numConfig.setSiteInfo(configLab.getSiteInfo());
		numConfig.setUriIcon(configLab.getUriIcon());
		if (configLab.getCible() != null) {
			numConfig.setCible(String.valueOf(configLab.getCible()));
		}
		if (configLab.getUnitPrice() != null) {
			numConfig.setUnitPrice(String.valueOf(configLab.getUnitPrice()));
		}
		return numConfig;
	}

	public NumConfigLab005 convertIdtoMuduleNumber(ConfigLab005 configLab) {
		NumConfigLab005 numConfig = new NumConfigLab005();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		numConfig.setSiteName(configLab.getSiteName());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setReportName(configLab.getReportName());
		return numConfig;
	}

	public NumConfigLab006 convertIdtoMuduleNumber(ConfigLab006 configLab) {
		NumConfigLab006 numConfig = new NumConfigLab006();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		numConfig.setSiteName(configLab.getSiteName());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setLogo(configLab.getLogo());
		numConfig.setSummerStartDate(configLab.getSummerStartDate());
		numConfig.setWinterStartDate(configLab.getWinterStartDate());
		numConfig.setStartHourHC(configLab.getStartHourHC());
		numConfig.setStartHourHP(configLab.getStartHourHP());
		// if (configLab.getCommonPortionModuleId() != null) {
		// numConfig.setCommonPortionModuleId(configLab.getCommonPortionModuleId());
		// }
		// if (configLab.getEnergyConsumptionModuleId() != null) {
		// numConfig.setEnergyConsumptionModuleId(configLab.getEnergyConsumptionModuleId());
		// }
		GetCSMDataAction csm = new GetCSMDataAction();
		String listCommonPortionModuleId = configLab.getCommonPortionModuleId();
		String listEnergyConsumptionModuleId = configLab.getEnergyConsumptionModuleId();
		String listCommonPortion = "";
		String listEnergyComsumption = "";

		// if (listCommonPortionModuleId != null &&
		// !listCommonPortionModuleId.trim().equals("")) {
		// String[] arrObj = listCommonPortionModuleId.split(FrontalKey.COMMA);
		// for (int i = 0; i < arrObj.length; i++) {
		// if (!arrObj[i].equals("")) {
		// listCommonPortion +=
		// csm.getModuleNumberByID(Integer.parseInt(arrObj[i])) +
		// FrontalKey.COMMA;
		// }
		// }
		// }
		if (listEnergyConsumptionModuleId != null && !listEnergyConsumptionModuleId.trim().equals("")) {
			String[] arrObj = listEnergyConsumptionModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					// listEnergyComsumption +=
					// csm.getModuleNumberByID(Integer.parseInt(arrObj[i])) +
					// FrontalKey.COMMA;
					listEnergyComsumption += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listCommonPortionModuleId != null && !"".equals(listCommonPortionModuleId.trim())) {
			// numConfig.setCommonPortionModuleId(csm.getModuleNumberByID(Integer.parseInt(listCommonPortionModuleId)));
			String[] arrObj = listCommonPortionModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					// listEnergyComsumption +=
					// csm.getModuleNumberByID(Integer.parseInt(arrObj[i])) +
					// FrontalKey.COMMA;
					listCommonPortion += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listCommonPortion != null && !"".equals(listCommonPortion)) {
			if (FrontalKey.PLUS.equals(listCommonPortion.charAt(listCommonPortion.length() - 1))) {
				listCommonPortion = listCommonPortion.substring(0, listCommonPortion.length() - 1);
			}
		}
		if (listEnergyComsumption != null && !"".equals(listEnergyComsumption)) {
			if (FrontalKey.PLUS.equals(listEnergyComsumption.charAt(listEnergyComsumption.length() - 1))) {
				listEnergyComsumption = listEnergyComsumption.substring(0, listEnergyComsumption.length() - 1);
			}
		}
		numConfig.setEnergyConsumptionModuleId(listEnergyComsumption);
		numConfig.setCommonPortionModuleId(listCommonPortion);
		numConfig.setReportName(configLab.getReportName());
		return numConfig;
	}

	public NumConfigLab006Client convertIdtoModuleNumber(ConfigLab006Client client) {
		NumConfigLab006Client numConfig = new NumConfigLab006Client();
		numConfig.setId(client.getId());
		numConfig.setClientName(client.getClientName());
		numConfig.setReportClientName(client.getReportClientName());
		numConfig.setImgClientName(client.getImageClientName());
		String listModuleHCE = "";
		String listModuleHPE = "";
		String listModuleHCH = "";
		String listModuleHPH = "";

		GetCSMDataAction csm = new GetCSMDataAction();

		String listModuleIdHCE = client.getModuleIdHCE();
		String listModuleIdHPE = client.getModuleIdHPE();
		String listModuleIdHCH = client.getModuleIdHCH();
		String listModuleIdHPH = client.getModuleIdHPH();

		// if (listModuleIdHCE != null && !listModuleIdHCE.trim().equals("")) {
		// String[] arrObj = listModuleIdHCE.split(FrontalKey.COMMA);
		// for (int i = 0; i < arrObj.length; i++) {
		// if (!arrObj[i].equals("")) {
		// listModuleHCE += Utils.getModuleIndexRatio(arrObj[i])
		// +
		// csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
		// + FrontalKey.COMMA;
		// }
		// }
		// }
		// if (listModuleIdHPE != null && !listModuleIdHPE.trim().equals("")) {
		// String[] arrObj = listModuleIdHPE.split(FrontalKey.COMMA);
		// for (int i = 0; i < arrObj.length; i++) {
		// if (!arrObj[i].equals("")) {
		// listModuleHPE += Utils.getModuleIndexRatio(arrObj[i])
		// +
		// csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
		// + FrontalKey.COMMA;
		// }
		// }
		// }
		// if (listModuleIdHCH != null && !listModuleIdHCH.trim().equals("")) {
		// String[] arrObj = listModuleIdHCH.split(FrontalKey.COMMA);
		// for (int i = 0; i < arrObj.length; i++) {
		// if (!arrObj[i].equals("")) {
		// listModuleHCH += Utils.getModuleIndexRatio(arrObj[i])
		// +
		// csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
		// + FrontalKey.COMMA;
		// }
		// }
		// }
		// if (listModuleIdHPH != null && !listModuleIdHPH.trim().equals("")) {
		// String[] arrObj = listModuleIdHPH.split(FrontalKey.COMMA);
		// for (int i = 0; i < arrObj.length; i++) {
		// if (!arrObj[i].equals("")) {
		// listModuleHPH += Utils.getModuleIndexRatio(arrObj[i])
		// +
		// csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
		// + FrontalKey.COMMA;
		// }
		// }
		// }
		if (listModuleIdHCE != null && !listModuleIdHCE.trim().equals("")) {
			String[] arrObj = listModuleIdHCE.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listModuleHCE += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listModuleIdHPE != null && !listModuleIdHPE.trim().equals("")) {
			String[] arrObj = listModuleIdHPE.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listModuleHPE += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listModuleIdHCH != null && !listModuleIdHCH.trim().equals("")) {
			String[] arrObj = listModuleIdHCH.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listModuleHCH += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (listModuleIdHPH != null && !listModuleIdHPH.trim().equals("")) {
			String[] arrObj = listModuleIdHPH.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listModuleHPH += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}

		/*
		 * if (!"".equals(listModuleIdHCE) && listModuleIdHCE != null){
		 * numConfig.setListModuleHCE(csm.getModuleNumberByID(Integer.parseInt(
		 * listModuleIdHCE))); } if (!"".equals(listModuleIdHCH) &&
		 * listModuleIdHCH != null){
		 * numConfig.setListModuleHCH(csm.getModuleNumberByID(Integer.parseInt(
		 * listModuleIdHCH))); } if (!"".equals(listModuleIdHPE) &&
		 * listModuleIdHPE != null){
		 * numConfig.setListModuleHPE(csm.getModuleNumberByID(Integer.parseInt(
		 * listModuleIdHPE))); } if (!"".equals(listModuleIdHPH) &&
		 * listModuleIdHPH != null){
		 * numConfig.setListModuleHPH(csm.getModuleNumberByID(Integer.parseInt(
		 * listModuleIdHPH))); }
		 */
		if (listModuleHCE != null && !"".equals(listModuleHCE)) {
			if (FrontalKey.PLUS.equals(listModuleHCE.charAt(listModuleHCE.length() - 1))) {
				listModuleHCE = listModuleHCE.substring(0, listModuleHCE.length() - 1);
			}
		}
		if (listModuleHPE != null && !"".equals(listModuleHPE)) {
			if (FrontalKey.PLUS.equals(listModuleHPE.charAt(listModuleHPE.length() - 1))) {
				listModuleHPE = listModuleHPE.substring(0, listModuleHPE.length() - 1);
			}
		}
		if (listModuleHCH != null && !"".equals(listModuleHCH)) {
			if (FrontalKey.PLUS.equals(listModuleHCH.charAt(listModuleHCH.length() - 1))) {
				listModuleHCH = listModuleHCH.substring(0, listModuleHCH.length() - 1);
			}
		}
		if (listModuleHPH != null && !"".equals(listModuleHPH)) {
			if (FrontalKey.PLUS.equals(listModuleHPH.charAt(listModuleHPH.length() - 1))) {
				listModuleHPH = listModuleHPH.substring(0, listModuleHPH.length() - 1);
			}
		}
		numConfig.setListModuleHCE(listModuleHCE);
		numConfig.setListModuleHPE(listModuleHPE);
		numConfig.setListModuleHCH(listModuleHCH);
		numConfig.setListModuleHPH(listModuleHPH);

		return numConfig;
	}

	public NumConfigLab007 convertIdtoMuduleNumber(ConfigLab007 configLab) {
		NumConfigLab007 numConfig = new NumConfigLab007();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(configLab.getSiteId());
		numConfig.setSiteName(configLab.getSiteName());

		String listElecModule = "";
		String listGasModule = "";
		String listPylonesModule = "";
		String listWaterModule = "";

		GetCSMDataAction csm = new GetCSMDataAction();
		String totalElecModuleId = configLab.getTotalElecModuleId();
		String totalGasModuleId = configLab.getTotalGasModuleId();
		String totalPylonesModuleId = configLab.getTotalPylonesModuleId();
		String totalWaterModuleId = configLab.getTotalWaterModuleId();
		if (totalElecModuleId != null && !totalElecModuleId.trim().equals("")) {
			String[] arrObj = totalElecModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listElecModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (totalGasModuleId != null && !totalGasModuleId.trim().equals("")) {
			String[] arrObj = totalGasModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listGasModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (totalPylonesModuleId != null && !totalPylonesModuleId.trim().equals("")) {
			String[] arrObj = totalPylonesModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listPylonesModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		if (totalWaterModuleId != null && !totalWaterModuleId.trim().equals("")) {
			String[] arrObj = totalWaterModuleId.split(FrontalKey.PLUS_SPECIAL);
			for (int i = 0; i < arrObj.length; i++) {
				if (!arrObj[i].equals("")) {
					listWaterModule += Utils.getModuleIndexRatio(arrObj[i])
							+ csm.getModuleNumberByID(Integer.parseInt(Utils.getModuleNumberFromArray(arrObj[i])))
							+ FrontalKey.PLUS;
				}
			}
		}
		// Character character =
		// listElecModule.charAt(listElecModule.length()-1);
		if (FrontalKey.PLUS.equals(listElecModule.charAt(listElecModule.length() - 1))) {
			listElecModule = listElecModule.substring(0, listElecModule.length() - 1);
		}
		if (FrontalKey.PLUS.equals(listGasModule.charAt(listGasModule.length() - 1))) {
			listGasModule = listGasModule.substring(0, listGasModule.length() - 1);
		}
		if (FrontalKey.PLUS.equals(listPylonesModule.charAt(listPylonesModule.length() - 1))) {
			listPylonesModule = listPylonesModule.substring(0, listPylonesModule.length() - 1);
		}
		if (FrontalKey.PLUS.equals(listWaterModule.charAt(listWaterModule.length() - 1))) {
			listWaterModule = listWaterModule.substring(0, listWaterModule.length() - 1);
		}
		numConfig.setTotalElecModuleId(listElecModule);
		numConfig.setTotalGasModuleId(listGasModule);
		numConfig.setTotalPylonesModuleId(listPylonesModule);
		numConfig.setTotalWaterModuleId(listWaterModule);
		if (configLab.getUnitElec() != null) {
			numConfig.setUnitElec(String.valueOf(configLab.getUnitElec()));
		}
		if (configLab.getUnitGas() != null) {
			numConfig.setUnitGas(String.valueOf(configLab.getUnitGas()));
		}
		if (configLab.getUnitPylones() != null) {
			numConfig.setUnitPylones(String.valueOf(configLab.getUnitPylones()));
		}
		if (configLab.getUnitWater() != null) {
			numConfig.setUnitWater(String.valueOf(configLab.getUnitWater()));
		}
		numConfig.setLogo(configLab.getLogo());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setReportName(configLab.getReportName());
		return numConfig;
	}

	public NumConfigLab008 convertIdtoMuduleNumberChart(ConfigLab008V2 configLab) {
		NumConfigLab008 numConfig = new NumConfigLab008();

		numConfig.setId(configLab.getId());
		if (configLab.getSiteId() != null) {
			numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		}

		// Set Site Name
		numConfig.setSiteName(configLab.getSiteName());

		// Set AvgAirTempModuleId
		numConfig.setAvgAirTempModuleId(convertModuleIDToNumberModule(configLab.getAvgAirTempModuleId()));

		// Set
		numConfig.setAvgExtTempModuleId(convertModuleIDToNumberModule(configLab.getAvgExtTempModuleId()));

		numConfig.setChauffageModuleId(convertModuleIDToNumberModule(configLab.getChauffageModuleId()));

		numConfig.setEcsZoneBasse(convertModuleIDToNumberModule(configLab.getEcsZoneBasse()));

		numConfig.setEcsZoneHaute(convertModuleIDToNumberModule(configLab.getEcsZoneHaute()));

		// Set boilerModel
		if(configLab.getBoilerModel() != null){
			numConfig.setBoilerModel(configLab.getBoilerModel().trim());
		}

		// SET
		if (configLab.getBoilerYear() != null) {
			numConfig.setBoilerYear(String.valueOf(configLab.getBoilerYear()));
		}

		// SET
		if (configLab.getBoilerTheoryPower() != null) {
			numConfig.setBoilerTheoryPower(String.valueOf(configLab.getBoilerTheoryPower()));
		}

		// SET GasNaturalModuleId
		numConfig.setGasNaturalModuleId(convertModuleIDToNumberModule(configLab.getGasNaturalModuleId()));

		// SET ProductionModuleId
		numConfig.setProductionModuleId(convertModuleIDToNumberModule(configLab.getProductionModuleId()));

		// SET
		if (configLab.getCoeff1() != null) {
			numConfig.setCoeff1(String.valueOf(configLab.getCoeff1()));
		}
		if (configLab.getCoeff2() != null) {
			numConfig.setCoeff2(String.valueOf(configLab.getCoeff2()));
		}
		if (configLab.getCoeff3() != null) {
			numConfig.setCoeff3(String.valueOf(configLab.getCoeff3()));
		}
		if (configLab.getCoeff4() != null) {
			numConfig.setCoeff4(String.valueOf(configLab.getCoeff4()));
		}
		if (configLab.getCoeff5() != null) {
			numConfig.setCoeff5(String.valueOf(configLab.getCoeff5()));
		}
		if (configLab.getCoeff6() != null) {
			numConfig.setCoeff6(String.valueOf(configLab.getCoeff6()));
		}
		if (configLab.getModelPrecision() != null) {
			numConfig.setModelPrecision(String.valueOf(configLab.getModelPrecision()));
		}
		if (configLab.getCoeffRadnConvection() != null) {
			numConfig.setCoeffRadnConvection(String.valueOf(configLab.getCoeffRadnConvection()));
		}

		if (configLab.getFromDate() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			numConfig.setFromDate(dateFormat.format((configLab.getFromDate())));
		}

		String uriIcon = configLab.getUriIcon();
		if (uriIcon != null) {
			numConfig.setUriIcon(uriIcon.trim());
		}

		String logo = configLab.getLogo();
		if (logo != null) {
			numConfig.setLogo(logo.trim());
		}

		String reportName = configLab.getReportName();
		if (reportName != null) {
			numConfig.setReportName(reportName.trim());
		}
		return numConfig;
	}

	private String convertModuleIDToNumberModule(String moduleNumber) {
		GetCSMDataAction csm = new GetCSMDataAction();
		if (moduleNumber != null && !moduleNumber.trim().isEmpty()) {
			return csm.getCalculateModuleByID(moduleNumber);
		} else {
			return null;
		}
	}

	public NumConfigLab008V2 convertIdtoMuduleNumber(ConfigLab008V2 configLab) {
		NumConfigLab008V2 numConfig = new NumConfigLab008V2();
		numConfig.setId(configLab.getId());
		numConfig.setSiteId(String.valueOf(configLab.getSiteId()));
		numConfig.setSiteName(configLab.getSiteName());
		numConfig.setUriIcon(configLab.getUriIcon());
		numConfig.setLogo(configLab.getLogo());
		numConfig.setEnergyProvider(configLab.getEnergyProvider());
		numConfig.setSubscribedPower(configLab.getSubscribedPower());
		GetCSMDataAction csm = new GetCSMDataAction();
		if (configLab.getModuleOutsite() != null && !configLab.getModuleOutsite().isEmpty()) {
			numConfig.setModuleOutsite(csm.getCalculateModuleByID(configLab.getModuleOutsite()));
		}

		if (configLab.getTemperatureAmbianteLogementLimit() != null) {
			numConfig.setTemperatureAmbianteLogementLimit(configLab.getTemperatureAmbianteLogementLimit());
		}
		if (configLab.getTemperatureProductionChauffageLimit() != null) {
			numConfig.setTemperatureProductionChauffageLimit(configLab.getTemperatureProductionChauffageLimit());
		}
		if (configLab.getTemperatureProductionECSLimit() != null) {
			numConfig.setTemperatureProductionECSLimit(configLab.getTemperatureProductionECSLimit());
		}
		if (configLab.getTemperatureRecyclageECSLimit() != null) {
			numConfig.setTemperatureRecyclageECSLimit(configLab.getTemperatureRecyclageECSLimit());
		}
		numConfig.setUnitWater(configLab.getUnitWater());
		numConfig.setBoilerType(configLab.getBoilerType());
//		numConfig.setVentilation(csm.getCalculateModuleByID(configLab.getVentilation()));
		numConfig.setProductionENR(csm.getCalculateModuleByID(configLab.getProductionENR()));
		numConfig.setUsedHeatproduction(configLab.getUsedHeatproduction());
		numConfig.setUsedECSproduction(configLab.getUsedECSproduction());
		return numConfig;
	}

	public NumConfigLab008ECS convertIdtoModuleNumber(Lab008ECS ecs) {
		NumConfigLab008ECS numConfig = new NumConfigLab008ECS();
		numConfig.setId(ecs.getId());
		numConfig.setName(ecs.getName());
		String listModuleCSM = "";

		GetCSMDataAction csm = new GetCSMDataAction();

		String moduleCSM = ecs.getModuleCSM();
		if (moduleCSM != null && !moduleCSM.trim().equals("")) {
			listModuleCSM  = csm.getCalculateModuleByID(moduleCSM);
		}
		numConfig.setModuleNumberCSM(listModuleCSM);
		numConfig.setType(ecs.getType());
		return numConfig;
	}
	public NumConfigLab010IdexSite convertIdtoModuleNumber(IdexSite idexSite) {
		NumConfigLab010IdexSite numConfig = new NumConfigLab010IdexSite();
		numConfig.setIdexSiteId(String.valueOf(idexSite.getIdexSiteId()));
		numConfig.setLogo(idexSite.getLogo());
		return numConfig;
	}
	public NumConfigLab010IdexSupplier convertIdtoModuleNumber(IdexEnergySupplier idexEnergySupplier) {
		NumConfigLab010IdexSupplier numConfig = new NumConfigLab010IdexSupplier();
		numConfig.setIdexEnergySupplierId(String.valueOf(idexEnergySupplier.getIdexEnergySupplierId()));
		numConfig.setLogo(idexEnergySupplier.getLogo());
		return numConfig;
	}
	public NumconfigLab011Resident convertIdtoModuleNumber(CaloonResident caloonResident) {
		NumconfigLab011Resident numConfig = new NumconfigLab011Resident();
		numConfig.setAppartementNumber(caloonResident.getAppartementNumber());
		numConfig.setCaloonSyndicId(caloonResident.getCaloonSyndicId());
		numConfig.setChauffage(caloonResident.getChauffage());
		numConfig.setEauChaude(caloonResident.getEauChaude());
		numConfig.setEauFroide(caloonResident.getEauFroide());
		numConfig.setResidentId(caloonResident.getId());
		numConfig.setClientName(caloonResident.getClientName());
		numConfig.setLogements(caloonResident.getLogements());
		numConfig.setSuperficie(caloonResident.getSuperficie());
		numConfig.setUserId(caloonResident.getCaloonUserId());
		return numConfig;
	}
	public NumConfigLab011Range convertIdtoModuleNumber(CaloonConsommationRange caloonConsommationRange){
		NumConfigLab011Range numConfig = new NumConfigLab011Range();
		numConfig.setRangeId(caloonConsommationRange.getId());
		numConfig.setFromNumber(caloonConsommationRange.getFromNumber());
		numConfig.setToNumber(caloonConsommationRange.getToNumber());
		numConfig.setOrderBy(caloonConsommationRange.getOrderBy());
		return numConfig;
	}
}
