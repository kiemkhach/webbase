package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab007;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab007Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab007;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class Lab007Action extends AbstractBaseAction implements ModelDriven<NumConfigLab007>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private Lab007Service lab007Service;
	
	@Autowired
	private LabService labService;
	
	@Autowired
	private NumConfigLab007 numConfigLab007;
	
	@Autowired
	private ConstantService constantService;

	private String username_lab_service;
	
	private ConfigLab007 configLab007;
	
	private String mes;

	private ConvertObject co = new ConvertObject();

	private Integer siteId;
	
	private List<ConfigLab007> listAllConfig = new ArrayList<ConfigLab007>();
	private Lab lab;
	private List<String> listUsers;

	private String listUser;
	public boolean isCreate;
	
	@Override
	public NumConfigLab007 getModel() {
		// TODO Auto-generated method stub
		return numConfigLab007;
	}
	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = lab007Service.getAllConfigLab007();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab007 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab007.getSiteId();
			session.put(FrontalKey.SITE_ID_007, siteId);
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		}
		isCreate = false;
		return SUCCESS;
	}
	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		numConfigLab007 = new NumConfigLab007();
		isCreate = true;
		return SUCCESS;
	}
	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = Integer.parseInt(request.getParameter("siteId"));
		session.put(FrontalKey.SITE_ID_007, siteId);
		configLab007 = lab007Service.getConfigLab007BySite(siteId);
		numConfigLab007 = co.convertIdtoMuduleNumber(configLab007);
		listAllConfig = lab007Service.getAllConfigLab007();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}
	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			if (file != null) {
				Integer newSiteId = lab007Service.getMaxSiteId() + 1;
				mes = lab007Service.uploadFile(newSiteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab007.setReportName(filename);
					mes = lab007Service.saveConfig(numConfigLab007);
					if (mes.equals(SUCCESS)) {
						listAllConfig = lab007Service.getAllConfigLab007();
						numConfigLab007 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
						siteId = numConfigLab007.getSiteId();
						session.put(FrontalKey.SITE_ID_007, siteId);
						// add default user
						labService.registerUserForLab(lab.getName(), siteId.toString(), username_lab_service);
						listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
						isCreate = false;
					} else {
						lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
						isCreate = true;
					}
				}
			} else {
				mes = lab007Service.saveConfig(numConfigLab007);
				if (mes.equals(SUCCESS)) {
					listAllConfig = lab007Service.getAllConfigLab007();
					numConfigLab007 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
					siteId = numConfigLab007.getSiteId();
					session.put(FrontalKey.SITE_ID_007, siteId);
					// add default user
					labService.registerUserForLab(lab.getName(), siteId.toString(), username_lab_service);
					listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
					isCreate = false;
				} else {
					lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
					isCreate = true;
				}
			}
		} else {			
			siteId = numConfigLab007.getSiteId();		
			isCreate = false;

			if (file != null) {
				mes = lab007Service.uploadFile(siteId.toString(), file, filename);
				if (mes.equals(SUCCESS)) {
					numConfigLab007.setReportName(filename);
					mes = lab007Service.saveConfig(numConfigLab007);
				}
			} else {
				mes = lab007Service.saveConfig(numConfigLab007);
			}
			String oldSiteId = session.get(FrontalKey.SITE_ID_007).toString();
			if(mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), siteId);
				session.put(FrontalKey.SITE_ID_007, siteId);
			} else {
				siteId = Integer.parseInt(oldSiteId);
			}
			numConfigLab007.setSiteId(siteId);
			listAllConfig = lab007Service.getAllConfigLab007();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		}
		return SUCCESS;
	}
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab007Service.deleteConfig(id);
		siteId = Integer.parseInt(request.getParameter("siteId"));
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
			labService.deleteUserForSite(lab.getId(), siteId);
			listAllConfig = lab007Service.getAllConfigLab007();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab007 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab007.getSiteId();
				session.put(FrontalKey.SITE_ID_007, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_007, siteId);
			configLab007 = lab007Service.getConfigLab007BySite(siteId);
			numConfigLab007 = co.convertIdtoMuduleNumber(configLab007);
			listAllConfig = lab007Service.getAllConfigLab007();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			isCreate = false;
		}
		return SUCCESS;
	}
	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = (Integer)session.get(FrontalKey.SITE_ID_007);
		mes = labService.registerUserForLab(lab.getName(), siteId.toString(), listUserRegist);
		configLab007 = lab007Service.getConfigLab007BySite(siteId);
		numConfigLab007 = co.convertIdtoMuduleNumber(configLab007);
		listAllConfig = lab007Service.getAllConfigLab007();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}
	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_007);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = (Integer) session.get(FrontalKey.SITE_ID_007);
		mes = labService.unregisterUserForLab(lab.getName(), siteId.toString(), listUser);
		configLab007 = lab007Service.getConfigLab007BySite(siteId);
		numConfigLab007 = co.convertIdtoMuduleNumber(configLab007);
		listAllConfig = lab007Service.getAllConfigLab007();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}
	
	public Lab007Service getLab007Service() {
		return lab007Service;
	}
	public void setLab007Service(Lab007Service lab007Service) {
		this.lab007Service = lab007Service;
	}
	public LabService getLabService() {
		return labService;
	}
	public void setLabService(LabService labService) {
		this.labService = labService;
	}
	public NumConfigLab007 getNumConfigLab007() {
		return numConfigLab007;
	}
	public void setNumConfigLab007(NumConfigLab007 numConfigLab007) {
		this.numConfigLab007 = numConfigLab007;
	}
	public ConstantService getConstantService() {
		return constantService;
	}
	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}
	public ConfigLab007 getConfigLab007() {
		return configLab007;
	}
	public void setConfigLab007(ConfigLab007 configLab007) {
		this.configLab007 = configLab007;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public List<ConfigLab007> getListAllConfig() {
		return listAllConfig;
	}
	public void setListAllConfig(List<ConfigLab007> listAllConfig) {
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

}
