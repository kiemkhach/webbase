package com.ifi.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab009ConfigBean;
import com.ifi.common.bean.Lab009Contract;
import com.ifi.common.bean.Lab009DataRow;
import com.ifi.common.bean.Lab009DetailBean;
import com.ifi.common.bean.Lab009DetailContract;
import com.ifi.common.bean.Lab009EnergyTypeBean;
import com.ifi.common.bean.Lab009FilterBean;
import com.ifi.common.bean.Lab009HomeBean;
import com.ifi.common.bean.Lab009LotConsommationBean;
import com.ifi.common.bean.Lab009ModuleData;
import com.ifi.common.bean.Lab009ProviderBean;
import com.ifi.common.bean.Lab009SitePage;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.csm.SiteCSM;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.ws.GetCSMDataActionOLD;
import com.ifi.lab.LabDAO.dao.ConfigLab009DAO;
import com.ifi.lab.LabDAO.dao.Lab009EnergyTypeDao;
import com.ifi.lab.LabDAO.dao.Lab009FilterDao;
import com.ifi.lab.LabDAO.dao.Lab009FilterValueDao;
import com.ifi.lab.LabDAO.dao.Lab009LotConsommationDAO;
import com.ifi.lab.LabDAO.dao.Lab009ModuleDAO;
import com.ifi.lab.LabDAO.dao.Lab009ProviderDAO;
import com.ifi.lab.LabDAO.model.ConfigLab009;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;
import com.ifi.lab.LabDAO.model.Lab009Filter;
import com.ifi.lab.LabDAO.model.Lab009FilterValue;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.Lab009Module;
import com.ifi.lab.LabDAO.model.Lab009Provider;

@Controller
@RequestMapping("lab009")
public class Lab009Rest {
	@Autowired
	private Lab009FilterDao lab009FilterDao;
	@Autowired
	private Lab009FilterValueDao lab009FilterValueDao;

	@Autowired
	private Lab009EnergyTypeDao lab009EnergyTypeDao;

	@Autowired
	private Lab009ProviderDAO lab009ProviderDao;

	@Autowired
	private ConfigLab009DAO configLab009DAO;

	@Autowired
	private Lab009ModuleDAO lab009ModuleDao;

	@Autowired
	private Lab009LotConsommationDAO lab009LotConsommationDAO;

