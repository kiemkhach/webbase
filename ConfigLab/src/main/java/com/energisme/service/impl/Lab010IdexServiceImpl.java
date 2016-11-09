package com.energisme.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab010Idex;
import com.energisme.service.Lab010IdexService;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.lab.LabDAO.dao.Idex.IdexConfigDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCostDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCostDetailDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexMeteoDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexRelevesDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexConfig;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexMeteo;
import com.ifi.lab.LabDAO.model.Idex.IdexReleves;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public class Lab010IdexServiceImpl implements Lab010IdexService{
	@Autowired
	private IdexInstallationDAO idexInstallationDAO;
	@Autowired
	private IdexConfigDAO idexConfigDAO;
	@Autowired
	private IdexSiteDAO idexSiteDAO;
	@Autowired
	private IdexCounterDAO idexCounterDAO;
	@Autowired
	private IdexCostDAO idexCostDAO;
	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;
	@Autowired
	private IdexCostDetailDAO idexCostDetailDAO;
	@Autowired
	private IdexRelevesDAO idexRelevesDAO;
	@Autowired
	private IdexMeteoDAO idexMeteoDAO;
	private String messageError = null;
	private String messageSuccess = null;
	private String messageRelevesError = null;
	private String messageRelevesSuccess = null;
	private String messageMeteoError = null;
	private String messageMeteoSuccess = null;
	@Override
	public List<IdexInstallation> getAllIdexInstallation() {
		List<IdexInstallation> idexInstallations = idexInstallationDAO.getAll();
		return idexInstallations;
	}

	@Override
	public IdexConfig getIdexConfigByInstallationId(Integer installationId) {
		IdexConfig idexConfig = new IdexConfig();
		idexConfig = idexConfigDAO.getByInstalltion(installationId);
		return idexConfig;
	}

	@Override
	public boolean saveIdexConfig(IdexConfig obj) {
		NumConfigLab010Idex numConfigLab010Idex = new NumConfigLab010Idex();
		if(numConfigLab010Idex.getIdexInstallationId()!=null){
			obj = idexConfigDAO.getByInstalltion(numConfigLab010Idex.getIdexInstallationId());
		}
		return idexConfigDAO.saveOrUpdate(obj);
	}

	@Override
	public List<IdexEnergySupplier> getAllIdexEnergySupplier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdexCost> getAllIdexCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdexSite> getAllIdexSite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdexCounter> getGroupIdexCounterByInstallation(
			int idexInstallation) {
		return idexCounterDAO.findByInstallation(idexInstallation);

	}

	@Override
	public List<IdexCost> getGroupIdexCostByInstallation(int idexInstallation) {
		return idexCostDAO.findByInstallationId(idexInstallation);
	}

	@Override
	public List<IdexEnergySupplier> getGroupIdexEnergySupplierByInstallation(
			int idexInstalltion) {
		return idexEnergySupplierDAO.findByInstallation(idexInstalltion);

	}

	@Override
	public List<IdexSite> getGroupIdexSiteByInstallation(int idexInstallation) {
		return idexSiteDAO.findByInstallation(idexInstallation);

	}

	@Override
	public boolean saveIdexCost(IdexCost obj) {
		return idexCostDAO.save(obj);
	}
	
	@Override
	public Boolean uploadFile(String path, String filename,File file) {
		try {
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return false;
				} 
			}	
			File newFile = new File(path + FrontalKey.WINDOWS + filename);
			FileUtils.copyFile(file, newFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public InputStream exportLab010CostDetailByIdToCSV(String[] dataHeader,
			int idexCostId) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexCostDetail> idexCostDetailList = idexCostDetailDAO.findByIdexCostId(idexCostId);
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexCostDetailList != null && idexCostDetailList.size() > 0){
				for (IdexCostDetail idexCostDetail : idexCostDetailList) {
					String dateStr = "";
					String costStr = "";
					String consumptionStr = "";
					if(idexCostDetail.getDetailDate() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexCostDetail.getDetailDate(), "MM-yyyy");
					}
					if(idexCostDetail.getAmount() != null){
						costStr =  idexCostDetail.getAmount().toString();
					}
					if(idexCostDetail.getConsumption() != null){
						consumptionStr = idexCostDetail.getConsumption().toString();
					}
/*					if(idexCostDetail.getDetailDate() != null && idexCostDetail.getAmount() != null && idexCostDetail.getConsumption() != null){
						String formatDate = LabUtils.convertDateByFormatNonUTC(idexCostDetail.getDetailDate(), "MM-yyyy");
						String[] dataLine = {formatDate, idexCostDetail.getAmount().toString(), 
								idexCostDetail.getConsumption().toString()};
						dataList.add(dataLine);
					}*/
					String[] dataLine = {dateStr, costStr, consumptionStr};
					dataList.add(dataLine);

				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}

	@Override
	public InputStream exportAllLab010CostDetailByIdToCSV(String[] dataHeader) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexCostDetail> idexCostDetailList = idexCostDetailDAO.getAll();
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexCostDetailList != null && idexCostDetailList.size() > 0){
				for (IdexCostDetail idexCostDetail : idexCostDetailList) {
					String dateStr = "";
					String costStr = "";
					String consumptionStr = "";
					if(idexCostDetail.getDetailDate() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexCostDetail.getDetailDate(), "MM-yyyy");
					}
					if(idexCostDetail.getAmount() != null){
						costStr =  idexCostDetail.getAmount().toString();
					}
					if(idexCostDetail.getConsumption() != null){
						consumptionStr = idexCostDetail.getConsumption().toString();
					}
					if(idexCostDetail.getIdexCost().getIdexCostId() != null){
						String[] dataLine = {idexCostDetail.getIdexCost().getIdexCostId().toString(), dateStr, costStr, consumptionStr};
						dataList.add(dataLine);
					}
				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}
	
	private InputStream generateCsvFile(String[] dataHeader, List<String[]> dataList) {
		InputStream ret = null;
		if (dataHeader != null && dataHeader.length > 0) {
			try {
				StringBuilder writer = new StringBuilder();
				String newLineSeparator = "\n";
				String fieldSeparator = ",";
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

	@Override
	public String importSubCategoryByCSV(File importFile, int idexCostId) {
		String ret = "success";

		if (importFile != null) {
			List<IdexCostDetail> costDetailList = getIdexCostDetailListFromCSV(importFile, idexCostId);
			if (costDetailList != null && !costDetailList.isEmpty()) {
				if (idexCostDetailDAO.deleteByIdexCostId(idexCostId)) {
					if (idexCostDetailDAO.insertList(costDetailList)) {
					} else {
						ret = "Error when insert DB";
					}
				} else {
					ret = "Error when delete old data";
				}
			} else {
				ret = messageError;
			}

		} else {
			ret = "import file is null";
		}

		return ret;
	}

	private List<IdexCostDetail> getIdexCostDetailListFromCSV(File importFile,
			int idexCostId) {
		BufferedReader br = null;
		int i = 2;
		String line = "";
		String cvsSplitBy = ",";
		List<IdexCostDetail> listData = new ArrayList<IdexCostDetail>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		/*Map<String, Integer> map = new HashMap<>();*/
		IdexCost idexCost = new IdexCost();
		idexCost.setIdexCostId(idexCostId);
		if (importFile != null) {
			try {
				br = new BufferedReader(new FileReader(importFile));
				br.readLine();
				while ((line = br.readLine()) != null) {
					String[] idexCostDetailData = line.split(cvsSplitBy);
					if (idexCostDetailData != null) {
						if(idexCostDetailData.length == 1){
							if(!idexCostDetailData[0].trim().equals("")){
								messageError = "Incorrect number of columns!, row:" + i;
								return null;
							}
						}else if(idexCostDetailData.length == 3){
							Date detailDate = null;
							Float amount = null;
							Float consumption = null;
							int column = 0;
							if (idexCostDetailData[column] != null && !idexCostDetailData[column].trim().isEmpty()) {
								try {

									String dateStr = "01-"+idexCostDetailData[column].trim();
									System.out.println(dateStr);
									detailDate = dateFormat.parse(dateStr);
	/*								if (map.containsKey(dateStr)) {
										messageError = "There are 2 row have the save value " + dateStr + "| row:" + i
												+ " and row: " + map.get(dateStr);
										return null;
									} else {
										map.put(dateStr, i);
									}*/

								} catch (ParseException nfe) {
									messageError = "Date format error in line " + i +",correct format: MM-yyyy ";
									return null;
								}
							}
							column++;
							if (idexCostDetailData[column] != null && !idexCostDetailData[column].trim().isEmpty()) {
								try {
									amount = Float.valueOf(idexCostDetailData[column].trim());
								} catch (NumberFormatException nfe) {
									messageError = "Number format error in line " + i;
									return null;
								}
							}
							column++;
							if (idexCostDetailData[column] != null && !idexCostDetailData[column].trim().isEmpty()) {
								try {
									consumption = Float.valueOf(idexCostDetailData[column].trim());
								} catch (NumberFormatException nfe) {
									messageError = "Number format error in line " + i;
									return null;
								}
							}
								IdexCostDetail obj = new IdexCostDetail();
								obj.setIdexCost(idexCost);
								obj.setDetailDate(detailDate);
								obj.setAmount(amount);
								obj.setConsumption(consumption);
								listData.add(obj);	
						}else{
							messageError = "Incorrect number of columns!, row:" + i;
							return null;
						}
						}
/*					else {
						messageError = "Incorrect number of columns!, row:" + i;
						return null;
					}*/
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
		messageSuccess = "Sum records " + listData.size();
		return listData;
	}

	@Override
	public boolean deleteIdexCost(int idexCostId) {
		try{
			idexCostDetailDAO.deleteByIdexCostId(idexCostId);
			idexCostDAO.deleteById(idexCostId);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public String importRelevesByCSV(File importFile, int idexCounterId) {
		String ret = "success";

		if (importFile != null) {
			List<IdexReleves> idexRelevesList = getIdexRelevesListFromCSV(importFile, idexCounterId);
			if (idexRelevesList != null && !idexRelevesList.isEmpty()) {
				if (idexRelevesDAO.deleteByCounter(idexCounterId)) {
					if (idexRelevesDAO.saveRelevesList(idexRelevesList)) {
					} else {
						ret = "Error when insert DB";
					}
				} else {
					ret = "Error when delete old data";
				}
			} else {
				ret = messageRelevesError;
			}

		} else {
			ret = "import file is null";
		}

		return ret;
	}
	private List<IdexReleves> getIdexRelevesListFromCSV(File importFile,
			int idexCounterId){
		BufferedReader br = null;
		int i = 2;
		String line = "";
		String cvsSplitBy = ",";
		List<IdexReleves> listData = new ArrayList<IdexReleves>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		/*Map<String, Integer> map = new HashMap<>();*/
		IdexCounter idexCounter = new IdexCounter();
		idexCounter.setIdexCounterId(idexCounterId);
		if (importFile != null) {
			try {
				br = new BufferedReader(new FileReader(importFile));
				br.readLine();
				while ((line = br.readLine()) != null) {
					String[] idexRelevesData = line.split(cvsSplitBy);
					if (idexRelevesData != null) {
						if(idexRelevesData.length == 1){
							if(!idexRelevesData[0].trim().equals("")){
								messageRelevesError = "Incorrect number of columns!, row:" + i;
								return null;
							}
						}else if(idexRelevesData.length == 2){
							Date detailDate = null;
							Float value = null;
							int column = 0;
							if (idexRelevesData[column] != null
									&& !idexRelevesData[column].trim().isEmpty()) {
								try {

									String dateStr = "01-"+idexRelevesData[column].trim();
									System.out.println(dateStr);
									detailDate = dateFormat.parse(dateStr);
								} catch (ParseException nfe) {
									messageRelevesError = "Date format error in line " + i +",correct format: MM-yyyy ";
									return null;
								}
							}else{
								messageRelevesError = "Incorrect number of columns!, row:" + i;
								return null;
							}
							column++;
							if (idexRelevesData[column] != null && idexRelevesData[column] != null
									&& !idexRelevesData[column].trim().isEmpty()) {
								try {
									value = Float.valueOf(idexRelevesData[column].trim());
								} catch (NumberFormatException nfe) {
									messageRelevesError = "Number format error in line " + i;
									return null;
								}
							}else{
								messageRelevesError = "Incorrect number of columns!, row:" + i;
								return null;
							}
								IdexReleves obj = new IdexReleves();
								obj.setMonth(detailDate);
								obj.setValue(value);
								obj.setIdexCounter(idexCounter);
								listData.add(obj);	
						}else{
							messageRelevesError = "Incorrect number of columns!, row:" + i;
							return null;
						}
						}/* else {
							messageRelevesError = "Incorrect number of columns!, row:" + i;
						return null;
					}*/
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
		messageRelevesSuccess = "Sum records " + listData.size();
		return listData;
	}
	@Override
	public InputStream exportLab010RelevesByIdToCSV(String[] dataHeader,
			int idexCounterId) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexReleves> idexRelevesList = idexRelevesDAO.findByCounter(idexCounterId);
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexRelevesList != null && idexRelevesList.size() > 0){
				for (IdexReleves idexReleves : idexRelevesList) {
					String dateStr = "";
					String valueStr = "";
					if(idexReleves.getMonth() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexReleves.getMonth(), "MM-yyyy");
					}
					if(idexReleves.getValue() != null){
						valueStr = idexReleves.getValue().toString();
					}
/*					if(idexReleves.getMonth() != null && idexReleves.getValue() != null){
						String formatDate = LabUtils.convertDateByFormatNonUTC(idexReleves.getMonth(), "MM-yyyy");
						String[] dataLine = {formatDate, idexReleves.getValue().toString()};
						dataList.add(dataLine);
					}*/
					String[] dataLine = {dateStr, valueStr};
					dataList.add(dataLine);
				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}

	@Override
	public Boolean deleteFile(String path, String filename) {
		try {
			File file = new File(path +File.separator+ filename);
			if((file != null) && (file.exists())){
				file.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public InputStream exportAllLab010RelevesToCSV(String[] dataHeader) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexReleves> idexRelevesList = idexRelevesDAO.getAll();
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexRelevesList != null && idexRelevesList.size() > 0){
				for (IdexReleves idexReleves : idexRelevesList) {
					String dateStr = "";
					String valueStr = "";
					if(idexReleves.getMonth() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexReleves.getMonth(), "MM-yyyy");
					}
					if(idexReleves.getValue() != null){
						valueStr = idexReleves.getValue().toString();
					}
					if(idexReleves.getIdexCounter().getIdexCounterId() != null){
						String[] dataLine = {idexReleves.getIdexCounter().getIdexCounterId().toString(), dateStr, valueStr};
						dataList.add(dataLine);
					}
				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}

	@Override
	public String importMeteoCSVByInstallation(File importFile,
			int idexInstallationId) {
		String ret = "Success";

		if (importFile != null) {
			List<IdexMeteo> idexMeteoList = getIdexMeteoListFromCSV(importFile, idexInstallationId);
			if (idexMeteoList != null && !idexMeteoList.isEmpty()) {
				if (idexMeteoDAO.deleteByInstallation(idexInstallationId)) {
					if (idexMeteoDAO.saveMeteoList(idexMeteoList)) {
					} else {
						ret = "Error when insert DB";
					}
				} else {
					ret = "Error when delete old data";
				}
			} else {
				ret = messageMeteoError;
			}

		} else {
			ret = "Import file is null";
		}

		return ret;
	}
	private List<IdexMeteo> getIdexMeteoListFromCSV(File importFile,
			int idexInstallationId){
		BufferedReader br = null;
		int i = 2;
		String line = "";
		String cvsSplitBy = ",";
		List<IdexMeteo> listData = new ArrayList<IdexMeteo>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		/*Map<String, Integer> map = new HashMap<>();*/
		if (importFile != null) {
			try {
				br = new BufferedReader(new FileReader(importFile));
				br.readLine();
				while ((line = br.readLine()) != null) {
					String[] idexMeteoData = line.split(cvsSplitBy);
					if (idexMeteoData != null) {
					if(idexMeteoData.length == 1){
						if(!idexMeteoData[0].trim().equals("")){
							messageMeteoError = "Incorrect number of columns!, row:" + i;
							return null;
						}
					}else if(idexMeteoData.length == 2){
						Date detailDate = null;
						Integer reel = null;
						int column = 0;
						if (idexMeteoData[column] != null
								&& !idexMeteoData[column].trim().isEmpty()) {
							try {

								String dateStr = idexMeteoData[column].trim();
								System.out.println(dateStr);
								detailDate = dateFormat.parse(dateStr);
							} catch (ParseException nfe) {
								messageMeteoError = "Date format error in line " + i +",correct format: dd-MM-yyyy ";
								return null;
							}
						}else{
							messageMeteoError = "Incorrect number of columns!, row:" + i;
							return null;
						}
						column++;
						if (idexMeteoData[column] != null && idexMeteoData[column] != null
								&& !idexMeteoData[column].trim().isEmpty()) {
							try {
								reel = Integer.parseInt(idexMeteoData[column].trim());
							} catch (NumberFormatException nfe) {
								messageMeteoError = "Number format error in line " + i;
								return null;
							}
						}else{
							messageMeteoError = "Incorrect number of columns!, row:" + i;
							return null;
						}
							IdexMeteo obj = new IdexMeteo();
							obj.setDate(detailDate);
							obj.setReel(reel);
							obj.setIdexInstallationId(idexInstallationId);
							listData.add(obj);	
					}else{
						messageMeteoError = "Incorrect number of columns!, row:" + i;
						return null;
					}
						} /*else {
							messageMeteoError = "Incorrect number of columns!, row:" + i;
						return null;
					}*/
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
		messageMeteoSuccess = "Sum records " + listData.size();
		return listData;
	}
	@Override
	public InputStream exportIdexMeteoByInstallationToCSV(String[] dataHeader,
			int idexInstallationId) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexMeteo> idexMeteoList = idexMeteoDAO.findByInstallation(idexInstallationId);
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexMeteoList != null && idexMeteoList.size() > 0){
				for (IdexMeteo idexMeteo : idexMeteoList) {
					String dateStr = "";
					String reel = "";
					if(idexMeteo.getDate() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexMeteo.getDate(), "dd-MM-yyyy");
					}
					if(idexMeteo.getReel() != null){
						reel = idexMeteo.getReel().toString();
					}
						String[] dataLine = {dateStr, reel};
						dataList.add(dataLine);
				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}

	@Override
	public InputStream exportAllIdexMeteoToCSV(String[] dataHeader) {
		InputStream ins = null;
		if (dataHeader != null && dataHeader.length > 0) {
			List<IdexMeteo> idexMeteoList = idexMeteoDAO.getAll();
			List<String[]> dataList = new ArrayList<String[]>();
			if(idexMeteoList != null && idexMeteoList.size() > 0){
				for (IdexMeteo idexMeteo : idexMeteoList) {
					String dateStr = "";
					String reelStr = "";
					if(idexMeteo.getDate() != null){
						dateStr = LabUtils.convertDateByFormatNonUTC(idexMeteo.getDate(), "dd-MM-yyyy");
					}
					if(idexMeteo.getReel() != null){
						reelStr = idexMeteo.getReel().toString();
					}
					if(idexMeteo.getIdexInstallationId() != null){
						String[] dataLine = {idexMeteo.getIdexInstallationId().toString(), dateStr, reelStr};
						dataList.add(dataLine);
					}
/*					if(idexMeteo.getIdexInstallationId() != null && idexMeteo.getDate() != null && idexMeteo.getReel() != null){
						String formatDate = LabUtils.convertDateByFormatNonUTC(idexMeteo.getDate(), "dd-MM-yyyy");
						String[] dataLine = {idexMeteo.getIdexInstallationId().toString(), formatDate, idexMeteo.getReel().toString()};
						dataList.add(dataLine);
					}*/
				}
			}
			ins = generateCsvFile(dataHeader, dataList);
		}
		return ins;
	}

}
