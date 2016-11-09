package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab003;
import com.energisme.service.ConstantService;
import com.energisme.service.ELeclercService;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab003;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class ELeclercAction extends AbstractBaseAction implements ModelDriven<NumConfigLab003> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ELeclercService eLeclercService;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab003 numConfigLab003;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab003 configLab003;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private List<ConfigLab003> listAllConfig = new ArrayList<ConfigLab003>();

	private Lab lab;

	private List<String> listUsers;

	private String listUser;

	public boolean isCreate;

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = eLeclercService.getAllConfigLab003();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab003 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab003.getSiteId();
			session.put(FrontalKey.SITE_ID_003, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		numConfigLab003 = new NumConfigLab003();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_003, siteId);
		configLab003 = eLeclercService.getConfigLab003BySite(Integer.parseInt(siteId));
		numConfigLab003 = co.convertIdtoMuduleNumber(configLab003);
		listAllConfig = eLeclercService.getAllConfigLab003();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null || driveFile != null) {
				Integer newSiteId = eLeclercService.getMaxSiteId() + 1;
				mes = eLeclercService.uploadFile(newSiteId.toString(), file, filename, driveFile, driveFileName);
				if (mes.equals(SUCCESS)) {
					if (file != null) {
						numConfigLab003.setReportName(filename);
					}
					if (driveFile != null) {
						numConfigLab003.setDriveReportName(driveFileName);
					}
					mes = eLeclercService.saveConfig(numConfigLab003);
					if (mes.equals(SUCCESS)) {
						listAllConfig = eLeclercService.getAllConfigLab003();
						numConfigLab003 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						siteId = numConfigLab003.getSiteId();
						session.put(FrontalKey.SITE_ID_003, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId);
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
						isCreate = true;
					}
				}
			} else {
				mes = eLeclercService.saveConfig(numConfigLab003);
				if (mes.equals(SUCCESS)) {
					listAllConfig = eLeclercService.getAllConfigLab003();
					numConfigLab003 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					siteId = numConfigLab003.getSiteId();
					session.put(FrontalKey.SITE_ID_003, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId);
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab003.getSiteId();
			isCreate = false;

			if (file != null || driveFile != null) {
				mes = eLeclercService.uploadFile(siteId, file, filename, driveFile, driveFileName);
				if (mes.equals(SUCCESS)) {
					if (file != null) {
						numConfigLab003.setReportName(filename);
					}
					if (driveFile != null) {
						numConfigLab003.setDriveReportName(driveFileName);
					}
					mes = eLeclercService.saveConfig(numConfigLab003);
				}
			} else {
				mes = eLeclercService.saveConfig(numConfigLab003);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_003).toString();
			if (mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_003, siteId);
			} else {
				siteId = oldSiteId;
			}
			numConfigLab003.setSiteId(siteId);
			listAllConfig = eLeclercService.getAllConfigLab003();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = eLeclercService.deleteConfig(id);
		siteId = request.getParameter("siteId");
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = eLeclercService.getAllConfigLab003();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab003 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab003.getSiteId();
				session.put(FrontalKey.SITE_ID_003, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_003, siteId);
			configLab003 = eLeclercService.getConfigLab003BySite(Integer.parseInt(siteId));
			numConfigLab003 = co.convertIdtoMuduleNumber(configLab003);
			listAllConfig = eLeclercService.getAllConfigLab003();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_003).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		configLab003 = eLeclercService.getConfigLab003BySite(Integer.parseInt(siteId));
		numConfigLab003 = co.convertIdtoMuduleNumber(configLab003);
		listAllConfig = eLeclercService.getAllConfigLab003();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_003);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_003).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		configLab003 = eLeclercService.getConfigLab003BySite(Integer.parseInt(siteId));
		numConfigLab003 = co.convertIdtoMuduleNumber(configLab003);
		listAllConfig = eLeclercService.getAllConfigLab003();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	@Override
	public NumConfigLab003 getModel() {
		return numConfigLab003;
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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public ConfigLab003 getConfigLab003() {
		return configLab003;
	}

	public void setConfigLab003(ConfigLab003 configLab003) {
		this.configLab003 = configLab003;
	}

	public ELeclercService getELeclercService() {
		return eLeclercService;
	}

	public void setELeclercService(ELeclercService eLeclercService) {
		this.eLeclercService = eLeclercService;
	}

	public NumConfigLab003 getNumConfigLab003() {
		return numConfigLab003;
	}

	public void setNumConfigLab003(NumConfigLab003 numConfigLab003) {
		this.numConfigLab003 = numConfigLab003;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ConfigLab003> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab003> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}

	public String getUsername_lab_service() {
		return username_lab_service;
	}

	public void setUsername_lab_service(String username_lab_service) {
		this.username_lab_service = username_lab_service;
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

	private File driveFile;

	public File getDriveFile() {
		return driveFile;
	}

	public void setDriveFile(File driveFile) {
		this.driveFile = driveFile;
	}

	private String driveFileName;

	public void setDriveFileFileName(String driveFileName) {
		this.driveFileName = driveFileName;
	}

}
