package com.energisme.action;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab008;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab008Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class Lab008Action extends AbstractBaseAction implements ModelDriven<NumConfigLab008> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Lab008Service lab008Service;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab008 numConfigLab008;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab008V2 configLab008;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private Integer siteId;

	private List<ConfigLab008V2> listAllConfig = new ArrayList<ConfigLab008V2>();
	private Lab lab;
	private List<String> listUsers;

	private String listUser;
	public boolean isCreate;

	private String siteImport;
	private File importCsvFile;

	private File importTrefCsvFile;

	private File importPerformaceCsvFile;

	private InputStream fileInputStream;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	@Override
	public NumConfigLab008 getModel() {
		return numConfigLab008;
	}

	public String redirect() {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (numConfigLab008.getSiteId() != null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			siteId = Integer.parseInt(request.getParameter("siteId"));
			session.put(FrontalKey.SITE_ID_008, siteId);
			configLab008 = lab008Service.getConfigLab008BySite(siteId);
			numConfigLab008 = co.convertIdtoMuduleNumberChart(configLab008);
			listAllConfig = lab008Service.getAllConfigLab008();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		} else {
			listAllConfig = lab008Service.getAllConfigLab008();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab008 = co.convertIdtoMuduleNumberChart(listAllConfig.get(0));
				siteId = Integer.parseInt(numConfigLab008.getSiteId());
				session.put(FrontalKey.SITE_ID_008, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			}
		}
		isCreate = false;

		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		numConfigLab008 = new NumConfigLab008();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = Integer.parseInt(request.getParameter("siteId"));
		session.put(FrontalKey.SITE_ID_008, siteId);
		configLab008 = lab008Service.getConfigLab008BySite(siteId);
		numConfigLab008 = co.convertIdtoMuduleNumberChart(configLab008);
		listAllConfig = lab008Service.getAllConfigLab008();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = lab008Service.getMaxSiteId() + 1;
				mes = lab008Service.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab008.setReportName(filename);
					mes = lab008Service.saveConfig(numConfigLab008);
					if (mes.equals(SUCCESS)) {
						listAllConfig = lab008Service.getAllConfigLab008();
						numConfigLab008 = co.convertIdtoMuduleNumberChart(listAllConfig.get(listAllConfig.size() - 1));
						siteId = Integer.parseInt(numConfigLab008.getSiteId());
						session.put(FrontalKey.SITE_ID_008, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId.toString(), username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
						isCreate = true;
					}
				}
			} else {

				mes = lab008Service.saveConfig(numConfigLab008);
				if (mes.equals(SUCCESS)) {
					listAllConfig = lab008Service.getAllConfigLab008();
					numConfigLab008 = co.convertIdtoMuduleNumberChart(listAllConfig.get(listAllConfig.size() - 1));
					siteId = Integer.parseInt(numConfigLab008.getSiteId());
					session.put(FrontalKey.SITE_ID_008, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId.toString(), username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
					isCreate = true;
				}
			}
		} else {
			try {
				siteId = Integer.parseInt(numConfigLab008.getSiteId());
			} catch (NumberFormatException nfe) {
				mes = "SiteID must a number:" + numConfigLab008.getSiteId();
			}

			isCreate = false;

			String oldSiteId = session.get(FrontalKey.SITE_ID_008).toString();
			if (mes == null) {
				if (file != null) {
					mes = lab008Service.uploadFile(siteId.toString(), file, filename);
					if (mes.equals(SUCCESS)) {
						numConfigLab008.setReportName(filename);
						mes = lab008Service.saveConfig(numConfigLab008);
					}
				} else {
					mes = lab008Service.saveConfig(numConfigLab008);
				}
				if (mes.equals(SUCCESS)) {
					if (!siteId.equals(oldSiteId))
						labService.updateSiteId(lab.getId(), Integer.parseInt(oldSiteId), siteId);
					session.put(FrontalKey.SITE_ID_008, siteId);
				} else {
					siteId = Integer.parseInt(oldSiteId);
				}
			}else{
				siteId = Integer.parseInt(oldSiteId);
			}
			numConfigLab008.setSiteId(String.valueOf((siteId)));
			listAllConfig = lab008Service.getAllConfigLab008();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		}
		return SUCCESS;
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab008Service.deleteConfig(id);
		siteId = Integer.parseInt(request.getParameter("siteId"));
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
			labService.deleteUserForSite(lab.getId(), siteId);
			listAllConfig = lab008Service.getAllConfigLab008();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab008 = co.convertIdtoMuduleNumberChart(listAllConfig.get(0));
				siteId = Integer.parseInt(numConfigLab008.getSiteId());
				session.put(FrontalKey.SITE_ID_008, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_008, siteId);
			configLab008 = lab008Service.getConfigLab008BySite(siteId);
			numConfigLab008 = co.convertIdtoMuduleNumberChart(configLab008);
			listAllConfig = lab008Service.getAllConfigLab008();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = (Integer) session.get(FrontalKey.SITE_ID_008);
		mes = labService.registerUserForLab(lab.getName(), siteId.toString(), listUserRegist);
		configLab008 = lab008Service.getConfigLab008BySite(siteId);
		numConfigLab008 = co.convertIdtoMuduleNumberChart(configLab008);
		listAllConfig = lab008Service.getAllConfigLab008();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = (Integer) session.get(FrontalKey.SITE_ID_008);
		mes = labService.unregisterUserForLab(lab.getName(), siteId.toString(), listUser);
		configLab008 = lab008Service.getConfigLab008BySite(siteId);
		numConfigLab008 = co.convertIdtoMuduleNumberChart(configLab008);
		listAllConfig = lab008Service.getAllConfigLab008();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public String importlab008BoilerInfoFrCsv() {
		if (importCsvFile != null) {
			mes = lab008Service.updatelab008BoilerInfoFrCsv(importCsvFile, Integer.parseInt(siteImport));
		} else {
			mes = "import file is null!";
		}
		return SUCCESS;
	}

	public String importlab008PerformanceCsv() {
		if (importPerformaceCsvFile != null) {
			mes = lab008Service.updatelab008PerformanceFrCsv(importPerformaceCsvFile, Integer.parseInt(siteImport));
		} else {
			mes = "import file is null!";
		}
		return SUCCESS;
	}

	// public String importlab008TrefFrCsv() {
	// redirectImport();
	// if (importTrefCsvFile != null) {
	// mes = lab008Service.updatelab008TrefFrCsv(importTrefCsvFile);
	// }else{
	// mes = "import file is null!";
	// }
	// return SUCCESS;
	// }

	// public String exportlab008TrefFrCsv() {
	// String[] dataHeader = { "temperatureRef", "applicableDate" };
	// fileInputStream = lab008Service.exportLab008TrefInfotoCsv(dataHeader);
	// return SUCCESS;
	// }

	public String exportlab008BoilerInfoFrCsv() {
		String[] dataHeader = { "date", "temperature", "inputModuleValue", "outputModuleValue" };
		fileInputStream = lab008Service.exportLab008BoilerInfotoCsv(dataHeader, Integer.parseInt(siteImport));
		return SUCCESS;
	}

	public String exportlab008PerformanceCsv() {
		String[] dataHeader = { "month", "dataDJU", "datakWhMensuel" };
		fileInputStream = lab008Service.exportLab008PerformanceToCsv(dataHeader, Integer.parseInt(siteImport));
		return SUCCESS;
	}

	public Lab008Service getLab008Service() {
		return lab008Service;
	}

	public void setLab008Service(Lab008Service Lab008Service) {
		this.lab008Service = Lab008Service;
	}

	public LabService getLabService() {
		return labService;
	}

	public void setLabService(LabService labService) {
		this.labService = labService;
	}

	public NumConfigLab008 getNumConfigLab008() {
		return numConfigLab008;
	}

	public void setNumConfigLab008(NumConfigLab008 numConfigLab008) {
		this.numConfigLab008 = numConfigLab008;
	}

	public ConstantService getConstantService() {
		return constantService;
	}

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	public ConfigLab008V2 getConfigLab008() {
		return configLab008;
	}

	public void setConfigLab008(ConfigLab008V2 configLab008) {
		this.configLab008 = configLab008;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public List<ConfigLab008V2> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab008V2> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUsername_lab_service() {
		return username_lab_service;
	}

	public void setUsername_lab_service(String username_lab_service) {
		this.username_lab_service = username_lab_service;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public String getListUser() {
		return listUser;
	}

	public void setListUser(String listUser) {
		this.listUser = listUser;
	}

	public List<String> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<String> listUsers) {
		this.listUsers = listUsers;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private String filename;

	public void setFileFileName(String filename) {
		this.filename = filename;
	}

	public File getImportCsvFile() {
		return importCsvFile;
	}

	public void setImportCsvFile(File importCsvFile) {
		this.importCsvFile = importCsvFile;
	}

	public File getImportTrefCsvFile() {
		return importTrefCsvFile;
	}

	public void setImportTrefCsvFile(File importTrefCsvFile) {
		this.importTrefCsvFile = importTrefCsvFile;
	}

	public File getImportPerformaceCsvFile() {
		return importPerformaceCsvFile;
	}

	public void setImportPerformaceCsvFile(File importPerformaceCsvFile) {
		this.importPerformaceCsvFile = importPerformaceCsvFile;
	}

	public String getSiteImport() {
		return siteImport;
	}

	public void setSiteImport(String siteImport) {
		this.siteImport = siteImport;
	}

}
