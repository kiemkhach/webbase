package com.ifi.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab008Bean;
import com.ifi.common.bean.Lab008BoilerInfo;
import com.ifi.common.bean.Lab008DetailBean;
import com.ifi.common.bean.Lab008ECSBean;
import com.ifi.common.bean.Lab008ECSPipeBean;
import com.ifi.common.bean.Lab008GrapPoint;
import com.ifi.common.bean.Lab008GraphBean;
import com.ifi.common.bean.Lab008HomeBean;
import com.ifi.common.bean.Lab008ProductBean;
import com.ifi.common.bean.Lab008ProductChartData;
import com.ifi.common.bean.Lab008ProductDimensionBean;
import com.ifi.common.bean.Lab008ProductDimensionData;
import com.ifi.common.bean.Lab008ProductECSBean;
import com.ifi.common.bean.Lab008ProductECSSummary;
import com.ifi.common.bean.Lab008ProductionMonitoringBean;
import com.ifi.common.bean.TemperaturePoint;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.util.CalculateUtils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.common.ws.GetDataAction;
import com.ifi.common.ws.GetIndusDataAction;
import com.ifi.common.ws.IndusHistoryData;
import com.ifi.lab.LabDAO.dao.ConfigLab008DAO;
import com.ifi.lab.LabDAO.dao.ConfigLab008V2DAO;
import com.ifi.lab.LabDAO.dao.Lab008SimulationDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab008;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Lab008ECS;
import com.ifi.lab.LabDAO.model.Lab008Simulation;
import com.ifi.lab.LabDAO.model.User;
import com.ifi.lab.LabDAO.model.UserLab;
import com.reports.util.Utils;

/**
 * Created by Kim Anh 20/04/2016
 */
@Controller
@RequestMapping("lab008V2")
public class Lab008RestV2 {

	@Autowired
	private ConfigLab008DAO lab008OldDAO;
	@Autowired
	private ConfigLab008V2DAO lab008DAO;
	@Autowired
	private UserLabDAO userLabDAO;
	@Autowired
	private LabDAO labDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private Lab008SimulationDAO lab008SimulationDAO;
	private static final Logger log = LoggerFactory.getLogger(Lab008RestV2.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);

	private static final Integer PRODUCTION_ENR_1 = -1;
	private static final Integer PRODUCTION_ENR_2 = -2;
	private static final Integer MODULE_OUTSITE_ENR_ID = -3;
	private static final String PRODUCTION_ENR_NAME_1 = "Production d'ENR for ECS";
	private static final String PRODUCTION_ENR_NAME_2 = "Production d'ENR for Heat";
	private static final String MODULE_OUTSITE_ENR_NAME = "Module outsite";

	@RequestMapping(value = "getLab008Home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008HomeBean getLab008Data(@RequestParam Integer siteId, @RequestParam String fromDate,
			@RequestParam String toDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		if (toDate == null || toDate.isEmpty()) {
			toDate = sdf.format(cal.getTime());
		}
		if (fromDate == null || fromDate.isEmpty()) {
			cal.add(Calendar.DATE, 0);
			fromDate = sdf.format(cal.getTime());
		}
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		List<Integer> typeLst = new ArrayList<Integer>();
		typeLst.add(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
		typeLst.add(FrontalKey.TYPE_CONSOMMATION_ECS);
		List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId, typeLst);
		// [Start]Get data from indus
		List<Integer> moduleConsommationLst = new ArrayList<Integer>();
		List<Integer> moduleTemperatureAllLst = new ArrayList<Integer>();
		moduleTemperatureAllLst.addAll(CalculateUtils.getIDFromStr(config.getModuleOutsite()));

		for (Lab008ECS lab008ecs : lab008ECSLst) {
			CalculateUtils.addModuleIDToList(lab008ecs.getModuleCSM(), moduleConsommationLst);
		}

		if (config.getProductionENR() != null && !config.getProductionENR().isEmpty()) {
			CalculateUtils.addModuleIDToList(config.getProductionENR(), moduleConsommationLst);
		}

		if (!FrontalKey.BOILER_TYPE_URBAN_HEAT.equals(config.getBoilerType())) {
			CalculateUtils.addModuleIDToList(config.getGasNaturalModuleId(), moduleConsommationLst);
			CalculateUtils.addModuleIDToList(config.getProductionModuleId(), moduleConsommationLst);
		}

		GetIndusDataAction indus = new GetIndusDataAction();
		Map<Integer, Map<String, TemperaturePoint>> mapTempartureHour = indus
				.getGroupPriosByTime(moduleTemperatureAllLst, fromDate, toDate, FrontalKey.TYPE_TIME_HOUR);
		Map<Integer, Map<String, Integer>> mapConsommation = indus.getGroupTramesByTime(moduleConsommationLst, fromDate,
				toDate, FrontalKey.TYPE_TIME_ALL);

		// [End]Get data from indus
		Lab008HomeBean labBean = new Lab008HomeBean();
		labBean.setSiteName(config.getSiteName());
		labBean.setEnergyProvider(config.getEnergyProvider());
		labBean.setSubscribedPower(config.getSubscribedPower());
		Integer moduleOutsite = null;
		if (config.getModuleOutsite() != null && !config.getModuleOutsite().isEmpty()) {
			moduleOutsite = GetIndusDataAction.getLastestTemperature(config.getModuleOutsite(), mapTempartureHour);
		}
		labBean.setModuleOutsite(moduleOutsite);
		Integer moduleHeating = 0;
		Integer moduleDeparture = 0;
		List<Lab008ECSPipeBean> ecsPipe = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> chauffage = new ArrayList<Lab008ECSPipeBean>();
		if (lab008ECSLst != null && lab008ECSLst.size() > 0) {
			for (Lab008ECS lab008ecs : lab008ECSLst) {
				String name = lab008ecs.getName();
				Lab008ECSPipeBean temp = new Lab008ECSPipeBean();
				Integer number = 0;
				if (name != null && !name.trim().isEmpty()) {
					temp.setName(name);
				}
				if (lab008ecs.getModuleCSM() != null && !lab008ecs.getModuleCSM().isEmpty()) {
					number = CalculateUtils.getValueFromStr(lab008ecs.getModuleCSM(), mapConsommation);
				}
				if (number != null) {
					temp.setNumber(number);
				} else {
					temp.setNumber(0);
				}
				if (lab008ecs.getType() != null) {
					switch (lab008ecs.getType()) {
					case FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE:
						moduleHeating += number;
						chauffage.add(temp);
						break;
					case FrontalKey.TYPE_CONSOMMATION_ECS:
						moduleDeparture += number;
						ecsPipe.add(temp);

					default:
						break;
					}
				}
			}
		}
		labBean.setModuleHeating(moduleHeating);
		labBean.setModuleDeparture(moduleDeparture);
		if (ecsPipe != null && ecsPipe.size() > 0) {
			labBean.setLab008ECSPipeLst(ecsPipe);
		} else {
			labBean.setLab008ECSPipeLst(null);
		}

		if (chauffage != null && chauffage.size() > 0) {
			labBean.setLab008ChauffageLst(chauffage);
		} else {
			labBean.setLab008ChauffageLst(null);
		}

		labBean.setBoilerType(config.getBoilerType());

		if (!FrontalKey.BOILER_TYPE_URBAN_HEAT.equals(config.getBoilerType())) {

			labBean.setBoilerModel(config.getBoilerModel());
			labBean.setBoilerYear(config.getBoilerYear());
			if (config.getBoilerTheoryPower() != null) {
				labBean.setBoilerTheoryPower(config.getBoilerTheoryPower().intValue());
			}

			// labBean.setCoeffRadnConvection(config.getCoeffRadnConvection());
			Double gasNatureConsumptionPCI = null;
			Double gasNatureConsumptionPCS = null;
			if (config.getGasNaturalModuleId() != null && !config.getGasNaturalModuleId().isEmpty()) {
				Integer gasNatureConsumption = CalculateUtils.getValueFromStr(config.getGasNaturalModuleId(),
						mapConsommation);
				if (gasNatureConsumption != null) {
					gasNatureConsumptionPCI = gasNatureConsumption * config.getCoeff1().doubleValue()
							* config.getCoeff2().doubleValue();
					gasNatureConsumptionPCS = gasNatureConsumption * config.getCoeff3().doubleValue()
							* config.getCoeff4().doubleValue();
				}
			}
			Double productionConsumption = null;
			if (config.getProductionModuleId() != null && !config.getProductionModuleId().isEmpty()) {
				Integer productionModule = CalculateUtils.getValueFromStr(config.getProductionModuleId(),
						mapConsommation);
				// Double productionModule =
				// getConsumptionByModule(config.getProductionModuleId(),
				// fromDate, toDate);
				if (productionModule != null) {
					productionConsumption = productionModule * config.getCoeff5() * config.getCoeff6();
				}
			}
			// LongND: change get by date = PCS * k : before 1985: k =
			// 2%;between
			// 1985 and 1994 : k 1%; after 1995: k = 0.5%
			// Start
			double k = 0;
			int year = Integer.parseInt(fromDate.substring(0, 4));
			if (year > 1995) {
				k = 0.005;
			} else if (year >= 1985) {
				k = 0.01;
			} else {
				k = 0.02;
			}

			Double lossByRadnConvection = null;
			if (gasNatureConsumptionPCS != null) {
				lossByRadnConvection = gasNatureConsumptionPCS * k;
			}
			// END

			Double lossBySmoke = null;
			if (lossByRadnConvection != null) {
				lossBySmoke = gasNatureConsumptionPCS - productionConsumption - lossByRadnConvection;
			}
			if (gasNatureConsumptionPCI != null) {
				labBean.setConsumptionGasNaturalPCI(gasNatureConsumptionPCI.intValue());
			}
			if (gasNatureConsumptionPCS != null) {
				labBean.setConsumptionGasNaturalPCS(gasNatureConsumptionPCS.intValue());
			}
			if (productionConsumption != null) {
				labBean.setConsumptionProduction(productionConsumption.intValue());
			}
			if (lossBySmoke != null) {
				labBean.setLossBySmoke(lossBySmoke.intValue());
			}
			if (lossByRadnConvection != null) {
				labBean.setLossByRadnConvection(lossByRadnConvection.intValue());
			}

		}

		// Get production ENR
		labBean.setUsedECSproduction(config.getUsedECSproduction());
		labBean.setUsedHeatproduction(config.getUsedHeatproduction());
		if (config.getProductionENR() != null && !config.getProductionENR().isEmpty()) {
			Integer number = CalculateUtils.getValueFromStr(config.getProductionENR(), mapConsommation);
			if (number != null) {
				labBean.setProductionENR(number);
			}
		}
		return labBean;
	}

	@RequestMapping(value = "getLab008Detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008DetailBean getLab008Detail(@RequestParam Integer siteId, @RequestParam String fromDate,
			@RequestParam String toDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		if (toDate == null || toDate.isEmpty()) {
			toDate = sdf.format(cal.getTime());
		}
		if (fromDate == null || fromDate.isEmpty()) {
			cal.add(Calendar.DATE, 0);
			fromDate = sdf.format(cal.getTime());
		}
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		List<Integer> typeLst = new ArrayList<Integer>();
		typeLst.add(FrontalKey.TYPE_WATER);
		typeLst.add(FrontalKey.TYPE_AMBIANTE_LOGEMENT);
		typeLst.add(FrontalKey.TYPE_PRODUCTION_CHAUFFAGE);
		typeLst.add(FrontalKey.TYPE_PRODUCTION_ECS);
		typeLst.add(FrontalKey.TYPE_RECYCLAGE);
		typeLst.add(FrontalKey.TYPE_VENTILATION);

		List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId, typeLst);

