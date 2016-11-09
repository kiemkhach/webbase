package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab010Idex;
import com.energisme.service.Lab010IdexService;
import com.ifi.lab.LabDAO.model.Idex.IdexConfig;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.opensymphony.xwork2.ModelDriven;

public class Lab010IdexAction extends AbstractBaseAction implements ModelDriven<NumConfigLab010Idex> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private NumConfigLab010Idex numConfigLab010Idex;
	@Autowired 
	private Lab010IdexService lab010IdexService;
	@Override
	public NumConfigLab010Idex getModel() {
		// TODO Auto-generated method stub
		return numConfigLab010Idex;
	}
	private Integer installationId;
	private List<IdexInstallation> listAllIdexInstallation = new ArrayList<IdexInstallation>();
	private IdexConfig idexConfig = new IdexConfig();
	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		listAllIdexInstallation = lab010IdexService.getAllIdexInstallation();
		if(installationId == null){
			if(listAllIdexInstallation!=null || listAllIdexInstallation.size()>0){
				installationId = listAllIdexInstallation.get(0).getIdexInstallationId();
			}
		}
		idexConfig = lab010IdexService.getIdexConfigByInstallationId(installationId);
		if(idexConfig != null){
			numConfigLab010Idex.setIdexInstallationId(idexConfig.getInstallationId());
			numConfigLab010Idex.setUnitConsommation(idexConfig.getUnitConsommation());
			numConfigLab010Idex.setUnitMontal(idexConfig.getUnitMontal());
//			numConfigLab010Idex.setIsConsommationFirst(idexConfig.getIsConsommationFirst());
		}
		return SUCCESS;
	}
	public String saveIdexConfig(){
		installationId = numConfigLab010Idex.getIdexInstallationId();
		idexConfig = lab010IdexService.getIdexConfigByInstallationId(installationId);
		idexConfig.setUnitConsommation(numConfigLab010Idex.getUnitConsommation());
		idexConfig.setUnitMontal(numConfigLab010Idex.getUnitMontal());
//		idexConfig.setIsConsommationFirst(numConfigLab010Idex.getIsConsommationFirst());
		lab010IdexService.saveIdexConfig(idexConfig);
		
		return SUCCESS;
	}
	
	public List<IdexInstallation> getListAllIdexInstallation() {
		return listAllIdexInstallation;
	}

	public Integer getInstallationId() {
		return installationId;
	}
	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}
	public void setListAllIdexInstallation(List<IdexInstallation> listAllIdexInstallation) {
		this.listAllIdexInstallation = listAllIdexInstallation;
	}
	public IdexConfig getIdexConfig() {
		return idexConfig;
	}
	public void setIdexConfig(IdexConfig idexConfig) {
		this.idexConfig = idexConfig;
	}
	public NumConfigLab010Idex getNumConfigLab010Idex() {
		return numConfigLab010Idex;
	}
	public void setNumConfigLab010Idex(NumConfigLab010Idex numConfigLab010Idex) {
		this.numConfigLab010Idex = numConfigLab010Idex;
	}

}
