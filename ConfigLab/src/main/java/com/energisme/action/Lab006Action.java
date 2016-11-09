package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab006;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab006ClientService;
import com.energisme.service.Lab006Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class Lab006Action extends AbstractBaseAction implements ModelDriven<NumConfigLab006> {

	private static final long serialVersionUID = 1L;

	@Override
	public NumConfigLab006 getModel() {
		return numConfigLab006;
	}

	@Autowired
	private Lab006Service lab006Service;
	
	@Autowired
	private Lab006ClientService lab006ClientService;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab006 numConfigLab006;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab006 configLab006;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private List<ConfigLab006> listAllConfig = new ArrayList<ConfigLab006>();

	private Lab lab;

	private List<String> listUsers;

	private String listUser;

	public boolean isCreate;

	public String redirect() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = lab006Service.getAllConfigLab006();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab006 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			List<ConfigLab006Client> listClients = lab006ClientService.getClientsBySubscriptionId(numConfigLab006.getId());
			if (listClients != null && !listClients.isEmpty()) {
				numConfigLab006.setListClients(listClients);
			}
			siteId = numConfigLab006.getSiteId();
			session.put(FrontalKey.SITE_ID_006, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		numConfigLab006 = new NumConfigLab006();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_006, siteId);
		configLab006 = lab006Service.getConfigLab006BySite(Integer.parseInt(siteId));
		numConfigLab006 = co.convertIdtoMuduleNumber(configLab006);
		List<ConfigLab006Client> listClients = lab006ClientService.getClientsBySubscriptionId(numConfigLab006.getId());
		if (listClients != null && !listClients.isEmpty()) {
			numConfigLab006.setListClients(listClients);
		}
		listAllConfig = lab006Service.getAllConfigLab006();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = lab006Service.getMaxSiteId() + 1;
				mes = lab006Service.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab006.setReportName(filename);
					mes = lab006Service.saveConfig(numConfigLab006);
					if (mes.equals(SUCCESS)) {
						listAllConfig = lab006Service.getAllConfigLab006();
						numConfigLab006 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						List<ConfigLab006Client> listClients = lab006ClientService.getClientsBySubscriptionId(numConfigLab006.getId());
						if (listClients != null && !listClients.isEmpty()) {
							numConfigLab006.setListClients(listClients);
						}
						siteId = numConfigLab006.getSiteId();
						session.put(FrontalKey.SITE_ID_006, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId);
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
						isCreate = true;
					}
				}
			} else {
				mes = lab006Service.saveConfig(numConfigLab006);
				if (mes.equals(SUCCESS)) {
					listAllConfig = lab006Service.getAllConfigLab006();
					numConfigLab006 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					List<ConfigLab006Client> listClients = lab006ClientService.getClientsBySubscriptionId(numConfigLab006.getId());
					if (listClients != null && !listClients.isEmpty()) {
						numConfigLab006.setListClients(listClients);
					}
					siteId = numConfigLab006.getSiteId();
					session.put(FrontalKey.SITE_ID_006, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId);
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab006.getSiteId();
			isCreate = false;

			if (file != null) {
				mes = lab006Service.uploadFile(siteId, file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab006.setReportName(filename);
					mes = lab006Service.saveConfig(numConfigLab006);
				}
			} else {
				mes = lab006Service.saveConfig(numConfigLab006);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_006).toString();
			if (mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_006, siteId);
			} else {
				siteId = oldSiteId;
			}
			List<ConfigLab006Client> listClients = lab006ClientService.getClientsBySubscriptionId(numConfigLab006.getId());
			if (listClients != null && !listClients.isEmpty()) {
				numConfigLab006.setListClients(listClients);
			}
			numConfigLab006.setSiteId(siteId);
			listAllConfig = lab006Service.getAllConfigLab006();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab006Service.deleteConfig(id);
		siteId = request.getParameter("siteId");
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = lab006Service.getAllConfigLab006();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab006 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab006.getSiteId();
				session.put(FrontalKey.SITE_ID_006, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_006, siteId);
			configLab006 = lab006Service.getConfigLab006BySite(Integer.parseInt(siteId));
			numConfigLab006 = co.convertIdtoMuduleNumber(configLab006);
			listAllConfig = lab006Service.getAllConfigLab006();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_006).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		configLab006 = lab006Service.getConfigLab006BySite(Integer.parseInt(siteId));
		numConfigLab006 = co.convertIdtoMuduleNumber(configLab006);
		listAllConfig = lab006Service.getAllConfigLab006();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_006);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_006).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		configLab006 = lab006Service.getConfigLab006BySite(Integer.parseInt(siteId));
		numConfigLab006 = co.convertIdtoMuduleNumber(configLab006);
		listAllConfig = lab006Service.getAllConfigLab006();
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

	public ConfigLab006 getConfigLab006() {
		return configLab006;
	}

	public void setConfigLab006(ConfigLab006 configLab006) {
		this.configLab006 = configLab006;
	}

	public Lab006Service getLab006Service() {
		return lab006Service;
	}

	public void setLab006Service(Lab006Service lab006Service) {
		this.lab006Service = lab006Service;
	}

	public NumConfigLab006 getNumConfigLab006() {
		return numConfigLab006;
	}

	public void setNumConfigLab006(NumConfigLab006 numConfigLab006) {
		this.numConfigLab006 = numConfigLab006;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ConfigLab006> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab006> listAllConfig) {
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
