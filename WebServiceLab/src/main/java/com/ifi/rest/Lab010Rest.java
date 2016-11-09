package com.ifi.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Idex.IdexInstallationSearchBean;
import com.ifi.common.bean.Idex.IdexKeyBean;
import com.ifi.common.bean.Idex.InstallationOneExelRowData;
import com.ifi.common.bean.Idex.Lab010AddBean;
import com.ifi.common.bean.Idex.Lab010BoilerBean;
import com.ifi.common.bean.Idex.Lab010CategoryCostBean;
import com.ifi.common.bean.Idex.Lab010CompteurBean;
import com.ifi.common.bean.Idex.Lab010CompteurInfo;
import com.ifi.common.bean.Idex.Lab010CostAnalysis;
import com.ifi.common.bean.Idex.Lab010CostChartBean;
import com.ifi.common.bean.Idex.Lab010DataChart;
import com.ifi.common.bean.Idex.Lab010DataSeriesChart;
import com.ifi.common.bean.Idex.Lab010EnergyAnalysis;
import com.ifi.common.bean.Idex.Lab010EnergyTypeBean;
import com.ifi.common.bean.Idex.Lab010IdexRelevesBean;
import com.ifi.common.bean.Idex.Lab010InstallationBean;
import com.ifi.common.bean.Idex.Lab010InstallationNode;
import com.ifi.common.bean.Idex.Lab010InstallationTree;
import com.ifi.common.bean.Idex.Lab010SiteBean;
import com.ifi.common.bean.Idex.Lab010SubCategory;
import com.ifi.common.bean.Idex.NodeInfo;
import com.ifi.common.bean.Idex.IdexConstant.IdexConstant;
import com.ifi.common.util.CalculateUtils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexBoilerDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexConfigDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCostDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCostDetailDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexKeyDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexMeteoDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexRelevesDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexValueDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;
import com.ifi.lab.LabDAO.model.Idex.IdexConfig;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergyType;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexKey;
import com.ifi.lab.LabDAO.model.Idex.IdexReleves;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

@Controller
@RequestMapping("lab010")
public class Lab010Rest {

	@Autowired
	private IdexSiteDAO idexSiteDAO;
	@Autowired
	private IdexKeyDAO idexKeyDAO;
	@Autowired
	private IdexValueDAO idexValueDAO;

	@Autowired
	private IdexInstallationDAO idexInstallationDAO;

	@Autowired
	private IdexCounterDAO idexCounterDAO;

	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;

	@Autowired
	private IdexBoilerDAO idexBoilerDAO;

	@Autowired
	private IdexRelevesDAO idexRelevesDAO;

	@Autowired
	private IdexConfigDAO idexConfigDAO;

	@Autowired
	private IdexMeteoDAO idexMeteoDAO;

	@Autowired
	private IdexCostDAO idexCostDAO;

	@Autowired
	private IdexCostDetailDAO idexCostDetailDAO;
	
	@Autowired
	private ConstantDAO constantDAO;
	
	@RequestMapping(value = "checkLogin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean checkLogin(String userName,String password) {
		return constantDAO.getByKeyValue(userName, password);
	}

	@RequestMapping(value = "getInstallationById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010InstallationBean getInstallationById(Integer installationId) {
		IdexInstallation idexInstallation = idexInstallationDAO.findById(installationId);
		Lab010InstallationBean bean = new Lab010InstallationBean();
		bean.setInstallationId(idexInstallation.getIdexInstallationId());
		bean.setName(idexInstallation.getName());
		bean.setEnergyTypeFile(idexInstallation.getEnergyReportFile());
		bean.setCostFile(idexInstallation.getCostReportFile());
		return bean;
	}

