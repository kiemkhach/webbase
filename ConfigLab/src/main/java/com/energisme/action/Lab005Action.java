package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab005;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab005Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab005;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class Lab005Action extends AbstractBaseAction implements ModelDriven<NumConfigLab005> {

	private static final long serialVersionUID = 1L;

	@Override
	public NumConfigLab005 getModel() {
		return numConfigLab005;
	}

	@Autowired
	private Lab005Service lab005Service;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab005 numConfigLab005;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab005 configLab005;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private List<ConfigLab005> listAllConfig = new ArrayList<ConfigLab005>();

	private Lab lab;

	private List<String> listUsers;

	private String listUser;

	public boolean isCreate;

	public String redirect() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = lab005Service.getAllConfigLab005();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab005 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab005.getSiteId();
			session.put(FrontalKey.SITE_ID_005, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		numConfigLab005 = new NumConfigLab005();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_005, siteId);
		configLab005 = lab005Service.getConfigLab005BySite(Integer.parseInt(siteId));
		numConfigLab005 = co.convertIdtoMuduleNumber(configLab005);
		listAllConfig = lab005Service.getAllConfigLab005();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = lab005Service.getMaxSiteId() + 1;
				mes = lab005Service.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab005.setReportName(filename);
					mes = lab005Service.saveConfig(numConfigLab005);
					if (mes.equals(SUCCESS)) {
						listAllConfig = lab005Service.getAllConfigLab005();
						numConfigLab005 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						siteId = numConfigLab005.getSiteId();
						session.put(FrontalKey.SITE_ID_005, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId);
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
						isCreate = true;
					}
				}
			} else {
				mes = lab005Service.saveConfig(numConfigLab005);
				if (mes.equals(SUCCESS)) {
					listAllConfig = lab005Service.getAllConfigLab005();
					numConfigLab005 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					siteId = numConfigLab005.getSiteId();
					session.put(FrontalKey.SITE_ID_005, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId);
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab005.getSiteId();
			isCreate = false;

			if (file != null) {
				mes = lab005Service.uploadFile(siteId, file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab005.setReportName(filename);
					mes = lab005Service.saveConfig(numConfigLab005);
				}
			} else {
				mes = lab005Service.saveConfig(numConfigLab005);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_005).toString();
			if (mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_005, siteId);
			} else {
				siteId = oldSiteId;
			}
			numConfigLab005.setSiteId(siteId);
			listAllConfig = lab005Service.getAllConfigLab005();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab005Service.deleteConfig(id);
		siteId = request.getParameter("siteId");
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = lab005Service.getAllConfigLab005();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab005 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab005.getSiteId();
				session.put(FrontalKey.SITE_ID_005, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_005, siteId);
			configLab005 = lab005Service.getConfigLab005BySite(Integer.parseInt(siteId));
			numConfigLab005 = co.convertIdtoMuduleNumber(configLab005);
			listAllConfig = lab005Service.getAllConfigLab005();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_005).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		configLab005 = lab005Service.getConfigLab005BySite(Integer.parseInt(siteId));
		numConfigLab005 = co.convertIdtoMuduleNumber(configLab005);
		listAllConfig = lab005Service.getAllConfigLab005();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_005);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_005).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		configLab005 = lab005Service.getConfigLab005BySite(Integer.parseInt(siteId));
		numConfigLab005 = co.convertIdtoMuduleNumber(configLab005);
		listAllConfig = lab005Service.getAllConfigLab005();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
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

	public ConfigLab005 getConfigLab005() {
		return configLab005;
	}

	public void setConfigLab005(ConfigLab005 configLab005) {
		this.configLab005 = configLab005;
	}

	public Lab005Service getLab005Service() {
		return lab005Service;
	}

	public void setLab005Service(Lab005Service lab005Service) {
		this.lab005Service = lab005Service;
	}

	public NumConfigLab005 getNumConfigLab005() {
		return numConfigLab005;
	}

	public void setNumConfigLab005(NumConfigLab005 numConfigLab005) {
		this.numConfigLab005 = numConfigLab005;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ConfigLab005> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab005> listAllConfig) {
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
}
