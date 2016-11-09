package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab004;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab004Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab004Line;
import com.ifi.lab.LabDAO.model.Lab;
import com.opensymphony.xwork2.ModelDriven;

public class Lab004Action extends AbstractBaseAction implements ModelDriven<NumConfigLab004> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Lab004Service lab004Service;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab004 numConfigLab004;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab004 configLab004;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private String siteId;

	private File file1;
	private File file2;
	private File file3;
	private String file1name;
	private String file2name;
	private String file3name;

	private List<ConfigLab004> listAllConfig = new ArrayList<ConfigLab004>();

	private List<ConfigLab004Line> listAllConfigLine = new ArrayList<ConfigLab004Line>();

	private Lab lab;

	private List<String> listUsers;

	private String listUser;

	public boolean isCreate;

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		listAllConfig = lab004Service.getAllConfigLab004();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			numConfigLab004 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
			siteId = numConfigLab004.getSiteId();
			session.put(FrontalKey.SITE_ID_004, siteId);
			listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
		}
		isCreate = false;
		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		numConfigLab004 = new NumConfigLab004();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = request.getParameter("siteId");
		session.put(FrontalKey.SITE_ID_004, siteId);
		configLab004 = lab004Service.getConfigLab004BySite(Integer.parseInt(siteId));
		numConfigLab004 = co.convertIdtoMuduleNumber(configLab004);
		listAllConfig = lab004Service.getAllConfigLab004();
		listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		mes = lab004Service.saveConfig(numConfigLab004);
		if (isCreate) {
			if (mes.equals(SUCCESS)) {
				listAllConfig = lab004Service.getAllConfigLab004();
				numConfigLab004 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
				siteId = numConfigLab004.getSiteId();
				session.put(FrontalKey.SITE_ID_004, siteId);
				// add default user
				labService.registerUserForLab(lab.getName(), siteId, username_lab_service);
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
				listAllConfigLine = new ArrayList<ConfigLab004Line>();
				for (int i = 1; i < 4; i++) {
					ConfigLab004Line cfl = new ConfigLab004Line();
					cfl.setConfigLab004Id(numConfigLab004.getId());
					cfl.setType(i);
					cfl.setAddress("");
					cfl.setLogoType("");
					listAllConfigLine.add(cfl);
				}
				lab004Service.savaAllLine(listAllConfigLine);
				isCreate = false;
			} else {
				lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
				isCreate = true;
			}
		} else {			
			listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
			siteId = numConfigLab004.getSiteId();
			String oldSiteId = session.get(FrontalKey.SITE_ID_004).toString();
			if (mes.equals(SUCCESS)) {
				if (!siteId.equals(oldSiteId))
					labService.updateSiteId(lab.getId(),
							Integer.parseInt(oldSiteId), Integer.parseInt(siteId));
				session.put(FrontalKey.SITE_ID_004, siteId);
			} else {
				siteId = oldSiteId;
			}
			numConfigLab004.setSiteId(siteId);
			listAllConfig = lab004Service.getAllConfigLab004();
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String delete() {
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab004Service.deleteConfig(id);
		siteId = request.getParameter("siteId");
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
			labService.deleteUserForSite(lab.getId(), Integer.parseInt(siteId));
			listAllConfig = lab004Service.getAllConfigLab004();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab004 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = numConfigLab004.getSiteId();
				session.put(FrontalKey.SITE_ID_004, siteId);
				listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
				listUsers = labService.getUsersInLab(lab.getName(), siteId);
			}
			isCreate = false;
		} else {
			session.put(FrontalKey.SITE_ID_004, siteId);
			configLab004 = lab004Service.getConfigLab004BySite(Integer.parseInt(siteId));
			numConfigLab004 = co.convertIdtoMuduleNumber(configLab004);
			listAllConfig = lab004Service.getAllConfigLab004();
			listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
			listUsers = labService.getUsersInLab(lab.getName(), siteId);
			isCreate = false;
		}
		return SUCCESS;
	}

	public String saveLine() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_004).toString();

		if (file1 != null || file2 != null || file3 != null) {
			mes = lab004Service.uploadFile(siteId, file1, file1name, file2, file2name, file3, file3name);
			if (mes.equals(SUCCESS)) {
				ConfigLab004Line obj1 = listAllConfigLine.get(0);
				ConfigLab004Line obj2 = listAllConfigLine.get(1);
				ConfigLab004Line obj3 = listAllConfigLine.get(2);
				if (file1 != null) {
					obj1.setReportName(file1name);
				}
				if (file2 != null) {
					obj2.setReportName(file2name);
				}
				if (file3 != null) {
					obj3.setReportName(file3name);
				}
				List<ConfigLab004Line> listObj = new ArrayList<ConfigLab004Line>();
				listObj.add(obj1);
				listObj.add(obj2);
				listObj.add(obj3);
				mes = lab004Service.savaAllLine(listObj);
			}
		} else {
			mes = lab004Service.savaAllLine(listAllConfigLine);
		}
		listAllConfig = lab004Service.getAllConfigLab004();
		configLab004 = lab004Service.getConfigLab004BySite(Integer.parseInt(siteId));
		numConfigLab004 = co.convertIdtoMuduleNumber(configLab004);
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = session.get(FrontalKey.SITE_ID_004).toString();
		mes = labService.registerUserForLab(lab.getName(), siteId, listUserRegist);
		configLab004 = lab004Service.getConfigLab004BySite(Integer.parseInt(siteId));
		numConfigLab004 = co.convertIdtoMuduleNumber(configLab004);
		listAllConfig = lab004Service.getAllConfigLab004();
		listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_004);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = session.get(FrontalKey.SITE_ID_004).toString();
		mes = labService.unregisterUserForLab(lab.getName(), siteId, listUser);
		configLab004 = lab004Service.getConfigLab004BySite(Integer.parseInt(siteId));
		numConfigLab004 = co.convertIdtoMuduleNumber(configLab004);
		listAllConfig = lab004Service.getAllConfigLab004();
		listAllConfigLine = lab004Service.getAllLine(numConfigLab004.getId());
		listUsers = labService.getUsersInLab(lab.getName(), siteId);
		isCreate = false;
		return SUCCESS;
	}

	@Override
	public NumConfigLab004 getModel() {
		return numConfigLab004;
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

	public ConfigLab004 getConfigLab004() {
		return configLab004;
	}

	public void setConfigLab004(ConfigLab004 configLab004) {
		this.configLab004 = configLab004;
	}

	public Lab004Service getLab004Service() {
		return lab004Service;
	}

	public void setLab004Service(Lab004Service lab004Service) {
		this.lab004Service = lab004Service;
	}

	public NumConfigLab004 getNumConfigLab004() {
		return numConfigLab004;
	}

	public void setNumConfigLab004(NumConfigLab004 numConfigLab004) {
		this.numConfigLab004 = numConfigLab004;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ConfigLab004> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab004> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}

	public List<ConfigLab004Line> getListAllConfigLine() {
		return listAllConfigLine;
	}

	public void setListAllConfigLine(List<ConfigLab004Line> listAllConfigLine) {
		this.listAllConfigLine = listAllConfigLine;
	}

	public String getUsername_lab_service() {
		return username_lab_service;
	}

	public void setUsername_lab_service(String username_lab_service) {
		this.username_lab_service = username_lab_service;
	}

	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public File getFile2() {
		return file2;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public File getFile3() {
		return file3;
	}

	public void setFile3(File file3) {
		this.file3 = file3;
	}

	public void setFile1FileName(String file1name) {
		this.file1name = file1name;
	}

	public void setFile2FileName(String file2name) {
		this.file2name = file2name;
	}

	public void setFile3FileName(String file3name) {
		this.file3name = file3name;
	}
}