	@RequestMapping(value = "getInstallationTree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010InstallationTree getInstallationTree(Integer installationId) {

		// IdexConfig idexConfig =
		// idexConfigDAO.getByInstalltion(installationId);
		Lab010InstallationTree tree = new Lab010InstallationTree();
		tree.setUrlToLab008(PropertiesReader.getValue(ConfigEnum.LINK_TO_LAB008));
		tree.setUrlToPerial(PropertiesReader.getValue(ConfigEnum.LINK_TO_PERIAL));
		tree.setInstallationId(installationId);
		IdexInstallation idexInstallation = idexInstallationDAO.findById(installationId);
		if (idexInstallation == null) {
			return null;
		}

		tree.setIsConsommationFirst(idexInstallation.getIsConsommationFirst());

		List<Lab010InstallationNode> energySupplyLst = new ArrayList<Lab010InstallationNode>();

		List<Lab010InstallationNode> primaryCompteurLst = new ArrayList<Lab010InstallationNode>();

		List<Lab010InstallationNode> productionBoilerLst = new ArrayList<Lab010InstallationNode>();

		List<Lab010InstallationNode> sortieCompteurLst = new ArrayList<Lab010InstallationNode>();

		List<Lab010InstallationNode> consommationSiteLst = new ArrayList<Lab010InstallationNode>();

		List<List<Lab010InstallationNode>> utileCompteurLst = new ArrayList<List<Lab010InstallationNode>>();

		// Supply
		List<IdexEnergySupplier> idexEnergySupplierList = idexEnergySupplierDAO.findByInstallation(installationId);
		if (idexEnergySupplierList.isEmpty()) {
			return null;
		}

		for (IdexEnergySupplier idexEnergySupplier : idexEnergySupplierList) {
			Lab010InstallationNode node = new Lab010InstallationNode();
			node.setId(idexEnergySupplier.getIdexEnergySupplierId());
			node.setName(idexEnergySupplier.getName());
			node.setIcon(idexEnergySupplier.getLogo());
			if (idexEnergySupplier.getIdexEnergyType() != null) {
				node.setTypeEnergy(idexEnergySupplier.getIdexEnergyType().getCode());
			} else {
				node.setTypeEnergy(IdexConstant.LAB010_TYPE_GAZ);
			}
			energySupplyLst.add(node);
		}

		// Counter
		List<IdexCounter> idexCounterLst = idexCounterDAO.findAllByInstallation(installationId);

		for (IdexCounter idexCounter : idexCounterLst) {
			if (!idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
				Lab010InstallationNode node = new Lab010InstallationNode();
				node.setId(idexCounter.getIdexCounterId());
				node.setName(idexCounter.getName());
				List<Lab010CompteurBean> childLst = null;
				List<Integer> parentLst = new ArrayList<Integer>();

				for (IdexCounter idexCounterTmp : idexCounterLst) {
					if (idexCounterTmp.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)
							&& idexCounterTmp.getIdexCounter().getIdexCounterId()
									.equals(idexCounter.getIdexCounterId())) {
						if (childLst == null) {
							childLst = new ArrayList<Lab010CompteurBean>();
						}
						Lab010CompteurBean compBean = new Lab010CompteurBean();
						compBean.setIdexCounterId(idexCounterTmp.getIdexCounterId());
						compBean.setName(idexCounterTmp.getName());
						childLst.add(compBean);
					}
				}
				node.setParentLst(parentLst);
				node.setChildLst(childLst);
				if (!IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE.equals(idexCounter.getIsSubModule())) {
					if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())) {
						IdexEnergySupplier idexEnergySupplier = idexCounter.getIdexEnergySupplier();
						if (idexEnergySupplier != null) {
							parentLst.add(idexEnergySupplier.getIdexEnergySupplierId());
						}
						if (idexEnergySupplier.getIdexEnergyType() != null) {
							node.setTypeEnergy(idexEnergySupplier.getIdexEnergyType().getCode());
						} else {
							node.setTypeEnergy(IdexConstant.LAB010_TYPE_GAZ);
						}
						primaryCompteurLst.add(node);
					} else if (IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())) {
						IdexBoiler idexBoiler = idexCounter.getIdexBoiler();
						if (idexBoiler != null) {
							parentLst.add(idexBoiler.getIdexBoilerId());
						}
						sortieCompteurLst.add(node);
					} else {
						IdexSite idexSite = idexCounter.getIdexSite();
						Integer parent;
						if (idexSite != null) {
							parent = idexSite.getIdexSiteId();
							parentLst.add(parent);
							boolean isFind = false;
							for (int i = 0; i < utileCompteurLst.size(); i++) {
								List<Lab010InstallationNode> lst = utileCompteurLst.get(i);
								if (lst.size() > 0) {
									if (lst.get(0).getParentLst().get(0).equals(parent)) {
										isFind = true;
										lst.add(node);
									}
								}
							}

							if (!isFind) {
								List<Lab010InstallationNode> tmpLst = new ArrayList<Lab010InstallationNode>();
								tmpLst.add(node);
								utileCompteurLst.add(tmpLst);
							}
						}

					}
				}
			}

		}

		// boiler
		List<IdexBoiler> idexBoilerLst = idexBoilerDAO.findByInstallation(installationId);
		for (IdexBoiler idexBoiler : idexBoilerLst) {
			Lab010InstallationNode node = new Lab010InstallationNode();
			node.setId(idexBoiler.getIdexBoilerId());
			node.setName(idexBoiler.getName());
			node.setIcon(idexBoiler.getLogo());
			List<Integer> parentLst = new ArrayList<Integer>();
			if (idexBoiler.getIdexCounter() != null) {
				parentLst.add(idexBoiler.getIdexCounter().getIdexCounterId());
			}
			node.setParentLst(parentLst);
			productionBoilerLst.add(node);
		}

		List<IdexSite> idexSitesLst = idexSiteDAO.findByInstallation(installationId);
		for (IdexSite idexSite : idexSitesLst) {

			Lab010InstallationNode node = new Lab010InstallationNode();
			node.setId(idexSite.getIdexSiteId());
			node.setName(idexSite.getName());
			node.setIcon(idexSite.getLogo());
			List<Integer> parentLst = new ArrayList<Integer>();
			Integer firstParent = idexSite.getFirstParentId();
			if (firstParent != null) {
				parentLst.add(idexSite.getFirstParentId());
			}
			List<Integer> compteurLst = idexSiteDAO.getListCompteurBySite(idexSite.getIdexSiteId());
			for (Integer idexCounterID : compteurLst) {
				if (firstParent != null && idexCounterID.equals(firstParent)) {
					continue;
				}
				parentLst.add(idexCounterID);
			}
			node.setParentLst(parentLst);
			consommationSiteLst.add(node);
		}

		tree.setName(idexInstallation.getName());
		tree.setIsUpdateable(idexInstallation.getIsUpdateable());

		tree.setEnergySupplyLst(energySupplyLst);
		tree.setPrimaryCompteurLst(primaryCompteurLst);
		tree.setProductionBoilerLst(productionBoilerLst);
		tree.setSortieCompteurLst(sortieCompteurLst);
		tree.setConsommationSiteLst(consommationSiteLst);
		tree.setUtileCompteurLst(utileCompteurLst);

		return tree;
	}

	private Float getConsommationByUnit(Float value, Integer unit) {
		if (unit == null) {
			return value;
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_K.equals(unit)) {
			return value;
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_M.equals(unit)) {
			return value / 1000;
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_G.equals(unit)) {
			return value / 1000000;
		}
		return value;
	}

	private String getConsommationUnitNameByUnit(Integer unit) {
		if (unit == null) {
			return "KWh";
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_K.equals(unit)) {
			return "KWh";
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_M.equals(unit)) {
			return "MWh";
		} else if (IdexConstant.LAB010_CONSOMMATION_UNIT_G.equals(unit)) {
			return "GWh";
		}
		return "KWh";
	}

	private String getMontalUnitNameByUnit(Integer unit) {
		if (unit == null) {
			return "";
		} else if (IdexConstant.LAB010_MONTAL_UNIT_E.equals(unit)) {
			return "";
		} else if (IdexConstant.LAB010_MONTAL_UNIT_KE.equals(unit)) {
			return "K";
		} else if (IdexConstant.LAB010_MONTAL_UNIT_MG.equals(unit)) {
			return "M";
		}
		return "";
	}

	/**
	 * 
	 * @param installationId
	 * @param fromMonth
	 *            yyyyMM
	 * @param toMonth
	 *            yyyyMM
	 * @return
	 */
	@RequestMapping(value = "getEnergyAnalysis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010EnergyAnalysis getEnergyAnalysis(Integer installationId, String fromMonth,
			String toMonth) {
		Date fromDate = null;
		if (fromMonth != null && !fromMonth.isEmpty()) {
			fromDate = LabUtils.convertDateByFormat(fromMonth + "01", FrontalKey.DATE_FORMAT_DAY_1);
		}
		Date toDate = null;
		if (toMonth != null && !toMonth.isEmpty()) {
			toDate = LabUtils.getLastDateOfMonth(toMonth);
		}

		IdexConfig config = idexConfigDAO.getByInstalltion(installationId);

		List<IdexCounter> counterLst = idexCounterDAO.findAllByInstallation(installationId);

		Lab010IdexRelevesBean lab010IdexRelevesBean = idexRelevesDAO.getSumMapByInstallation(installationId, fromDate,
				toDate);
		fromDate = lab010IdexRelevesBean.getFromDate();
		toDate = lab010IdexRelevesBean.getToDate();
		Map<Integer, Float> mapData = lab010IdexRelevesBean.getMapData();

		Lab010EnergyAnalysis energyAnalysis = new Lab010EnergyAnalysis();

		if (fromMonth == null || fromMonth.isEmpty()) {
			fromMonth = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		}
		energyAnalysis.setFromMonth(fromMonth);

		if (toMonth == null || toMonth.isEmpty()) {
			toMonth = LabUtils.convertDateByFormat(toDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		}
		energyAnalysis.setToMonth(toMonth);

		Integer realiseDju = idexMeteoDAO.sumByInstallation(installationId, fromDate, toDate);

		List<IdexBoiler> boierLst = idexBoilerDAO.findByInstallation(installationId);

		List<Lab010CompteurBean> primaireCompteurLst = new ArrayList<Lab010CompteurBean>();

		List<Lab010CompteurBean> sortieCompteurLst = new ArrayList<Lab010CompteurBean>();

		List<Lab010CompteurBean> utileCompteurLst = new ArrayList<Lab010CompteurBean>();

		Float sumPrimaryCompteur = 0f;
		Float sumSortieCompteur = 0f;
		Float sumUtileCompteur = 0f;
		Map<Integer, Lab010EnergyTypeBean> mapEnergyTypePrimary = new HashMap<Integer, Lab010EnergyTypeBean>();
		Map<Integer, Float> mapEnergyTypeSortie = new HashMap<Integer, Float>();

		Map<Integer, Lab010EnergyTypeBean> mapBoilerPrimary = new HashMap<Integer, Lab010EnergyTypeBean>();
		Map<Integer, Float> mapBoilerSortie = new HashMap<Integer, Float>();

		for (IdexBoiler idexBoiler : boierLst) {
			Integer primaryCounterID = null;
			if (idexBoiler.getIdexCounter() != null) {
				primaryCounterID = idexBoiler.getIdexCounter().getIdexCounterId();
			} else {
				primaryCounterID = -1;
			}
			Lab010EnergyTypeBean bean = new Lab010EnergyTypeBean();
			if (mapData.containsKey(primaryCounterID)) {
				bean.setConsommation(mapData.get(primaryCounterID));
				bean.setEnergyName(idexBoiler.getName());
			}
			mapBoilerPrimary.put(idexBoiler.getIdexBoilerId(), bean);
		}

		Map<Integer, String> mapCounterName = new HashMap<Integer, String>();
		for (IdexCounter idexCounter : counterLst) {
			mapCounterName.put(idexCounter.getIdexCounterId(), idexCounter.getName());
		}
		for (IdexCounter idexCounter : counterLst) {

			Float consommation = null;
			if (IdexConstant.LAB010_IS_VIRTUAL_COMPTEUR.equalsIgnoreCase(idexCounter.getCompteurVirtuel())) {
				consommation = CalculateUtils.calculateByFormular(idexCounter.getFormuleDeCalcul(), mapData,
						mapCounterName);
			} else {
				consommation = mapData.get(idexCounter.getIdexCounterId());
			}

			if (consommation == null) {
				consommation = 0f;
			}
			Lab010CompteurBean compteurBean = new Lab010CompteurBean();
			compteurBean.setName(idexCounter.getName());
			compteurBean.setCounterType(idexCounter.getCounterType());
			if (idexCounter.getCalculDuNc() != null && !idexCounter.getCalculDuNc().isEmpty()) {
				compteurBean.setNc(
						CalculateUtils.calculateByFormular(idexCounter.getCalculDuNc(), mapData, mapCounterName));
			} else {
				compteurBean.setNc(consommation);
			}

			if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())) {

				// Get total
				if (!idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
					sumPrimaryCompteur += consommation;
					if (idexCounter.getIdexEnergySupplier() != null) {
						Integer energyTypeId = idexCounter.getIdexEnergySupplier().getIdexEnergyType()
								.getIdexEnergyTypeId();
						// Chart
						if(energyTypeId != null){
							Lab010EnergyTypeBean energyTypeBean = null;
							if (mapEnergyTypePrimary.containsKey(energyTypeId)) {
								energyTypeBean = mapEnergyTypePrimary.get(energyTypeId);
							} else {
								energyTypeBean = new Lab010EnergyTypeBean();
								energyTypeBean.setEnergyTypeId(
										idexCounter.getIdexEnergySupplier().getIdexEnergyType().getIdexEnergyTypeId());
								energyTypeBean
										.setEnergyName(idexCounter.getIdexEnergySupplier().getIdexEnergyType().getName());
							}

							if (energyTypeBean.getConsommation() == null) {
								energyTypeBean.setConsommation(consommation);
							} else {
								energyTypeBean.setConsommation(consommation + energyTypeBean.getConsommation());
							}
							mapEnergyTypePrimary.put(energyTypeId, energyTypeBean);
						}
					}
				}
				primaireCompteurLst.add(compteurBean);
			} else if (IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())) {
				if (!idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {

					sumSortieCompteur += consommation;
//					Integer primaryCounterID = null;
					IdexCounter primaryCounter = idexCounter.getIdexBoiler().getIdexCounter();
//					if (primaryCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
//						primaryCounterID = primaryCounter.getIdexCounter().getIdexCounterId();
//					} else {
//						primaryCounterID = primaryCounter.getIdexCounterId();
//					}

					if (primaryCounter.getIdexEnergySupplier() != null) {
						Integer energyTypeID = primaryCounter.getIdexEnergySupplier().getIdexEnergyType()
								.getIdexEnergyTypeId();
						Float consommationSortie = null;
						if (mapData.containsKey(idexCounter.getIdexCounterId())) {
							if (mapEnergyTypeSortie.containsKey(energyTypeID)) {
								consommationSortie = mapEnergyTypeSortie.get(energyTypeID)
										+ mapData.get(idexCounter.getIdexCounterId());
							} else {
								consommationSortie = mapData.get(idexCounter.getIdexCounterId());
							}
							if (consommationSortie == null) {
								consommationSortie = 0f;
							}
							mapEnergyTypeSortie.put(energyTypeID, consommationSortie);
						}
					}
					if (idexCounter.getIdexBoiler() != null) {
						mapBoilerSortie.put(idexCounter.getIdexBoiler().getIdexBoilerId(), consommation);
					}
				}

				sortieCompteurLst.add(compteurBean);
			} else if (IdexConstant.LAB010_COUPTER_UTILE.equals(idexCounter.getCounterType())) {
				if (!idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
					sumUtileCompteur += consommation;
				}
				utileCompteurLst.add(compteurBean);
			}
		}

		// Add chart data;
		Integer sumPercent = 0;
		Integer size = mapEnergyTypePrimary.size();
		int count = 0;

		List<Lab010EnergyTypeBean> energyTypeBeanLst = new ArrayList<Lab010EnergyTypeBean>();
		for (Map.Entry<Integer, Lab010EnergyTypeBean> entry : mapEnergyTypePrimary.entrySet()) {
			count++;
			Lab010EnergyTypeBean bean = entry.getValue();
			Integer percent = null;
			if (count == size) {
				percent = 100 - sumPercent;
			} else {
				percent = getPercent(bean.getConsommation(), sumPrimaryCompteur);
			}
			if(percent != null){
				sumPercent += percent;
				bean.setRate(percent);
			}
			bean.setConsommation(getConsommationByUnit(bean.getConsommation(), config.getUnitConsommation()));
			energyTypeBeanLst.add(bean);
		}

		List<Lab010BoilerBean> transformationEnergyTypeLst = new ArrayList<Lab010BoilerBean>();
		for (Map.Entry<Integer, Lab010EnergyTypeBean> entry : mapEnergyTypePrimary.entrySet()) {
			Integer energyType = entry.getKey();
			Lab010EnergyTypeBean value = entry.getValue();
			Lab010BoilerBean bean = new Lab010BoilerBean();
			bean.setName(value.getEnergyName());
			if (mapEnergyTypeSortie.containsKey(energyType) && value.getConsommation() != 0) {
				bean.setRate((int) (mapEnergyTypeSortie.get(energyType) / value.getConsommation() * 100));
			}

			transformationEnergyTypeLst.add(bean);
		}

		List<Lab010BoilerBean> listBoilerBean = new ArrayList<Lab010BoilerBean>();
		for (Map.Entry<Integer, Lab010EnergyTypeBean> entry : mapBoilerPrimary.entrySet()) {
			Lab010BoilerBean bean = new Lab010BoilerBean();
			bean.setName(entry.getValue().getEnergyName());
			Integer rate = null;
			if (mapBoilerSortie.containsKey(entry.getKey()) && entry.getValue().getConsommation() != null
					&& entry.getValue().getConsommation() != 0) {
				rate = (int) (mapBoilerSortie.get(entry.getKey()) / entry.getValue().getConsommation() * 100);
			}
			bean.setRate(rate);
			listBoilerBean.add(bean);
		}

		energyAnalysis.setTotalUnit("MWh");
		energyAnalysis.setMixEnergyTotal(getConsommationByUnit(sumPrimaryCompteur, config.getUnitConsommation()));
		energyAnalysis.setnCTotal(getConsommationByUnit(sumUtileCompteur, config.getUnitConsommation()));

		energyAnalysis.setContractuelNB(config.getContractNB());
		energyAnalysis.setContractuelDJU(config.getContractDju());
		energyAnalysis.setRealiseDJU(realiseDju);
		if (config.getContractNB() != null & config.getContractDju() != null && realiseDju != null) {
			energyAnalysis.setRealiseNB(realiseDju * config.getContractNB() / config.getContractDju());
		}

		// Rendements
		energyAnalysis.setTransformation(getPercent(sumSortieCompteur, sumPrimaryCompteur));
		energyAnalysis.setDistribution(getPercent(sumUtileCompteur, sumSortieCompteur));
		energyAnalysis.setGlobal(getPercent(sumUtileCompteur, sumPrimaryCompteur));

		if (energyAnalysis.getRealiseDJU() != null && energyAnalysis.getRealiseDJU() != 0) {
			if (energyAnalysis.getRealiseNB() != null) {
				energyAnalysis
						.setInstallationNB_DJU((float) energyAnalysis.getRealiseNB() / energyAnalysis.getRealiseDJU());
			}

			if (energyAnalysis.getnCTotal() != null && energyAnalysis.getRealiseNB() != null
					&& energyAnalysis.getRealiseNB() != 0) {
				energyAnalysis.setInstallationNC_NB(energyAnalysis.getnCTotal() / energyAnalysis.getRealiseNB());
			}

			if (energyAnalysis.getnCTotal() != null) {
				energyAnalysis.setInstallationNC_DJU(energyAnalysis.getnCTotal() / energyAnalysis.getRealiseDJU());
			}
		}

		energyAnalysis.setTransformationEnergyTypeLst(transformationEnergyTypeLst);

		energyAnalysis.setAttachment("test");
		energyAnalysis.setBoilerBeanLst(listBoilerBean);
		energyAnalysis.setEnergyTypeBeanLst(energyTypeBeanLst);
		energyAnalysis.setPertesReseau((int) (sumSortieCompteur - sumUtileCompteur));
		energyAnalysis.setPrimaireCompteurLst(primaireCompteurLst);
		energyAnalysis.setSortieCompteurLst(sortieCompteurLst);
		energyAnalysis.setUtileCompteurLst(utileCompteurLst);

		return energyAnalysis;
	}

	private Integer getPercent(Float value, Float total) {
		if (total == null || total == 0 || value == null) {
			return null;
		}
		return Math.round(value / total * 100);
	}
	
	/**
	 * 
	 * @param installationId
	 * @param fromMonth
	 *            yyyyMM
	 * @param toMonth
	 *            yyyyMM
	 * @return
	 */
	@RequestMapping(value = "getCostAnalysis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010CostAnalysis getCostAnalysis(Integer installationId, String fromMonth, String toMonth) {

		IdexConfig idexConfig = idexConfigDAO.getByInstalltion(installationId);

		Date fromDate = null;
		if (fromMonth != null && !fromMonth.isEmpty()) {
			fromDate = LabUtils.convertDateByFormat(fromMonth + "01", FrontalKey.DATE_FORMAT_DAY_1);
		}

		Date toDate = null;
		if (toMonth != null && !toMonth.isEmpty()) {
			toDate = LabUtils.getLastDateOfMonth(toMonth);
		}

		List<IdexCost> idexCostLst = idexCostDAO.findByInstallation(installationId);
		Lab010CostAnalysis bean = new Lab010CostAnalysis();
		bean.setMontalUnit("");
		if (idexCostLst.isEmpty()) {
			return bean;
		}
		Date minDate = null;
		Date maxDate = null;

		Map<Integer, Lab010EnergyTypeBean> energyTypeBeanMap = new HashMap<Integer, Lab010EnergyTypeBean>();
		Map<Integer, Lab010SiteBean> siteInBeanMap = new HashMap<Integer, Lab010SiteBean>();
//		Map<Integer, Lab010SiteBean> siteOutBeanMap = new HashMap<Integer, Lab010SiteBean>();

		Float totalAchart = 0f;
		Float totalVente = 0f;
		Map<Integer, List<IdexCostDetail>> mapDetail = idexCostDAO.getDetailByInstallation(installationId, fromDate,
				toDate);
		for (IdexCost idexCost : idexCostLst) {

			// Iterator<IdexCostDetail> idexCostDetailIt =
			// idexCost.getIdexCostDetails().iterator();
			float sumIdexCode = 0f;
			if (idexCost.getIdexEnergySupplier() != null) {
				Integer energyTypeID = idexCost.getIdexEnergySupplier().getIdexEnergyType().getIdexEnergyTypeId();

				Lab010EnergyTypeBean beanEnergy = null;
				if (energyTypeBeanMap.containsKey(energyTypeID)) {
					beanEnergy = energyTypeBeanMap.get(energyTypeID);
				} else {
					beanEnergy = new Lab010EnergyTypeBean();
					beanEnergy.setEnergyTypeId(energyTypeID);
					beanEnergy.setEnergyName(idexCost.getIdexEnergySupplier().getIdexEnergyType().getName());
				}
				List<IdexCostDetail> lstCostDetail = mapDetail.get(idexCost.getIdexCostId());

				if (lstCostDetail != null) {
					for (IdexCostDetail idexCostDetail : lstCostDetail) {
						if (minDate == null || minDate.compareTo(idexCostDetail.getDetailDate()) > 0) {
							minDate = idexCostDetail.getDetailDate();
						}

						if (maxDate == null || maxDate.compareTo(idexCostDetail.getDetailDate()) < 0) {
							maxDate = idexCostDetail.getDetailDate();
						}
						Float amount = idexCostDetail.getAmount();
						totalAchart += amount;
						sumIdexCode += amount;

						if (beanEnergy.getMontal() == null) {
							beanEnergy.setMontal(amount);
						} else {
							beanEnergy.setMontal(amount + beanEnergy.getMontal());
						}
					}
				}

				List<Object[]> subLst = beanEnergy.getSubLst();
				if (subLst == null) {
					subLst = new ArrayList<Object[]>();
				}
				subLst.add(new Object[] { idexCost.getName(), sumIdexCode });
				beanEnergy.setSubLst(subLst);
				energyTypeBeanMap.put(energyTypeID, beanEnergy);
			} else if (idexCost.getIdexSiteIn() != null) {

				Lab010SiteBean lab010SiteBean = null;
				if (siteInBeanMap.containsKey(idexCost.getIdexSiteIn().getIdexSiteId())) {
					lab010SiteBean = siteInBeanMap.get(idexCost.getIdexSiteIn().getIdexSiteId());
				} else {
					lab010SiteBean = new Lab010SiteBean();
					lab010SiteBean.setSiteId(idexCost.getIdexSiteIn().getIdexSiteId());
					lab010SiteBean.setSiteName(idexCost.getIdexSiteIn().getName());
				}
				List<IdexCostDetail> lstCostDetail = mapDetail.get(idexCost.getIdexCostId());

				if (lstCostDetail != null) {
					for (IdexCostDetail idexCostDetail : lstCostDetail) {
						if (minDate == null || minDate.compareTo(idexCostDetail.getDetailDate()) > 0) {
							minDate = idexCostDetail.getDetailDate();
						}

						if (maxDate == null || maxDate.compareTo(idexCostDetail.getDetailDate()) < 0) {
							maxDate = idexCostDetail.getDetailDate();
						}
						Float amount = idexCostDetail.getAmount();
						totalVente += amount;

						if (lab010SiteBean.getCost() == null) {
							lab010SiteBean.setCost(amount);
						} else {
							lab010SiteBean.setCost(amount + lab010SiteBean.getCost());
						}
						siteInBeanMap.put(idexCost.getIdexSiteIn().getIdexSiteId(), lab010SiteBean);
					}
				}
			} 
//			else if (idexCost.getIdexSiteOut() != null) {
//
//				Lab010SiteBean lab010SiteBean = null;
//				if (siteOutBeanMap.containsKey(idexCost.getIdexSiteOut().getIdexSiteId())) {
//					lab010SiteBean = siteOutBeanMap.get(idexCost.getIdexSiteOut().getIdexSiteId());
//				} else {
//					lab010SiteBean = new Lab010SiteBean();
//					lab010SiteBean.setSiteId(idexCost.getIdexSiteOut().getIdexSiteId());
//					lab010SiteBean.setSiteName(idexCost.getIdexSiteOut().getName());
//				}
//				List<IdexCostDetail> lstCostDetail = mapDetail.get(idexCost.getIdexCostId());
//
//				if (lstCostDetail != null) {
//					for (IdexCostDetail idexCostDetail : lstCostDetail) {
//						if (minDate == null || minDate.compareTo(idexCostDetail.getDetailDate()) > 0) {
//							minDate = idexCostDetail.getDetailDate();
//						}
//
//						if (maxDate == null || maxDate.compareTo(idexCostDetail.getDetailDate()) < 0) {
//							maxDate = idexCostDetail.getDetailDate();
//						}
//						Float amount = idexCostDetail.getAmount();
//						if (lab010SiteBean.getCost() == null) {
//							lab010SiteBean.setCost(amount);
//						} else {
//							lab010SiteBean.setCost(amount + lab010SiteBean.getCost());
//						}
//						siteOutBeanMap.put(idexCost.getIdexSiteOut().getIdexSiteId(), lab010SiteBean);
//					}
//				}
//			}
		}

		// set maxdate mindate
		if (fromMonth == null || fromMonth.isEmpty()) {
			fromMonth = LabUtils.convertDateByFormat(minDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		}
		bean.setFromMonth(fromMonth);

		if (toMonth == null || toMonth.isEmpty()) {
			toMonth = LabUtils.convertDateByFormat(maxDate, FrontalKey.DATE_FORMAT_MONTH_SHORT);
		}
		bean.setToMonth(toMonth);

		List<Lab010EnergyTypeBean> energyTypeBeanLst = new ArrayList<Lab010EnergyTypeBean>();

		int size = energyTypeBeanMap.size();
		int count = 0;
		int totalPercent = 0;
		for (Map.Entry<Integer, Lab010EnergyTypeBean> entry : energyTypeBeanMap.entrySet()) {
			count++;
			Lab010EnergyTypeBean tmp = entry.getValue();
			if(tmp.getMontal() == null || tmp.getMontal() < 0){
				tmp.setMontal(0f);
			}
			if (count == size) {
				tmp.setRate(100 - totalPercent);
			} else {
				int rate = getPercent(tmp.getMontal(), totalAchart);
				totalPercent += rate;
				tmp.setRate(rate);

			}
			energyTypeBeanLst.add(tmp);
		}

		List<Lab010SiteBean> siteBeanLst = new ArrayList<Lab010SiteBean>();

		size = siteInBeanMap.size();
		count = 0;
		totalPercent = 0;
		for (Map.Entry<Integer, Lab010SiteBean> entry : siteInBeanMap.entrySet()) {
			count++;
			Lab010SiteBean tmp = entry.getValue();
			if (count == size) {

				tmp.setRate(100 - totalPercent);
			} else {
				int rate = getPercent(tmp.getCost(), totalVente);
				totalPercent += rate;
				tmp.setRate(rate);
			}
			siteBeanLst.add(tmp);
		}

		List<Lab010SiteBean> clientBeanLst = new ArrayList<Lab010SiteBean>();

		for (Map.Entry<Integer, Lab010SiteBean> entry : siteInBeanMap.entrySet()) {
			clientBeanLst.add(entry.getValue());
		}

		bean.setTotalAchart(totalAchart);
		bean.setMontalUnit(getMontalUnitNameByUnit(idexConfig.getUnitMontal()));
		bean.setEnergyTypeBeanLst(energyTypeBeanLst);
		bean.setClientBeanLst(siteBeanLst);
		bean.setSiteBeanLst(siteBeanLst);
		bean.setTotalVente(totalVente);
		bean.setTotalBilan(totalVente - totalAchart);

		return bean;
	}

	@RequestMapping(value = "getDataPopupCost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010CostChartBean getDataPopupCost(Integer installationId,String fromMonth, String toMonth) {
		Lab010CostChartBean bean = new Lab010CostChartBean();
		
		IdexConfig idexConfig = idexConfigDAO.getByInstalltion(installationId);

		Date fromDate = null;
		if (fromMonth != null && !fromMonth.isEmpty()) {
			fromDate = LabUtils.convertDateByFormat(fromMonth + "01", FrontalKey.DATE_FORMAT_DAY_1);
		}

		Date toDate = null;
		if (toMonth != null && !toMonth.isEmpty()) {
			toDate = LabUtils.getLastDateOfMonth(toMonth);
		}

		List<IdexCost> idexCostLst = idexCostDAO.findByInstallation(installationId);
		bean.setMontalUnit("");
		if (idexCostLst.isEmpty()) {
			return bean;
		}

		Map<Integer, Lab010CategoryCostBean> energyTypeBeanMap = new HashMap<Integer, Lab010CategoryCostBean>();
		Map<Integer, Lab010CategoryCostBean> siteInBeanMap = new HashMap<Integer, Lab010CategoryCostBean>();

		Map<Integer, List<IdexCostDetail>> mapDetail = idexCostDAO.getDetailByInstallation(installationId, fromDate,
				toDate);
		for (IdexCost idexCost : idexCostLst) {

			if (idexCost.getIdexEnergySupplier() != null) {
				Integer energyTypeID = idexCost.getIdexEnergySupplier().getIdexEnergyType().getIdexEnergyTypeId();

				Lab010CategoryCostBean beanEnergy = null;
				if (energyTypeBeanMap.containsKey(energyTypeID)) {
					beanEnergy = energyTypeBeanMap.get(energyTypeID);
				} else {
					beanEnergy = new Lab010CategoryCostBean();
					beanEnergy.setId(energyTypeID);
					beanEnergy.setName(idexCost.getIdexEnergySupplier().getIdexEnergyType().getName());
				}
				
				List<Lab010SubCategory> subCategoryLst = beanEnergy.getSubCategoryLst();
				if(subCategoryLst == null){
					subCategoryLst = new ArrayList<Lab010SubCategory>();
				}
				
				Lab010SubCategory lab010SubCategory = new Lab010SubCategory();
				lab010SubCategory.setId(idexCost.getIdexCostId());
				lab010SubCategory.setName(idexCost.getName());
			
				List<IdexCostDetail> lstCostDetail = mapDetail.get(idexCost.getIdexCostId());

				if (lstCostDetail != null) {
					List<Object[]> dataLst = new ArrayList<Object[]>();
					for (IdexCostDetail idexCostDetail : lstCostDetail) {
						Float amount = idexCostDetail.getAmount();
						if(idexCostDetail.getDetailDate() != null && amount != null )
						dataLst.add(new Object[]{LabUtils.getTimeInUTC(idexCostDetail.getDetailDate()),amount});
					}
					
					lab010SubCategory.setDataLst(dataLst);
				}
				subCategoryLst.add(lab010SubCategory);
				beanEnergy.setSubCategoryLst(subCategoryLst);
				energyTypeBeanMap.put(energyTypeID, beanEnergy);
			} else if (idexCost.getIdexSiteIn() != null) {

				Integer idexSiteId = idexCost.getIdexSiteIn().getIdexSiteId();

				Lab010CategoryCostBean beanSite = null;
				if (siteInBeanMap.containsKey(idexSiteId)) {
					beanSite = siteInBeanMap.get(idexSiteId);
				} else {
					beanSite = new Lab010CategoryCostBean();
					beanSite.setId(idexSiteId);
					beanSite.setName(idexCost.getIdexSiteIn().getName());
				}
				
				List<Lab010SubCategory> subCategoryLst = beanSite.getSubCategoryLst();
				if(subCategoryLst == null){
					subCategoryLst = new ArrayList<Lab010SubCategory>();
				}
				
				Lab010SubCategory lab010SubCategory = new Lab010SubCategory();
				lab010SubCategory.setId(idexCost.getIdexCostId());
				lab010SubCategory.setName(idexCost.getName());
			
				List<IdexCostDetail> lstCostDetail = mapDetail.get(idexCost.getIdexCostId());

				if (lstCostDetail != null) {
					List<Object[]> dataLst = new ArrayList<Object[]>();
					for (IdexCostDetail idexCostDetail : lstCostDetail) {
						Float amount = idexCostDetail.getAmount();
						if(idexCostDetail.getDetailDate() != null && amount != null )
						dataLst.add(new Object[]{LabUtils.getTimeInUTC(idexCostDetail.getDetailDate()),amount});
					}
					
					lab010SubCategory.setDataLst(dataLst);
				}
				subCategoryLst.add(lab010SubCategory);
				beanSite.setSubCategoryLst(subCategoryLst);
				siteInBeanMap.put(idexSiteId, beanSite);
			} 
		}
			
		List<Lab010CategoryCostBean> energyPurchasedLst = new ArrayList<Lab010CategoryCostBean>();
		for(Map.Entry<Integer, Lab010CategoryCostBean> entry: energyTypeBeanMap.entrySet()){
			energyPurchasedLst.add(entry.getValue());
		}
		
		List<Lab010CategoryCostBean> heatSoldLst = new ArrayList<Lab010CategoryCostBean>();
		for(Map.Entry<Integer, Lab010CategoryCostBean> entry: siteInBeanMap.entrySet()){
			heatSoldLst.add(entry.getValue());
		}
		
		
		bean.setEnergyPurchasedLst(energyPurchasedLst);
		bean.setHeatSoldLst(heatSoldLst);
		bean.setMontalUnit(getMontalUnitNameByUnit(idexConfig.getUnitMontal()));
		return bean;
	}
	/**
	 * 
	 * @param coupteurIDLst
	 * @param fromMonth
	 * @param toMonth
	 * @return
	 */
	@RequestMapping(value = "getDataPopup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010DataChart getDataPopup(Integer installationId, Integer type, Integer nodeId,
			String fromMonth, String toMonth) {

		IdexConfig idexConfig = idexConfigDAO.getByInstalltion(installationId);

		List<IdexCounter> coupteurLst = idexCounterDAO.findAllByInstallation(installationId);
		List<IdexBoiler> idexBoilerLst = idexBoilerDAO.findByInstallation(installationId);
		List<Integer> coupteurIDLst = new ArrayList<Integer>();
		List<Lab010CompteurBean> compteurBeanLst = new ArrayList<Lab010CompteurBean>();
		Map<Integer, String> mapCompteurName = new HashMap<Integer, String>();
		for (IdexCounter idexCounter : coupteurLst) {
			coupteurIDLst.add(idexCounter.getIdexCounterId());
			mapCompteurName.put(idexCounter.getIdexCounterId(), idexCounter.getName());
			if (!IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE.equals(idexCounter.getIsSubModule())) {
				Lab010CompteurBean bean = new Lab010CompteurBean();
				bean.setIdexCounterId(idexCounter.getIdexCounterId());
				bean.setName(idexCounter.getName());
				bean.setIsSubModule(idexCounter.getIsSubModule());
				bean.setCounterType(idexCounter.getCounterType());
				bean.setIsSelected(false);

				if (type == 1) {
					if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())
							&& idexCounter.getIdexEnergySupplier().getIdexEnergySupplierId().equals(nodeId)) {
						bean.setIsSelected(true);
					}
				} else if (type == 2 || type == 4 || type == 6) {
					if (idexCounter.getIdexCounterId().equals(nodeId)) {
						bean.setIsSelected(true);
					}
				} else if (type == 3) {
					if ((IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())
							&& (idexCounter.getIdexBoiler().getIdexBoilerId().equals(nodeId)))) {
						bean.setIsSelected(true);
					} else if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())) {
						for (IdexBoiler idexBoiler : idexBoilerLst) {
							if (idexBoiler.getIdexBoilerId().equals(nodeId)) {
								if(idexBoiler.getIdexCounter() != null){
									if(idexBoiler.getIdexCounter().getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)){
										if(idexBoiler.getIdexCounter().getIdexCounter() != null){
											if(idexBoiler.getIdexCounter().getIdexCounter().getIdexCounterId().equals(idexCounter.getIdexCounterId())){
												bean.setIsSelected(true);
											}
										}
									}else{
										if(idexBoiler.getIdexCounter().getIdexCounterId().equals(idexCounter.getIdexCounterId())){
											bean.setIsSelected(true);
										}
									}
								}
								break;
							}
						}
					}
				} else if (type == 5) {
					if ((IdexConstant.LAB010_COUPTER_UTILE.equals(idexCounter.getCounterType())
							&& (idexCounter.getIdexSite().getIdexSiteId() == nodeId))) {
						bean.setIsSelected(true);
					}
				} else {
					bean.setIsSelected(true);
				}

				compteurBeanLst.add(bean);
			}
		}

		Lab010DataChart dataChart = new Lab010DataChart();
		dataChart.setCompteurLst(compteurBeanLst);

		if (fromMonth == null || fromMonth.isEmpty()) {
			return dataChart;
		}
		if (toMonth == null || toMonth.isEmpty()) {
			return dataChart;
		}

		// gan counter con vao cac conuter cha
		for (IdexCounter idexCounter : coupteurLst) {
			if (IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE.equals(idexCounter.getIsSubModule())) {
				Lab010CompteurBean beanChild = new Lab010CompteurBean();
				beanChild.setIdexCounterId(idexCounter.getIdexCounterId());
				beanChild.setName(idexCounter.getName());
				beanChild.setIsSubModule(idexCounter.getIsSubModule());
				beanChild.setCounterType(idexCounter.getCounterType());
				beanChild.setIsSelected(false);
				List<Lab010CompteurBean> childLst = null;
				for (Lab010CompteurBean bean : compteurBeanLst) {
					if (bean.getIdexCounterId().equals(idexCounter.getIdexCounter().getIdexCounterId())) {

						if (type == 1 || type == 3 || type == 5 || type == 7) {
							beanChild.setIsSelected(bean.getIsSelected());
						} else {
							if (idexCounter.getIdexCounterId().equals(nodeId)) {
								beanChild.setIsSelected(true);
							}
						}
						if (bean.getChildLst() == null) {
							childLst = new ArrayList<Lab010CompteurBean>();
						} else {
							childLst = bean.getChildLst();
						}
						childLst.add(beanChild);
						bean.setChildLst(childLst);
						break;
					}
				}
			}
		}

		// Lay du lieu
		List<Lab010DataSeriesChart> lst = new ArrayList<Lab010DataSeriesChart>();
		Date startDate = LabUtils.convertDateByFormat(fromMonth + "01", FrontalKey.DATE_FORMAT_DAY_1);
		Date endDate = LabUtils.getLastDateOfMonth(toMonth);
		Map<Integer, List<IdexReleves>> dataMap = idexRelevesDAO.getMapByCompteur(coupteurIDLst);
		
		for (IdexCounter idexCounter : coupteurLst) {
			Lab010DataSeriesChart series = new Lab010DataSeriesChart();
			series.setCouterType(idexCounter.getCounterType());
			series.setIsSubModule(idexCounter.getIsSubModule());
			if (idexCounter.getIdexCounter() != null) {
				series.setParentId(idexCounter.getIdexCounter().getIdexCounterId());
			}
			Integer idexCounterId = idexCounter.getIdexCounterId();
			series.setID(idexCounterId);
			series.setName(idexCounter.getName());
			List<IdexReleves> dataLst = null;
			Map<Long,Float> mapTmp = new TreeMap<Long, Float>();
			
			Date tmpDate = new Date(startDate.getTime());
			while(tmpDate.compareTo(endDate) < 0){
				mapTmp.put(tmpDate.getTime(), 0f);
				tmpDate = LabUtils.addMonth(tmpDate, 1);
			}
			if (IdexConstant.LAB010_IS_VIRTUAL_COMPTEUR.equalsIgnoreCase(idexCounter.getCompteurVirtuel())) {
				Map<Long, Map<Integer, Float>> mapTimeData = new TreeMap<Long, Map<Integer, Float>>();

				for (Map.Entry<Integer, List<IdexReleves>> entry : dataMap.entrySet()) {
					Integer id = entry.getKey();
					List<IdexReleves> idexRelevesLst = entry.getValue();
					Float beforeValue = null;
					for (IdexReleves idexReleves : idexRelevesLst) {
						Date d = idexReleves.getMonth();
						if (d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0) {
							Long time = LabUtils.getTimeInUTC(d);
							float consommation = 0f;
							if (beforeValue != null &&  idexReleves.getValue() != null) {
								consommation = idexReleves.getValue().floatValue() - beforeValue;
								if (consommation < 0) {
									consommation = 0f;
								}
							}

							if (idexReleves.getIdexCounter().getCoefficientDeConversion() != null) {
								consommation = consommation * idexReleves.getIdexCounter().getCoefficientDeConversion();
							}
							if (idexReleves.getIdexCounter().getCoefficientEnergetique() != null) {
								consommation = consommation * idexReleves.getIdexCounter().getCoefficientEnergetique();
							}
							
							consommation = getConsommationByUnit(consommation, idexConfig.getUnitConsommation());
							Map<Integer, Float> mapValue = null;
							if (mapTimeData.containsKey(time)) {
								mapValue = mapTimeData.get(time);
							} else {
								mapValue = new HashMap<Integer, Float>();
							}
							if (mapValue.containsKey(id)) {
								mapValue.put(id, mapValue.get(id) + consommation);
							} else {
								mapValue.put(id, consommation);
							}
							mapTimeData.put(time, mapValue);

						}
						
						if(idexReleves.getValue() != null){
							beforeValue = idexReleves.getValue().floatValue();
						}
					}
				}

				for (Map.Entry<Long, Map<Integer, Float>> entry : mapTimeData.entrySet()) {
					Float value = CalculateUtils.calculateByFormular(idexCounter.getFormuleDeCalcul(), entry.getValue(),
							mapCompteurName);
					if (value != null) {
						mapTmp.put(entry.getKey(), value);
					}
				}

			} else {
				dataLst = dataMap.get(idexCounterId);
				Float beforeValue = null;
				if (dataLst != null) {
					for (IdexReleves idexReleves : dataLst) {
						Date d = idexReleves.getMonth();
						if (d.compareTo(startDate) >= 0 && d.compareTo(endDate) <= 0) {
							float consommation = 0f;
							if (beforeValue != null && idexReleves.getValue() != null) {
								consommation = idexReleves.getValue().floatValue() - beforeValue;
							}
							if(consommation < 0){
								consommation = 0f; 
							}
							if (idexReleves.getIdexCounter().getCoefficientDeConversion() != null) {
								consommation = consommation * idexReleves.getIdexCounter().getCoefficientDeConversion();
							}
							if (idexReleves.getIdexCounter().getCoefficientEnergetique() != null) {
								consommation = consommation * idexReleves.getIdexCounter().getCoefficientEnergetique();
							}
							consommation = getConsommationByUnit(consommation, idexConfig.getUnitConsommation());
							mapTmp.put(LabUtils.getTimeInUTC(d), consommation);
						}
						
						if(idexReleves.getValue() != null){
							beforeValue = idexReleves.getValue().floatValue();
						}
					}
				}
			}

			List<Object[]> lstData = new ArrayList<Object[]>();
			for(Map.Entry<Long, Float> entry: mapTmp.entrySet()){
				lstData.add(new Object[]{entry.getKey(),entry.getValue()});
			}
			series.setLstData(lstData);
			lst.add(series);

		}

		dataChart.setDataSeriesChartLst(lst);