		Lab008DetailBean labBean = new Lab008DetailBean();
		labBean.setSiteName(config.getSiteName());
		if (config.getTemperatureAmbianteLogementLimit() != null) {
			labBean.setTemperatureAmbianteLogementLimit(config.getTemperatureAmbianteLogementLimit());
		} else {
			labBean.setTemperatureAmbianteLogementLimit(0);
		}
		if (config.getTemperatureProductionChauffageLimit() != null) {
			labBean.setTemperatureProductionChauffageLimit(config.getTemperatureProductionChauffageLimit());
		} else {
			labBean.setTemperatureProductionChauffageLimit(0);
		}
		if (config.getTemperatureProductionECSLimit() != null) {
			labBean.setTemperatureProductionECSLimit(config.getTemperatureProductionECSLimit());
		} else {
			labBean.setTemperatureProductionECSLimit(0);
		}
		if (config.getTemperatureRecyclageECSLimit() != null) {
			labBean.setTemperatureRecyclageECSLimit(config.getTemperatureRecyclageECSLimit());
		} else {
			labBean.setTemperatureRecyclageECSLimit(0);
		}

		// [Start]Get data from indus
		List<Integer> moduleConsommationLst = new ArrayList<Integer>();
		// CalculateUtils.addModuleIDToList(config.getVentilation(),
		// moduleConsommationLst);

		List<Integer> moduleTemperatureAllLst = new ArrayList<Integer>();
		List<Integer> moduleOutsiteTemperature = new ArrayList<Integer>();
		CalculateUtils.addModuleIDToList(config.getModuleOutsite(), moduleOutsiteTemperature);
		List<Integer> moduleWaterIDLst = new ArrayList<Integer>();

		for (Lab008ECS lab008ecs : lab008ECSLst) {
			if (lab008ecs.getType().equals(FrontalKey.TYPE_WATER)) {
				CalculateUtils.addModuleIDToList(lab008ecs.getModuleCSM(), moduleWaterIDLst);
			} else if (lab008ecs.getType().equals(FrontalKey.TYPE_VENTILATION)) {
				CalculateUtils.addModuleIDToList(lab008ecs.getModuleCSM(), moduleConsommationLst);
			} else {
				CalculateUtils.addModuleIDToList(lab008ecs.getModuleCSM(), moduleTemperatureAllLst);
			}
		}

		GetIndusDataAction indus = new GetIndusDataAction();
		Map<Integer, Map<String, TemperaturePoint>> mapTempartureAll = indus
				.getGroupPriosByTime(moduleOutsiteTemperature, fromDate, toDate, FrontalKey.TYPE_TIME_HOUR);
		Map<Integer, Map<String, TemperaturePoint>> mapTempartureHour = indus
				.getGroupPriosByTime(moduleTemperatureAllLst, fromDate, toDate, FrontalKey.TYPE_TIME_ALL);
		Map<Integer, Map<String, Integer>> mapConsommation = indus.getGroupTramesByTime(moduleConsommationLst, fromDate,
				toDate, FrontalKey.TYPE_TIME_ALL);
		Map<Integer, Map<String, Integer>> mapWater = indus.getPriosPulse(moduleWaterIDLst, fromDate, toDate,
				FrontalKey.TYPE_TIME_ALL);
		// [End]Get data from indus

		labBean.setIsVentilation(false);

		Integer moduleOutsite = null;
		if (config.getModuleOutsite() != null && !config.getModuleOutsite().isEmpty()) {
			moduleOutsite = GetIndusDataAction.getLastestTemperature(config.getModuleOutsite(), mapTempartureAll);
		}
		labBean.setModuleOutsite(moduleOutsite);

		List<Lab008ECSPipeBean> moduleAmbianteLogementLst = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> moduleProductionChauffageLst = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> moduleProductionECSLst = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> moduleRecyclageECSLst = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> moduleWaterLst = new ArrayList<Lab008ECSPipeBean>();
		List<Lab008ECSPipeBean> ventilationLst = new ArrayList<Lab008ECSPipeBean>();
		GetCSMDataAction csm = new GetCSMDataAction();
		if (lab008ECSLst != null && lab008ECSLst.size() > 0) {
//			int sumValidation = 0;
			for (Lab008ECS lab008ecs : lab008ECSLst) {
				String name = lab008ecs.getName();
				Lab008ECSPipeBean temp = new Lab008ECSPipeBean();
				Integer value = null;
				if (lab008ecs.getModuleCSM() == null || lab008ecs.getModuleCSM().isEmpty()) {
					continue;
				}

				if (lab008ecs.getType().equals(FrontalKey.TYPE_WATER)) {
					value = CalculateUtils.getValueFromStr(lab008ecs.getModuleCSM(), mapWater);
					List<Integer> lst = CalculateUtils.getIDFromStr(lab008ecs.getModuleCSM());
					if (lst.size() > 0) {
						ModuleCSM module = csm.getModuleCSMByID(lst.get(0));
						if (config.getUnitWater() == null || config.getUnitWater().isEmpty()) {
							labBean.setUnit(module.getUnit());
						} else {
							labBean.setUnit(config.getUnitWater());
						}
						value = LabUtils.convertToCubicMeter(value, module.getUnit(), config.getUnitWater());
					}

				} 
				else if (lab008ecs.getType().equals(FrontalKey.TYPE_VENTILATION)) {
					value = CalculateUtils.getValueFromStr(lab008ecs.getModuleCSM(), mapConsommation);
//					if (value != null) {
//						sumValidation += value;
//					}
					// continue;
				}
				else {
					value = GetIndusDataAction.getLowestTemperature(lab008ecs.getModuleCSM(), mapTempartureHour);
				}
				if (name != null && !name.trim().isEmpty()) {
					temp.setName(name);
				}
				if (value != null) {
					temp.setNumber(value);
				} else {
					temp.setNumber(0);
				}
				if (lab008ecs.getType() != null) {
					switch (lab008ecs.getType()) {
					case FrontalKey.TYPE_AMBIANTE_LOGEMENT:
						moduleAmbianteLogementLst.add(temp);
						break;
					case FrontalKey.TYPE_PRODUCTION_CHAUFFAGE:
						moduleProductionChauffageLst.add(temp);
						break;
					case FrontalKey.TYPE_PRODUCTION_ECS:
						moduleProductionECSLst.add(temp);
						break;
					case FrontalKey.TYPE_RECYCLAGE:
						moduleRecyclageECSLst.add(temp);
						break;
					case FrontalKey.TYPE_WATER:
						moduleWaterLst.add(temp);
						break;
					case FrontalKey.TYPE_VENTILATION:
						ventilationLst.add(temp);
						break;
					default:
						break;
					}
				}
			}

//			if (sumValidation > 0) {
//				labBean.setIsVentilation(true);
//			}
			if(ventilationLst.size() >0){
				for(int i =0; i<ventilationLst.size(); i++){
					if(ventilationLst.get(i).getNumber() > 0){
						labBean.setIsVentilation(true);
					}
					else{
						labBean.setIsVentilation(false);
						break;
					}
				}
			}
		}

		if (moduleAmbianteLogementLst != null && moduleAmbianteLogementLst.size() > 0) {
			labBean.setModuleAmbianteLogementLst(moduleAmbianteLogementLst);
		}
		if (moduleProductionChauffageLst != null && moduleProductionChauffageLst.size() > 0) {
			labBean.setModuleProductionChauffageLst(moduleProductionChauffageLst);
		}
		if (moduleProductionECSLst != null && moduleProductionECSLst.size() > 0) {
			labBean.setModuleProductionECSLst(moduleProductionECSLst);
		}
		if (moduleRecyclageECSLst != null && moduleRecyclageECSLst.size() > 0) {
			labBean.setModuleRecyclageECSLst(moduleRecyclageECSLst);
		}
		if (ventilationLst != null && ventilationLst.size() > 0) {
			labBean.setVentilationLst(ventilationLst);
		}

		if (moduleAmbianteLogementLst != null && moduleAmbianteLogementLst.size() > 0) {
			Integer moduleAmbianteLogement = moduleAmbianteLogementLst.get(0).getNumber();
			if (moduleAmbianteLogement == null) {
				moduleAmbianteLogement = 0;
			}
			for (Lab008ECSPipeBean lab008ecsPipeBean : moduleAmbianteLogementLst) {
				Integer temp = lab008ecsPipeBean.getNumber();
				if (temp != null && moduleAmbianteLogement > temp) {
					moduleAmbianteLogement = temp;
				}
			}
			labBean.setModuleAmbianteLogement(moduleAmbianteLogement);
		}

		if (moduleProductionChauffageLst != null && moduleProductionChauffageLst.size() > 0) {
			Integer moduleProductionChauffage = moduleProductionChauffageLst.get(0).getNumber();
			if (moduleProductionChauffage == null) {
				moduleProductionChauffage = 0;
			}
			for (Lab008ECSPipeBean lab008ecsPipeBean : moduleProductionChauffageLst) {
				Integer temp = lab008ecsPipeBean.getNumber();
				if (temp != null && moduleProductionChauffage > temp) {
					moduleProductionChauffage = temp;
				}
			}
			labBean.setModuleProductionChauffage(moduleProductionChauffage);
		}

		if (moduleProductionECSLst != null && moduleProductionECSLst.size() > 0) {
			Integer moduleProductionECS = moduleProductionECSLst.get(0).getNumber();
			if (moduleProductionECS == null) {
				moduleProductionECS = 0;
			}
			for (Lab008ECSPipeBean lab008ecsPipeBean : moduleProductionECSLst) {
				Integer temp = lab008ecsPipeBean.getNumber();
				if (temp != null && moduleProductionECS > temp) {
					moduleProductionECS = temp;
				}
			}
			labBean.setModuleProductionECS(moduleProductionECS);
		}
		//
		if (moduleRecyclageECSLst != null && moduleRecyclageECSLst.size() > 0) {
			Integer moduleRecyclageECS = moduleRecyclageECSLst.get(0).getNumber();
			if (moduleRecyclageECS == null) {
				moduleRecyclageECS = 0;
			}
			for (Lab008ECSPipeBean lab008ecsPipeBean : moduleRecyclageECSLst) {
				Integer temp = lab008ecsPipeBean.getNumber();
				if (temp != null && moduleRecyclageECS > temp) {
					moduleRecyclageECS = temp;
				}
			}
			labBean.setModuleRecyclageECS(moduleRecyclageECS);
		}

