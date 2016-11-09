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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ifi.common.bean.caloon.CaloonConstant;
import com.ifi.common.bean.caloon.CaloonResidentBean;
import com.ifi.common.bean.caloon.CaloonSyndicBean;
import com.ifi.common.bean.caloon.CaloonUserBean;
import com.ifi.common.bean.caloon.Lab011Client;
import com.ifi.common.bean.caloon.Lab011ConsommationRangeBean;
import com.ifi.common.bean.caloon.Lab011ResidentBean;
import com.ifi.common.bean.caloon.Lab011ResidentBottomBean;
import com.ifi.common.bean.caloon.Lab011SyndicBottom;
import com.ifi.common.bean.caloon.Lab011SyndicUpper;
import com.ifi.common.util.CalculateUtils;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.ws.GetIndusDataAction;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonResidentDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonSyndicDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonUserDAO;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonResident;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;

@Controller
@RequestMapping("lab011")
public class Lab011Rest {

	@Autowired
	private CaloonUserDAO caloonUserDAO;

	@Autowired
	private CaloonResidentDAO caloonResidentDAO;

	@Autowired
	private CaloonSyndicDAO caloonSyndicDAO;

	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;

	private SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);

	@RequestMapping(value = "searchCaloonSyndic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<CaloonSyndicBean> searchCaloonSyndic(String key) {
		List<CaloonSyndic> caloonSyndicLst = caloonSyndicDAO.searchByKey(key);
		List<CaloonSyndicBean> beanLst = new ArrayList<CaloonSyndicBean>();
		for (CaloonSyndic caloonSyndic : caloonSyndicLst) {
			CaloonSyndicBean bean = new CaloonSyndicBean();
			bean.setAddress(caloonSyndic.getAddress());
			bean.setCodePostal(caloonSyndic.getCodePostal());
			bean.setVille(caloonSyndic.getVille());
			bean.setName(caloonSyndic.getName());
			bean.setReportPath(caloonSyndic.getReportPath());
			bean.setCaloonSyndicId(caloonSyndic.getId());
			beanLst.add(bean);
		}

		return beanLst;
	}

	@RequestMapping(value = "getCaloonSyndicBean", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody CaloonSyndicBean getCaloonSyndicBean(Integer caloonSyndicId) {
		CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(caloonSyndicId);
		CaloonSyndicBean bean = new CaloonSyndicBean();
		bean.setAddress(caloonSyndic.getAddress());
		bean.setCodePostal(caloonSyndic.getCodePostal());
		bean.setVille(caloonSyndic.getVille());
		bean.setName(caloonSyndic.getName());
		bean.setReportPath(caloonSyndic.getReportPath());
		bean.setCaloonSyndicId(caloonSyndic.getId());
		return bean;
	}

	@RequestMapping(value = "getCaloonResidentBean", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody CaloonResidentBean getCaloonResidentBean(Integer caloonResidentId) {
		CaloonResident caloonResident = caloonResidentDAO.findById(caloonResidentId);
		CaloonResidentBean bean = new CaloonResidentBean();
		bean.setAppartmentNumber(caloonResident.getAppartementNumber());
		bean.setCaloonResidentId(caloonResident.getId());
		return bean;
	}

	private Double convertKwhToM3(Double value, Double confident) {
		if (confident == null || value == null) {
			return value;
		} else {
			return value * confident;
		}
	}

	@RequestMapping(value = "getLab011Resident", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab011ResidentBean getLab011Resident(String fromDate, Integer residentId) {

		CaloonResident caloonResident = caloonResidentDAO.findById(residentId);
		Double confident = null;
		if (caloonResident.getCaloonSyndicId() != null) {
			CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(caloonResident.getCaloonSyndicId());
			confident = caloonSyndic.getCoeffUnit();
		}

		Lab011ResidentBean lab011ResidentBean = new Lab011ResidentBean();

		GetIndusDataAction indus = new GetIndusDataAction();
		if (caloonResident != null) {
			Date currentDate = LabUtils.getCurrentDay();
			String toDate = LabUtils.convertDateByFormat(currentDate, FrontalKey.DATE_FORMAT_DAY_1);
			lab011ResidentBean.setAppartementNumber(caloonResident.getAppartementNumber());
			List<String> moduleLst = new ArrayList<String>();

			if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
				moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getChauffage()));
			}

			if (caloonResident.getEauChaude() != null && !caloonResident.getEauChaude().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauChaude())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getEauChaude()));
				}
			}

			if (caloonResident.getEauFroide() != null && !caloonResident.getEauFroide().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauFroide())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getEauFroide()));
				}
			}

			Map<String, Map<String, Double>> mapConsommation = indus.getPreCalPulseByType(moduleLst, fromDate, toDate,
					FrontalKey.TYPE_TIME_ALL);

			if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
				Double value = CalculateUtils.calculateFirstByFormular(caloonResident.getChauffage(), mapConsommation);
				if (value != null) {
					lab011ResidentBean.setChauffage(value.intValue());
				}
			}

			if (caloonResident.getEauChaude() != null && !caloonResident.getEauChaude().isEmpty()) {
				Double value = CalculateUtils.calculateFirstByFormular(caloonResident.getEauChaude(), mapConsommation);
				value = convertKwhToM3(value, confident);
				if (value != null) {
					lab011ResidentBean.setEauChaude(value.intValue());
				}
			}

			if (caloonResident.getEauFroide() != null && !caloonResident.getEauFroide().isEmpty()) {
				Double value = CalculateUtils.calculateFirstByFormular(caloonResident.getEauFroide(), mapConsommation);
				value = convertKwhToM3(value, confident);
				if (value != null) {
					lab011ResidentBean.setEauFroide(value.intValue());
				}
			}
		}

		Lab lab = labDAO.findByName(FrontalKey.LAB_NAME_011_CALOON);
		if (lab != null) {
			List<Constant> constantLst = constantDAO.getByLabId(lab.getId());
			if (constantLst != null && !constantLst.isEmpty()) {
				List<String> moduleTempLst = new ArrayList<String>();
				String tempAppartement = null;
				String tempMeteo = null;
				for (Constant constant : constantLst) {
					if (constant.getKey().equals(FrontalKey.CALOON_TEMP_APPARTEMENT)) {
						tempAppartement = constant.getValue();
						moduleTempLst.add(tempAppartement);
					} else if (constant.getKey().equals(FrontalKey.CALOON_TEMP_METEO)) {
						tempMeteo = constant.getValue();
						moduleTempLst.add(tempMeteo);
					}
				}
				if (!moduleTempLst.isEmpty()) {
					Calendar cal = Calendar.getInstance();
					String toDateP = sdf.format(cal.getTime());
					cal.add(Calendar.YEAR, -10);
					String fromDateP = sdf.format(cal.getTime());
					Map<String, Double> mapTemperature = indus.getLastTemperatureByTime(moduleTempLst, fromDateP,
							toDateP);

					if (tempAppartement != null) {
						if (mapTemperature.containsKey(tempAppartement)) {
							double temp = mapTemperature.get(tempAppartement);
							lab011ResidentBean.setTempAppartement((int) temp);
						}
					}

					if (tempMeteo != null) {
						if (mapTemperature.containsKey(tempMeteo)) {
							double temp = mapTemperature.get(tempMeteo);
							lab011ResidentBean.setTempMeteo((int) temp);
						}
					}
				}
			}
		}

		return lab011ResidentBean;
	}

	@RequestMapping(value = "getLab011ResidentBottom", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab011ResidentBottomBean getLab011ResidentBottom(String fromDateBlue, String toDateBlue,
			String fromDateGreen, String toDateGreen, Integer residentId) {

		GetIndusDataAction indus = new GetIndusDataAction();
		CaloonResident caloonResident = caloonResidentDAO.findById(residentId);
		Double confident = null;
		if (caloonResident.getCaloonSyndicId() != null) {
			CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(caloonResident.getCaloonSyndicId());
			confident = caloonSyndic.getCoeffUnit();
		}
		Lab011ResidentBottomBean bean = new Lab011ResidentBottomBean();

		int typeTime = FrontalKey.TYPE_TIME_DAY;
		if (fromDateBlue != null && !fromDateBlue.isEmpty() && toDateBlue != null && !toDateBlue.isEmpty()) {
			Date dateP1 = LabUtils.convertDateByFormat(fromDateBlue, FrontalKey.DATE_FORMAT_DAY_1);
			Date dateP2 = LabUtils.convertDateByFormat(toDateBlue, FrontalKey.DATE_FORMAT_DAY_1);

			typeTime = LabUtils.getTypeTimeByDate(dateP1, dateP2);
		} else if (fromDateGreen != null && !fromDateGreen.isEmpty() && toDateGreen != null && !toDateGreen.isEmpty()) {
			Date dateP1 = LabUtils.convertDateByFormat(fromDateGreen, FrontalKey.DATE_FORMAT_DAY_1);
			Date dateP2 = LabUtils.convertDateByFormat(toDateGreen, FrontalKey.DATE_FORMAT_DAY_1);

			typeTime = LabUtils.getTypeTimeByDate(dateP1, dateP2);
		}
		if (caloonResident != null) {
			List<String> moduleLst = new ArrayList<String>();

			if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
				moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getChauffage()));
			}

			if (caloonResident.getEauChaude() != null && !caloonResident.getEauChaude().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauChaude())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getEauChaude()));
				}
			}

			if (caloonResident.getEauFroide() != null && !caloonResident.getEauFroide().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauFroide())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getEauFroide()));
				}
			}

			if (fromDateBlue != null && !fromDateBlue.isEmpty() && toDateBlue != null && !toDateBlue.isEmpty()) {
				Map<String, Map<String, Double>> mapConsommation = indus.getPreCalPulseByType(moduleLst, fromDateBlue,
						toDateBlue, typeTime);
				Map<Long, Map<String, Double>> mapTime = convertToMapTime(mapConsommation);

				bean.setChauffageBlueLst(getListFromMap(mapTime, caloonResident.getChauffage(), null));
				bean.setEauChaudeBlueLst(getListFromMap(mapTime, caloonResident.getEauChaude(), confident));
				bean.setEauFroideBlueLst(getListFromMap(mapTime, caloonResident.getEauFroide(), confident));
			}

			if (fromDateGreen != null && !fromDateGreen.isEmpty() && toDateGreen != null && !toDateGreen.isEmpty()) {
				Map<String, Map<String, Double>> mapConsommation = indus.getPreCalPulseByType(moduleLst, fromDateGreen,
						toDateGreen, typeTime);
				Map<Long, Map<String, Double>> mapTime = convertToMapTime(mapConsommation);
				bean.setChauffageGreenLst(getListFromMap(mapTime, caloonResident.getChauffage(), null));
				bean.setEauChaudeGreenLst(getListFromMap(mapTime, caloonResident.getEauChaude(), confident));
				bean.setEauFroideGreenLst(getListFromMap(mapTime, caloonResident.getEauFroide(), confident));
			}
		}
		bean.setTypeTime(typeTime);
		return bean;
	}

	private Map<Long, Map<String, Double>> convertToMapTime(Map<String, Map<String, Double>> mapConsommation) {
		Map<Long, Map<String, Double>> mapTimeModule = new TreeMap<Long, Map<String, Double>>();
		for (Map.Entry<String, Map<String, Double>> entry : mapConsommation.entrySet()) {
			String module = entry.getKey();
			Map<String, Double> mapTime = entry.getValue();
			for (Map.Entry<String, Double> entryTime : mapTime.entrySet()) {
				Long time = LabUtils.convertDateByFormat(entryTime.getKey(), FrontalKey.DATE_FORMAT_DAY_3).getTime();
				Map<String, Double> mapModule = null;
				if (mapTimeModule.containsKey(time)) {
					mapModule = mapTimeModule.get(time);
				} else {
					mapModule = new HashMap<String, Double>();
				}
				mapModule.put(module, entryTime.getValue());
				mapTimeModule.put(time, mapModule);
			}
		}
		return mapTimeModule;
	}

	private List<Object[]> getListFromMap(Map<Long, Map<String, Double>> mapTime, String module, Double confident) {
		List<Object[]> dataLst = new ArrayList<Object[]>();
		if (module != null && !module.isEmpty()) {
			for (Map.Entry<Long, Map<String, Double>> entry : mapTime.entrySet()) {
				Long time = entry.getKey();
				Map<String, Double> mapModule = entry.getValue();
				Double value = CalculateUtils.calculateByFormular(module, mapModule);
				value = convertKwhToM3(value, confident);
				if (value != null) {
					dataLst.add(new Object[] { time, value });
				}
			}
		}
		return dataLst;
	}

	private Double getValueFromMap(Map<String, Map<String, Double>> mapConsommation, String module, Double confident) {
		Double value = CalculateUtils.calculateFirstByFormular(module, mapConsommation);
		return value = convertKwhToM3(value, confident);
	}

	@RequestMapping(value = "getLab011SyndicUpper", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab011SyndicUpper getLab011SyndicUpper(String fromDate, Integer syndicId) {
		CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(syndicId);
		List<CaloonResident> caloonResidentLst = caloonResidentDAO.findBySyndic(syndicId);
		Lab011SyndicUpper lab011SyndicUpper = new Lab011SyndicUpper();

		if (caloonSyndic != null) {

			Double confident = caloonSyndic.getCoeffUnit();

			List<String> moduleLst = new ArrayList<String>();

			if (caloonSyndic.getChauffage() != null && !caloonSyndic.getChauffage().isEmpty()) {
				moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonSyndic.getChauffage()));
			}

			if (caloonSyndic.getEauChaude() != null && !caloonSyndic.getEauChaude().isEmpty()) {
				if (!moduleLst.contains(caloonSyndic.getEauChaude())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonSyndic.getEauChaude()));
				}
			}

			if (caloonSyndic.getEauFroide() != null && !caloonSyndic.getEauFroide().isEmpty()) {
				if (!moduleLst.contains(caloonSyndic.getEauFroide())) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonSyndic.getEauFroide()));
				}
			}

			for (CaloonResident caloonResident : caloonResidentLst) {
				if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
					moduleLst.addAll(CalculateUtils.getModuleFromFomular(caloonResident.getChauffage()));
				}
			}

			GetIndusDataAction indus = new GetIndusDataAction();

			Date currentDate = LabUtils.getCurrentDay();
			String toDate = LabUtils.convertDateByFormat(currentDate, FrontalKey.DATE_FORMAT_DAY_1);

			Date fromDay = LabUtils.convertDateByFormat(fromDate, FrontalKey.DATE_FORMAT_DAY_1);
			int numberDay = LabUtils.different2Date(fromDay, currentDate);
			// int numberResident = caloonResidentLst.size();

			Map<String, Map<String, Double>> mapConsommation = indus.getPreCalPulseByType(moduleLst, fromDate, toDate,
					FrontalKey.TYPE_TIME_ALL);
			Double value = getValueFromMap(mapConsommation, caloonSyndic.getChauffage(), null);
			if (value != null) {
				lab011SyndicUpper.setGlobaleChauffage(value.intValue());
				// if (numberResident != 0 && numberDay != 0) {
				// lab011SyndicUpper.setLogementEtMoisChauffage((int) (30 *
				// value / (numberDay * numberResident)));
				// }
				if (numberDay != 0) {
					lab011SyndicUpper.setLogementEtMoisChauffage((int) ((value / numberDay) * 30));
				}
			}

			value = getValueFromMap(mapConsommation, caloonSyndic.getEauChaude(), confident);
			if (value != null) {
				lab011SyndicUpper.setGlobaleEauChaude(value.intValue());
				// if (numberResident != 0 && numberDay != 0) {
				// lab011SyndicUpper.setLogementEtMoisEauChaude((int) (30 *
				// value / (numberDay * numberResident)));
				// }
				if (numberDay != 0) {
					lab011SyndicUpper.setLogementEtMoisEauChaude((int) ((value / numberDay) * 30));
				}
			}

			value = getValueFromMap(mapConsommation, caloonSyndic.getEauFroide(), confident);
			if (value != null) {
				lab011SyndicUpper.setGlobaleEauFroide(value.intValue());
				// if (numberResident != 0 && numberDay != 0) {
				// lab011SyndicUpper.setLogementEtMoisEauFroide((int) (30 *
				// value / (numberDay * numberResident)));
				// }
				if (numberDay != 0) {
					lab011SyndicUpper.setLogementEtMoisEauFroide((int) ((value / numberDay) * 30));
				}
			}

			Double totalClient = 0d;
			for (CaloonResident caloonResident : caloonResidentLst) {
				value = getValueFromMap(mapConsommation, caloonResident.getChauffage(), null);
				if (value != null) {
					totalClient += value;
				}
			}

			if (lab011SyndicUpper.getGlobaleChauffage() != null) {
				lab011SyndicUpper.setWastageEnergy(
						getConvertToConffident(lab011SyndicUpper.getGlobaleChauffage(), caloonSyndic.getCoeffTotal())
								- getConvertToConffident(totalClient.intValue(), caloonSyndic.getCoeffEcs()));
			}
		}

		return lab011SyndicUpper;
	}

	@RequestMapping(value = "getLab011SyndicBottom", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Lab011SyndicBottom getLab011SyndicBottom(Integer syndicId, Integer type, String fromDate,
			String toDate, Boolean isConsommationGreater, Integer consommationRange, Boolean isSuperficieGreater,
			Integer superficie, Boolean logements) {

		CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(syndicId);
		List<CaloonResident> caloonResidentLst = caloonResidentDAO.findByFilter(syndicId, isSuperficieGreater,
				superficie, logements);

		int clientSize = 0;
		Lab011SyndicBottom lab011SyndicBottom = new Lab011SyndicBottom();
		if (caloonSyndic != null) {

			Double confident = caloonSyndic.getCoeffUnit();
			List<String> moduleLst = new ArrayList<String>();
			String consommationGlobale = null;
			String consommationCommunes = null;

			if (type.equals(CaloonConstant.TYPE_CHAUFFAGE)) {
				consommationGlobale = caloonSyndic.getChauffage();
				consommationCommunes = caloonSyndic.getChauffageCommunes();
				confident = null;
			} else if (type.equals(CaloonConstant.TYPE_EAU_CHAUDE)) {
				consommationGlobale = caloonSyndic.getEauChaude();
				consommationCommunes = caloonSyndic.getEauChaudeCommunes();
			} else {
				consommationGlobale = caloonSyndic.getEauFroide();
				consommationCommunes = caloonSyndic.getEauFroideCommunes();
			}

			moduleLst = addModuleToLst(moduleLst, consommationGlobale);
			moduleLst = addModuleToLst(moduleLst, consommationCommunes);

			for (CaloonResident caloonResident : caloonResidentLst) {
				String moduleClient = null;
				if (type.equals(CaloonConstant.TYPE_CHAUFFAGE)) {
					moduleClient = caloonResident.getChauffage();
				} else if (type.equals(CaloonConstant.TYPE_EAU_CHAUDE)) {
					moduleClient = caloonResident.getEauChaude();
				} else {
					moduleClient = caloonResident.getEauFroide();
				}
				moduleLst = addModuleToLst(moduleLst, moduleClient);
			}

			GetIndusDataAction indus = new GetIndusDataAction();
			Map<String, Map<String, Double>> mapConsommation = indus.getPreCalPulseByType(moduleLst, fromDate, toDate,
					FrontalKey.TYPE_TIME_ALL);

			Double value = getValueFromMap(mapConsommation, consommationGlobale, confident);
			if (value != null) {
				lab011SyndicBottom.setGlobaleConsommation(value.intValue());
			}
			double totalConsommation = 0;
			double totalAllConsommation = 0;
			List<Lab011Client> lab011ClientsLst = new ArrayList<Lab011Client>();
			for (CaloonResident caloonResident : caloonResidentLst) {
				String module = null;
				if (type.equals(CaloonConstant.TYPE_CHAUFFAGE)) {
					module = caloonResident.getChauffage();
				} else if (type.equals(CaloonConstant.TYPE_EAU_CHAUDE)) {
					module = caloonResident.getEauChaude();
				} else {
					module = caloonResident.getEauFroide();
				}

				value = getValueFromMap(mapConsommation, module, confident);
				Integer consommation = null;
				if (value != null) {
					consommation = Math.round(value.floatValue());
				}
				if (consommation != null) {
					totalAllConsommation += consommation;
				}
				if (isConsommationGreater != null && consommationRange != null & consommation != null) {
					if (isConsommationGreater) {
						if (consommation <= consommationRange) {
							continue;
						}
					} else {
						if (consommation >= consommationRange) {
							continue;
						}
					}

				}
				if (consommation != null) {
					totalConsommation += consommation;
					clientSize ++;
				}
				Lab011Client client = new Lab011Client(consommation);
				client.setClientName(caloonResident.getClientName());
				client.setResidentId(caloonResident.getId());
				lab011ClientsLst.add(client);

			}

			if (clientSize != 0) {
				lab011SyndicBottom.setGlobaleMoyenneConsommation(totalConsommation / clientSize);
			}

			value = 0d;
			Integer valueComunes = null;
			if(consommationCommunes != null){
				value = getValueFromMap(mapConsommation, consommationCommunes, confident);
				if (value != null) {
					valueComunes = Math.round(value.floatValue());
					totalConsommation += value;
				}
			}else{
				if(lab011SyndicBottom.getGlobaleConsommation() != null){
					value = lab011SyndicBottom.getGlobaleConsommation() - totalAllConsommation;
					valueComunes = Math.round(value.floatValue());
					totalConsommation += value;
				}
			}
			
			Lab011Client communes = new Lab011Client(valueComunes);

			// Set rate
			List<Lab011Client> lab011ClientsLstTmp = new ArrayList<Lab011Client>();
			if (totalConsommation != 0) {
				double totalPercent = 0;
				for (Lab011Client client : lab011ClientsLst) {
					if (client.getEnergy() != null) {
						double percent = (double) client.getEnergy() / totalConsommation * 100;
						client.setRate(percent);
						totalPercent += percent;
						// client.setClientName(clientName);
					}
					lab011ClientsLstTmp.add(client);
				}

				if (communes.getEnergy() != null) {
					communes.setRate(100 - totalPercent);
				}
				lab011SyndicBottom.setPartiesCommunes(communes);
			}
			lab011SyndicBottom.setLab011ClientsLst(lab011ClientsLstTmp);

			if (type.equals(CaloonConstant.TYPE_CHAUFFAGE) && lab011SyndicBottom.getGlobaleConsommation() != null) {
				lab011SyndicBottom.setWastageEnergy(getConvertToConffident(lab011SyndicBottom.getGlobaleConsommation(),
						caloonSyndic.getCoeffTotal())
						- getConvertToConffident((int) totalConsommation, caloonSyndic.getCoeffEcs()));
			}
		}
		return lab011SyndicBottom;
	}

	private Integer getConvertToConffident(Integer value, Double conffident) {
		if (conffident == null || value == null) {
			return value;
		} else {
			return (int) (conffident * value);
		}
	}

	private List<String> addModuleToLst(List<String> lst, String module) {
		if (module != null && !module.isEmpty() && !lst.contains(module)) {
			lst.addAll(CalculateUtils.getModuleFromFomular(module));
		}
		return lst;
	}

	@RequestMapping(value = "getAllConsommationRange", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<Lab011ConsommationRangeBean> getAllConsommationRange() {

		List<CaloonConsommationRange> consommationRangeLst = caloonSyndicDAO.getAllConsommationRange();
		List<Lab011ConsommationRangeBean> beanLst = new ArrayList<Lab011ConsommationRangeBean>();
		for (CaloonConsommationRange range : consommationRangeLst) {
			Lab011ConsommationRangeBean bean = new Lab011ConsommationRangeBean();
			bean.setId(range.getId());
			if (range.getFromNumber() != null && range.getToNumber() != null) {
				bean.setRangeSearch(
						"CONSOMMATION >= " + range.getFromNumber() + " kwh et <= " + range.getToNumber() + " kwh");
			} else if (range.getFromNumber() != null) {
				bean.setRangeSearch("CONSOMMATION >= " + range.getFromNumber() + " kwh");
			} else {
				bean.setRangeSearch("CONSOMMATION <= " + range.getToNumber() + " kwh");
			}
			beanLst.add(bean);
		}
		return beanLst;
	}

	@RequestMapping(value = "getStatusCaloonByUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody CaloonUserBean getStatusCaloonByUser(String userName, String passWord) {
		CaloonUserBean caloonUserBean = caloonUserDAO.checkLogin(userName, passWord);
		if (CaloonConstant.USER_STATUS_RESIDENT.equals(caloonUserBean.getStatus())) {
			CaloonResident caloonResident = caloonResidentDAO.findByUser(caloonUserBean.getId());
			if (caloonResident != null) {
				caloonUserBean.setCaloonResidentId(caloonResident.getId());
			}
		} else {
			List<CaloonSyndic> caloonSyndicLst = caloonSyndicDAO.findByUser(caloonUserBean.getId());
			if (!caloonSyndicLst.isEmpty()) {
				boolean hasDefault = false;
				for (CaloonSyndic caloonSyndic : caloonSyndicLst) {
					if (caloonSyndic.getIsDefaultSyndic() != null && caloonSyndic.getIsDefaultSyndic()) {
						hasDefault = true;
						caloonUserBean.setCaloonSyndicId(caloonSyndic.getId());
						break;
					}
				}
				if (!hasDefault) {
					caloonUserBean.setCaloonSyndicId(caloonSyndicLst.get(0).getId());
				}
			}
		}
		return caloonUserBean;
	}

	@RequestMapping(value = "getLastDayResident", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody String getLastDayResident(Integer residentId) {

		CaloonResident caloonResident = caloonResidentDAO.findById(residentId);

		if (caloonResident != null) {
			List<String> moduleLst = new ArrayList<String>();
			if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
				moduleLst.add(caloonResident.getChauffage());
			}

			if (caloonResident.getEauChaude() != null && !caloonResident.getEauChaude().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauChaude())) {
					moduleLst.add(caloonResident.getEauChaude());
				}
			}

			if (caloonResident.getEauFroide() != null && !caloonResident.getEauFroide().isEmpty()) {
				if (!moduleLst.contains(caloonResident.getEauFroide())) {
					moduleLst.add(caloonResident.getEauFroide());
				}
			}

			GetIndusDataAction indus = new GetIndusDataAction();
			Date d = indus.getLastDayHasData(moduleLst);
			if (d != null) {
				return LabUtils.convertDateByFormat(d, FrontalKey.DATE_SLASH_FORMAT);
			}
		}
		return null;
	}

	@RequestMapping(value = "getLastDaySyndic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody String getLastDaySyndic(Integer syndicId) {

		List<String> moduleLst = new ArrayList<String>();
		List<CaloonResident> caloonResidentLst = caloonResidentDAO.findBySyndic(syndicId);

		if (caloonResidentLst != null && !caloonResidentLst.isEmpty()) {
			for (CaloonResident caloonResident : caloonResidentLst) {
				if (caloonResident.getChauffage() != null && !caloonResident.getChauffage().isEmpty()) {
					moduleLst.add(caloonResident.getChauffage());
				}

				if (caloonResident.getEauChaude() != null && !caloonResident.getEauChaude().isEmpty()) {
					if (!moduleLst.contains(caloonResident.getEauChaude())) {
						moduleLst.add(caloonResident.getEauChaude());
					}
				}

				if (caloonResident.getEauFroide() != null && !caloonResident.getEauFroide().isEmpty()) {
					if (!moduleLst.contains(caloonResident.getEauFroide())) {
						moduleLst.add(caloonResident.getEauFroide());
					}
				}
			}

			GetIndusDataAction indus = new GetIndusDataAction();
			Date d = indus.getLastDayHasData(moduleLst);
			if (d != null) {
				return LabUtils.convertDateByFormat(d, FrontalKey.DATE_SLASH_FORMAT);
			}
		}
		return null;
	}

}
