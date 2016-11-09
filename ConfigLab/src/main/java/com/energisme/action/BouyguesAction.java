package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab002;
import com.energisme.service.BouyguesService;
import com.energisme.service.ConstantService;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.Bouygues;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class BouyguesAction extends AbstractBaseAction implements ModelDriven<NumConfigLab002> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private BouyguesService bouyguesService;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab002 numConfigLab002;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private Bouygues bouygues;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private List<Bouygues> listAllConfig = new ArrayList<Bouygues>();
	private Lab lab;
	private List<String> listUsers;

	private String listUser;
	public boolean isCreate;

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = bouyguesService.getAllBouygues();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab002 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab002.getSiteId();
			session.put(FrontalKey.SITE_ID_002, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		numConfigLab002 = new NumConfigLab002();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_002, siteId);
		bouygues = bouyguesService.getBouyguesBySite(Integer.parseInt(siteId));
		numConfigLab002 = co.convertIdtoMuduleNumber(bouygues);
		listAllConfig = bouyguesService.getAllBouygues();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = bouyguesService.getMaxSiteId() + 1;
				mes = bouyguesService.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab002.setReportName(filename);
					mes = bouyguesService.saveConfig(numConfigLab002);
					if (mes.equals(SUCCESS)) {
						listAllConfig = bouyguesService.getAllBouygues();
						numConfigLab002 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						siteId = numConfigLab002.getSiteId();
						session.put(FrontalKey.SITE_ID_002, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId);
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
						isCreate = true;
					}
				}
			} else {
				mes = bouyguesService.saveConfig(numConfigLab002);
				if (mes.equals(SUCCESS)) {
					listAllConfig = bouyguesService.getAllBouygues();
					numConfigLab002 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					siteId = numConfigLab002.getSiteId();
					session.put(FrontalKey.SITE_ID_002, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId);
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab002.getSiteId();		
			isCreate = false;

			if (file != null) {
				mes = bouyguesService.uploadFile(siteId, file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab002.setReportName(filename);
					mes = bouyguesService.saveConfig(numConfigLab002);
				}
			} else {
				mes = bouyguesService.saveConfig(numConfigLab002);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_002).toString();
			if(mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_002, siteId);
			} else {
				siteId = oldSiteId;
			}
			numConfigLab002.setSiteId(siteId);
			listAllConfig = bouyguesService.getAllBouygues();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = bouyguesService.deleteConfig(id);
		siteId = request.getParameter("siteId");
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = bouyguesService.getAllBouygues();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab002 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab002.getSiteId();
				session.put(FrontalKey.SITE_ID_002, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_002, siteId);
			bouygues = bouyguesService.getBouyguesBySite(Integer.parseInt(siteId));
			numConfigLab002 = co.convertIdtoMuduleNumber(bouygues);
			listAllConfig = bouyguesService.getAllBouygues();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_002).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		bouygues = bouyguesService.getBouyguesBySite(Integer.parseInt(siteId));
		numConfigLab002 = co.convertIdtoMuduleNumber(bouygues);
		listAllConfig = bouyguesService.getAllBouygues();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_002);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_002).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		bouygues = bouyguesService.getBouyguesBySite(Integer.parseInt(siteId));
		numConfigLab002 = co.convertIdtoMuduleNumber(bouygues);
		listAllConfig = bouyguesService.getAllBouygues();
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	@Override
	public NumConfigLab002 getModel() {
		return numConfigLab002;
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

	public Bouygues getBouygues() {
		return bouygues;
	}

	public void setBouygues(Bouygues bouygues) {
		this.bouygues = bouygues;
	}

	public BouyguesService getBouyguesService() {
		return bouyguesService;
	}

	public void setBouyguesService(BouyguesService bouyguesService) {
		this.bouyguesService = bouyguesService;
	}

	public NumConfigLab002 getNumConfigLab002() {
		return numConfigLab002;
	}

	public void setNumConfigLab002(NumConfigLab002 numConfigLab002) {
		this.numConfigLab002 = numConfigLab002;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Bouygues> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<Bouygues> listAllConfig) {
		this.listAllConfig = listAllConfig;
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