		Integer moduleWaterTotal = null;
		if (moduleWaterLst != null && moduleWaterLst.size() > 0) {
			moduleWaterTotal = 0;
			for (Lab008ECSPipeBean lab008ecsPipeBean : moduleWaterLst) {
				Integer temp = lab008ecsPipeBean.getNumber();
				if (temp != null) {
					moduleWaterTotal += temp;
				}
			}
		}
		labBean.setModuleWaterLst(moduleWaterLst);
		labBean.setModuleWater(moduleWaterTotal);
		labBean.setBoilerType(config.getBoilerType());
		return labBean;
	}

	// private Integer getTemperatureByModule(String moduleStr, String
	// startDate, String endDate) {
	// if (moduleStr == null || moduleStr.isEmpty()) {
	// return null;
	// }
	// // String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
	// GetDataAction csm = new GetDataAction();
	// return csm.getLastTemperatureByTime(Integer.parseInt(moduleStr),
	// startDate, endDate, null, null);
	// }

	// private Integer getTemperatureByModuleLownest(String moduleStr, String
	// startDate, String endDate) {
	// if (moduleStr == null || moduleStr.isEmpty()) {
	// return null;
	// }
	// // String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
	// GetDataAction data = new GetDataAction();
	// return data.getTemperatureByModuleLownest(Integer.parseInt(moduleStr),
	// startDate, endDate, null, null);
	// }

	// /**
	// * moduleArr module id sepecrate by comma
	// *
	// * @param moduleStr
	// * @param fromDate
	// * @param toDate
	// * @return
	// */
	// private Double getConsumptionByModule(String moduleStr, String startDate,
	// String endDate) {
	// GetDataAction csm = new GetDataAction();
	// if (moduleStr == null || moduleStr.isEmpty()) {
	// return null;
	// }
	// String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
	// Double rs = 0d;
	// List<Integer> moduleLst = new ArrayList<Integer>();
	// for (String module : moduleArr) {
	// moduleLst.add(Integer.parseInt(Utils.getModuleNumberFromArray(module)));
	// }
	// Map<Integer, Integer> map = csm.getMapConsommationByTime(moduleLst,
	// startDate, endDate);
	// for (String module : moduleArr) {
	// Integer moduleId =
	// Integer.parseInt(Utils.getModuleNumberFromArray(module));
	// Integer consommation = map.get(moduleId);
	// if (consommation != null) {
	// rs += Utils.convertToKWh(module, consommation);
	// }
	// }
	// return rs;
	// }

	@RequestMapping(value = "getListSiteByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab008Bean> getListSiteByUser(@RequestParam String userName) {
		User user = userDAO.findByUserName(userName);
		List<Lab008Bean> listConfig = null;
		if (user != null) {
			Lab lab8 = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_008));
			if (lab8 != null) {
				List<UserLab> listUserLab = userLabDAO.getByUserAndLab(user.getId(), lab8.getId());
				if (listUserLab != null && !listUserLab.isEmpty()) {
					listConfig = new ArrayList<Lab008Bean>();
					for (UserLab item : listUserLab) {
						ConfigLab008V2 obj = lab008DAO.findBySiteId(item.getSiteId());
						if (obj != null) {
							Lab008Bean bean = new Lab008Bean();
							bean.setId(obj.getId());
							bean.setSiteName(obj.getSiteName());
							bean.setSiteId(obj.getSiteId());
							bean.setBoilerType(obj.getBoilerType());
							listConfig.add(bean);
						}
					}
				}
			}
		}
		return listConfig;
	}

	// @RequestMapping(value = "getTemperatureGrap", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Lab008GraphBean getTemperatureGrap(@RequestParam
	// Integer siteId,
	// @RequestParam String startDate, @RequestParam String endDate,
	// @RequestParam int type,
	// @RequestParam Integer typePicker, @RequestParam Integer season) throws
	// ParseException {
	//
	// ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
	// List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId,
	// type);
	// Lab008GraphBean bean = new Lab008GraphBean();
	// if (config == null || lab008ECSLst == null) {
	// return null;
	// }
	// Date startTime = LabUtils.convertDateByFormat(startDate + "01",
	// FrontalKey.DATE_FORMAT_DAY_3);
	// bean.setStartTime(startTime.getTime());
	// Date endTime = LabUtils.convertDateByFormat(endDate + "24",
	// FrontalKey.DATE_FORMAT_DAY_3);
	// bean.setEndTime(endTime.getTime());
	//
	// String time = getLabelDatePicker(startDate, endDate, typePicker, season);
	// bean.setLabelTime(time);
	//
	// List<String> nameChart = new ArrayList<String>();
	// List<List<Lab008GrapPoint>> dataChartLst = new
	// ArrayList<List<Lab008GrapPoint>>();
	// GetDataAction dataAction = new GetDataAction();
	// for (Lab008ECS lab008ECS : lab008ECSLst) {
	// nameChart.add(lab008ECS.getName());
	// List<Lab008GrapPoint> grapPointLst = new ArrayList<Lab008GrapPoint>();
	// if (lab008ECS.getModuleCSM() != null &&
	// !lab008ECS.getModuleCSM().isEmpty()) {
	// List<Object[]> lst =
	// dataAction.getAllTemperatureByTime(Integer.parseInt(lab008ECS.getModuleCSM()),
	// startDate, endDate);
	// for (Object[] obj : lst) {
	// Lab008GrapPoint point = new Lab008GrapPoint();
	// point.setTime((Long) obj[0]);
	// point.setValue((Integer) obj[1]);
	// grapPointLst.add(point);
	// }
	//
	// }
	// dataChartLst.add(grapPointLst);
	// }
	//
	// bean.setNameChart(nameChart);
	// bean.setDataChartLst(dataChartLst);
	//
	// int limit = 0;
	//
	// switch (type) {
	// case FrontalKey.TYPE_AMBIANTE_LOGEMENT:
	// limit = config.getTemperatureAmbianteLogementLimit();
	// break;
	// case FrontalKey.TYPE_PRODUCTION_CHAUFFAGE:
	// limit = config.getTemperatureProductionChauffageLimit();
	// break;
	// case FrontalKey.TYPE_PRODUCTION_ECS:
	// limit = config.getTemperatureProductionECSLimit();
	// break;
	// case FrontalKey.TYPE_RECYCLAGE:
	// limit = config.getTemperatureRecyclageECSLimit();
	// break;
	// }
	//
	// List<Lab008GrapPoint> limitChartLst = new ArrayList<Lab008GrapPoint>();
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(startTime);
	// while (cal.getTimeInMillis() <= endTime.getTime()) {
	// Lab008GrapPoint point = new Lab008GrapPoint();
	// point.setTime(cal.getTimeInMillis());
	// point.setValue(limit);
	// limitChartLst.add(point);
	// cal.add(Calendar.HOUR_OF_DAY, 1);
	// }
	//
	// bean.setLimitChartLst(limitChartLst);
	//
	// return bean;
	// }

	// @RequestMapping(value = "getConsommationGrap", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Lab008GraphBean getConsommationGrap(@RequestParam
	// Integer siteId,
	// @RequestParam String startDate, @RequestParam String endDate,
	// @RequestParam int type,
	// @RequestParam Integer typePicker, @RequestParam Integer season) throws
	// ParseException {
	// List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId,
	// type);
	// Lab008GraphBean bean = new Lab008GraphBean();
	// if (lab008ECSLst == null) {
	// return null;
	// }
	// Date startTime = LabUtils.convertDateByFormat(startDate + "01",
	// FrontalKey.DATE_FORMAT_DAY_3);
	// bean.setStartTime(startTime.getTime());
	// Date endTime = LabUtils.convertDateByFormat(endDate + "24",
	// FrontalKey.DATE_FORMAT_DAY_3);
	// bean.setEndTime(endTime.getTime());
	//
	// String time = getLabelDatePicker(startDate, endDate, typePicker, season);
	// bean.setLabelTime(time);
	//
	// List<String> nameChart = new ArrayList<String>();
	// List<List<Lab008GrapPoint>> dataChartLst = new
	// ArrayList<List<Lab008GrapPoint>>();
	// GetDataAction dataAction = new GetDataAction();
	// for (Lab008ECS lab008ECS : lab008ECSLst) {
	// nameChart.add(lab008ECS.getName());
	// List<Lab008GrapPoint> grapPointLst = new ArrayList<Lab008GrapPoint>();
	// if (lab008ECS.getModuleCSM() != null &&
	// !lab008ECS.getModuleCSM().isEmpty()) {
	//
	// List<Object[]> lst =
	// dataAction.getAllConsommationByTime(Integer.parseInt(lab008ECS.getModuleCSM()),
	// startDate, endDate, FrontalKey.TYPE_TIME_HOUR);
	// for (Object[] obj : lst) {
	// Lab008GrapPoint point = new Lab008GrapPoint();
	// point.setTime((Long) obj[0]);
	// point.setValue((Integer) obj[1]);
	// grapPointLst.add(point);
	// }
	//
	// }
	// dataChartLst.add(grapPointLst);
	// }
	//
	// bean.setNameChart(nameChart);
	// bean.setDataChartLst(dataChartLst);
	// return bean;
	// }

	// /**
	// * Get Data of graphic
	// *
	// * @param siteId
	// * @param startDate
	// * @param endDate
	// * @param type
	// * @param typePicker
	// * @param season
	// * @return
	// * @throws ParseException
	// */
	// @RequestMapping(value = "getDataGrap", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Lab008GraphBean getDataGrap(@RequestParam Integer
	// siteId, @RequestParam String startDate,
	// @RequestParam String endDate, @RequestParam int type) throws
	// ParseException {
	// Lab008GraphBean bean = getGrap(siteId, startDate, endDate, type,
	// FrontalKey.TYPE_TIME_HOUR);
	// if (bean == null) {
	// return null;
	// }
	// return bean;
	// }

	private Lab008GraphBean getGrap(Integer siteId, String startDate, String endDate, int type, int typeTime,List<Long> lstCategory) {

		Lab008GraphBean bean = new Lab008GraphBean();
		List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId, type);
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		if (type != FrontalKey.TYPE_MODULE_OUTSITE && lab008ECSLst == null) {
			return null;
		}
		List<String[]> nameChart = new ArrayList<String[]>();

		Date startTime = LabUtils.convertDateByFormat(startDate + "00", FrontalKey.DATE_FORMAT_DAY_3);
		bean.setStartTime(startTime.getTime());
		Date endTime = LabUtils.convertDateByFormat(endDate + "23", FrontalKey.DATE_FORMAT_DAY_3);
		bean.setEndTime(endTime.getTime());
		GetCSMDataAction csm = new GetCSMDataAction();
		List<Map<Long,Integer>> dataChartLst = new ArrayList<Map<Long,Integer>>();
		GetDataAction dataAction = new GetDataAction();
		if (type == FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE || type == FrontalKey.TYPE_CONSOMMATION_ECS
				|| type == FrontalKey.TYPE_VENTILATION) {

			for (Lab008ECS lab008ECS : lab008ECSLst) {
				nameChart.add(new String[] { String.valueOf(lab008ECS.getId()), lab008ECS.getName() });
				Map<Long,Integer> pointMap = new TreeMap<Long, Integer>();
				if (lab008ECS.getModuleCSM() != null && !lab008ECS.getModuleCSM().isEmpty()) {
					pointMap = dataAction.getAllConsommationByTime(lab008ECS.getModuleCSM(), startDate,
							endDate, typeTime);
				}
				dataChartLst.add(pointMap);
			}

			// IF CHAUFFAGE or ECS add new line for Production ENR
			if (type == FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE) {
				if (config.getUsedHeatproduction() != null && config.getUsedHeatproduction()) {
					nameChart.add(new String[] { String.valueOf(PRODUCTION_ENR_1), PRODUCTION_ENR_NAME_1 });
					Map<Long,Integer> pointMap = new TreeMap<Long, Integer>();
					if (config.getProductionENR() != null && !config.getProductionENR().isEmpty()) {
						pointMap = dataAction.getAllConsommationByTime(config.getProductionENR(), startDate,
								endDate, typeTime);
					}

					dataChartLst.add(pointMap);
				}
			} else if (type == FrontalKey.TYPE_CONSOMMATION_ECS) {
				if (config.getUsedECSproduction() != null && config.getUsedECSproduction()) {
					nameChart.add(new String[] { String.valueOf(PRODUCTION_ENR_2), PRODUCTION_ENR_NAME_2 });
					Map<Long,Integer> pointMap = new TreeMap<Long, Integer>();
					if (config.getProductionENR() != null && !config.getProductionENR().isEmpty()) {
						pointMap = dataAction.getAllConsommationByTime(config.getProductionENR(), startDate,
								endDate, typeTime);
					}
					dataChartLst.add(pointMap);
				}
			}

		} else if (type == FrontalKey.TYPE_WATER) {
			List<Integer> moduleIDLst = new ArrayList<Integer>();
			for (Lab008ECS lab008ECS : lab008ECSLst) {
				if (lab008ECS.getModuleCSM() != null && !lab008ECS.getModuleCSM().isEmpty()) {
					moduleIDLst.addAll(CalculateUtils.getIDFromStr(lab008ECS.getModuleCSM()));
				}
			}
			GetIndusDataAction indusAction = new GetIndusDataAction();
			Map<Integer, Map<String, Integer>> mapData = indusAction.getPriosPulse(moduleIDLst, startDate, endDate,
					typeTime);
			for (Lab008ECS lab008ECS : lab008ECSLst) {
				nameChart.add(new String[] { String.valueOf(lab008ECS.getId()), lab008ECS.getName() });
				Map<Long,Integer> pointMap = new TreeMap<Long, Integer>();
				if (lab008ECS.getModuleCSM() != null && !lab008ECS.getModuleCSM().isEmpty()) {
					Map<String, Integer> mapTime = null;
					if (!CalculateUtils.isContainCalculate(lab008ECS.getModuleCSM())) {
						mapTime = mapData.get(Integer.parseInt(lab008ECS.getModuleCSM()));
					} else {
						mapTime = new HashMap<String, Integer>();
						List<Integer> moduleIDInCalLst = CalculateUtils.getIDFromStr(lab008ECS.getModuleCSM());
						Map<String, Map<Integer, Integer>> mapTimeForModule = new HashMap<String, Map<Integer, Integer>>();
						Map<String, Integer> mapTimeTmp = null;
						for (Integer moduleId : moduleIDInCalLst) {
							if (mapData.containsKey(moduleId)) {
								mapTimeTmp = mapData.get(moduleId);
								for (Map.Entry<String, Integer> dataTime : mapTimeTmp.entrySet()) {
									Map<Integer, Integer> mapModuleConsommation = new HashMap<Integer, Integer>();
									if (mapTimeForModule.containsKey(dataTime.getKey())) {
										mapModuleConsommation = mapTimeForModule.get(dataTime.getKey());
									}
									mapModuleConsommation.put(moduleId, dataTime.getValue());
									mapTimeForModule.put(dataTime.getKey(), mapModuleConsommation);
								}
							}
						}
						for (Map.Entry<String, Map<Integer, Integer>> dataTime : mapTimeForModule.entrySet()) {
							mapTime.put(dataTime.getKey(),
									CalculateUtils.getConsommation(lab008ECS.getModuleCSM(), dataTime.getValue()));
						}
					}

					if (mapTime != null) {
						for (Map.Entry<String, Integer> entry : mapTime.entrySet()) {
							pointMap.put(LabUtils.convertDateByFormat(entry.getKey(), FrontalKey.DATE_FORMAT_DAY_3)
									.getTime(),entry.getValue());
						}
					}
				}
				dataChartLst.add(pointMap);
			}

			// Get unit of module water
			if (lab008ECSLst.size() > 0) {
				if (config.getUnitWater() == null || config.getUnitWater().isEmpty()) {
					ModuleCSM module = csm.getModuleCSMByID(Integer.parseInt(lab008ECSLst.get(0).getModuleCSM()));
					bean.setWaterUnit(module.getUnit());
				} else {
					bean.setWaterUnit(config.getUnitWater());
				}
			}
		} else {

			if (type == FrontalKey.TYPE_MODULE_OUTSITE) {
				lab008ECSLst = new ArrayList<Lab008ECS>();
				Lab008ECS lab008ECS = new Lab008ECS();
				lab008ECS.setId(MODULE_OUTSITE_ENR_ID);
				lab008ECS.setName(MODULE_OUTSITE_ENR_NAME);
				lab008ECS.setModuleCSM(config.getModuleOutsite());
				lab008ECSLst.add(lab008ECS);
			}
			for (Lab008ECS lab008ECS : lab008ECSLst) {
				if (typeTime != FrontalKey.TYPE_TIME_HOUR) {
					nameChart.add((new String[] { String.valueOf(lab008ECS.getId()) + "_min",
							lab008ECS.getName() + " min" }));
					nameChart.add((new String[] { String.valueOf(lab008ECS.getId()) + "_moy",
							lab008ECS.getName() + " moy" }));
					nameChart.add((new String[] { String.valueOf(lab008ECS.getId()) + "_max",
							lab008ECS.getName() + " max" }));
				} else {
					nameChart.add(new String[] { String.valueOf(lab008ECS.getId()), lab008ECS.getName() });
				}
				Map<Long,Integer> minGrapPointMap = new TreeMap<Long, Integer>();
				Map<Long,Integer> avgGrapPointMap = new TreeMap<Long,Integer>();
				Map<Long,Integer> maxGrapPointMap = new TreeMap<Long,Integer>();

				if (lab008ECS.getModuleCSM() != null && !lab008ECS.getModuleCSM().isEmpty()) {
					Map<String, TemperaturePoint> map = dataAction.getMapTemperatureByTime(
							Integer.parseInt(lab008ECS.getModuleCSM()), startDate, endDate, typeTime);

					for (Map.Entry<String, TemperaturePoint> entry : map.entrySet()) {
						TemperaturePoint point = entry.getValue();
						long key = LabUtils.convertDateByFormat(entry.getKey(), FrontalKey.DATE_FORMAT_DAY_3).getTime();
						// Min
						// if (point.getCountTemp() != null &&
						// point.getCountTemp() != 0) {
						Lab008GrapPoint pointMin = new Lab008GrapPoint();
						pointMin.setTime(key);
						pointMin.setValue(point.getMinTemp());
						minGrapPointMap.put(key,point.getMinTemp());

						if (typeTime != FrontalKey.TYPE_TIME_HOUR) {
							// Avg
							avgGrapPointMap.put(key,point.getAvgTemp());

							// Max
							maxGrapPointMap.put(key,point.getMaxTemp());
						}
						// }

					}
				}
				dataChartLst.add(minGrapPointMap);
				if (typeTime != FrontalKey.TYPE_TIME_HOUR) {
					dataChartLst.add(avgGrapPointMap);
					dataChartLst.add(maxGrapPointMap);
				}
			}

			if (type != FrontalKey.TYPE_MODULE_OUTSITE) {
				int limit = 0;

				switch (type) {
				case FrontalKey.TYPE_AMBIANTE_LOGEMENT:
					limit = config.getTemperatureAmbianteLogementLimit();
					break;
				case FrontalKey.TYPE_PRODUCTION_CHAUFFAGE:
					limit = config.getTemperatureProductionChauffageLimit();
					break;
				case FrontalKey.TYPE_PRODUCTION_ECS:
					limit = config.getTemperatureProductionECSLimit();
					break;
				case FrontalKey.TYPE_RECYCLAGE:
					limit = config.getTemperatureRecyclageECSLimit();
					break;
				}

				List<Integer> limitChartLst = new ArrayList<Integer>();
				Calendar cal = Calendar.getInstance();
				cal.setTime(startTime);
				while (cal.getTimeInMillis() < endTime.getTime()) {
					limitChartLst.add(limit);
					if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
						cal.add(Calendar.HOUR_OF_DAY, 1);
					} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
						cal.add(Calendar.DAY_OF_YEAR, 1);
					} else {
						cal.add(Calendar.MONTH, 1);
					}
				}
				limitChartLst.add(limit);

				bean.setLimitChartLst(limitChartLst);
			}
		}

		bean.setTypeTime(typeTime);
		bean.setType(type);
		bean.setNameChart(nameChart);
		
		List<List<Integer>> dataChartLstRS = new ArrayList<List<Integer>>();
		
		for (Map<Long, Integer> mapData : dataChartLst) {
			Map<Long, Integer> mapFullData = new TreeMap<Long, Integer>();
			for (Long time : lstCategory) {
				if (mapData.containsKey(time)) {
					mapFullData.put(time, mapData.get(time));
				} else {
					mapFullData.put(time, null);
				}
			}
			List<Integer> dataLst = new ArrayList<Integer>();
			for (Map.Entry<Long, Integer> entry : mapFullData.entrySet()) {
				dataLst.add(entry.getValue());
			}
			dataChartLstRS.add(dataLst);
		}
			
		bean.setDataChartLst(dataChartLstRS);

		return bean;
	}

	private Map<Integer, List<Lab008ECSBean>> getMapECSName(Integer siteId) {

		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		List<Lab008ECS> lab008ESClist = lab008DAO.getAllECSBySiteId(siteId);
		Map<Integer, List<Lab008ECSBean>> map = new HashMap<Integer, List<Lab008ECSBean>>();
		if (lab008ESClist != null) {
			for (int i = 0; i < lab008ESClist.size(); i++) {
				Lab008ECS lab008ECS = lab008ESClist.get(i);
				List<Lab008ECSBean> nameLst = null;
				if (map.containsKey(lab008ECS.getType())) {
					nameLst = map.get(lab008ECS.getType());
				} else {
					nameLst = new ArrayList<Lab008ECSBean>();
				}
				Lab008ECSBean bean = new Lab008ECSBean();
				bean.setId(lab008ECS.getId());
				bean.setName(lab008ECS.getName());
				bean.setType(lab008ECS.getType());
				nameLst.add(bean);
				map.put(lab008ECS.getType(), nameLst);
			}
		}

		if (config.getUsedHeatproduction() != null) {
			List<Lab008ECSBean> nameLst = map.get(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
			if (nameLst == null) {
				nameLst = new ArrayList<Lab008ECSBean>();
			}
			Lab008ECSBean bean = new Lab008ECSBean();
			bean.setId(PRODUCTION_ENR_1);
			bean.setName(PRODUCTION_ENR_NAME_1);
			bean.setType(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
			nameLst.add(bean);
			map.put(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE, nameLst);
		}
		if (config.getUsedECSproduction() != null) {
			List<Lab008ECSBean> nameLst = map.get(FrontalKey.TYPE_CONSOMMATION_ECS);
			if (nameLst == null) {
				nameLst = new ArrayList<Lab008ECSBean>();
			}
			Lab008ECSBean bean = new Lab008ECSBean();
			bean.setId(PRODUCTION_ENR_2);
			bean.setName(PRODUCTION_ENR_NAME_2);
			bean.setType(FrontalKey.TYPE_CONSOMMATION_ECS);
			nameLst.add(bean);
			map.put(FrontalKey.TYPE_CONSOMMATION_ECS, nameLst);
		}
		// Add module outsite
		List<Lab008ECSBean> nameLst = new ArrayList<Lab008ECSBean>();
		Lab008ECSBean bean = new Lab008ECSBean();
		bean.setId(MODULE_OUTSITE_ENR_ID);
		bean.setName(MODULE_OUTSITE_ENR_NAME);
		bean.setType(FrontalKey.TYPE_MODULE_OUTSITE);
		nameLst.add(bean);
		map.put(FrontalKey.TYPE_MODULE_OUTSITE, nameLst);

		return map;
	}

	@RequestMapping(value = "getAllNameGrap", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Map<Integer, List<Lab008ECSBean>> getAllNameGrap(@RequestParam Integer siteId)
			throws ParseException {
		Map<Integer, List<Lab008ECSBean>> map = this.getMapECSName(siteId);
		return map;
	}

	private List<Integer> getTypeLst(String typeLst) {
		String[] arr = typeLst.split(FrontalKey.COMMA);
		List<Integer> rsLst = new ArrayList<Integer>();
		for (String tmp : arr) {
			rsLst.add(Integer.parseInt(tmp));
		}
		return rsLst;
	}

	@RequestMapping(value = "getAllDataGrap", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab008GraphBean> getAllDataGrap(@RequestParam Integer siteId,
			@RequestParam String startDate, @RequestParam String endDate, @RequestParam String typeLst)
			throws ParseException {
		Date fromDate = LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_FORMAT_DAY_1);
		Date toDate = LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_FORMAT_DAY_1);
		int typeTime = LabUtils.getTypeTimeByDate(fromDate, toDate);
		List<Long> lstCategory = LabUtils.getLstCategoryByType(fromDate, toDate, typeTime);
		List<Lab008GraphBean> lst = new ArrayList<Lab008GraphBean>();

		List<Integer> typel = getTypeLst(typeLst);

		for (Integer type : typel) {
			Lab008GraphBean bean = getGrap(siteId, startDate, endDate, type, typeTime,lstCategory);
			if (bean != null) {
				bean.setLstCategory(lstCategory);
				lst.add(bean);
			}
		}
		return lst;
	}

	// @RequestMapping(value = "getEcsListNotDisplay", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody List<Integer> getEcsListNotDisplay(@RequestParam
	// Integer siteId) throws ParseException {
	// List<Lab008ECS> lab008ECSLst = lab008DAO.getAllECSBySiteId(siteId);
	// List<Integer> lst = new ArrayList<Integer>();
	// for (int i = 1; i <= 7; i++) {
	// lst.add(i);
	// }
	// for (Lab008ECS ecs : lab008ECSLst) {
	// if (ecs.getType() != null && ecs.getModuleCSM() != null &&
	// !ecs.getModuleCSM().isEmpty()
	// && lst.contains(ecs.getType())) {
	// lst.remove(ecs.getType());
	// }
	// }
	//
	// if (lst.contains(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE) &&
	// lst.contains(FrontalKey.TYPE_CONSOMMATION_ECS)) {
	// lst.add(FrontalKey.TYPE_CONSOMMATION_PARENT);
	// }
	//
	// if (lst.contains(FrontalKey.TYPE_AMBIANTE_LOGEMENT) &&
	// lst.contains(FrontalKey.TYPE_PRODUCTION_CHAUFFAGE)
	// && lst.contains(FrontalKey.TYPE_PRODUCTION_ECS) &&
	// lst.contains(FrontalKey.TYPE_RECYCLAGE)) {
	// lst.add(FrontalKey.TYPE_TEMPERATURE_PARENT);
	// }
	//
	// if (lst.contains(FrontalKey.TYPE_WATER)) {
	// lst.add(FrontalKey.TYPE_WATER_PARENT);
	// }
	//
	// if (lst.contains(FrontalKey.TYPE_CONSOMMATION_PARENT) &&
	// lst.contains(FrontalKey.TYPE_TEMPERATURE_PARENT)
	// && lst.contains(FrontalKey.TYPE_WATER_PARENT)) {
	// lst.add(FrontalKey.TYPE_ALL);
	// }
	// return lst;
	// }

	/**
	 * Get data fromDate -> toDate. get Data from CSM by month.
	 * 
	 * @param siteId
	 * @param fromDate
	 *            yyyyMM
	 * @param toDate
	 *            yyyyMM
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "getProductionECS", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008ProductECSBean getProductionECS(@RequestParam Integer siteId,
			@RequestParam String fromTime, @RequestParam String toTime, @RequestParam String actionName)
			throws ParseException {

		Date toDay = LabUtils.getCurrentDay();
		Date firstDay = LabUtils.getfirstDayOfCurrentMonth();

		Date fromDate = null;
		if (fromTime == null || fromTime.isEmpty()) {
			fromDate = firstDay;
		} else {
			fromDate = LabUtils.convertDateByFormat(fromTime, FrontalKey.DATE_SLASH_FORMAT);
		}

		Date toDate = null;
		if (toTime == null || toTime.isEmpty()) {
			toDate = toDay;
		} else {
			toDate = LabUtils.convertDateByFormat(toTime, FrontalKey.DATE_SLASH_FORMAT);
		}

		if (fromDate == null) {
			return null;
		}

		if (toDate == null) {
			return null;
		}

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
				fromDate = firstDay;
				toDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();
						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
				fromDate = firstDay;
				toDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				fromDate = LabUtils.getPreviousMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				fromDate = LabUtils.getNextMonth(fromDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				toDate = LabUtils.getPreviousMonth(toDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				toDate = LabUtils.getNextMonth(toDate);
			}
		}

		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		Lab008ProductECSBean bean = new Lab008ProductECSBean();
		bean.setFromDateStr(LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());

		List<Integer> lstType = new ArrayList<Integer>();
		lstType.add(FrontalKey.TYPE_CONSOMMATION_ECS);
		lstType.add(FrontalKey.TYPE_WATER);
		List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId, lstType);
		if (lab008ECSLst == null) {
			return bean;
		}
		int lab008ECSLstSize = lab008ECSLst.size();
		List<Integer> moduleLst = new ArrayList<Integer>();

		for (int i = 0; i < lab008ECSLst.size(); i++) {
			CalculateUtils.addModuleIDToList(lab008ECSLst.get(i).getModuleCSM(), moduleLst);
		}

		List<Map<Long,Double>> lst = new ArrayList<Map<Long,Double>>();

		int typeTime = LabUtils.getTypeTimeByDate(fromDate, toDate);
		List<Long> lstCategory = LabUtils.getLstCategoryByType(fromDate, toDate, typeTime);
		bean.setLstCategory(lstCategory);
		GetDataAction action = new GetDataAction();
		GetCSMDataAction csm = new GetCSMDataAction();
		List<Integer[]> listStartYear = LabUtils.getListDisplayYearByIndex(fromDate, toDate,typeTime);
		List<Integer[]> listDisplayYear = LabUtils.getListIndexYear(fromDate, toDate,typeTime);
		Map<String, Integer> mapWater = new TreeMap<String, Integer>();
		Map<Integer, Map<String, Integer>> mapConsommation = new HashMap<Integer, Map<String, Integer>>();
		List<Object[]> nameChart = new ArrayList<Object[]>();
		for (int i = 0; i < lab008ECSLstSize; i++) {
			Lab008ECS ecs = lab008ECSLst.get(i);
			String moduleStr = ecs.getModuleCSM();
			boolean isCalculate = CalculateUtils.isContainCalculate(moduleStr);

			Integer moduleId = null;
			ModuleCSM module = null;
			if (!isCalculate) {
				moduleId = Integer.parseInt(moduleStr);
				module = csm.getModuleCSMByID(moduleId);
			}

			Map<String, Integer> tmp = null;
			if (ecs.getType() == FrontalKey.TYPE_WATER) {
				tmp = action.getMapTimeOfPulse(lab008ECSLst.get(i).getModuleCSM(),
						LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_FORMAT_DAY_1),
						LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_FORMAT_DAY_1), typeTime);
			} else {
				tmp = action.getMapTimeOfConsommation(lab008ECSLst.get(i).getModuleCSM(),
						LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_FORMAT_DAY_1),
						LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_FORMAT_DAY_1), typeTime);
			}
			if (ecs.getType() == FrontalKey.TYPE_WATER) {
				for (Map.Entry<String, Integer> entry : tmp.entrySet()) {
					Integer value = null;
					if (isCalculate) {
						value = entry.getValue();
					} else {
						value = LabUtils.convertToCubicMeter(entry.getValue(), module.getUnit(), config.getUnitWater());
					}
					LabUtils.putMapSum(mapWater, entry.getKey(), value);
				}
			}
			if (ecs.getType() == FrontalKey.TYPE_CONSOMMATION_ECS) {
				Integer ecsId = ecs.getId();
				String nameEcs = ecs.getName();
				Object[] nameChartTmp = { ecsId, nameEcs };
				nameChart.add(nameChartTmp);

				for (Map.Entry<String, Integer> entry : tmp.entrySet()) {
					Integer value = null;
					if (isCalculate) {
						value = entry.getValue();
					} else {
						value = LabUtils.convertToKWH(entry.getValue(), module.getUnit());
					}
					LabUtils.putValueToMapMap(mapConsommation, ecs.getId(), entry.getKey(), value);
				}
			}
		}

		Set<Integer> keysConsommation = mapConsommation.keySet();
		for (Iterator<Integer> i = keysConsommation.iterator(); i.hasNext();) {

			Map<Long,Double> arrTmp = new TreeMap<Long, Double>();
			Integer keyTmp = (Integer) i.next();
			Map<String, Integer> item = mapConsommation.get(keyTmp);
			Set<String> keyItem = item.keySet();
			Map<Long,Double> dataItem = null;
			for (Iterator<String> j = keyItem.iterator(); j.hasNext();) {
				dataItem = new TreeMap<Long, Double>();
					String key = j.next();
					Integer value = 0;
					if(mapWater.containsKey(key)){
						value = mapWater.get(key);
					}
					Long time = LabUtils.convertDateByFormat(key, FrontalKey.DATE_FORMAT_DAY_3).getTime();
					if (value != 0) {
						Double divide = 0d;
						divide = (double) item.get(key) / value;
						dataItem.put(time, divide);
					} else {
						dataItem.put(time, 0d);
					}
				arrTmp.putAll(dataItem);

			}
			lst.add(arrTmp);
		}

		// }
		
		List<List<Double>> dataLst = new ArrayList<List<Double>>();
		for (Map<Long, Double> map : lst) {
			Map<Long, Double> mapData = new TreeMap<Long, Double>();
			for (long time : lstCategory) {
				if (map.containsKey(time)) {
					mapData.put(time, map.get(time));
				} else {
					mapData.put(time, 0d);
				}

			}
			List<Double> lstData = new ArrayList<Double>();
			for (Map.Entry<Long, Double> entry : mapData.entrySet()) {
				lstData.add(entry.getValue());
			}
			dataLst.add(lstData);
		}
		bean.setNameChart(nameChart);
		bean.setDataLst(dataLst);
		bean.setStartTime(fromDate.getTime());
		bean.setEndTime(toDate.getTime());
		bean.setTypeTime(typeTime);
		bean.setListStartYear(listStartYear);
		bean.setListDisplayYear(listDisplayYear);
		return bean;
	}

	@RequestMapping(value = "getProductionMonitoring", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008ProductionMonitoringBean getProductionMonitoring(@RequestParam Integer siteId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String actionName,
			@RequestParam String userName) throws ParseException {

		Date toDay = LabUtils.getCurrentDay();
		Date firstDay = LabUtils.getfirstDayOfCurrentMonth();

		Date startDate = null;
		if (fromDate == null || fromDate.isEmpty()) {
			startDate = firstDay;
		} else {
			startDate = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT);
		}
		Date endDate = null;
		if (toDate == null || toDate.isEmpty()) {
			endDate = toDay;
		} else {
			endDate = LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT);
		}
		if (startDate == null) {
			return null;
		}

		if (endDate == null) {
			return null;
		}

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
				startDate = firstDay;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();
						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
				startDate = firstDay;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				startDate = LabUtils.getPreviousMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				startDate = LabUtils.getNextMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				endDate = LabUtils.getPreviousMonth(endDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				endDate = LabUtils.getNextMonth(endDate);
			}
		}

		String fromDateParam = LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_FORMAT_DAY_1);
		String toDateParam = LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_FORMAT_DAY_1);

		int sizeData = LabUtils.different2Date(fromDateParam, toDateParam, FrontalKey.DATE_FORMAT_DAY_1) * 24;
		Lab008ProductionMonitoringBean bean = new Lab008ProductionMonitoringBean();
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		if (config == null) {
			log.error("Not found siteId: " + siteId);
			return null;
		}
		if (config.getSubscribedPower() == null) {
			log.error("Not Config SubscribedPower of Site - siteId = " + siteId);
			return null;
		}
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());
		bean.setFromDateStr(LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_SLASH_FORMAT));
		List<Integer> lstType = new ArrayList<Integer>();
		lstType.add(FrontalKey.TYPE_CONSOMMATION_ECS);
		lstType.add(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
		List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(siteId, lstType);
		if (lab008ECSLst == null) {
			return bean;
		}
		List<Integer> moduleIdLst = new ArrayList<Integer>();
		for (Lab008ECS ecs : lab008ECSLst) {
			moduleIdLst.addAll(CalculateUtils.getIDFromStr(ecs.getModuleCSM()));
		}

		GetDataAction action = new GetDataAction();
		Map<String, Map<Integer, Integer>> mapALLData = action.getAllModuleByTime(moduleIdLst, fromDateParam,
				toDateParam, FrontalKey.TYPE_TIME_HOUR);

		List<List<Integer[]>> consommationLst = new ArrayList<List<Integer[]>>();
		List<String> nameLst = new ArrayList<String>();
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		List<Lab008ProductECSSummary> dataTableLst = new ArrayList<Lab008ProductECSSummary>();
		for (Lab008ECS ecs : lab008ECSLst) {
			nameLst.add(ecs.getName());
			String moduleStr = ecs.getModuleCSM();
			if (moduleStr == null || moduleStr.isEmpty()) {
				continue;
			}
			List<Integer> consommationTmpLst = new ArrayList<Integer>();
			// List<Object[]> lst =
			// action.getAllConsommationByTime(ecs.getModuleCSM(),
			// fromDateParam, toDateParam,
			// FrontalKey.TYPE_TIME_HOUR);
			// Get all Consommation
			String lastHour = null;
			Integer lastHourConsommation = 0;
			Integer maximum = null;
			Integer minimum = null;
			Integer sum = 0;
			Lab008ProductECSSummary summary = new Lab008ProductECSSummary();
			if (mapALLData.size() > 0) {
				int size = 0;
				for (Map.Entry<String, Map<Integer, Integer>> entry : mapALLData.entrySet()) {
					String time = entry.getKey();
					Map<Integer, Integer> mapConsommation = entry.getValue();
					Integer consommation = CalculateUtils.getConsommation(moduleStr, mapConsommation);
					if (consommation != null && consommation > 0) {
						size++;
						consommationTmpLst.add(consommation);
						if (treeMap.containsKey(time)) {
							consommation += treeMap.get(time);
						}
						treeMap.put(time, consommation);
						sum += consommation;
						if (lastHour == null || time.compareTo(lastHour) > 0) {
							time = lastHour;
							lastHourConsommation = consommation;
						}
						if (minimum == null || consommation < minimum) {
							minimum = consommation;
						}
						if (maximum == null || consommation > maximum) {
							maximum = consommation;
						}
					}
				}
				if (size > 0) {
					summary.setAverage(getPercent(sum / size, config.getSubscribedPower()));
					if (minimum != null) {
						summary.setMinimum(getPercent(minimum, config.getSubscribedPower()));
					}
					if (maximum != null) {
						summary.setMaximum(getPercent(maximum, config.getSubscribedPower()));
					}
					summary.setLastHour(getPercent(lastHourConsommation, config.getSubscribedPower()));
				}
			}

			dataTableLst.add(summary);

			// Sort Consommation
			Collections.sort(consommationTmpLst);
			Collections.reverse(consommationTmpLst);

			// Add if size data not equal
			if (consommationTmpLst.size() < sizeData) {
				for (int i = consommationTmpLst.size(); i < sizeData; i++) {
					consommationTmpLst.add(0);
				}
			}
			// Calculate percent
			List<Integer[]> lstData = new ArrayList<Integer[]>();
			int consommationTmpLstSize = consommationTmpLst.size();
			for (int i = 0; i < consommationTmpLstSize; i++) {
				lstData.add(new Integer[] { getPercent((i + 1), (consommationTmpLstSize)), consommationTmpLst.get(i) });
			}
			consommationLst.add(lstData);
		}

		// treeMap.descendingMap();

		Lab008ProductECSSummary summaryTotal = new Lab008ProductECSSummary();
		List<Integer[]> totalLst = new ArrayList<Integer[]>();
		int count = 0;
		// Long lastHour = 0l;
		Integer lastHourConsommation = null;
		Integer maximum = null;
		Integer minimum = null;
		Integer sum = 0;
		List<Integer> totalMapLst = new ArrayList<Integer>(treeMap.values());
		if (totalMapLst.size() > 0) {
			lastHourConsommation = totalMapLst.get(totalMapLst.size() - 1);
			Collections.sort(totalMapLst);
			Collections.reverse(totalMapLst);
			for (Integer value : totalMapLst) {
				sum += value;

				if (minimum == null || value < minimum) {
					minimum = value;
				}
				if (maximum == null || value > maximum) {
					maximum = value;
				}
				count++;
				totalLst.add(new Integer[] { getPercent(count, sizeData), value });
			}
			if (count != 0) {
				summaryTotal.setAverage(getPercent(sum / count, config.getSubscribedPower()));
			}

			summaryTotal.setAverageAll(getAverageOfAllSite(userName, startDate, endDate, config.getId(),
					summaryTotal.getAverage(), null, null));
		}

		if (minimum != null) {
			summaryTotal.setMinimum(getPercent(minimum, config.getSubscribedPower()));
		}
		if (maximum != null) {
			summaryTotal.setMaximum(getPercent(maximum, config.getSubscribedPower()));
		}
		if (lastHourConsommation != null) {
			summaryTotal.setLastHour(getPercent(lastHourConsommation, config.getSubscribedPower()));
		}

		dataTableLst.add(summaryTotal);

		if (totalLst.size() < sizeData) {
			for (int i = totalLst.size(); i <= sizeData; i++) {
				totalLst.add(new Integer[] { getPercent(i, sizeData), 0 });
			}
		}

		bean.setSubscribedPower(config.getSubscribedPower());
		bean.setDataTableLst(dataTableLst);
		bean.setTotalLst(totalLst);
		bean.setConsommationLst(consommationLst);
		nameLst.add("Taux de charge");
		bean.setNameLst(nameLst);
		return bean;
	}

	ProductionData convertFromDataLocalToDataChart(ConfigLab008V2 config, Date startDate, Date endDate) {
		Date lastestDataDate = config.getFromDate();
		List<Double> listPCI = new ArrayList<Double>();
		List<Double> listPCS = new ArrayList<Double>();
		List<Double> listRateCharge = new ArrayList<Double>();
		double sumProduction = 0;
		double sumPCI = 0;
		double sumPCS = 0;
		Integer siteId = config.getSiteId();
		if (startDate.compareTo(lastestDataDate) <= 0) {
			// get data in lab_demo database
			List<Lab008BoilerInfo> listRawData = IndusHistoryData.getBoilerInfo(siteId, startDate, endDate);
			if (listRawData != null && listRawData.size() > 0) {
				// listData = new ArrayList<Lab008ProductChartData>();
				for (Lab008BoilerInfo item : listRawData) {
					// Lab008ProductChartData data = new
					// Lab008ProductChartData();
					double gasNaturePCI = item.getInputModuleValue() * config.getCoeff1().doubleValue()
							* config.getCoeff2().doubleValue();
					double gasNaturePCS = item.getInputModuleValue() * config.getCoeff3().doubleValue()
							* config.getCoeff4().doubleValue();
					double production = item.getOutputModuleValue() * config.getCoeff5() * config.getCoeff6();
					sumProduction += production;
					double rateChargeValue = 0;
					double instantPCIValue = 0;
					double instantPCSValue = 0;
					if (config.getBoilerTheoryPower() != null && config.getBoilerTheoryPower().doubleValue() != 0) {
						rateChargeValue = production / config.getBoilerTheoryPower().doubleValue();
						rateChargeValue = (rateChargeValue < 0.96) ? rateChargeValue * 100 : 0;
					}
					if (gasNaturePCI != 0) {
						instantPCIValue = production / gasNaturePCI;
						instantPCIValue = (instantPCIValue < 0.96) ? instantPCIValue * 100 : 0;
					}
					if (gasNaturePCS != 0) {
						instantPCSValue = production / gasNaturePCS;
						instantPCSValue = (instantPCSValue < 0.96) ? instantPCSValue * 100 : 0;
					}

					sumPCI += instantPCIValue;
					sumPCS += instantPCSValue;
					// add data raw to list data
					listPCI.add(instantPCIValue);
					listPCS.add(instantPCSValue);
					listRateCharge.add(rateChargeValue);
				}
			}
		} else {
			String startDay = sdf.format(startDate.getTime());
			String endDay = sdf.format(endDate.getTime());
			Map<String, Integer> chauffageMap = new HashMap<String, Integer>();
			Map<String, Integer> ecsZoneBasseMap = new HashMap<String, Integer>();
			Map<String, Integer> ecsZoneHauteMap = new HashMap<String, Integer>();
			if (config.getChauffageModuleId() != null && !config.getChauffageModuleId().isEmpty()) {
				chauffageMap = getConsumptionHourByModule(config.getChauffageModuleId(), startDay, endDay);
			}
			if (config.getEcsZoneBasse() != null && !config.getEcsZoneBasse().isEmpty()) {
				ecsZoneBasseMap = getConsumptionHourByModule(config.getEcsZoneBasse(), startDay, endDay);
			}
			if (config.getEcsZoneHaute() != null && !config.getEcsZoneHaute().isEmpty()) {
				ecsZoneHauteMap = getConsumptionHourByModule(config.getEcsZoneHaute(), startDay, endDay);
			}

			for (Map.Entry<String, Integer> entry : chauffageMap.entrySet()) {
				double production = entry.getValue();
				sumProduction += production;
				String key = entry.getKey();
				Double ecsZoneBasse = 0d;
				ecsZoneBasse = new Double(ecsZoneBasseMap.get(key));
				Double ecsZoneHaute = 0d;
				ecsZoneHaute = new Double(ecsZoneHauteMap.get(key));

				double rateChargeValue = 0;
				double instantPCIValue = 0;
				double instantPCSValue = 0;
				if (config.getBoilerTheoryPower() != null && config.getBoilerTheoryPower().doubleValue() != 0) {
					rateChargeValue = production / config.getBoilerTheoryPower().doubleValue();
					rateChargeValue = (rateChargeValue < 0.96) ? rateChargeValue * 100 : 0;
				}
				if (ecsZoneBasse != null && ecsZoneBasse != 0) {
					instantPCIValue = production / ecsZoneBasse;
					instantPCIValue = (instantPCIValue < 0.96) ? instantPCIValue * 100 : 0;
				}
				if (ecsZoneHaute != null && ecsZoneHaute != 0) {
					instantPCSValue = production / ecsZoneHaute;
					instantPCSValue = (instantPCSValue < 0.96) ? instantPCSValue * 100 : 0;
				}

				sumPCI += instantPCIValue;
				sumPCS += instantPCSValue;
				listPCI.add(instantPCIValue);
				listPCS.add(instantPCSValue);
				listRateCharge.add(rateChargeValue);
			}
		}

		ProductionData data = new ProductionData();
		data.setSumProduction(sumProduction);
		data.setListPCI(listPCI);
		data.setListPCS(listPCS);
		data.setListRateCharge(listRateCharge);
		data.setSumPCI(sumPCI);
		data.setSumPCS(sumPCS);
		return data;
	}

	private Map<String, Integer> getConsumptionHourByModule(String moduleStr, String fromDay, String toDay) {
		if (moduleStr == null || moduleStr.isEmpty()) {
			return null;
		}
		String[] moduleArr = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		GetDataAction csm = new GetDataAction();
		for (String moduleID : moduleArr) {
			Map<String, Integer> map = csm.getConsommationGroupTime(
					Integer.parseInt(Utils.getModuleNumberFromArray(moduleID)), fromDay, toDay,
					FrontalKey.TYPE_TIME_HOUR);
			if (map != null) {
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();
					Integer consommation = Utils.convertToKWh(moduleID, value).intValue();
					if (resultMap.containsKey(key)) {
						resultMap.put(key, resultMap.get(key) + consommation);
					} else {
						resultMap.put(key, consommation);
					}
				}
			}
		}
		return resultMap;
	}

	@RequestMapping(value = "callSite", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008Bean callSite(@RequestParam Integer siteId, @RequestParam String actionName) {

		List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
		ConfigLab008V2 config = null;
		if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {

			for (int i = 0; i < lst.size(); i++) {
				if (lst.get(i).getSiteId().equals(siteId)) {
					if (i > 0) {

						config = lst.get(i - 1);
					} else {
						config = lst.get(lst.size() - 1);
					}
					break;
				}
			}
		} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
			for (int i = 0; i < lst.size(); i++) {
				if (lst.get(i).getSiteId().equals(siteId)) {
					if (i < lst.size() - 1) {
						config = lst.get(i + 1);
					} else {
						config = lst.get(0);
					}
					break;
				}
			}
		}

		Lab008Bean bean = new Lab008Bean();
		bean.setSiteId(config.getSiteId());
		bean.setSiteName(config.getSiteName());
		bean.setBoilerType(config.getBoilerType());

		return bean;
	}

	@RequestMapping(value = "getProductionData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008ProductBean getProductionData(@RequestParam Integer siteId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String actionName,
			@RequestParam String userName) {

		log.info("getProductionData start");

		Date toDay = LabUtils.getCurrentDay();
		Date firstDate = IndusHistoryData.getFirstDayBoilerInfo(siteId);

		Date startDate = null;
		Date endDate = null;
		if (fromDate == null || fromDate.isEmpty()) {
			startDate = firstDate;
		} else {
			startDate = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT);
		}
		if (toDate == null || toDate.isEmpty()) {
			endDate = toDay;
		} else {
			endDate = LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT);
		}

		if (startDate == null) {
			return null;
		}
		if (endDate == null) {
			return null;
		}

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
				startDate = firstDate;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();
						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
				startDate = firstDate;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				startDate = LabUtils.getPreviousMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				startDate = LabUtils.getNextMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				endDate = LabUtils.getPreviousMonth(endDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				endDate = LabUtils.getNextMonth(endDate);
			}
		}

		List<Double> listRateCharge = null;
		List<Double> listPCI = null;
		List<Double> listPCS = null;
		double sumProduction = 0;
		Lab008ProductBean bean = null;
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		ProductionData productionData = convertFromDataLocalToDataChart(config, startDate, endDate);
		listRateCharge = productionData.getListRateCharge();
		listPCI = productionData.getListPCI();
		listPCS = productionData.getListPCS();
		sumProduction = productionData.getSumProduction();
		int sizeOfData = listPCI.size();
		double sumPCI = 0;
		double sumPCS = 0;

		bean = new Lab008ProductBean();
		bean.setBoilerType(config.getBoilerType());
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());
		bean.setFromDateStr(LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_SLASH_FORMAT));
		Lab008ProductChartData chartData = new Lab008ProductChartData();

		if (listPCI != null && listPCI.size() > 0) {
			sizeOfData = listPCI.size();
			// get last hour data
			bean.setLastHourPCI(listPCI.get(0).intValue());
			bean.setLastHourPCS(listPCS.get(0).intValue());
			bean.setLastHourRateCharge(listRateCharge.get(0).intValue());
			// sort data
			Collections.sort(listPCS);
			Collections.reverse(listPCS);
			Collections.sort(listPCI);
			Collections.reverse(listPCI);
			Collections.sort(listRateCharge);
			Collections.reverse(listRateCharge);

			// calculate percent by index
			List<double[]> listPCIValue = new ArrayList<double[]>();
			List<double[]> listPCSValue = new ArrayList<double[]>();
			List<double[]> listRateChargeValue = new ArrayList<double[]>();
			for (int i = 0; i < listPCI.size(); i++) {
				int percent = Math.round((i + 1) * 100 / sizeOfData);
				listPCIValue.add(new double[] { percent, listPCI.get(i) });
				listPCSValue.add(new double[] { percent, listPCS.get(i) });
				listRateChargeValue.add(new double[] { percent, listRateCharge.get(i) });
				sumPCI += listPCI.get(i);
				sumPCS += listPCS.get(i);
			} // end for calculate percent
			chartData.setListPCIValue(listPCIValue);
			chartData.setListPCSValue(listPCSValue);
			chartData.setListRateChargeValue(listRateChargeValue);
			bean.setAvgPCI((int) sumPCI / sizeOfData);
			bean.setAvgPCS((int) sumPCS / sizeOfData);
			bean.setAvgRateCharge((int) ((sumProduction * 100) / (config.getBoilerTheoryPower() * sizeOfData)));
			bean.setMaxPCI(listPCI.get(0).intValue());
			bean.setMaxPCS(listPCS.get(0).intValue());
			bean.setMaxRateCharge(listRateCharge.get(0).intValue());
			bean.setMinPCI(listPCI.get(sizeOfData - 1).intValue());
			bean.setMinPCS(listPCS.get(sizeOfData - 1).intValue());
			bean.setMinRateCharge(listRateCharge.get(sizeOfData - 1).intValue());
			bean.setDataCharts(chartData);
		}

		bean.setAvgRateChargeAll(getAverageOfAllSite(userName, startDate, endDate, config.getId(),
				bean.getAvgRateCharge(), bean.getAvgPCI(), bean.getAvgPCS()));
		bean.setAvgPCIAll(avgPCIAll);
		bean.setAvgPCSAll(avgPCSAll);
		return bean;
	}

	@RequestMapping(value = "getProductionDimension", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab008ProductDimensionBean getProductionDimension(@RequestParam Integer siteId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String actionName) {
		log.info("getProductionDimension start");
		Lab008ProductDimensionBean bean = new Lab008ProductDimensionBean();

		Date toDay = LabUtils.convertDateByFormat("30/04/2016", FrontalKey.DATE_SLASH_FORMAT);
		Date firstDay = LabUtils.convertDateByFormat("15/03/2016", FrontalKey.DATE_SLASH_FORMAT);
		Date startDate = null;

		if (fromDate == null || fromDate.isEmpty()) {
			startDate = firstDay;
		} else {
			startDate = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_SLASH_FORMAT);
		}
		Date endDate = null;
		if (toDate == null || toDate.isEmpty()) {
			endDate = toDay;
		} else {
			endDate = LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_SLASH_FORMAT);
		}
		if (startDate == null) {
			return null;
		}

		if (endDate == null) {
			return null;
		}

		if (siteId == null || siteId == 0) {
			List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
			if (lst.size() > 0) {
				siteId = lst.get(0).getSiteId();
			} else {
				return null;
			}
		}

		if (actionName != null && !actionName.isEmpty()) {
			if (actionName.equals(FrontalKey.ACTION_BACK_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i > 0) {
							siteId = lst.get(i - 1).getSiteId();
						} else {
							siteId = lst.get(lst.size() - 1).getSiteId();
						}
						break;
					}
				}
				startDate = firstDay;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_SITE)) {
				List<ConfigLab008V2> lst = lab008DAO.getAllConfig();
				for (int i = 0; i < lst.size(); i++) {
					if (lst.get(i).getSiteId().equals(siteId)) {
						if (i < lst.size() - 1) {
							siteId = lst.get(i + 1).getSiteId();

						} else {
							siteId = lst.get(0).getSiteId();
						}
						break;
					}
				}
				startDate = firstDay;
				endDate = toDay;
			} else if (actionName.equals(FrontalKey.ACTION_BACK_FROM)) {
				startDate = LabUtils.getPreviousMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_FROM)) {
				startDate = LabUtils.getNextMonth(startDate);
			} else if (actionName.equals(FrontalKey.ACTION_BACK_TO)) {
				endDate = LabUtils.getPreviousMonth(endDate);
			} else if (actionName.equals(FrontalKey.ACTION_NEXT_TO)) {
				endDate = LabUtils.getNextMonth(endDate);
			}
		}
		ConfigLab008V2 config = lab008DAO.findBySiteId(siteId);
		Integer subscribed_power = config.getSubscribedPower();
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());
		bean.setFromDateStr(LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setToDateStr(LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_SLASH_FORMAT));
		bean.setContractPower(subscribed_power);

		// List<Integer> typeLst = new ArrayList<Integer>();
		// typeLst.add(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
		// List<Lab008ECS> lab008ECSLst =
		// lab008DAO.findECSBySiteIdAndType(siteId,
		// FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
		List<Lab008ECS> lab008ECSAllLst = lab008DAO.findECSByType(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);

		String startDateP = LabUtils.convertDateByFormat(startDate, FrontalKey.DATE_FORMAT_DAY_1);
		String endDateP = LabUtils.convertDateByFormat(endDate, FrontalKey.DATE_FORMAT_DAY_1);

		GetDataAction dataAction = new GetDataAction();
		Map<String, TemperaturePoint> mapTemperature = dataAction.getMapTemperatureByTime(
				Integer.parseInt(config.getModuleOutsite()), startDateP, endDateP, FrontalKey.TYPE_TIME_HOUR);
		if (mapTemperature == null || mapTemperature.isEmpty()) {
			return bean;
		}

		Map<String, Integer> mapConsommationAll = new TreeMap<String, Integer>();
		Map<String, Integer> mapConsommationBySite = new TreeMap<String, Integer>();
		for (Lab008ECS ecs : lab008ECSAllLst) {
			if (ecs.getModuleCSM() != null && !ecs.getModuleCSM().isEmpty()) {
				Map<String, Integer> mapConsommation = dataAction.getMapTimeOfConsommation(ecs.getModuleCSM(),
						startDateP, endDateP, FrontalKey.TYPE_TIME_HOUR);
				LabUtils.putMapToMap(mapConsommationAll, mapConsommation);
				if (ecs.getConfigLab008V2ID() == config.getId()) {
					Map<String, Integer> mapConsommationSite = dataAction.getMapTimeOfConsommation(ecs.getModuleCSM(),
							startDateP, endDateP, FrontalKey.TYPE_TIME_HOUR);
					LabUtils.putMapToMap(mapConsommationBySite, mapConsommationSite);
				}
			}
		}
		// if (config.getUsedECSproduction() != null &&
		// config.getUsedECSproduction()) {
		// Map<String, Integer> mapConsommation =
		// dataAction.getMapTimeOfConsommation(config.getProductionENR(),
		// startDateP, endDateP, FrontalKey.TYPE_TIME_HOUR);
		// LabUtils.putMapToMap(mapConsommation, mapConsommationAll);
		// LabUtils.putMapToMap(mapConsommation, mapConsommationBySite);
		// }
		if(mapConsommationAll.isEmpty()){
			return bean;
		}
		Double maxAvgAll = 0d;
		Map<Integer, List<Integer>> mapDataAll = new TreeMap<Integer, List<Integer>>();
		for (Map.Entry<String, TemperaturePoint> entry : mapTemperature.entrySet()) {
			String keyTime = entry.getKey();
			TemperaturePoint temperaturePoint = entry.getValue();
			Integer temp = temperaturePoint.getAvgTemp();
			if (mapConsommationAll.containsKey(keyTime)) {
				Integer consomAll = mapConsommationAll.get(keyTime);
				List<Integer> lstConsommationAll = null;
				if (mapDataAll.containsKey(temp)) {
					lstConsommationAll = mapDataAll.get(temp);
				} else {
					lstConsommationAll = new ArrayList<Integer>();
				}
				if (consomAll != 0) {
					lstConsommationAll.add(consomAll);
				}
				mapDataAll.put(temp, lstConsommationAll);
			}
		}
		for (Map.Entry<Integer, List<Integer>> entry : mapDataAll.entrySet()) {
			List<Integer> lst = entry.getValue();
			if (!lst.isEmpty()) {
				Lab008ProductDimensionData data = new Lab008ProductDimensionData();
				data.setTemperature(entry.getKey());
				data.setPower(((double) LabUtils.sumAllOfList(lst)) / lst.size());
				if (data.getPower() > maxAvgAll) {
					maxAvgAll = data.getPower();
				}
			}
		}
		Integer averageAllSite = (int) (maxAvgAll / subscribed_power * 100);
		bean.setAverageAllSite(averageAllSite);
		
		if (mapConsommationBySite.isEmpty()) {
			return bean;
		}
		Map<Integer, List<Integer>> mapData = new TreeMap<Integer, List<Integer>>();
		for (Map.Entry<String, TemperaturePoint> entry : mapTemperature.entrySet()) {
			String keyTime = entry.getKey();
			TemperaturePoint temperaturePoint = entry.getValue();
			Integer temp = temperaturePoint.getAvgTemp();
			if (mapConsommationBySite.containsKey(keyTime)) {
				Integer consom = mapConsommationBySite.get(keyTime);
				List<Integer> lstConsommation = null;
				if (mapData.containsKey(temp)) {
					lstConsommation = mapData.get(temp);
				} else {
					lstConsommation = new ArrayList<Integer>();
				}
				if (consom != 0) {
					lstConsommation.add(consom);
				}
				mapData.put(temp, lstConsommation);
			}
		}

		List<Lab008ProductDimensionData> listDimensionDataLst = new ArrayList<Lab008ProductDimensionData>();
		Double maxAvg = 0d;
		for (Map.Entry<Integer, List<Integer>> entry : mapData.entrySet()) {
			List<Integer> lst = entry.getValue();
			if (!lst.isEmpty()) {
				Lab008ProductDimensionData data = new Lab008ProductDimensionData();
				data.setTemperature(entry.getKey());
				data.setPower(((double) LabUtils.sumAllOfList(lst)) / lst.size());
				if (data.getPower() > maxAvg) {
					maxAvg = data.getPower();
				}
				listDimensionDataLst.add(data);
			}
		}
		Integer ratio = (int) (maxAvg / subscribed_power * 100);
		
		List<Object> listDimensionValue = new ArrayList<Object>();
		List<Object> lineLst = new ArrayList<Object>();
		if (listDimensionDataLst.isEmpty()) {
			return bean;
		}
		double[][] data = new double[listDimensionDataLst.size()][2];
		for (int i = 0; i < listDimensionDataLst.size(); i++) {
			Lab008ProductDimensionData tmp = listDimensionDataLst.get(i);
			data[i] = new double[] { tmp.getTemperature(), tmp.getPower() };
		}
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		double slope = regression.getSlope();
		double intercept = regression.getIntercept();
		double rSquare = regression.getRSquare();

		int minTemp = listDimensionDataLst.get(0).getTemperature();
		int maxTemp = listDimensionDataLst.get(0).getTemperature();
		for (int i = 0; i < listDimensionDataLst.size(); i++) {
			Lab008ProductDimensionData tmp = listDimensionDataLst.get(i);
			Integer tempData = tmp.getTemperature();
			Double powerData = tmp.getPower();
			if (tempData == null) {
				tempData = 0;
			}
			if (powerData == null) {
				powerData = 0d;
			}
			if(tempData < 16){
				if (tempData > maxTemp) {
					maxTemp = tempData;
				}
				if (tempData < minTemp) {
					minTemp = tempData;
				}
					listDimensionValue.add(new Object[] { tempData, powerData });
			}
		}
		double ymin = slope * minTemp + intercept;
		lineLst.add(new Object[] { minTemp, ymin });
		double ymax = slope * maxTemp + intercept;
		lineLst.add(new Object[] { maxTemp, ymax });
		List<Object> dataLst = new ArrayList<Object>();
		dataLst = getProductionLitmit(siteId);
		bean.setMinTempOutside(minTemp);
		bean.setReachMax(maxAvg.intValue());
		bean.setRatio(ratio);
		bean.setDataLst(dataLst);
		bean.setListDimensionValue(listDimensionValue);
		bean.setLineLst(lineLst);
		bean.setLinearA(Precision.round(slope, 3));
		bean.setLinearB(Precision.round(intercept, 2));
		bean.setrSquare(Precision.round(rSquare, 4));
		return bean;

	}

	@RequestMapping(value = "saveProductionDimension", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Object> saveProductionDimension(@RequestParam Integer siteId, @RequestParam Integer temp,
			@RequestParam Integer simulateMax, @RequestParam Integer ratio) {
		List<Object> productionLst = new ArrayList<Object>();
		Date toDay = new Date();
		Lab008Simulation lab008Simulation = new Lab008Simulation();
		lab008Simulation.setSiteId(siteId);
		lab008Simulation.setDateExecute(toDay);
		lab008Simulation.setTemperature(temp);
		lab008Simulation.setConsommation(simulateMax);
		lab008Simulation.setRatio(ratio);
		lab008SimulationDAO.saveOrUpdate(lab008Simulation);
		productionLst = getProductionLitmit(siteId);
		return productionLst;
	}
	
	@RequestMapping(value = "deleteProductionDimension",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Object> deleteProductionDimension(@RequestParam Integer id, @RequestParam Integer siteId){
		List<Object> productionLst = new ArrayList<Object>();
		lab008SimulationDAO.deleteById(id);
		productionLst = getProductionLitmit(siteId);
		return productionLst;
	}

	private List<Object> getProductionLitmit(Integer siteId) {
		List<Object> lst = new ArrayList<Object>();
		List<Lab008Simulation> lab008SimulationLst = lab008SimulationDAO.findSimulationLimitBySite(siteId);
		String dateValue = null;
		int tempSimulation = 0;
		int simulation = 0;
		int ratioSimulation = 0;
		int id = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat(FrontalKey.DATE_SLASH_FORMAT);
		if (lab008SimulationLst != null && lab008SimulationLst.size() > 0) {
			for (int i = 0; i < lab008SimulationLst.size(); i++) {
				dateValue = dateFormat.format(lab008SimulationLst.get(i).getDateExecute());
				id = lab008SimulationLst.get(i).getId();
				tempSimulation = lab008SimulationLst.get(i).getTemperature();
				simulation = lab008SimulationLst.get(i).getConsommation();
				ratioSimulation = lab008SimulationLst.get(i).getRatio();
				lst.add(new Object[] { id,dateValue, tempSimulation, simulation, ratioSimulation });
			}
		}
		return lst;
	}

	private int avgPCIAll;
	private int avgPCSAll;

	private Integer getAverageOfAllSite(String userName, Date fromDate, Date toDate, int configLabID, Integer percent,
			Integer avgPCI, Integer avgPCS) {

		List<ConfigLab008V2> configLab008V2Lst = lab008DAO.getConfigByUserExcept(userName, configLabID);
		GetDataAction action = new GetDataAction();
		int sumPercent = 0;
		int countPC = 0;
		int count = 0;
		List<Integer> lstType = new ArrayList<Integer>();
		lstType.add(FrontalKey.TYPE_CONSOMMATION_CHAUFFAGE);
		lstType.add(FrontalKey.TYPE_CONSOMMATION_ECS);
		int avgPCSAllSum = 0;
		int avgPCIAllSum = 0;
		List<Integer> moduleIdLst = new ArrayList<Integer>();

		for (ConfigLab008V2 config : configLab008V2Lst) {
			if (config.getBoilerType() == FrontalKey.BOILER_TYPE_URBAN_HEAT) {
				List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(config.getSiteId(), lstType);
				if (lab008ECSLst != null && !lab008ECSLst.isEmpty()) {
					for (Lab008ECS ecs : lab008ECSLst) {
						if (ecs.getModuleCSM() != null && !ecs.getModuleCSM().isEmpty()) {
							moduleIdLst.addAll(CalculateUtils.getIDFromStr(ecs.getModuleCSM()));
						}
					}
				}
			}
		}

		String fromDateP = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_FORMAT_DAY_1);
		String toDateP = LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_FORMAT_DAY_1);
		Map<String, Map<Integer, Integer>> mapALLData = action.getAllModuleByTime(moduleIdLst, fromDateP, toDateP,
				FrontalKey.TYPE_TIME_HOUR);
		for (ConfigLab008V2 config : configLab008V2Lst) {
			if (config.getBoilerType() == null) {
				log.info("Boiler Type Of Site ID null:" + config.getSiteId());
				continue;
			}
			if (config.getBoilerType() == FrontalKey.BOILER_TYPE_URBAN_HEAT) {
				List<Lab008ECS> lab008ECSLst = lab008DAO.findECSBySiteIdAndType(config.getSiteId(), lstType);
				if (lab008ECSLst == null || lab008ECSLst.size() == 0) {
					continue;
				}
				if (mapALLData == null || mapALLData.isEmpty()) {
					continue;
				}
				TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
				int sum = 0;

				for (Lab008ECS ecs : lab008ECSLst) {
					String moduleCSM = ecs.getModuleCSM();
					if (moduleCSM != null && !moduleCSM.isEmpty()) {
						for (Map.Entry<String, Map<Integer, Integer>> entry : mapALLData.entrySet()) {
							String keyTime = entry.getKey();
							Map<Integer, Integer> mapConsommation = entry.getValue();
							Integer consommation = CalculateUtils.getConsommation(moduleCSM, mapConsommation);
							if (consommation != 0) {
								sum += consommation;
								if (treeMap.containsKey(keyTime)) {
									consommation += treeMap.get(keyTime);
								}
								treeMap.put(keyTime, consommation);
							}
						}
					}

				}
				count++;
				if (treeMap.size() != 0) {
					sumPercent += getPercent(sum / treeMap.size(), config.getSubscribedPower());
				}
			} else {
				ProductionData productionData = convertFromDataLocalToDataChart(config, fromDate, toDate);
				int sizeOfData = productionData.getListPCI().size();
				sumPercent += (int) ((productionData.getSumProduction() * 100)
						/ (config.getBoilerTheoryPower() * sizeOfData));
				avgPCSAllSum += (int) (productionData.getSumPCS() / sizeOfData);
				avgPCIAllSum += (int) (productionData.getSumPCI() / sizeOfData);
				count++;
				countPC++;
			}
		}

		if (percent != null && percent > 0) {
			count++;
			sumPercent += percent;
		}
		if (avgPCI != null && avgPCS != null) {
			avgPCSAllSum += avgPCS;
			avgPCIAllSum += avgPCI;
			countPC++;
		}
		int rs = 0;
		if (countPC > 0) {
			avgPCIAll = avgPCIAllSum / countPC;
			avgPCSAll = avgPCSAllSum / countPC;
			rs = sumPercent / count;
		}

		return rs;
	}

	private int getPercent(int value, int total) {
		return (int) (((double) value) / total * 100);
	}

	@RequestMapping(value = "getBoilType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Integer getBoilType(@RequestParam Integer siteId) {
		return lab008DAO.getBoilType(siteId);
	}

	@RequestMapping(value = "transferDataLab008", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Integer transferDataLab008(@RequestParam Integer siteId) throws ParseException {

		List<ConfigLab008> configLst = new ArrayList<ConfigLab008>();
		if (siteId > 0) {
			ConfigLab008 lab = lab008OldDAO.findBySiteId(siteId);
			if (lab != null) {
				configLst.add(lab);
			}

		} else {
			configLst = lab008OldDAO.getAllConfig();
		}
		int count = 0;
		for (ConfigLab008 lab : configLst) {
			ConfigLab008V2 labv2 = lab008DAO.findBySiteId(lab.getSiteId());
			if (labv2 != null) {
				count++;
				labv2.setAvgAirTempModuleId(lab.getAvgAirTempModuleId());
				labv2.setAvgExtTempModuleId(lab.getAvgExtTempModuleId());
				labv2.setBoilerModel(lab.getBoilerModel());
				labv2.setBoilerYear(lab.getBoilerYear());
				labv2.setBoilerTheoryPower(lab.getBoilerTheoryPower());
				labv2.setGasNaturalModuleId(lab.getGasNaturalModuleId());
				labv2.setProductionModuleId(lab.getProductionModuleId());
				labv2.setCoeff1(lab.getCoeff1());
				labv2.setCoeff2(lab.getCoeff2());
				labv2.setCoeff3(lab.getCoeff3());
				labv2.setCoeff4(lab.getCoeff4());
				labv2.setCoeff5(lab.getCoeff5());
				labv2.setCoeff6(lab.getCoeff6());
				labv2.setCoeffRadnConvection(lab.getCoeffRadnConvection());
				labv2.setReportName(lab.getReportName());
				labv2.setFromDate(lab.getFromDate());
				labv2.setModelPrecision(lab.getModelPrecision());
				labv2.setChauffageModuleId(lab.getChauffageModuleId());
				labv2.setEcsZoneBasse(lab.getEcsZoneBasse());
				labv2.setEcsZoneHaute(lab.getEcsZoneHaute());
				lab008DAO.saveOrUpdate(labv2);
			}

		}
		return count;
	}

	private class ProductionData {
		private Double sumProduction;
		List<Double> listPCI;
		List<Double> listPCS;
		List<Double> listRateCharge;
		private Double sumPCI;
		private Double sumPCS;

		public Double getSumProduction() {
			return sumProduction;
		}

		public void setSumProduction(Double sumProduction) {
			this.sumProduction = sumProduction;
		}

		public List<Double> getListPCI() {
			return listPCI;
		}

		public void setListPCI(List<Double> listPCI) {
			this.listPCI = listPCI;
		}

		public List<Double> getListPCS() {
			return listPCS;
		}

		public void setListPCS(List<Double> listPCS) {
			this.listPCS = listPCS;
		}

		public List<Double> getListRateCharge() {
			return listRateCharge;
		}

		public void setListRateCharge(List<Double> listRateCharge) {
			this.listRateCharge = listRateCharge;
		}

		public Double getSumPCI() {
			return sumPCI;
		}

		public void setSumPCI(Double sumPCI) {
			this.sumPCI = sumPCI;
		}

		public Double getSumPCS() {
			return sumPCS;
		}

		public void setSumPCS(Double sumPCS) {
			this.sumPCS = sumPCS;
		}

	}
}
