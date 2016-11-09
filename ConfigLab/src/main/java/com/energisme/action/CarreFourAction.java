package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab001;
import com.energisme.service.CarreFourService;
import com.energisme.service.ConstantService;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab001;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class CarreFourAction extends AbstractBaseAction implements ModelDriven<NumConfigLab001> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CarreFourService carreFourService;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab001 numConfigLab001;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab001 configLab001;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private List<ConfigLab001> listAllConfig = new ArrayList<ConfigLab001>();

	private Lab lab;

	private List<String> listUsers;

	private String listUser;

	public boolean isCreate;

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = carreFourService.getAllConfigLab001();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab001 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab001.getSiteId();
			session.put(FrontalKey.SITE_ID_001, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		numConfigLab001 = new NumConfigLab001();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_001, siteId);
		configLab001 = carreFourService.getConfigLab001BySite(Integer.parseInt(siteId));
		numConfigLab001 = co.convertIdtoMuduleNumber(configLab001);
		listAllConfig = carreFourService.getAllConfigLab001();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = carreFourService.getMaxSiteId() + 1;
				mes = carreFourService.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab001.setReportName(filename);
					mes = carreFourService.saveConfig(numConfigLab001);
					if (mes.equals(SUCCESS)) {
						listAllConfig = carreFourService.getAllConfigLab001();
						numConfigLab001 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						siteId = numConfigLab001.getSiteId();
						session.put(FrontalKey.SITE_ID_001, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId);
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
						isCreate = true;
					}
				}
			} else {
				mes = carreFourService.saveConfig(numConfigLab001);
				if (mes.equals(SUCCESS)) {
					listAllConfig = carreFourService.getAllConfigLab001();
					numConfigLab001 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					siteId = numConfigLab001.getSiteId();
					session.put(FrontalKey.SITE_ID_001, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId);
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab001.getSiteId();
			isCreate = false;

			if (file != null) {
				mes = carreFourService.uploadFile(siteId, file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab001.setReportName(filename);
					mes = carreFourService.saveConfig(numConfigLab001);
				}
			} else {
				mes = carreFourService.saveConfig(numConfigLab001);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_001).toString();
			if (mes.equals(SUCCESS)) {				
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_001, siteId);
			} else {
				siteId = oldSiteId;
			}
			numConfigLab001.setSiteId(siteId);
			listAllConfig = carreFourService.getAllConfigLab001();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = carreFourService.deleteConfig(id);
		siteId = request.getParameter("siteId");
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = carreFourService.getAllConfigLab001();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab001 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab001.getSiteId();
				session.put(FrontalKey.SITE_ID_001, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_001, siteId);
			configLab001 = carreFourService.getConfigLab001BySite(Integer.parseInt(siteId));
			numConfigLab001 = co.convertIdtoMuduleNumber(configLab001);
			listAllConfig = carreFourService.getAllConfigLab001();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_001).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		configLab001 = carreFourService.getConfigLab001BySite(Integer.parseInt(siteId));
		numConfigLab001 = co.convertIdtoMuduleNumber(configLab001);
		listAllConfig = carreFourService.getAllConfigLab001();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_001);
		siteId = session.get(FrontalKey.SITE_ID_001).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		configLab001 = carreFourService.getConfigLab001BySite(Integer.parseInt(siteId));
		numConfigLab001 = co.convertIdtoMuduleNumber(configLab001);
		listAllConfig = carreFourService.getAllConfigLab001();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	@Override
	public NumConfigLab001 getModel() {
		return numConfigLab001;
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

	public ConfigLab001 getConfigLab001() {
		return configLab001;
	}

	public void setConfigLab001(ConfigLab001 configLab001) {
		this.configLab001 = configLab001;
	}

	public CarreFourService getCarreFourService() {
		return carreFourService;
	}

	public void setCarreFourService(CarreFourService carreFourService) {
		this.carreFourService = carreFourService;
	}

	public NumConfigLab001 getNumConfigLab001() {
		return numConfigLab001;
	}

	public void setNumConfigLab001(NumConfigLab001 numConfigLab001) {
		this.numConfigLab001 = numConfigLab001;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ConfigLab001> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab001> listAllConfig) {
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