//		dataChart.setUnit(getConsommationUnitNameByUnit(idexConfig.getUnitConsommation()));
		dataChart.setUnit("MWh");
		return dataChart;
	}

	@RequestMapping(value = "/deleteNodeInfo/installationId/{installationId}/nodeId/{nodeId}/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Integer deleteNode(@PathVariable("installationId") Integer installationId,
			@PathVariable("nodeId") Integer nodeId, @PathVariable("type") Integer type) {
		System.out.println("Istallation : node: type " + installationId + ": " + nodeId + ": " + type);
		Integer result = idexInstallationDAO.deleteNode(installationId, nodeId, type);

		return result;
	}

	@RequestMapping(value = "/addNodeInfo/insallationId/{insallationId}/type/{type}/parentId/{parentId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public @ResponseBody Integer addNode(@RequestBody NodeInfo nodeInfo,
			@PathVariable("insallationId") Integer insallationId, @PathVariable("type") Integer type,
			@PathVariable("parentId") Integer parentId) {
		System.out.println("installationId: " + insallationId + "- type: " + type + " - parentId: " + parentId);
		return idexInstallationDAO.addNode(nodeInfo, insallationId, type, parentId);
	}

	@RequestMapping(value = "/getNodeInfo/installationId/{installationId}/nodeId/{nodeId}/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody NodeInfo getNode(@PathVariable("installationId") Integer installationId,
			@PathVariable("nodeId") Integer nodeId, @PathVariable("type") Integer type) {
		System.out.println("Istallation : node: type " + installationId + ": " + nodeId + ": " + type);
		NodeInfo install = idexInstallationDAO.getNode(installationId, nodeId, type);

		return install;
	}

	@RequestMapping(value = "/getCompteurInfo/installationId/{installationId}/compteurId/{compteurId}/compteurType/{compteurType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010CompteurInfo getCompteurInfo(@PathVariable("installationId") Integer installationId,
			@PathVariable("compteurId") Integer compteurId, @PathVariable("compteurType") Integer compteurType) {
		Lab010CompteurInfo compteurInfo = new Lab010CompteurInfo();
		IdexCounter idexCounter = idexCounterDAO.findById(compteurId);
		compteurInfo.setCompterName(idexCounter.getName());
		compteurInfo.setIdexCounterId(idexCounter.getIdexCounterId());
		if (idexCounter.getNb() != null) {
			compteurInfo.setNb(Integer.parseInt(idexCounter.getNb()));
		}
		if (idexCounter.getCalculDuNc() != null && !idexCounter.getCalculDuNc().isEmpty()) {
			compteurInfo.setCalculDuNc(idexCounter.getCalculDuNc());
		}
		if (idexCounter.getFormuleDeCalcul() != null && !idexCounter.getFormuleDeCalcul().isEmpty()) {
			compteurInfo.setFormuleDeCalcul(idexCounter.getFormuleDeCalcul());
		}
		NodeInfo nodeInfo = idexInstallationDAO.getNode(installationId, compteurId, IdexConstant.COUNTER_TYPE);
		compteurInfo.setNodeInfo(nodeInfo);
		return compteurInfo;
	}

	@RequestMapping(value = "/saveCompteurInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010CompteurInfo saveCompteurInfo(@RequestBody Lab010CompteurInfo compteurInfo) {
		IdexCounter idexCounter = idexCounterDAO.findById(compteurInfo.getIdexCounterId());
		if (idexCounter != null) {
			if (compteurInfo.getNb() != null) {
				idexCounter.setNb(String.valueOf(compteurInfo.getNb()));
			} else {
				idexCounter.setNb(null);
			}
			if (compteurInfo.getCalculDuNc() != null && !compteurInfo.getCalculDuNc().isEmpty()) {
				idexCounter.setCalculDuNc(compteurInfo.getCalculDuNc());
			} else {
				idexCounter.setCalculDuNc(null);
			}
			if (compteurInfo.getFormuleDeCalcul() != null && !compteurInfo.getFormuleDeCalcul().isEmpty()) {
				idexCounter.setFormuleDeCalcul(compteurInfo.getFormuleDeCalcul());
			} else {
				idexCounter.setFormuleDeCalcul(null);
			}
			NodeInfo nodeInfo = compteurInfo.getNodeInfo();
			nodeInfo.setNodeId(idexCounter.getIdexCounterId());
			nodeInfo.setType(IdexConstant.COUNTER_TYPE);
			for (InstallationOneExelRowData row : nodeInfo.getData()) {
				if (row.getKeyId() == IdexConstant.KEY_COMPTEUR_VIRTUEL) {
					idexCounter.setCompteurVirtuel(row.getValue().trim());
				}
			}

			idexCounterDAO.saveIdexCounter(idexCounter);
			idexInstallationDAO.addNode(compteurInfo.getNodeInfo(), compteurInfo.getIdexInstallationId(),
					IdexConstant.COUNTER_TYPE, 0);
		}
		return compteurInfo;
	}

	@RequestMapping(value = "getInstallationByKey", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<IdexInstallationSearchBean> getInstallationByKey(@RequestParam Integer keyId1,
			@RequestParam String text1, @RequestParam Integer keyId2, @RequestParam String text2) {
		List<IdexInstallationSearchBean> bean = new ArrayList<IdexInstallationSearchBean>();
		List<IdexInstallation> idexInstallationLst = new ArrayList<IdexInstallation>();

		if (keyId1 != 0 && (keyId2 == 0 || keyId2 == null)) {
			idexInstallationLst = idexValueDAO.getIdexInstallation(keyId1, text1);
			for (IdexInstallation idex : idexInstallationLst) {
				IdexInstallationSearchBean idexBean = new IdexInstallationSearchBean();
				idexBean.setInstallationId(idex.getIdexInstallationId());
				idexBean.setName(idex.getName());
				idexBean.setCode(idex.getCodeDeLinstallation());
				idexBean.setLat(idex.getLat());
				idexBean.setLng(idex.getLng());
				bean.add(idexBean);
			}
		}
		if ((keyId1 == 0 || keyId1 == null) && keyId2 != 0) {
			idexInstallationLst = idexValueDAO.getIdexInstallation(keyId2, text2);
			for (IdexInstallation idex : idexInstallationLst) {
				IdexInstallationSearchBean idexBean = new IdexInstallationSearchBean();
				idexBean.setInstallationId(idex.getIdexInstallationId());
				idexBean.setName(idex.getName());
				idexBean.setCode(idex.getCodeDeLinstallation());
				idexBean.setLat(idex.getLat());
				idexBean.setLng(idex.getLng());
				bean.add(idexBean);
			}
		}
		if (keyId1 != 0 && keyId2 != 0 && keyId1 != null && keyId2 != null) {
			idexInstallationLst = idexValueDAO.getIdexInstallation(keyId1, text1, keyId2, text2);
			for (IdexInstallation idex : idexInstallationLst) {
				IdexInstallationSearchBean idexBean = new IdexInstallationSearchBean();
				idexBean.setInstallationId(idex.getIdexInstallationId());
				idexBean.setName(idex.getName());
				idexBean.setCode(idex.getCodeDeLinstallation());
				idexBean.setLat(idex.getLat());
				idexBean.setLng(idex.getLng());
				bean.add(idexBean);
			}
		}
		return bean;
	}

	@RequestMapping(value = "getInstallationByText", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<IdexInstallationSearchBean> getInstallationByText(@RequestParam String text) {
		List<Object[]> tempList = idexValueDAO.findIdexInstallation(text);
		if (tempList.isEmpty()) {
			return null;
		} else {
			List<IdexInstallationSearchBean> returnList = new ArrayList<IdexInstallationSearchBean>();
			Integer currentInstallationId = 0;
			for (Object[] a : tempList) {
				Integer idexInstallationId = (Integer) a[0];
				String nameInstallation = (String) a[1];
				Float lat = (Float) a[2];
				Float lng = (Float) a[3];
				String name = (String) a[4];
				String value = (String) a[5];
				String code = idexValueDAO.findCode(idexInstallationId);
				if (idexInstallationId != currentInstallationId) {
					IdexInstallationSearchBean newBean = new IdexInstallationSearchBean();
					newBean.setInstallationId(idexInstallationId);
					newBean.setName(nameInstallation);
					newBean.setCode(code);
					newBean.setLat(lat);
					newBean.setLng(lng);
					newBean.setValueSearch(new ArrayList<Object>());
					returnList.add(newBean);
					currentInstallationId = idexInstallationId;
				}
				IdexInstallationSearchBean currentBean = returnList.get(returnList.size() - 1);
				List<Object> valueSearch = currentBean.getValueSearch();
				valueSearch.add(new Object[] { name, value });
			}
			return returnList;
		}

	}

	@RequestMapping(value = "getIndexKeyByType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<IdexKeyBean> getIndexKeyByType(Integer type) {
		return getIndexKey(type, null);
	}

	@RequestMapping(value = "getAllInstallation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab010InstallationBean> getAllInstallation() {
		List<IdexInstallation> lst = idexInstallationDAO.getAll();
		List<Lab010InstallationBean> lstBean = new ArrayList<Lab010InstallationBean>();
		for (IdexInstallation idexInstallation : lst) {
			Lab010InstallationBean bean = new Lab010InstallationBean();
			bean.setInstallationId(idexInstallation.getIdexInstallationId());
			bean.setLat(idexInstallation.getLat());
			bean.setLng(idexInstallation.getLng());
			bean.setName(idexInstallation.getName());
			lstBean.add(bean);
		}
		return lstBean;
	}

	/**
	 * type: 1 compteru
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "getColumnAddDataInTree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab010AddBean getColumnAddDataInTree(Integer installationId, Integer type,
			Integer compteurType) {
		Lab010AddBean bean = new Lab010AddBean();
		List<IdexKeyBean> idexKeyBeanLst = getIndexKey(type, compteurType);
		bean.setIdexKeyBeanLst(idexKeyBeanLst);
		List<IdexKeyBean> parentLst = new ArrayList<IdexKeyBean>();
		List<IdexKeyBean> parentCompteurLst = new ArrayList<IdexKeyBean>();

		if (IdexConstant.INSTATLLATION_TYPE == type) {

		} else if (IdexConstant.COUNTER_TYPE == type) {
			List<IdexCounter> counterLst = idexCounterDAO.findAllByInstallation(installationId);
			if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(compteurType)) {
				List<IdexEnergySupplier> lst = idexEnergySupplierDAO.findByInstallation(installationId);

				for (IdexEnergySupplier tmp : lst) {
					IdexKeyBean beanTmp = new IdexKeyBean();
					beanTmp.setIdexKeyId(tmp.getIdexEnergySupplierId());
					beanTmp.setKeyName(tmp.getName());
					parentLst.add(beanTmp);
				}

				for (IdexCounter idexCounter : counterLst) {
					if (IdexConstant.COUNTER_TYPE == idexCounter.getCounterType()
							&& !idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
						IdexKeyBean beanTmp = new IdexKeyBean();
						beanTmp.setIdexKeyId(idexCounter.getIdexCounterId());
						beanTmp.setKeyName(idexCounter.getName());
						parentCompteurLst.add(beanTmp);
					}
				}
			} else if (IdexConstant.LAB010_COUPTER_SORTIE.equals(compteurType)) {
				List<IdexBoiler> lst = idexBoilerDAO.findByInstallation(installationId);
				for (IdexBoiler tmp : lst) {
					IdexKeyBean beanTmp = new IdexKeyBean();
					beanTmp.setIdexKeyId(tmp.getIdexBoilerId());
					beanTmp.setKeyName(tmp.getName());
					parentLst.add(beanTmp);
				}

				for (IdexCounter idexCounter : counterLst) {
					if (IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())
							&& !idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
						IdexKeyBean beanTmp = new IdexKeyBean();
						beanTmp.setIdexKeyId(idexCounter.getIdexCounterId());
						beanTmp.setKeyName(idexCounter.getName());
						parentCompteurLst.add(beanTmp);
					}
				}
			} else {
				List<IdexSite> lst = idexSiteDAO.findByInstallation(installationId);
				for (IdexSite tmp : lst) {
					IdexKeyBean beanTmp = new IdexKeyBean();
					beanTmp.setIdexKeyId(tmp.getIdexSiteId());
					beanTmp.setKeyName(tmp.getName());
					parentLst.add(beanTmp);
				}

				for (IdexCounter idexCounter : counterLst) {
					if (IdexConstant.LAB010_COUPTER_UTILE.equals(idexCounter.getCounterType())
							&& !idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
						IdexKeyBean beanTmp = new IdexKeyBean();
						beanTmp.setIdexKeyId(idexCounter.getIdexCounterId());
						beanTmp.setKeyName(idexCounter.getName());
						parentCompteurLst.add(beanTmp);
					}
				}
			}
		} else if (IdexConstant.BOILER_TYPE == type) {
			// List<IdexCounter> lst =
			// idexCounterDAO.findAllByInstallation(installationId);
			List<IdexCounter> counterLst = idexCounterDAO.findAllByInstallation(installationId);
			for (IdexCounter idexCounter : counterLst) {
				if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())) {
					IdexKeyBean beanTmp = new IdexKeyBean();
					beanTmp.setIdexKeyId(idexCounter.getIdexCounterId());
					beanTmp.setKeyName(idexCounter.getName());
					parentLst.add(beanTmp);
				}
			}
		} else if (IdexConstant.SITE_TYPE == type) {
			List<IdexCounter> counterLst = idexCounterDAO.findAllByInstallation(installationId);
			for (IdexCounter idexCounter : counterLst) {
				if (IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())) {
					IdexKeyBean beanTmp = new IdexKeyBean();
					beanTmp.setIdexKeyId(idexCounter.getIdexCounterId());
					beanTmp.setKeyName(idexCounter.getName());
					parentLst.add(beanTmp);
				}
			}

		} else if (IdexConstant.ENERGY_SUPPLIER_TYPE == type) {
			// parent is energy type
			List<IdexEnergyType> idexEnergySupplierLst = idexEnergySupplierDAO.getAllEnergyType();
			for (IdexEnergyType tmp : idexEnergySupplierLst) {
				IdexKeyBean beanTmp = new IdexKeyBean();
				beanTmp.setIdexKeyId(tmp.getIdexEnergyTypeId());
				beanTmp.setKeyName(tmp.getName());
				parentLst.add(beanTmp);
			}
		}

		bean.setParentCompteurLst(parentCompteurLst);
		bean.setParentLst(parentLst);
		return bean;
	}

	private List<IdexKeyBean> getIndexKey(Integer type, Integer couterType) {
		List<IdexKey> lst = idexKeyDAO.findByType(type);
		List<IdexKeyBean> lstBean = new ArrayList<IdexKeyBean>();
		for (IdexKey key : lst) {
			IdexKeyBean bean = new IdexKeyBean();
			bean.setIdexKeyId(key.getIdexKeyId());
			bean.setKeyName(key.getName());
			bean.setKeyType(key.getKey());
			if (key.getComment() != null) {
				bean.setComment(key.getComment());
			} else {
				bean.setComment("");
			}
			bean.setIsValidate(false);
			if (key.getIsRequried() != null) {
				bean.setIsValidate(key.getIsRequried());
			}
			if (type == IdexConstant.COUNTER_TYPE) {
				if (couterType != null) {
					if (couterType.equals(IdexConstant.LAB010_COUPTER_PRIMAY)) {
						if (key.getIsCounter1() != null
								&& !key.getIsCounter1().equals(IdexConstant.LAB010_IS_FIELD_COUNTER)) {
							continue;
						}
					} else if (couterType.equals(IdexConstant.LAB010_COUPTER_SORTIE)) {
						if (key.getIsCounter2() != null
								&& !key.getIsCounter2().equals(IdexConstant.LAB010_IS_FIELD_COUNTER)) {
							continue;
						}
					} else {
						if (key.getIsCounter3() != null
								&& !key.getIsCounter3().equals(IdexConstant.LAB010_IS_FIELD_COUNTER)) {
							continue;
						}
					}
				}
			}
			lstBean.add(bean);
		}
		return lstBean;
	}

	@RequestMapping(value = "deleteInstallation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean deleteInstallation(Integer installationId) {
		IdexInstallation idexInstallation = idexInstallationDAO.findById(installationId);

		if (idexInstallation.getIsUpdateable() == null || idexInstallation.getIsUpdateable()) {

			idexCostDetailDAO.delete(installationId);
			idexCostDAO.delete(installationId);
			idexCounterDAO.deleteByType(3, installationId);
			idexCounterDAO.deleteCounterSite(installationId);
			idexSiteDAO.delete(installationId);
			idexCounterDAO.deleteByType(2, installationId);
			idexBoilerDAO.delete(installationId);
			idexCounterDAO.deleteByType(1, installationId);
			idexEnergySupplierDAO.delete(installationId);
			idexInstallationDAO.delete(installationId);
			idexValueDAO.delete(installationId);
			return true;

		}
		return false;
	}

	// @RequestMapping(value = "getNumberBoiler", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Long getNumberBoiler() {
	// return idexBoilerDAO.getNumberBoiler();
	// }
	//
	// @RequestMapping(value = "getNumberSite", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Long getNumberSite() {
	// return idexSiteDAO.getNumberSite();
	// }
	//
	// @RequestMapping(value = "getNumberCounter", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON)
	// public @ResponseBody Long getNumberCounter() {
	// return idexCounterDAO.getNumberCounter();
	// }
	
//	@RequestMapping(value = "testHibernate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
//	public @ResponseBody String testHibernate(Integer idexCounterId) {
//		String rs = null;
//		List<IdexReleves> lst = idexRelevesDAO.findByCounter(idexCounterId);
//		for(IdexReleves idexReleves: lst){
//			System.out.println(idexReleves.getValue());
//			Float value= idexReleves.getValue();
//			value = 100 * value;
//		}
//		return rs;
//	}
//	
	

}
