package com.energisme.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab008;
import com.energisme.service.Lab008Service;
import com.energisme.util.Utils;
import com.ifi.common.bean.Lab008BoilerInfo;
import com.ifi.common.bean.Lab008PerformanceData;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.common.ws.IndusHistoryData;
import com.ifi.lab.LabDAO.dao.ConfigLab008V2DAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;

public class Lab008ServiceImpl implements Lab008Service {
	@Autowired
	private ConfigLab008V2DAO configLab008DAO;

	// @Autowired
	// private Lab008TrefDAO Lab008TrefDAO;

	private GetCSMDataAction csm = new GetCSMDataAction();

	private String msg = null;

	private String messBoilerError = null;
	private String messBoilerSuccess = null;
	// private String messTrefError = null;
	// private String messTrefSuccess = null;
	private String messPerformanceError = null;
	private String messPerformanceSuccess = null;

	public ConfigLab008V2 getConfigLab008BySite(Integer siteId) {
		ConfigLab008V2 configLab = configLab008DAO.findBySiteId(siteId);
		return configLab;
	}

	@Override
	public List<ConfigLab008V2> getAllConfigLab008() {
		List<ConfigLab008V2> listConfigLab = configLab008DAO.getAllConfig();
		return listConfigLab;
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
		if (configLab008DAO.delete(idNumber)) {
			return "success";
		} else {
			return "fail";
		}

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
		return configLab008DAO.getMaxSiteId();
	}