	private static final String FILTER_ID = "filterID";
	private static final String MAX_VALUE_KEY = "maxValue";
	private static final String MIN_VALUE_KEY = "minValue";
	private static final String STRING_KEY = "valueString";
	private static final String STRING_MENU = "valueSelect";

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Lab009Rest.class);

	private static Map<Integer, ModuleCSM> mapModule = null;
	private static Map<Integer, SiteCSM> mapSite = null;
	private static Map<Integer, Map<String, String>> mapModulePro = null;
	private static Map<Integer, Map<String, String>> sitePropertyMap = null;
	private static List<String> lstPro = null;

	private void getSitePropertyMap(List<Integer> siteIdLst) {
		if (sitePropertyMap == null) {
			if (lstPro == null) {
				lstPro = new ArrayList<String>();
			}
			GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
			sitePropertyMap = csm.getLstSitePropertyByID(siteIdLst);
			for (Map.Entry<Integer, Map<String, String>> entry : sitePropertyMap.entrySet()) {
				for (Map.Entry<String, String> entryPro : entry.getValue().entrySet()) {
					String key = entryPro.getKey();
					if (!lstPro.contains(key)) {
						lstPro.add(key);
					}
				}
			}

			List<Lab009Filter> filterLst = lab009FilterDao.getAll();
			for (String proKey : lstPro) {
				boolean isFind = false;
				for (Lab009Filter filter : filterLst) {
					if (filter.getColumnName().trim().equals(proKey.trim())) {
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					Lab009Filter filter = new Lab009Filter();
					filter.setText(proKey);
					filter.setColumnName(proKey);
					filter.setType(FrontalKey.SELECT_TYPE_STRING);
					lab009FilterDao.create(filter);
				}
			}
		}
	}

	@RequestMapping(value = "resetCache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody boolean resetCache() {
		mapModule = null;
		mapSite = null;
		mapModulePro = null;
		sitePropertyMap = null;
		lstPro = null;
		return true;
	}

	@RequestMapping(value = "getLab009ConfigBeanALL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009ConfigBean getLab009ConfigBeanALL() {
		Lab009ConfigBean configBean = new Lab009ConfigBean();

		List<Lab009LotConsommation> lab009LotConsommationLst = lab009LotConsommationDAO.getAllData();
		List<Lab009LotConsommationBean> lotLst = new ArrayList<Lab009LotConsommationBean>();
		for (Lab009LotConsommation model : lab009LotConsommationLst) {
			Lab009LotConsommationBean bean = new Lab009LotConsommationBean();
			bean.setId(model.getId());
			bean.setLotName(model.getLotName());
			bean.setColorCode(model.getColorCode());
			lotLst.add(bean);
		}
		configBean.setLotConsommationBeanlst(lotLst);

		return configBean;
	}

	@RequestMapping(value = "getLab009ConfigBean", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009ConfigBean getLab009ConfigBean() {
		Lab009ConfigBean configBean = new Lab009ConfigBean();

		List<Lab009LotConsommation> lab009LotConsommationLst = lab009LotConsommationDAO.getAllLotActive();
		List<Lab009LotConsommationBean> lotLst = new ArrayList<Lab009LotConsommationBean>();
		for (Lab009LotConsommation model : lab009LotConsommationLst) {
			Lab009LotConsommationBean bean = new Lab009LotConsommationBean();
			bean.setId(model.getId());
			bean.setLotName(model.getLotName());
			bean.setColorCode(model.getColorCode());
			lotLst.add(bean);
		}
		configBean.setLotConsommationBeanlst(lotLst);

		//
		List<Lab009EnergyType> lab009EnergyTypeLst = lab009EnergyTypeDao.getAllConsommation();
		List<Lab009EnergyTypeBean> lstType = new ArrayList<Lab009EnergyTypeBean>();
		for (Lab009EnergyType model : lab009EnergyTypeLst) {
			Lab009EnergyTypeBean bean = new Lab009EnergyTypeBean();
			bean.setId(model.getId());
			bean.setEnergyName(model.getEnergyName());
			bean.setColorCode(model.getColorCode());
			bean.setEnergyEmissions(model.getEnergyEmissions());
			lstType.add(bean);
		}
		configBean.setEnergyTypeBeanLst(lstType);

		//
		List<Lab009Provider> lab009ProviderLst = lab009ProviderDao.getAllData();
		List<Lab009ProviderBean> lstProvider = new ArrayList<Lab009ProviderBean>();
		for (Lab009Provider model : lab009ProviderLst) {
			Lab009ProviderBean bean = new Lab009ProviderBean();
			bean.setId(model.getId());
			bean.setProviderName(model.getProviderName());
			bean.setColorCode(model.getColorCode());
			lstProvider.add(bean);
		}
		configBean.setProviderBeanLst(lstProvider);

		return configBean;
	}

	@RequestMapping(value = "getAllFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab009FilterBean> getAllFilter(String clientId) {

		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<SiteCSM> allSiteLst = csm.getLstSiteByClient(clientId);
		// List<SiteCSM> siteLst = new ArrayList<SiteCSM>();
		if (allSiteLst == null || allSiteLst.isEmpty()) {
			return null;
		}
		List<Integer> siteIDLst = new ArrayList<Integer>();
		for (SiteCSM siteCSM : allSiteLst) {
			siteIDLst.add(siteCSM.getSiteId());
		}

		getSitePropertyMap(siteIDLst);

		List<Lab009Filter> filterLst = lab009FilterDao.getAll();
		List<Lab009FilterBean> lstRs = new ArrayList<Lab009FilterBean>();
		for (Lab009Filter lab009Filter : filterLst) {
			Lab009FilterBean bean = new Lab009FilterBean();
			bean.setId(lab009Filter.getId());
			bean.setText(lab009Filter.getText());
			bean.setType(lab009Filter.getType());
			if (FrontalKey.SELECT_TYPE_MULTI.equals(lab009Filter.getType())) {
				List<Lab009FilterValue> filterValueLst = lab009FilterValueDao.findByFilterID(lab009Filter.getId());
				if (filterValueLst != null && !filterValueLst.isEmpty()) {
					List<String> valueRange = new ArrayList<String>();
					for (Lab009FilterValue valueFilter : filterValueLst) {
						valueRange.add(valueFilter.getValue());
					}
					bean.setValueRange(valueRange);
				}
			}
			lstRs.add(bean);
		}
		return lstRs;
	}

	private static final String REFERENCE_KEY = "Référence";
	private static final String DENOMINATION_KEY = "Dénomination";

	@RequestMapping(value = "getDataSite", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009SitePage getDataSite(@RequestParam String clientId, @RequestParam Integer siteId,
			Integer year) {
		Lab009SitePage page = new Lab009SitePage();

		ConfigLab009 configLab009 = configLab009DAO.findByClient(Integer.parseInt(clientId));
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<SiteCSM> allSiteLst = csm.getLstSiteByClient(clientId);
		// List<SiteCSM> siteLst = new ArrayList<SiteCSM>();
		if (allSiteLst == null || allSiteLst.isEmpty()) {
			return null;
		}
		List<Integer> siteIDLst = new ArrayList<Integer>();
		for (SiteCSM siteCSM : allSiteLst) {
			siteIDLst.add(siteCSM.getSiteId());
		}

		getSitePropertyMap(siteIDLst);

		List<Lab009Filter> filterLst = lab009FilterDao.getAll();
		Map<String, String> propertyMap = null;

		if (sitePropertyMap.containsKey(siteId)) {
			propertyMap = sitePropertyMap.get(siteId);
			List<String[]> lst = new ArrayList<String[]>();
			List<Lab009Filter> filterTmpLst = new ArrayList<Lab009Filter>();
			for (Map.Entry<String, String> map : propertyMap.entrySet()) {

				if (map.getKey().equals(REFERENCE_KEY)) {
					page.setReference(map.getValue());
					continue;
				} else if (map.getKey().equals(DENOMINATION_KEY)) {
					page.setDenomination(map.getValue());
					continue;
				}

				Lab009Filter filter = new Lab009Filter();
				filter.setColumnName(map.getKey());
				filter.setText(map.getValue());
				for (Lab009Filter filterConfig : filterLst) {
					if (filterConfig.getColumnName().equals(filter.getColumnName())) {
						filter.setOrderBy(filterConfig.getOrderBy());
						break;
					}
				}
				filterTmpLst.add(filter);

			}
			// Sort by order
			Collections.sort(filterTmpLst, new Comparator<Lab009Filter>() {

				public int compare(Lab009Filter o1, Lab009Filter o2) {
					if (o1.getOrderBy() == null && o2.getOrderBy() == null) {
						return 0;
					} else if (o1.getOrderBy() == null) {
						return 1;
					} else if (o2.getOrderBy() == null) {
						return -1;
					} else {
						return o1.getOrderBy().compareTo(o2.getOrderBy());
					}
				}
			});
			for (Lab009Filter filter : filterTmpLst) {
				lst.add(new String[] { filter.getColumnName(), filter.getText() });
			}
			page.setSiteProperty(lst);
		} else {
			return null;
		}

		List<Lab009LotConsommation> lab009LotConsommationLst = lab009LotConsommationDAO.getAllData();

		List<Lab009EnergyType> lab009EnergyTypeLst = lab009EnergyTypeDao.getAll();
		List<Lab009Provider> lab009ProviderLst = lab009ProviderDao.getAllData();

		Map<Integer, Lab009Module> lab009ModuleMap = lab009ModuleDao.getAllMapLab009Module();
		// List<Lab009Provider> lab009ProviderLst =
		// lab009ProviderDao.getAllData();
		Integer energyWaterID = 0;
		for (Lab009EnergyType energyType : lab009EnergyTypeLst) {
			if (energyType.getCategory() != null) {
				if (energyType.getCategory().equals(FrontalKey.LAB009_CATEOGRY_WATER)) {
					energyWaterID = energyType.getId();
				}
			}

		}

		List<Integer> siteLst = new ArrayList<Integer>();
		siteLst.add(siteId);
		Map<String, Lab009Contract> mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst,
				lab009EnergyTypeLst, lab009ModuleMap, lab009ProviderLst, false);
		if (mapContract == null || mapContract.isEmpty()) {
			return null;
		}

		Map<Integer, Lab009LotConsommationBean> lotConsommationMap = getLab009LotConsommationBean(
				lab009LotConsommationLst);
		page.setLotConsommationMap(lotConsommationMap);
		Map<Integer, Lab009EnergyTypeBean> energyTypeMap = getLab009EnergyTypeBean(lab009EnergyTypeLst);
		page.setEnergyTypeMap(energyTypeMap);

		String fromMonth = year + "01";
		String toMonth = year + "12";
		List<Lab009ModuleData> moduleDataLst = csm.getModuleData(fromMonth, toMonth, mapContract.keySet());

		Map<Integer, Double> batchTypeMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> batchTypeDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> montantMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> montantDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> emissionsMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> emissionsDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, List<Lab009DetailContract>> mapDetailContract = new HashMap<Integer, List<Lab009DetailContract>>();
		Double totalConsommation = 0d;
		Double totalWater = 0d;
		Double totalMontal = 0d;
		Double totalEmissions = 0d;

		for (Map.Entry<String, Lab009Contract> entry : mapContract.entrySet()) {
			Lab009Contract contract = entry.getValue();
			Integer lotId = entry.getValue().getLotId();
			List<Lab009DetailContract> lst = null;
			if (mapDetailContract.containsKey(lotId)) {
				lst = mapDetailContract.get(lotId);
			} else {
				lst = new ArrayList<Lab009DetailContract>();
			}

			Lab009DetailContract detailContract = new Lab009DetailContract();
			detailContract.setModuleNumber(entry.getKey());
			detailContract.setProviderID(contract.getProviderId());
			detailContract.setEnergyTypeID(contract.getEnergyTypeId());
			detailContract.setNoCompteur(contract.getNumberCompteur());
			detailContract.setNoContract(contract.getNumberContract());
			lst.add(detailContract);
			mapDetailContract.put(lotId, lst);
		}
		for (Lab009ModuleData moduleData : moduleDataLst) {
			Lab009Contract contract = null;
			if (mapContract.containsKey(moduleData.getModuleNum())) {
				contract = mapContract.get(moduleData.getModuleNum());
			} else {
				LOG.error("Module not map in enr_module_property:" + moduleData.getModuleNum());
				continue;
			}
			Double consommation = moduleData.getConso();
			Double montant = moduleData.getCout();
			// Chart 1

			// Chart2
			if (energyWaterID != null && energyWaterID.equals(contract.getEnergyTypeId())) {
				totalWater += consommation;
			} else {
				totalConsommation += consommation;
				batchTypeMap = LabUtils.putValueToMap(batchTypeMap, contract.getLotId(), consommation);
				batchTypeDrillMap = LabUtils.putValueToMapMap(batchTypeDrillMap, contract.getLotId(),
						contract.getEnergyTypeId(), consommation);
				totalMontal += montant;
				montantMap = LabUtils.putValueToMap(montantMap, contract.getLotId(), montant);
				montantDrillMap = LabUtils.putValueToMapMap(montantDrillMap, contract.getLotId(),
						contract.getEnergyTypeId(), montant);

				// Chart3
				if (energyTypeMap.containsKey(contract.getEnergyTypeId())) {
					if (energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions() != null) {
						Double confident = energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions();
						if (confident != null) {
							Double emission = convertEmissions(consommation, confident);
							totalEmissions += emission;
							emissionsMap = LabUtils.putValueToMap(emissionsMap, contract.getLotId(), emission);
							emissionsDrillMap = LabUtils.putValueToMapMap(emissionsDrillMap, contract.getLotId(),
									contract.getEnergyTypeId(), emission);
						}
					}
				}
			}

			// Detail des contracts
			List<Lab009DetailContract> detailContractLst = null;
			if (mapDetailContract.containsKey(contract.getLotId())) {
				detailContractLst = mapDetailContract.get(contract.getLotId());
			} else {
				detailContractLst = new ArrayList<Lab009DetailContract>();
			}

			List<Lab009DetailContract> detailContractLstTmp = new ArrayList<Lab009DetailContract>();
			boolean isFind = false;
			for (Lab009DetailContract detailContract : detailContractLst) {
				if (detailContract.getModuleNumber().equals(moduleData.getModuleNum())
						&& detailContract.getEnergyTypeID().equals(contract.getEnergyTypeId())
						&& detailContract.getProviderID().equals(contract.getProviderId())) {
					isFind = true;

					if (detailContract.getConsommation() == null) {
						detailContract.setConsommation(consommation);
					} else {
						detailContract.setConsommation(consommation + detailContract.getConsommation());
					}
					if (detailContract.getMontal() == null) {
						detailContract.setMontal(montant);
					} else {
						detailContract.setMontal(montant + detailContract.getMontal());
					}
				}
				detailContractLstTmp.add(detailContract);
			}
			if (!isFind) {
				Lab009DetailContract newDetailContract = new Lab009DetailContract();
				newDetailContract.setConsommation(consommation);
				newDetailContract.setMontal(montant);
				detailContractLstTmp.add(newDetailContract);
			}

			mapDetailContract.put(contract.getLotId(), detailContractLstTmp);
		}

		// set chart
		page.setTotalHTFacture(convertConsommation(totalConsommation, configLab009.getUnitConsommation()));
		page.setBatchTypeMap(LabUtils.convertMapToPercent(batchTypeMap, totalConsommation));
		page.setBatchTypeDrillMap(LabUtils.convertMapMapToPercent(batchTypeDrillMap, totalConsommation, batchTypeMap));

		// Chart 2
		page.setTotalFourniture(convertMontant(totalMontal, configLab009.getUnitMontal()));
		page.setMontantMap(LabUtils.convertMapToPercent(montantMap, totalMontal));
		page.setMontantDrillMap(LabUtils.convertMapMapToPercent(montantDrillMap, totalMontal, montantMap));

		// Chart 3
		page.setTotalEmissions(totalEmissions);
		page.setEmissionsMap(emissionsMap);
		page.setEmissionsDrillMap(emissionsDrillMap);

		page.setMontalSite(totalMontal);

		if (propertyMap.containsKey(FrontalKey.LAB009_SITE_SURFACE_KEY)) {
			Double d = LabUtils.convertStringToDouble((propertyMap.get(FrontalKey.LAB009_SITE_SURFACE_KEY)));
			Double occupant = LabUtils.convertStringToDouble(propertyMap.get(FrontalKey.LAB009_SITE_OCCUPANTS_KEY));
			Integer surface = d.intValue();
			if (!surface.equals(0)) {
				page.setMontalSurface(totalMontal / surface);
				page.setEmission(convertEmissionsUnit(totalEmissions / surface, configLab009.getUnitEmissions()));
				if (!occupant.equals(0)) {
					page.setWater((totalWater * 1000) / (225 * occupant));
				}
			}
		}

		Map<String, Lab009Contract> mapContractAll = getMapContract(clientId, siteIDLst, lab009LotConsommationLst,
				lab009EnergyTypeLst, lab009ModuleMap, null, false);
		List<Lab009ModuleData> moduleDataAllLst = csm.getModuleData(fromMonth, toMonth, mapContractAll.keySet());

		totalConsommation = 0d;
		totalWater = 0d;
		totalMontal = 0d;
		totalEmissions = 0d;
		for (Lab009ModuleData moduleData : moduleDataAllLst) {
			Lab009Contract contract = null;
			if (mapContractAll.containsKey(moduleData.getModuleNum())) {
				contract = mapContractAll.get(moduleData.getModuleNum());
			} else {
				LOG.error("Module not map in enr_module_property:" + moduleData.getModuleNum());
				continue;
			}
			Map<String, String> propMap = sitePropertyMap.get(contract.getSiteId());

			Double consommation = moduleData.getConso();
			Double montant = moduleData.getCout();
			// Chart 1

			// Chart2
			if (energyWaterID != null && energyWaterID.equals(contract.getEnergyTypeId())) {
				if (propMap.containsKey(FrontalKey.LAB009_SITE_OCCUPANTS_KEY)) {
					Double d = LabUtils.convertStringToDouble(propertyMap.get(FrontalKey.LAB009_SITE_OCCUPANTS_KEY));
					Integer surface = d.intValue();
					if (!surface.equals(0)) {
						totalWater += consommation;
					}
				}
			} else {
				if (propMap.containsKey(FrontalKey.LAB009_SITE_SURFACE_KEY)) {
					Double d = LabUtils.convertStringToDouble(propertyMap.get(FrontalKey.LAB009_SITE_SURFACE_KEY));
					Integer surface = d.intValue();
					if (!surface.equals(0)) {
						totalConsommation += consommation;
						totalMontal += montant;

						if (energyTypeMap.containsKey(contract.getEnergyTypeId())) {
							if (energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions() != null) {
								Double confident = energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions();
								if (confident != null) {
									Double emission = convertEmissions(consommation, confident);
									totalEmissions += emission;
								}
							}
						}
					}
				}
			}

		}

		Double totalSurface = 0d;
		Double totalOccupant = 0d;
		for (Map.Entry<Integer, Map<String, String>> entry : sitePropertyMap.entrySet()) {
			Map<String, String> propMap = entry.getValue();
			if (propMap.containsKey(FrontalKey.LAB009_SITE_OCCUPANTS_KEY)) {
				Double d = LabUtils.convertStringToDouble(propMap.get(FrontalKey.LAB009_SITE_OCCUPANTS_KEY));
				Integer occupant = d.intValue();
				if (!occupant.equals(0)) {
					totalOccupant += occupant;
				}
			}
			if (propMap.containsKey(FrontalKey.LAB009_SITE_SURFACE_KEY)) {
				Double d = LabUtils.convertStringToDouble(propMap.get(FrontalKey.LAB009_SITE_SURFACE_KEY));
				Integer surface = d.intValue();
				if (!surface.equals(0)) {
					totalSurface += surface;
				}
			}
		}

		page.setTotalMontal(totalMontal);

		if (totalSurface != 0) {
			page.setMontalSurfaceTotal(totalMontal / totalSurface);
			page.setEmissionTotal(convertEmissionsUnit(totalEmissions / totalSurface, configLab009.getUnitEmissions()));
		}
		if (totalOccupant != 0) {
			page.setWaterTotal((totalWater * 1000) / (225 * totalOccupant));
		}

		// List<Object[]> detailContractMap = new HashMap<String,
		// List<Lab009DetailContract>>();
		List<Object[]> detailContractMap = new ArrayList<Object[]>();
		for (Map.Entry<Integer, List<Lab009DetailContract>> entry : mapDetailContract.entrySet()) {
			Integer key = entry.getKey();
			List<Lab009DetailContract> detailContractLst = entry.getValue();
			// String keyLotName = "";
			// for (Lab009LotConsommation lab009LotConsommation :
			// lab009LotConsommationLst) {
			// if (lab009LotConsommation.getId().equals(key)) {
			// keyLotName = lab009LotConsommation.getLotName();
			// break;
			// }
			// }

			List<Lab009DetailContract> detailContractRSLst = new ArrayList<Lab009DetailContract>();
			for (Lab009DetailContract detailContract : detailContractLst) {
				for (Lab009EnergyType obj : lab009EnergyTypeLst) {
					if (obj.getId().equals(detailContract.getEnergyTypeID())) {
						detailContract.setEnergyType(obj.getEnergyName());
						break;
					}
				}

				for (Lab009Provider obj : lab009ProviderLst) {
					if (obj.getId().equals(detailContract.getProviderID())) {
						detailContract.setProviderName(obj.getProviderName());
						break;
					}
				}
				if (!detailContract.getEnergyTypeID().equals(energyWaterID)) {
					if (detailContract.getConsommation() != null) {
						detailContract.setConsommation(detailContract.getConsommation() / 1000);
					}
				}

				if (detailContract.getMontal() != null && detailContract.getConsommation() != null
						&& detailContract.getConsommation() != 0) {
					detailContract.setPerformace(detailContract.getMontal() / detailContract.getConsommation());
				}
				detailContractRSLst.add(detailContract);
			}

			detailContractMap.add(new Object[] { key, detailContractRSLst });
		}

		page.setDetailContractLst(detailContractMap);

		page.setConsomationUnit(getConsommationUnitName(configLab009.getUnitConsommation()));
		page.setMontalUnit(getMontantUnitName(configLab009.getUnitMontal()));
		if (configLab009.getUnitEmissions() != null) {
			page.setEmissionsUnit(configLab009.getUnitEmissions());
		} else {
			page.setEmissionsUnit("kt");
		}

		return page;

	}

	@RequestMapping(value = "getFournitureYear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009DetailBean getFournitureYear(@RequestParam String clientId, @RequestParam String sites,
			@RequestParam Integer lotId, @RequestParam Integer energyTypeId, @RequestParam Integer providerId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String typePage) {

		ConfigLab009 configLab009 = configLab009DAO.findByClient(Integer.parseInt(clientId));
		List<Integer> siteLst = LabUtils.getLstByString(sites);
		if (siteLst == null || siteLst.isEmpty()) {
			return null;
		}

		List<Lab009LotConsommation> lab009LotConsommationLst = new ArrayList<Lab009LotConsommation>();
		if (lotId == null || lotId == 0) {
			lab009LotConsommationLst = lab009LotConsommationDAO.getAllLotActive();
		} else {
			Lab009LotConsommation obj = lab009LotConsommationDAO.findById(lotId);
			if (obj != null) {
				lab009LotConsommationLst.add(obj);
			}
		}

		Map<String, Lab009Contract> mapContract = null;
		List<Lab009Provider> lab009ProviderLst = new ArrayList<Lab009Provider>();
		List<Lab009EnergyType> lab009EnergyTypeLst = new ArrayList<Lab009EnergyType>();
		Lab009DetailBean bean = new Lab009DetailBean();
		Map<Integer, Lab009Module> lab009ModuleMap = lab009ModuleDao.getAllMapLab009ModuleNotWater();
		if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
			Lab009Provider obj = lab009ProviderDao.findById(providerId);
			if (obj != null) {
				lab009ProviderLst.add(obj);
			}
			Lab009EnergyType energyType = lab009EnergyTypeDao.findByID(energyTypeId);
			if (energyType != null) {
				lab009EnergyTypeLst.add(energyType);
			}
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, true);
			bean.setEnergyTypeName(energyType.getEnergyName());
		} else {
			Lab009EnergyType obj = lab009EnergyTypeDao.findByID(energyTypeId);
			if (obj != null) {
				lab009EnergyTypeLst.add(obj);
			}
			lab009ProviderLst = lab009ProviderDao.getAllData();
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, false);
		}
		if (mapContract == null || mapContract.isEmpty()) {
			return null;
		}
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<Lab009ModuleData> moduleDataLst = csm.getModuleData(fromDate, toDate, mapContract.keySet());
		if (moduleDataLst == null || moduleDataLst.isEmpty()) {
			return null;
		}
		// Chart1

		bean.setType(typePage);
		bean.setEnergyTypeId(energyTypeId);
		Map<Integer, Lab009LotConsommationBean> lotConsommationMap = getLab009LotConsommationBean(
				lab009LotConsommationLst);
		bean.setLotConsommationMap(lotConsommationMap);
		Map<Integer, Lab009EnergyTypeBean> energyTypeMap = getLab009EnergyTypeBean(lab009EnergyTypeLst);
		bean.setEnergyTypeMap(energyTypeMap);

		bean = getTotalYear(bean, moduleDataLst, configLab009, mapContract, fromDate, toDate);

		return bean;
	}

	private Lab009DetailBean getTotalYear(Lab009DetailBean bean, List<Lab009ModuleData> moduleDataLst,
			ConfigLab009 configLab009, Map<String, Lab009Contract> mapContract, String fromDate, String toDate) {
		Double energyTotal = 0d;
		Double montalTotal = 0d;
		List<String> lstContractTotal = new ArrayList<String>();
		for (Lab009ModuleData moduleData : moduleDataLst) {
			energyTotal += moduleData.getConso();
			montalTotal += moduleData.getCout();
			if (!lstContractTotal.contains(moduleData.getModuleNum())) {
				lstContractTotal.add(moduleData.getModuleNum());
			}
		}
		bean.setContracts(lstContractTotal.size());
		bean.setEnergyTotal(convertConsommation(energyTotal, configLab009.getUnitConsommation()));
		bean.setEnergyUnit(getConsommationUnitName(configLab009.getUnitConsommation()));
		bean.setMontalTotal(convertMontant(montalTotal, configLab009.getUnitMontal()));
		bean.setMontalUnit(getMontantUnitName(configLab009.getUnitMontal()));
		Double performanceTotal = 0d;
		if (energyTotal != 0) {
			performanceTotal = montalTotal / ((double) energyTotal / 1000);
			bean.setPerformance(performanceTotal);
		}
		// Get previous month;
		String fromDatePrevious = LabUtils.getPreviousMonth(fromDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		String lastDatePrevious = LabUtils.getPreviousMonth(toDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<Lab009ModuleData> moduleDataPreviousLst = csm.getModuleData(fromDatePrevious, lastDatePrevious,
				mapContract.keySet());

		if (moduleDataPreviousLst != null && !moduleDataPreviousLst.isEmpty()) {
			energyTotal = 0d;
			montalTotal = 0d;
			for (Lab009ModuleData invoice : moduleDataPreviousLst) {
				energyTotal += invoice.getConso();
				montalTotal += invoice.getCout();
			}
			Double performancePrevious = 0d;
			if (energyTotal != 0) {
				performancePrevious = montalTotal / ((double) energyTotal / 1000);
			}
			Double percent = null;
			if (performancePrevious != 0) {
				percent = 100 * (performanceTotal - performancePrevious) / performancePrevious;
			}
			bean.setPercent(percent);
		}
		return bean;
	}

	@RequestMapping(value = "getFournitureTable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009DetailBean getFournitureTable(@RequestParam String clientId, @RequestParam String sites,
			@RequestParam Integer lotId, @RequestParam Integer energyTypeId, @RequestParam Integer providerId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String typePage) {

		List<Integer> siteLst = LabUtils.getLstByString(sites);
		if (siteLst == null || siteLst.isEmpty()) {
			return null;
		}

		List<Lab009LotConsommation> lab009LotConsommationLst = new ArrayList<Lab009LotConsommation>();
		if (lotId == null || lotId == 0) {
			lab009LotConsommationLst = lab009LotConsommationDAO.getAllLotActive();
		} else {
			Lab009LotConsommation obj = lab009LotConsommationDAO.findById(lotId);
			if (obj != null) {
				lab009LotConsommationLst.add(obj);
			}
		}

		Map<String, Lab009Contract> mapContract = null;
		List<Lab009Provider> lab009ProviderLst = new ArrayList<Lab009Provider>();
		List<Lab009EnergyType> lab009EnergyTypeLst = new ArrayList<Lab009EnergyType>();
		Lab009DetailBean bean = new Lab009DetailBean();
		Map<Integer, Lab009Module> lab009ModuleMap = lab009ModuleDao.getAllMapLab009ModuleNotWater();
		if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
			Lab009Provider obj = lab009ProviderDao.findById(providerId);
			if (obj != null) {
				lab009ProviderLst.add(obj);
			}
			Lab009EnergyType energyType = lab009EnergyTypeDao.findByID(energyTypeId);
			if (energyType != null) {
				lab009EnergyTypeLst.add(energyType);
			}
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, true);
			bean.setEnergyTypeName(energyType.getEnergyName());
		} else {
			Lab009EnergyType obj = lab009EnergyTypeDao.findByID(energyTypeId);
			if (obj != null) {
				lab009EnergyTypeLst.add(obj);
			}
			lab009ProviderLst = lab009ProviderDao.getAllData();
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, false);
		}
		if (mapContract == null || mapContract.isEmpty()) {
			return null;
		}
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<Lab009ModuleData> moduleDataLst = csm.getModuleData(fromDate, toDate, mapContract.keySet());
		if (moduleDataLst == null || moduleDataLst.isEmpty()) {
			return null;
		}
		// Chart1

		bean.setType(typePage);
		bean.setEnergyTypeId(energyTypeId);
		Map<Integer, Lab009LotConsommationBean> lotConsommationMap = getLab009LotConsommationBean(
				lab009LotConsommationLst);
		bean.setLotConsommationMap(lotConsommationMap);
		Map<Integer, Lab009EnergyTypeBean> energyTypeMap = getLab009EnergyTypeBean(lab009EnergyTypeLst);
		bean.setEnergyTypeMap(energyTypeMap);

		return getDataTable(bean, moduleDataLst, mapContract, typePage);
	}

	private Lab009DetailBean getDataTable(Lab009DetailBean bean, List<Lab009ModuleData> moduleDataLst,
			Map<String, Lab009Contract> mapContract, String typePage) {
		// Start get data table
		Map<Integer, Lab009DataRow> providerBeanMap = new HashMap<Integer, Lab009DataRow>();

		for (Lab009ModuleData moduleData : moduleDataLst) {

			Lab009Contract contract = null;
			if (mapContract.containsKey(moduleData.getModuleNum())) {
				contract = mapContract.get(moduleData.getModuleNum());
			} else {
				LOG.error("Not Found module");
				continue;
			}

			// Get data table
			Lab009DataRow row = null;
			Integer key = null;
			if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
				key = contract.getSiteId();
			} else {
				key = contract.getProviderId();
			}
			if (providerBeanMap.containsKey(key)) {
				row = providerBeanMap.get(key);
			} else {
				row = new Lab009DataRow();
			}
			row.setId(key);
			List<String> lstInvariant = null;
			if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
				key = contract.getSiteId();
			} else {
				key = contract.getProviderId();
			}
			if (row.getName() == null) {
				if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
					row.setName(contract.getSiteName());
				} else {
					row.setName(contract.getProviderName());
				}
			}
			if (row.getListInvariant() == null) {
				lstInvariant = new ArrayList<String>();
			} else {
				lstInvariant = row.getListInvariant();
			}
			if (!lstInvariant.contains(moduleData.getModuleNum())) {
				lstInvariant.add(moduleData.getModuleNum());
			}
			row.setListInvariant(lstInvariant);
			Double montal = moduleData.getCout();
			if (row.getMontalTotal() != null) {
				montal += row.getMontalTotal();
			}
			row.setMontalTotal(montal);
			Double consommation = moduleData.getConso();
			if (row.getEnergyTotal() != null) {
				row.setEnergyTotal((double) consommation + row.getEnergyTotal());
			} else {
				row.setEnergyTotal((double) consommation);
			}
			providerBeanMap.put(key, row);

		}

		List<Lab009DataRow> providerBeanLst = new ArrayList<Lab009DataRow>();

		Double totalRow = 0d;
		Double montalTotal = 0d;
		Integer totalContract = 0;
		Double performaceMax = 0d;
		for (Map.Entry<Integer, Lab009DataRow> entry : providerBeanMap.entrySet()) {
			Lab009DataRow row = entry.getValue();
			if (row.getListInvariant() != null) {
				row.setContracts(row.getListInvariant().size());
				totalContract += row.getContracts();
			}
			Double montant = row.getMontalTotal() / 1000;
			row.setMontalTotal(montant);
			montalTotal += montant;
			Double consom = row.getEnergyTotal() / 1000;
			row.setEnergyTotal(consom);
			totalRow += consom;
			if (consom != 0) {
				Double performance = montant / (consom / 1000);
				row.setPerformance(performance);
				if (performance > performaceMax) {
					performaceMax = performance;
				}
			}
			providerBeanLst.add(row);
		}

		if (performaceMax != 0) {
			for (Lab009DataRow row : providerBeanLst) {
				row.setPercentRate((int) (row.getPerformance() * 100 / performaceMax));
			}
		}
		bean.setProviderBeanLst(providerBeanLst);

		Lab009DataRow providerBeanTotal = new Lab009DataRow();
		providerBeanTotal.setContracts(totalContract);
		providerBeanTotal.setMontalTotal(montalTotal);
		providerBeanTotal.setEnergyTotal(totalRow);
		providerBeanTotal.setPerformance(montalTotal / (totalRow / 1000));
		bean.setProviderBeanTotal(providerBeanTotal);
		// END datatable
		return bean;
	}

	@RequestMapping(value = "getFournitureFluide", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009DetailBean getFournitureFluide(@RequestParam String clientId, @RequestParam String sites,
			@RequestParam Integer lotId, @RequestParam Integer energyTypeId, @RequestParam Integer providerId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String typePage) {

		// fromDate = "201509";
		// toDate = "201608";
		ConfigLab009 configLab009 = configLab009DAO.findByClient(Integer.parseInt(clientId));
		List<Integer> siteLst = LabUtils.getLstByString(sites);
		if (siteLst == null || siteLst.isEmpty()) {
			return null;
		}

		List<Lab009LotConsommation> lab009LotConsommationLst = new ArrayList<Lab009LotConsommation>();
		if (lotId == null || lotId == 0) {
			lab009LotConsommationLst = lab009LotConsommationDAO.getAllLotActive();
		} else {
			Lab009LotConsommation obj = lab009LotConsommationDAO.findById(lotId);
			if (obj != null) {
				lab009LotConsommationLst.add(obj);
			}
		}

		Map<String, Lab009Contract> mapContract = null;
		List<Lab009Provider> lab009ProviderLst = new ArrayList<Lab009Provider>();
		List<Lab009EnergyType> lab009EnergyTypeLst = new ArrayList<Lab009EnergyType>();
		Lab009DetailBean bean = new Lab009DetailBean();
		Map<Integer, Lab009Module> lab009ModuleMap = lab009ModuleDao.getAllMapLab009ModuleNotWater();
		if (FrontalKey.LAB009_PAGE_PROVIDER.equals(typePage)) {
			Lab009Provider obj = lab009ProviderDao.findById(providerId);
			if (obj != null) {
				lab009ProviderLst.add(obj);
			}
			Lab009EnergyType energyType = lab009EnergyTypeDao.findByID(energyTypeId);
			if (energyType != null) {
				lab009EnergyTypeLst.add(energyType);
			}
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, true);
			bean.setEnergyTypeName(energyType.getEnergyName());
		} else {
			Lab009EnergyType obj = lab009EnergyTypeDao.findByID(energyTypeId);
			if (obj != null) {
				lab009EnergyTypeLst.add(obj);
			}
			lab009ProviderLst = lab009ProviderDao.getAllData();
			mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst, lab009EnergyTypeLst,
					lab009ModuleMap, lab009ProviderLst, false);
		}
		if (mapContract == null || mapContract.isEmpty()) {
			return null;
		}
		// Chart1

		bean.setType(typePage);
		bean.setEnergyTypeId(energyTypeId);
		Map<Integer, Lab009LotConsommationBean> lotConsommationMap = getLab009LotConsommationBean(
				lab009LotConsommationLst);
		bean.setLotConsommationMap(lotConsommationMap);
		Map<Integer, Lab009EnergyTypeBean> energyTypeMap = getLab009EnergyTypeBean(lab009EnergyTypeLst);
		bean.setEnergyTypeMap(energyTypeMap);

		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		// Start get data table

		// Start get history chart data
		Map<Integer, Double> yearLineChart = new TreeMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> monthLineChart = new TreeMap<Integer, Map<Integer, Double>>();
		Map<Integer, Map<Integer, Double>> yearBarChart = new TreeMap<Integer, Map<Integer, Double>>();
		Map<Integer, Map<Integer, Map<Integer, Double>>> monthBarChart = new TreeMap<Integer, Map<Integer, Map<Integer, Double>>>();

		Integer minYear = null;
		Integer maxYear = null;

		String minFromDate = "000000";
		String maxFromDate = "999999";
		List<Lab009ModuleData> moduleDataAllLst = csm.getModuleData(minFromDate, maxFromDate, mapContract.keySet());

		for (Lab009ModuleData moduleData : moduleDataAllLst) {

			Lab009Contract contract = null;
			if (mapContract.containsKey(moduleData.getModuleNum())) {
				contract = mapContract.get(moduleData.getModuleNum());
			} else {
				LOG.error("Not Found module");
				continue;
			}

			Double consommation = moduleData.getConso();
			Double montal = moduleData.getCout();

			// Get data chart
			Integer year = moduleData.getYear();
			Integer month = moduleData.getMonth();
			if (minYear == null || year.compareTo(minYear) < 0) {
				minYear = year;
			}
			if (maxYear == null || year.compareTo(maxYear) > 0) {
				maxYear = year;
			}

			// Double performance = 0d;
			// if (montal != null && consommation != null && consommation != 0)
			// {
			// performance = montal / (consommation / 1000);
			// }

			if (montal != null) {
				yearLineChart = LabUtils.putValueToMap(yearLineChart, year, montal);
				monthLineChart = LabUtils.putValueToMapMap(monthLineChart, year, month, montal);
			}
			if (consommation != null) {
				yearBarChart = LabUtils.putValueToMapMap(yearBarChart, contract.getLotId(), year, consommation);
			}
			Map<Integer, Map<Integer, Double>> mapTmp = null;
			if (monthBarChart.containsKey(contract.getLotId())) {
				mapTmp = monthBarChart.get(contract.getLotId());
			} else {
				mapTmp = new TreeMap<Integer, Map<Integer, Double>>();
			}

			mapTmp = LabUtils.putValueToMapMap(mapTmp, year, month, consommation);
			monthBarChart.put(contract.getLotId(), mapTmp);
		}

		// calculate performance
		Map<Integer, Double> performaceYearLineChart = new TreeMap<Integer, Double>();
		for (Map.Entry<Integer, Double> entry : yearLineChart.entrySet()) {
			Double sum = 0d;
			Integer year = entry.getKey();
			Double montant = entry.getValue();
			for (Map.Entry<Integer, Map<Integer, Double>> entryBar : yearBarChart.entrySet()) {
				Map<Integer, Double> mapYear = entryBar.getValue();
				if (mapYear.containsKey(year)) {
					sum += mapYear.get(year);
				}
			}
			if (sum != 0) {
				performaceYearLineChart.put(year, montant / (sum / 1000));
			}
		}

		Map<Integer, Map<Integer, Double>> performaceMonthLineChart = new TreeMap<Integer, Map<Integer, Double>>();
		for (Map.Entry<Integer, Map<Integer, Double>> entry : monthLineChart.entrySet()) {

			Integer year = entry.getKey();
			Map<Integer, Double> monthMap = entry.getValue();

			Map<Integer, Double> monthMapTmp = new TreeMap<Integer, Double>();
			for (Map.Entry<Integer, Double> entryMonth : monthMap.entrySet()) {
				Integer month = entryMonth.getKey();
				Double value = entryMonth.getValue();
				Double sum = 0d;
				for (Map.Entry<Integer, Map<Integer, Map<Integer, Double>>> entryBar : monthBarChart.entrySet()) {
					Map<Integer, Map<Integer, Double>> mapBarMonth = entryBar.getValue();
					if (mapBarMonth.containsKey(year)) {
						Map<Integer, Double> mapBarYear = mapBarMonth.get(year);
						if (mapBarYear.containsKey(month)) {
							sum += mapBarYear.get(month);
						}
					}
				}
				if (sum != 0) {
					monthMapTmp.put(month, value / (sum / 1000));
				}
			}

			performaceMonthLineChart.put(year, monthMapTmp);
		}

		// Start create data chart
		for (int i = minYear; i <= maxYear; i++) {
			Integer year = i;
			if (!performaceYearLineChart.containsKey(year) || performaceYearLineChart.get(year).compareTo(0d) < 0) {
				performaceYearLineChart.put(year, 0d);
			}
			Map<Integer, Double> map = null;
			if (!performaceMonthLineChart.containsKey(year)) {
				map = new TreeMap<Integer, Double>();
			} else {
				map = performaceMonthLineChart.get(year);
			}
			for (int j = 1; j <= 12; j++) {
				Integer month = j;
				if (!map.containsKey(month) || map.get(month).compareTo(0d) < 0) {
					map.put(month, 0d);
				}
			}
			performaceMonthLineChart.put(i, map);

		}

		for (Map.Entry<Integer, Map<Integer, Double>> entry : yearBarChart.entrySet()) {
			Map<Integer, Double> map = entry.getValue();
			for (int i = minYear; i <= maxYear; i++) {
				Integer year = i;
				if (!map.containsKey(year) || map.get(year).compareTo(0d) < 0) {
					map.put(year, 0d);
				}
			}
		}

		for (Map.Entry<Integer, Map<Integer, Map<Integer, Double>>> entry : monthBarChart.entrySet()) {
			Map<Integer, Map<Integer, Double>> map = entry.getValue();
			for (int i = minYear; i <= maxYear; i++) {
				Integer year = i;
				Map<Integer, Double> mapMonth = null;
				if (!map.containsKey(year)) {
					mapMonth = new TreeMap<Integer, Double>();
				} else {
					mapMonth = map.get(year);
				}
				for (int j = 1; j <= 12; j++) {
					Integer month = j;
					if (!mapMonth.containsKey(month) || mapMonth.get(month).compareTo(0d) < 0) {
						mapMonth.put(month, 0d);
					}
				}
				map.put(i, mapMonth);
			}
		}

		bean.setYearLineChart(performaceYearLineChart);
		bean.setMonthLineChart(performaceMonthLineChart);
		bean.setYearBarChart(yearBarChart);
		bean.setMonthBarChart(monthBarChart);

		List<Lab009ModuleData> moduleDataLst = csm.getModuleData(fromDate, toDate, mapContract.keySet());
		if (moduleDataLst != null && !moduleDataLst.isEmpty()) {
			bean = getTotalYear(bean, moduleDataLst, configLab009, mapContract, fromDate, toDate);

			bean = getDataTable(bean, moduleDataLst, mapContract, typePage);
		}
		return bean;
	}

	private Map<String, Lab009Contract> getMapContract(String clientId, List<Integer> siteLst,
			List<Lab009LotConsommation> lab009LotConsommationLst, List<Lab009EnergyType> lab009EnergyTypeLst,
			Map<Integer, Lab009Module> lab009ModuleMap, List<Lab009Provider> lab009ProviderLst,
			boolean isGetByProvider) {
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();

		Map<String, Lab009Contract> map = new HashMap<String, Lab009Contract>();
		if (mapModule == null) {
			System.out.println("Get Module");
			mapModule = csm.getMapModuleSiteByClient(clientId);
		}
		if (mapSite == null) {
			mapSite = csm.getMapSiteByClient(clientId);
		}

		if (mapModulePro == null) {
			mapModulePro = csm.getLstModulePropertyByID(mapModule.keySet());
		}

		List<Integer> moduleIDLst = new ArrayList<Integer>();
		for (Map.Entry<Integer, ModuleCSM> entry : mapModule.entrySet()) {
			if (siteLst.contains(entry.getValue().getSiteId())) {
				moduleIDLst.add(entry.getKey());
			}
		}

		for (Integer moduleId : moduleIDLst) {

			Map<String, String> mapPro = null;
			ModuleCSM moduleCSM = null;
			if (mapModule.containsKey(moduleId)) {
				moduleCSM = mapModule.get(moduleId);
			} else {
				LOG.error("Module ID not found:" + moduleId);
				continue;
			}
			Integer siteId = moduleCSM.getSiteId();
			if (mapModulePro.containsKey(moduleId)) {
				mapPro = mapModulePro.get(moduleId);
			} else {
				LOG.error("Module pro not found:" + moduleId);
				continue;
			}

			Integer lotId;
			Integer energyTypeId;
			if (lab009ModuleMap.containsKey(moduleId)) {
				Lab009Module module = lab009ModuleMap.get(moduleId);
				lotId = module.getLotConsommation().getId();
				boolean isFind = false;
				for (Lab009LotConsommation tmp : lab009LotConsommationLst) {
					if (tmp.getId().equals(lotId)) {
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					continue;
				}

				isFind = false;
				energyTypeId = module.getEnergyType().getId();
				for (Lab009EnergyType tmp : lab009EnergyTypeLst) {
					if (tmp.getId().equals(energyTypeId)) {
						isFind = true;
						break;
					}
				}
				if (!isFind) {
					continue;
				}
			} else {
				continue;
			}

			Lab009Contract contract = new Lab009Contract();
			contract.setSiteId(siteId);
			contract.setSiteName(mapSite.get(siteId).getSiteName());
			contract.setEnergyTypeId(energyTypeId);
			contract.setLotId(lotId);
			if (contract.getEnergyTypeId() == null) {
				LOG.error("Not config Energy Type - moduleID:" + moduleId);
				continue;
			}

			if (contract.getLotId() == null) {
				LOG.error("Not config Lot - module ID:" + moduleId);
				continue;
			}

			if (lab009ProviderLst != null) {
				String obj = mapPro.get("Fournisseur");
				if (obj != null && !obj.isEmpty()) {
					String providerName = mapPro.get("Fournisseur");
					boolean isFind = false;
					for (Lab009Provider provider : lab009ProviderLst) {
						if (providerName.equals(provider.getProviderName())) {
							isFind = true;
							contract.setProviderId(provider.getId());
							contract.setProviderName(provider.getProviderName());
							break;
						}
					}
					if (!isGetByProvider && !isFind) {
						Lab009Provider provider = new Lab009Provider();
						provider.setProviderCode(providerName);
						provider.setProviderName(providerName);
						provider.setColorCode(FrontalKey.LAB009_DEFAULT_CHART_COLOR);
						Integer id = lab009ProviderDao.createPro(provider);
						if (id != null) {
							contract.setProviderId(id);
							contract.setProviderName(provider.getProviderName());
							lab009ProviderLst.add(provider);
						} else {
							contract.setProviderId(0);
						}
					}
				} else {
					LOG.error("Not Exists  Fournisseur:");
					contract.setProviderId(0);
				}
			}

			if (isGetByProvider) {
				if (contract.getProviderId() == null || contract.getProviderId() == 0) {
					continue;
				}
			}

			if (mapPro.containsKey("N°Compteur")) {
				contract.setNumberCompteur(mapPro.get("N°Compteur"));
			}

			if (mapPro.containsKey("N°Contrat")) {
				contract.setNumberContract(mapPro.get("N°Contrat"));
			}

			map.put(moduleCSM.getNumberModule(), contract);
		}

		return map;

		// Get
	}

	private Map<Integer, Lab009LotConsommationBean> getLab009LotConsommationBean(
			List<Lab009LotConsommation> lab009LotConsommationLst) {
		Map<Integer, Lab009LotConsommationBean> map = new HashMap<Integer, Lab009LotConsommationBean>();
		for (Lab009LotConsommation model : lab009LotConsommationLst) {
			Lab009LotConsommationBean bean = new Lab009LotConsommationBean();
			bean.setId(model.getId());
			bean.setLotName(model.getLotName());
			bean.setColorCode(model.getColorCode());
			map.put(model.getId(), bean);
		}

		return map;
	}

	private Map<Integer, Lab009EnergyTypeBean> getLab009EnergyTypeBean(List<Lab009EnergyType> lab009EnergyTypeLst) {
		Map<Integer, Lab009EnergyTypeBean> map = new HashMap<Integer, Lab009EnergyTypeBean>();
		for (Lab009EnergyType model : lab009EnergyTypeLst) {
			Lab009EnergyTypeBean bean = new Lab009EnergyTypeBean();
			bean.setId(model.getId());
			bean.setEnergyName(model.getEnergyName());
			bean.setColorCode(model.getColorCode());
			bean.setEnergyEmissions(model.getEnergyEmissions());
			map.put(model.getId(), bean);
		}

		return map;
	}

	private Double convertConsommation(Double consommation, Integer unit) {
		Double d = consommation;
		if (unit == null) {
			return d;
		} else if (unit.equals(FrontalKey.UNIT_CONSOMMATION_GWH)) {
			return d / 1000000;
		} else if (unit.equals(FrontalKey.UNIT_CONSOMMATION_MWH)) {
			return d / 1000;
		} else {
			return d;
		}
	}

	private Double convertMontant(Double montant, Integer unit) {
		Double rs = montant;
		if (unit != null) {
			if (unit.equals(FrontalKey.UNIT_MONTAL_KE)) {
				rs = montant / 1000;
			} else if (unit.equals(FrontalKey.UNIT_MONTAL_ME)) {
				rs = montant / 1000000;
			} else {
				rs = montant;
			}
		}
		return rs;
	}

	private Double convertEmissions(Double consommation, Double confident) {
		if (confident == null) {
			confident = 1d;
		}
		return confident * consommation;
	}

	private Double convertEmissionsUnit(Double consommation, String unit) {
		if (unit == null) {
			return consommation;
		} else if (unit.equals(FrontalKey.EMISSIONS_T)) {
			return consommation * 1000;
		} else if (unit.equals(FrontalKey.EMISSIONS_KT)) {
			return consommation * 1000000;
		} else {
			return consommation;
		}
	}

	private String getConsommationUnitName(Integer unit) {
		if (unit == null) {
			return "KWh";
		} else if (unit.equals(FrontalKey.UNIT_CONSOMMATION_GWH)) {
			return "GWh";
		} else if (unit.equals(FrontalKey.UNIT_CONSOMMATION_MWH)) {
			return "MWh";
		} else {
			return "KWh";
		}
	}

	private String getMontantUnitName(Integer unit) {
		if (unit == null) {
			return "M";
		} else if (unit.equals(FrontalKey.UNIT_MONTAL_E)) {
			return "";
		} else if (unit.equals(FrontalKey.UNIT_MONTAL_KE)) {
			return FrontalKey.EURO_KSIGN;
		} else if (unit.equals(FrontalKey.UNIT_MONTAL_ME)) {
			return FrontalKey.EURO_MSIGN;
		} else {
			return "M";
		}
	}

	/**
	 * Get data chart for home page
	 * 
	 * @param sites
	 *            - seperate siteid by comma
	 * @param fromDate
	 *            - yyyyMM
	 * @param toDate
	 *            - yyyyMM
	 * @return
	 */
	@RequestMapping(value = "getLab009Home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab009HomeBean getLab009Home(@RequestParam String clientId, @RequestParam String sites,
			@RequestParam String fromDate, @RequestParam String toDate) {

		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("constantConfig.xml");
		// System.setProperty("http.proxyHost", "10.225.1.1");
		// System.setProperty("http.proxyPort", "3128");

		Lab009HomeBean bean = new Lab009HomeBean();

		ConfigLab009 configLab009 = configLab009DAO.findByClient(Integer.parseInt(clientId));
		List<Integer> siteLst = LabUtils.getLstByString(sites);
		if (siteLst == null || siteLst.isEmpty()) {
			return null;
		}
		List<Lab009LotConsommation> lab009LotConsommationLst = lab009LotConsommationDAO.getAllLotActive();
		List<Lab009EnergyType> lab009EnergyTypeLst = lab009EnergyTypeDao.getAllConsommation();
		Map<Integer, Lab009Module> lab009ModuleMap = lab009ModuleDao.getAllMapLab009ModuleNotWater();
		// List<Lab009Provider> lab009ProviderLst =
		// lab009ProviderDao.getAllData();

		Map<String, Lab009Contract> mapContract = getMapContract(clientId, siteLst, lab009LotConsommationLst,
				lab009EnergyTypeLst, lab009ModuleMap, null, false);
		if (mapContract == null || mapContract.isEmpty()) {
			return null;
		}
		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		List<Lab009ModuleData> moduleDataLst = csm.getModuleData(fromDate, toDate, mapContract.keySet());
		if (moduleDataLst == null || moduleDataLst.isEmpty()) {
			return null;
		}
		// Chart1

		Map<Integer, Lab009LotConsommationBean> lotConsommationMap = getLab009LotConsommationBean(
				lab009LotConsommationLst);
		bean.setLotConsommationMap(lotConsommationMap);
		Map<Integer, Lab009EnergyTypeBean> energyTypeMap = getLab009EnergyTypeBean(lab009EnergyTypeLst);
		bean.setEnergyTypeMap(energyTypeMap);

		Map<Integer, Double> batchTypeMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> batchTypeDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> energismeTypeMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> energismeTypeDrillMap = new HashMap<Integer, Map<Integer, Double>>();

		Map<Integer, Double> montantMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> montantDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> montantEnergyMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> montantEnergyDrillMap = new HashMap<Integer, Map<Integer, Double>>();

		Map<Integer, Double> emissionsMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> emissionsDrillMap = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> emissionsEnergyMap = new HashMap<Integer, Double>();
		Map<Integer, Map<Integer, Double>> emissionsEnergyDrillMap = new HashMap<Integer, Map<Integer, Double>>();

		Double totalConsommation = 0d;
		Double totalMontal = 0d;
		Double totalEmissions = 0d;
		for (Lab009ModuleData moduleData : moduleDataLst) {
			Lab009Contract contract = null;
			if (mapContract.containsKey(moduleData.getModuleNum())) {
				contract = mapContract.get(moduleData.getModuleNum());
			} else {
				LOG.error("Module not map in enr_module_property:" + moduleData.getModuleNum());
				continue;
			}
			Double consommation = moduleData.getConso();
			Double montant = moduleData.getCout();
			// Chart 1
			totalConsommation += consommation;
			batchTypeMap = LabUtils.putValueToMap(batchTypeMap, contract.getLotId(), consommation);
			batchTypeDrillMap = LabUtils.putValueToMapMap(batchTypeDrillMap, contract.getLotId(),
					contract.getEnergyTypeId(), consommation);

			energismeTypeMap = LabUtils.putValueToMap(energismeTypeMap, contract.getEnergyTypeId(), consommation);
			energismeTypeDrillMap = LabUtils.putValueToMapMap(energismeTypeDrillMap, contract.getEnergyTypeId(),
					contract.getLotId(), consommation);

			// Chart2
			totalMontal += montant;
			montantMap = LabUtils.putValueToMap(montantMap, contract.getLotId(), montant);
			montantDrillMap = LabUtils.putValueToMapMap(montantDrillMap, contract.getLotId(),
					contract.getEnergyTypeId(), montant);
			montantEnergyMap = LabUtils.putValueToMap(montantEnergyMap, contract.getEnergyTypeId(), montant);
			montantEnergyDrillMap = LabUtils.putValueToMapMap(montantEnergyDrillMap, contract.getEnergyTypeId(),
					contract.getLotId(), montant);

			// Chart3
			Double confident = 1d;
			if (energyTypeMap.containsKey(contract.getEnergyTypeId())) {
				if (energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions() != null) {
					confident = energyTypeMap.get(contract.getEnergyTypeId()).getEnergyEmissions();
				}
			}
			Double emission = convertEmissions(consommation, confident);
			totalEmissions += emission;
			emissionsMap = LabUtils.putValueToMap(emissionsMap, contract.getLotId(), emission);
			emissionsDrillMap = LabUtils.putValueToMapMap(emissionsDrillMap, contract.getLotId(),
					contract.getEnergyTypeId(), emission);
			emissionsEnergyMap = LabUtils.putValueToMap(emissionsEnergyMap, contract.getEnergyTypeId(), emission);
			emissionsEnergyDrillMap = LabUtils.putValueToMapMap(emissionsEnergyDrillMap, contract.getEnergyTypeId(),
					contract.getLotId(), emission);

		}

		// Chart 1
		bean.setTotalHTFacture(convertConsommation(totalConsommation, configLab009.getUnitConsommation()));
		bean.setBatchTypeMap(LabUtils.convertMapToPercent(batchTypeMap, totalConsommation));
		bean.setBatchTypeDrillMap(
				LabUtils.convertMapMapToPercentByAll(batchTypeDrillMap, totalConsommation, bean.getBatchTypeMap()));
		bean.setEnergismeTypeMap(LabUtils.convertMapToPercent(energismeTypeMap, totalConsommation));
		bean.setEnergismeTypeDrillMap(LabUtils.convertMapMapToPercentByAll(energismeTypeDrillMap, totalConsommation,
				bean.getEnergismeTypeMap()));

		// Chart 2
		bean.setTotalFourniture(convertMontant(totalMontal, configLab009.getUnitMontal()));
		bean.setMontantMap(LabUtils.convertMapToPercent(montantMap, totalMontal));
		bean.setMontantDrillMap(LabUtils.convertMapMapToPercent(montantDrillMap, totalMontal, montantMap));
		bean.setMontantEnergyMap(LabUtils.convertMapToPercent(montantEnergyMap, totalMontal));
		bean.setMontantEnergyDrillMap(
				LabUtils.convertMapMapToPercent(montantEnergyDrillMap, totalMontal, montantEnergyMap));

		// Chart 3
		bean.setTotalEmissions(totalEmissions);
		bean.setEmissionsMap(emissionsMap);
		bean.setEmissionsDrillMap(emissionsDrillMap);
		bean.setEmissionsEnergyMap(emissionsEnergyMap);
		bean.setEmissionsEnergyDrillMap(emissionsEnergyDrillMap);

		// Unit
		bean.setConsomationUnit(getConsommationUnitName(configLab009.getUnitConsommation()));
		bean.setMontalUnit(getMontantUnitName(configLab009.getUnitMontal()));
		if (configLab009.getUnitEmissions() != null) {
			bean.setEmissionsUnit(configLab009.getUnitEmissions());
		} else {
			bean.setEmissionsUnit("kt");
		}

		return bean;
	}

	@RequestMapping(value = "getSiteByFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<SiteCSM> getSiteByFilter(@RequestParam Map<String, String> allRequestParams) {

		GetCSMDataActionOLD csm = new GetCSMDataActionOLD();
		String clientId = allRequestParams.get("clientId");
		List<SiteCSM> allSiteLst = csm.getLstSiteByClient(clientId);
		// List<SiteCSM> siteLst = new ArrayList<SiteCSM>();
		if (allSiteLst == null || allSiteLst.isEmpty()) {
			return null;
		}
		// String sites = allRequestParams.get("sites");
		// List<Integer> siteIDLst = new ArrayList<Integer>();
		// // if (sites == null || sites.isEmpty()) {
		// // return null;
		// // }
		// // siteIDLst = LabUtils.getLstByString(sites);
		// // }
		// for (SiteCSM siteCSM : allSiteLst) {
		// siteIDLst.add(siteCSM.getSiteId());
		// }

		String filterIDs = allRequestParams.get(FILTER_ID);
		String filterIndexs = allRequestParams.get("filterIndex");
		List<Lab009Filter> filterLst = null;

		List<Integer> idLst = new ArrayList<Integer>();
		List<String> filterIndex = new ArrayList<String>();
		if (filterIDs != null && !filterIDs.isEmpty()) {
			if (sitePropertyMap == null || sitePropertyMap.isEmpty()) {
				return null;
			}
			String[] filterArry = filterIDs.split(FrontalKey.COMMA);
			for (String tmp : filterArry) {
				if (!tmp.isEmpty()) {
					idLst.add(Integer.parseInt(tmp));
				}
			}
			String[] filterIndexarr = filterIndexs.split(FrontalKey.COMMA);
			for (String tmp : filterIndexarr) {
				if (!tmp.isEmpty()) {
					filterIndex.add(tmp);
				}
			}
			filterLst = lab009FilterDao.findByIDs(idLst);
		}

		List<SiteCSM> lst = new ArrayList<SiteCSM>();
		loopSite: for (SiteCSM siteCSM : allSiteLst) {
			boolean isAdd = true;
			if (filterLst != null && !filterLst.isEmpty()) {
				isAdd = false;
				Map<String, String> mapProp = sitePropertyMap.get(siteCSM.getSiteId());
				if (mapProp == null || mapProp.isEmpty()) {
					continue;
				}
				for (int i = 0; i < idLst.size(); i++) {
					Integer filterID = idLst.get(i);
					for (Lab009Filter lab009Filter : filterLst) {
						if (lab009Filter.getId().equals(filterID)) {
							String columnName = lab009Filter.getColumnName();
							String propValue = mapProp.get(columnName);
							if (propValue == null) {
								isAdd = false;
								continue;
							} else {
								if (!checkSiteKey(allRequestParams, propValue, lab009Filter, filterIndex.get(i))) {
									isAdd = false;
									continue loopSite;
								} else {
									isAdd = true;
								}
							}
						}
					}
				}
			}

			if (isAdd) {
				lst.add(siteCSM);
			}
		}
		return lst;
	}

	/**
	 * Check client
	 * 
	 * @param allRequestParams
	 * @param siteProperty
	 * @param id
	 * @return
	 */
	private boolean checkSiteKey(Map<String, String> allRequestParams, String propValue, Lab009Filter filter,
			String index) {

		String maxValueKey = MAX_VALUE_KEY + index;
		String minValueKey = MIN_VALUE_KEY + index;
		String valueString = STRING_KEY + index;
		String valueMenu = STRING_MENU + index;

		String columnName = filter.getColumnName();
		if (FrontalKey.SELECT_TYPE_DATE.equals(filter.getType())) {
			String maxValue = allRequestParams.get(maxValueKey);
			Date proDate = LabUtils.convertDateByFormat(propValue, FrontalKey.DATE_SLASH_FORMAT);
			if (proDate == null) {
				return false;
			}
			if (maxValue != null && !maxValue.isEmpty()) {
				Date maxDate = LabUtils.convertDateByFormat(maxValue, FrontalKey.DATE_FORMAT_DAY_2);
				if (proDate.compareTo(maxDate) > 0) {
					return false;
				}
			}
			String minValue = allRequestParams.get(minValueKey);
			if (minValue != null && !minValue.isEmpty()) {
				Date minDate = LabUtils.convertDateByFormat(minValue, FrontalKey.DATE_FORMAT_DAY_2);
				if (proDate.compareTo(minDate) < 0) {
					return false;
				}
			}
		} else if (FrontalKey.SELECT_TYPE_NUMBER.equals(filter.getType())) {
			Double propValueNumber = 0d;
			try {
				propValueNumber = LabUtils.convertStringToDouble(propValue);
			} catch (Exception e) {
				LOG.error("Not a number: filterKey:" + columnName + "- value:" + propValue);
				return false;
			}

			// Compare max
			String maxValue = allRequestParams.get(maxValueKey);
			if (maxValue != null && !maxValue.isEmpty()) {
				Double maxValueNumber = 0d;
				try {
					maxValueNumber = LabUtils.convertStringToDouble(maxValue);
				} catch (Exception e) {
					LOG.error("maxValueNumber Not a number: " + maxValue);
					return false;
				}
				if (propValueNumber.compareTo(maxValueNumber) > 0) {
					return false;
				}
			}

			// Compare minimum
			String minValue = allRequestParams.get(minValueKey);
			if (minValue != null && !minValue.isEmpty()) {
				Double minValueNumber = 0d;
				try {
					minValueNumber = LabUtils.convertStringToDouble(minValue);
				} catch (Exception e) {
					LOG.error("minValueNumber Not a number: " + minValue);
					return false;
				}
				if (propValueNumber.compareTo(minValueNumber) < 0) {
					return false;
				}
			}
		} else if (FrontalKey.SELECT_TYPE_MULTI.equals(filter.getType())) {
			String value = allRequestParams.get(valueMenu);
			if (value == null) {
				return false;
			}
			String[] values = value.split(FrontalKey.COMMA);
			boolean isFind = false;
			for (String valueSelec : values) {
				if (StringUtils.containsIgnoreCase(propValue, valueSelec)) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				return false;
			}
		} else {
			String value = allRequestParams.get(valueString);
			if (value == null) {
				return false;
			}
			if (!StringUtils.containsIgnoreCase(propValue, value)) {
				return false;
			}
		}

		return true;
	}

}