	@Override
	public String saveConfig(NumConfigLab008 numConfigLab) {

		ConfigLab008V2 configLab008 = new ConfigLab008V2();

		// Set Site ID
		Integer siteId = null;
		Double precision = null;
		if (numConfigLab.getSiteId() == null || numConfigLab.getSiteId().isEmpty()) { // create
																						// site
			siteId = configLab008DAO.getMaxSiteId() + 1;
		} else {
			try {
				siteId = Integer.parseInt(numConfigLab.getSiteId().trim());
			} catch (NumberFormatException nfe) {
				return "Subscription ID must be a number:" + numConfigLab.getSiteId();
			}
			if (siteId == null || siteId <= 0) {
				return "Subscription ID must be a number";
			}

			ConfigLab008V2 ckConfig = configLab008DAO.findBySiteId(siteId);
			if (numConfigLab.getId() != null) {
				// update
				configLab008 = configLab008DAO.findById(numConfigLab.getId());
				if (ckConfig != null && ckConfig.getId() != null && !configLab008.getId().equals(ckConfig.getId())) {
					return "Subscription ID : '" + siteId + "' has been duplicated";
				}
			} else {
				// Insert
				if (ckConfig != null && ckConfig.getId() != null) {
					return "Subscription ID : '" + siteId + "' has been duplicated";
				}
			}

		}
		configLab008.setSiteId(siteId);

		// Set Site Name
		if (numConfigLab.getSiteName() != null) {
			configLab008.setSiteName(numConfigLab.getSiteName().trim());
		}

		// Set boilerModel
		if (numConfigLab.getBoilerModel() != null) {
			configLab008.setBoilerModel(numConfigLab.getBoilerModel().trim());
		}

		// SET
		if (numConfigLab.getBoilerYear() != null && !numConfigLab.getBoilerYear().trim().isEmpty()) {
			try {
				configLab008.setBoilerYear(Integer.parseInt(numConfigLab.getBoilerYear()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getBoilerYear() + " not a number";
			}
		}

		// SET
		if (numConfigLab.getBoilerTheoryPower() != null && !numConfigLab.getBoilerTheoryPower().trim().isEmpty()) {
			try {
				configLab008.setBoilerTheoryPower(Double.parseDouble(numConfigLab.getBoilerTheoryPower()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getBoilerTheoryPower() + " not a number";
			}
		} else {
			return "Boilers theoretical power is null";
		}

		// SET
		if (numConfigLab.getCoeff1() != null && !numConfigLab.getCoeff1().trim().isEmpty()) {
			try {
				configLab008.setCoeff1(Double.parseDouble(numConfigLab.getCoeff1()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff1() + " not a number";
			}
		} else {
			configLab008.setCoeff1(1d);
		}

		if (numConfigLab.getCoeff2() != null && !numConfigLab.getCoeff2().trim().isEmpty()) {
			try {
				configLab008.setCoeff2(Double.parseDouble(numConfigLab.getCoeff2()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff2() + " not a number";
			}
		} else {
			configLab008.setCoeff2(1d);
		}

		if (numConfigLab.getCoeff3() != null && !numConfigLab.getCoeff3().trim().isEmpty()) {
			try {
				configLab008.setCoeff3(Double.parseDouble(numConfigLab.getCoeff3()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff3() + " not a number";
			}
		} else {
			configLab008.setCoeff3(1d);
		}

		if (numConfigLab.getCoeff4() != null && !numConfigLab.getCoeff4().trim().isEmpty()) {
			try {
				configLab008.setCoeff4(Double.parseDouble(numConfigLab.getCoeff4()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff4() + " not a number";
			}
		} else {
			configLab008.setCoeff4(1d);
		}

		// Set precision
		if (numConfigLab.getModelPrecision() != null && !numConfigLab.getModelPrecision().trim().isEmpty()) {
			try {
				precision = Double.parseDouble(numConfigLab.getModelPrecision());
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff4() + " not a number";
			}
		}
		if (precision != null && precision >= 0 && precision <= 100) {
			configLab008.setModelPrecision(precision);
		}

		if (numConfigLab.getCoeff5() != null && !numConfigLab.getCoeff5().trim().isEmpty()) {
			try {
				configLab008.setCoeff5(Double.parseDouble(numConfigLab.getCoeff5()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff5() + " not a number";
			}
		} else {
			configLab008.setCoeff5(1d);
		}

		if (numConfigLab.getCoeff6() != null && !numConfigLab.getCoeff6().trim().isEmpty()) {
			try {
				configLab008.setCoeff6(Double.parseDouble(numConfigLab.getCoeff6()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeff6() + " not a number";
			}
		} else {
			configLab008.setCoeff6(1d);
		}

		if (numConfigLab.getCoeffRadnConvection() != null && !numConfigLab.getCoeffRadnConvection().trim().isEmpty()) {
			try {
				configLab008.setCoeffRadnConvection(Double.parseDouble(numConfigLab.getCoeffRadnConvection()));
			} catch (NumberFormatException nfe) {
				return numConfigLab.getCoeffRadnConvection() + " not a number";
			}
		} else {
			configLab008.setCoeffRadnConvection(1d);
		}

		// Set AvgAirTempModuleId
		String moduleID = "";
		moduleID = convertSingerModuleToID(numConfigLab.getAvgAirTempModuleId());
		if (msg != null) {
			return msg;
		} else {
			configLab008.setAvgAirTempModuleId(moduleID);
		}

		// Set
		moduleID = convertSingerModuleToID(numConfigLab.getAvgExtTempModuleId());
		if (msg != null) {
			return msg;
		} else {
			configLab008.setAvgExtTempModuleId(moduleID);
		}

		// SET GasNaturalModuleId
		if (numConfigLab.getGasNaturalModuleId() != null && !numConfigLab.getGasNaturalModuleId().isEmpty()) {
			moduleID = csm.getCalculateModuleByName(numConfigLab.getGasNaturalModuleId());
			if (moduleID != null) {
				configLab008.setGasNaturalModuleId(moduleID);
			} else {
				return "Module not exists:" + numConfigLab.getGasNaturalModuleId();
			}
		}

		moduleID = convertSingerModuleToID(numConfigLab.getChauffageModuleId());
		if (msg != null) {
			return msg;
		} else {
			configLab008.setChauffageModuleId(moduleID);
		}

		moduleID = convertSingerModuleToID(numConfigLab.getEcsZoneBasse());
		if (msg != null) {
			return msg;
		} else {
			configLab008.setEcsZoneBasse(moduleID);
		}

		moduleID = convertSingerModuleToID(numConfigLab.getEcsZoneHaute());
		if (msg != null) {
			return msg;
		} else {
			configLab008.setEcsZoneHaute(moduleID);
		}

		if (numConfigLab.getFromDate() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				String datecf = numConfigLab.getFromDate();
				Date date = dateFormat.parse(datecf);
				configLab008.setFromDate(date);

			} catch (ParseException nfe) {
				return " From date is in format dd/MM/yyyy";
			}
		} else {
			return "From date invalid";
		}

		// SET ProductionModuleId
		
		if (numConfigLab.getProductionModuleId() != null && !numConfigLab.getProductionModuleId().isEmpty()) {
			moduleID = csm.getCalculateModuleByName(numConfigLab.getProductionModuleId());
			if (moduleID != null) {
				configLab008.setProductionModuleId(moduleID);
			} else {
				return "Module not exists:" + numConfigLab.getProductionModuleId();
			}
		}
		
		String uriIcon = numConfigLab.getUriIcon();
		if (uriIcon != null) {
			configLab008.setUriIcon(uriIcon.trim());
		}

		String logo = numConfigLab.getLogo();
		if (logo != null) {
			configLab008.setLogo(logo.trim());
		}

		String reportName = numConfigLab.getReportName();
		if (reportName != null) {
			configLab008.setReportName(reportName.trim());
		}

		if (configLab008DAO.saveOrUpdate(configLab008)) {
			return "success";
		} else {
			return "fail";
		}
	}

	// private String convertMultiModuleToID(String moduleNumber) {
	// if (moduleNumber == null || moduleNumber.isEmpty()) {
	// return null;
	// } else {
	// String[] arr = moduleNumber.split(FrontalKey.PLUS_SPECIAL);
	// String lstModule = "";
	// for (int i = 0; i < arr.length; i++) {
	// Integer number =
	// csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()).trim());
	// if (number != null) {
	// lstModule += Utils.getModuleIndexRatio(arr[i].trim()).trim() +
	// number.toString() + FrontalKey.PLUS;
	// } else {
	// msg = "Module '" + arr[i] + "' does not exist";
	// return null;
	// }
	// }
	// if (FrontalKey.PLUS.equals(lstModule.charAt(lstModule.length() - 1))) {
	// lstModule = lstModule.substring(0, lstModule.length() - 1);
	// }
	// return lstModule;
	// }
	// }

	private String convertSingerModuleToID(String moduleNumber) {
		if (moduleNumber == null || moduleNumber.isEmpty()) {
			return null;
		} else {
			Integer number = csm.getModuleCSMIDByNumber(moduleNumber.trim());
			if (number != null) {
				return String.valueOf(number);
			} else {
				msg = "Module '" + moduleNumber.trim() + "' does not exist";
				return null;
			}
		}
	}

	public String updatelab008BoilerInfoFrCsv(File importCsvFile, Integer siteId) {
		String ret = "successfull";

		if (importCsvFile != null) {
			ArrayList<Lab008BoilerInfo> boilerInfoList = getLab008BoilerInfoFrCsvFile(importCsvFile, siteId);
			if (boilerInfoList != null && boilerInfoList.size() > 0) {
				if (IndusHistoryData.deleteBoilerInfo(siteId)) {
					if (IndusHistoryData.createBoilerInfo(boilerInfoList)) {
						ret = messBoilerSuccess;
					} else {
						ret = "Error when insert DB";
					}
				} else {
					ret = "Error when delete old data";
				}

			} else {
				ret = messBoilerError;
			}

		} else {
			ret = "Import file is null";
		}

		return ret;
	}

	@Override
	public String updatelab008PerformanceFrCsv(File importCsvFile, Integer siteId) {
		String ret = "success";

		if (importCsvFile != null) {
			ArrayList<Lab008PerformanceData> performanceList = getLab008PerfomanceFrCsvFile(importCsvFile, siteId);
			if (performanceList != null && performanceList.size() > 0) {
				if (IndusHistoryData.deletePerformanceData(siteId)) {
					if (IndusHistoryData.createPerformanceData(performanceList)) {
						ret = messPerformanceSuccess;
					} else {
						ret = "Error when insert DB";
					}
				} else {
					ret = "Error when delete old data";
				}
			} else {
				ret = messPerformanceError;
			}

		} else {
			ret = "import file is null";
		}

		return ret;
	}

	public InputStream exportLab008BoilerInfotoCsv(String[] dataHeader, Integer siteId) {
		InputStream ret = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<Lab008BoilerInfo> objList = IndusHistoryData.getBoilerInfo(siteId);
			ArrayList<String[]> dataList = new ArrayList<String[]>();
			if (objList != null && objList.size() > 0) {
				for (int i = 0; i < objList.size(); i++) {
					Lab008BoilerInfo obj = objList.get(i);
					if (obj != null) {
						if (obj.getSiteId() != null && obj.getDateTime() != null && obj.getTemperature() != null
								&& obj.getInputModuleValue() != null && obj.getOutputModuleValue() != null) {
							String[] dataLine = {
									LabUtils.convertDateByFormat(obj.getDateTime(), FrontalKey.DATE_DATA_FORMAT),
									obj.getTemperature().toString(), obj.getInputModuleValue().toString(),
									obj.getOutputModuleValue().toString() };
							dataList.add(dataLine);
						}

					}
				}

			}
			ret = generateCsvFile(dataHeader, dataList);
		}
		return ret;
	}

	public InputStream exportLab008PerformanceToCsv(String[] dataHeader, Integer siteId) {
		InputStream ret = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<Lab008PerformanceData> objList = IndusHistoryData.getPerformanceData(siteId);
			ArrayList<String[]> dataList = new ArrayList<String[]>();
			if (objList != null && objList.size() > 0) {
				for (int i = 0; i < objList.size(); i++) {
					Lab008PerformanceData obj = objList.get(i);
					if (obj != null) {

						if (obj.getSiteId() != null && obj.getMonth() != null && obj.getDataDJU() != null
								&& obj.getDatakWhMensuel() != null) {
							String[] dataLine = {
									LabUtils.convertDateByFormat(obj.getMonth(), FrontalKey.DATE_SLASH_FORMAT),
									obj.getDataDJU().toString(), obj.getDatakWhMensuel().toString() };
							dataList.add(dataLine);
						}

					}
				}
			}
			ret = generateCsvFile(dataHeader, dataList);
		}
		return ret;
	}

	// public InputStream exportLab008TrefInfotoCsv(String[] dataHeader) {
	// InputStream ret = null;
	// List<Lab008Tref> objList = Lab008TrefDAO.getAllData();
	// // if (objList != null && objList.size() > 0) {
	// ArrayList<String[]> dataList = new ArrayList<String[]>();
	// for (int i = 0; i < objList.size(); i++) {
	// Lab008Tref obj = objList.get(i);
	// if (obj != null) {
	// if (obj.getTemperatureRef() != null && obj.getApplicableDate() != null) {
	// String[] dataLine = { obj.getTemperatureRef().toString(),
	// obj.getApplicableDate().toString() };
	// dataList.add(dataLine);
	// }
	//
	// }
	// }
	//
	// ret = generateCsvFile(dataHeader, dataList);
	// // }
	//
	// return ret;
	// }

	private InputStream generateCsvFile(String[] dataHeader, ArrayList<String[]> dataList) {
		InputStream ret = null;
		if (dataHeader != null && dataHeader.length > 0) {
			try {
				// File layersConf = new File(exportCsvFilePath);
				// layersConf.createNewFile();
				StringBuilder writer = new StringBuilder();
				String newLineSeparator = "\n";
				String fieldSeparator = ",";
				// write header
				for (int j = 0; j < dataHeader.length; j++) {
					writer.append(dataHeader[j]);
					writer.append(fieldSeparator);
				}

				for (int i = 0; i < dataList.size(); i++) {
					String[] dataLine = dataList.get(i);
					if (dataHeader.length <= dataLine.length) {
						writer.append(newLineSeparator);
						for (int j = 0; j < dataHeader.length; j++) {
							writer.append(dataLine[j]);
							writer.append(fieldSeparator);
						}
					}
				}

				ret = new ByteArrayInputStream(writer.toString().getBytes(Charset.forName("UTF-8")));
			} catch (Exception e) {
				ret = null;
				e.printStackTrace();
			}
		}

		return ret;

	}

	private ArrayList<Lab008BoilerInfo> getLab008BoilerInfoFrCsvFile(File importCsvFile, Integer siteId) {
		BufferedReader br = null;
		int i = 2;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Lab008BoilerInfo> boilerInfoList = new ArrayList<Lab008BoilerInfo>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(FrontalKey.DATE_DATA_FORMAT);
		Map<String, Integer> map = new HashMap<>();
		if (importCsvFile != null) {
			try {
				br = new BufferedReader(new FileReader(importCsvFile));
				br.readLine();
				while ((line = br.readLine()) != null) {
					// use comma as separator
					String[] boilerInfo = line.split(cvsSplitBy);
					if (boilerInfo != null && boilerInfo.length > 3) {

						// Integer siteId = null;
						Date date = null;
						// java.sql.Time time = null;
						Double temperature = null;
						Double inputModuleValue = null;
						Double outputModuleValue = null;
						int column = 0;
						// column 0
						if (boilerInfo[column] != null && boilerInfo[column] != null
								&& !boilerInfo[column].trim().isEmpty()) {
							try {
								String dateStr = boilerInfo[column].trim();
								date = dateFormat.parse(dateStr);
								if (map.containsKey(dateStr)) {
									messBoilerError = "There are 2 row have the save value " + dateStr + "| row:" + i
											+ " and row: " + map.get(dateStr);
									return null;
								} else {
									map.put(dateStr, i);
								}
							} catch (ParseException nfe) {
								messBoilerError = "Date format error in line " + i;
								return null;
							}
						}

						column++;// column 1
						if (boilerInfo[column] != null && boilerInfo[column] != null
								&& !boilerInfo[column].trim().isEmpty()) {
							try {
								temperature = Double.parseDouble(boilerInfo[column].trim());
							} catch (NumberFormatException nfe) {
								messBoilerError = "Number format error in line " + i;
								return null;
							}
						}
						column++;// column 2
						if (boilerInfo[column] != null && boilerInfo[column] != null
								&& !boilerInfo[column].trim().isEmpty()) {
							try {
								inputModuleValue = Double.parseDouble(boilerInfo[column].trim());
							} catch (NumberFormatException nfe) {
								messBoilerError = "Number format error in line " + i;
								return null;
							}
						}

						column++;// column 3
						if (boilerInfo[column] != null && boilerInfo[column] != null
								&& !boilerInfo[column].trim().isEmpty()) {
							try {
								outputModuleValue = Double.parseDouble(boilerInfo[column].trim());
							} catch (NumberFormatException nfe) {
								messBoilerError = "Number format error in line " + i;
								return null;
							}
						}

						Lab008BoilerInfo boilerInfoObj = new Lab008BoilerInfo();
						boilerInfoObj.setSiteId(siteId);
						boilerInfoObj.setDateTime(date);
						boilerInfoObj.setTemperature(temperature);
						boilerInfoObj.setInputModuleValue(inputModuleValue);
						boilerInfoObj.setOutputModuleValue(outputModuleValue);

						boilerInfoList.add(boilerInfoObj);

					} else {
						messBoilerError = "Incorrect number of columns!, row:" + i;
						return null;
					}
					i++;
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		messBoilerSuccess = "Sum records " + boilerInfoList.size();
		return boilerInfoList;
	}

	private ArrayList<Lab008PerformanceData> getLab008PerfomanceFrCsvFile(File importCsvFile, Integer siteId) {
		BufferedReader br = null;
		int i = 2;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Lab008PerformanceData> listData = new ArrayList<Lab008PerformanceData>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(FrontalKey.DATE_SLASH_FORMAT);
		Map<String, Integer> map = new HashMap<>();
		if (importCsvFile != null) {
			try {
				br = new BufferedReader(new FileReader(importCsvFile));
				br.readLine();
				while ((line = br.readLine()) != null) {
					// use comma as separator
					String[] performData = line.split(cvsSplitBy);
					if (performData != null && performData.length > 2) {

						// Integer siteId = null;
						Date month = null;
						Double dataDJU = null;
						Double datakWhMensuel = null;
						// Double signatureEnergy = null;

						int column = 0;
						if (performData[column] != null && performData[column] != null
								&& !performData[column].trim().isEmpty()) {
							try {

								String dateStr = performData[column].trim();
								month = dateFormat.parse(performData[column].trim());
								if (map.containsKey(dateStr)) {
									messBoilerError = "There are 2 row have the save value " + dateStr + "| row:" + i
											+ " and row: " + map.get(dateStr);
									return null;
								} else {
									map.put(dateStr, i);
								}

							} catch (ParseException nfe) {
								messPerformanceError = "Date format error in line " + i;
								return null;
							}
						}
						column++;// column 1
						if (performData[column] != null && performData[column] != null
								&& !performData[column].trim().isEmpty()) {
							try {
								dataDJU = Double.parseDouble(performData[column].trim());
							} catch (NumberFormatException nfe) {
								messPerformanceError = "Number format error in line " + i;
								return null;
							}
						}
						column++;// column 2
						if (performData[column] != null && performData[column] != null
								&& !performData[column].trim().isEmpty()) {
							try {
								datakWhMensuel = Double.parseDouble(performData[column].trim());
							} catch (NumberFormatException nfe) {
								messPerformanceError = "Number format error in line " + i;
								return null;
							}
						}

						Lab008PerformanceData obj = new Lab008PerformanceData();
						obj.setSiteId(siteId);
						obj.setDataDJU(dataDJU);
						obj.setDatakWhMensuel(datakWhMensuel);
						obj.setMonth(month);
						listData.add(obj);
					} else {
						messPerformanceError = "Incorrect number of columns!, row:" + i;
						return null;
					}
					i++;
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		messPerformanceSuccess = "Sum records " + listData.size();
		return listData;
	}

	// public String updatelab008TrefFrCsv(File importCsvFile) {
	// String ret = "success";
	// if (importCsvFile != null) {
	// ArrayList<Lab008Tref> tRefList = getLab008TrefFrCsvFile(importCsvFile);
	// if (tRefList != null && tRefList.size() > 0) {
	// Lab008TrefDAO.deleteAll();
	// Lab008TrefDAO.create(tRefList);
	// ret = messTrefSuccess;
	// } else {
	// ret = messTrefError;
	// }
	//
	// } else {
	// ret = "import file is null";
	// }
	//
	// return ret;
	// }

	// private ArrayList<Lab008Tref> getLab008TrefFrCsvFile(File importCsvFile)
	// {
	// BufferedReader br = null;
	// String line = "";
	// String cvsSplitBy = ",";
	// ArrayList<Lab008Tref> tRefList = new ArrayList<Lab008Tref>();
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss");
	//
	// if (importCsvFile != null) {
	// try {
	// br = new BufferedReader(new FileReader(importCsvFile));
	// br.readLine();
	// int i = 2;
	// while ((line = br.readLine()) != null) {
	// // use comma as separator
	// String[] tRef = line.split(cvsSplitBy);
	// if (tRef != null && tRef.length > 1) {
	// Double temperatureRef = null;
	// java.util.Date appDate = null;
	//
	// if (tRef[0] != null && tRef[0] != null && !tRef[0].trim().isEmpty()) {
	// try {
	// temperatureRef = Double.parseDouble(tRef[0].trim());
	//
	// } catch (NumberFormatException nfe) {
	// messTrefError = "Number format error in line " + i;
	// return null;
	// }
	// }
	//
	// if (tRef[1] != null && tRef[1] != null && !tRef[1].trim().isEmpty()) {
	// try {
	// appDate = dateFormat.parse(tRef[1].trim());
	//
	// } catch (ParseException nfe) {
	// messTrefError = "Date format error in line " + i;
	// return null;
	// }
	// }
	//
	// if (temperatureRef != null && appDate != null) {
	// Lab008Tref tRefObj = new Lab008Tref();
	// tRefObj.setTemperatureRef(temperatureRef);
	// tRefObj.setApplicableDate(appDate);
	//
	// tRefList.add(tRefObj);
	// }
	//
	// } else {
	// messTrefError = "Incorrect number of columns!";
	// return null;
	// }
	// i++;
	// }
	//
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if (br != null) {
	// try {
	// br.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// }
	// messTrefSuccess = "Sum records " + tRefList.size();
	// return tRefList;
	// }
}
