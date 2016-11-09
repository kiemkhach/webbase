package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab010IdexSite;
import com.energisme.bean.NumConfigLab010IdexSupplier;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab010IdexEnergySupplierService;
import com.energisme.service.Lab010IdexService;
import com.energisme.service.Lab010IdexSiteService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;
import com.opensymphony.xwork2.ModelDriven;

public class Lab010IdexEnergySupplierAction extends AbstractBaseAction implements ModelDriven<NumConfigLab010IdexSupplier>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	@Autowired
//	private ConstantService constantService;
	@Autowired
	private NumConfigLab010IdexSupplier numConfigLab010IdexSupplier;
	@Autowired
	private Lab010IdexEnergySupplierService lab010IdexEnergySupplierService;
	
	@Autowired
	private Lab010IdexService lab010IdexService;
	
	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;
	
	private IdexEnergySupplier idexEnergySupplier;
	private Lab lab;
//	private String username_lab_service;
    private String energySupplierId;
    private ConvertObject co = new ConvertObject();
	private List<IdexEnergySupplier> listAllConfig = new ArrayList<IdexEnergySupplier>();
	private File myFile;
	private String myFileFileName;
	private List<NumConfigLab010IdexSupplier> numConfigLab010IdexSupplierLst = new ArrayList<NumConfigLab010IdexSupplier>();
	@Override
	public NumConfigLab010IdexSupplier getModel() {
		return numConfigLab010IdexSupplier;
	}
	public String redirect() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = lab010IdexEnergySupplierService.getLabinfo(FrontalKey.LAB_NAME_010_IDEX);
//		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
//				FrontalKey.CONFIG_LAB_KEY_USERNAME);
		listAllConfig = lab010IdexEnergySupplierService.getAllIdexEnergySupplier();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			for (IdexEnergySupplier idexEnergySupplier : listAllConfig) {
				NumConfigLab010IdexSupplier numIdexSupplier= new NumConfigLab010IdexSupplier();
				Integer idexSiteSupplier = idexEnergySupplier.getIdexEnergySupplierId();
				numIdexSupplier.setIdexEnergySupplierId(idexSiteSupplier.toString());
				numIdexSupplier.setName(idexEnergySupplier.getName());
				numIdexSupplier.setLogo(idexEnergySupplier.getLogo());
				numConfigLab010IdexSupplierLst.add(numIdexSupplier);
			}
			numConfigLab010IdexSupplier = co.convertIdtoModuleNumber((listAllConfig.get(0)));
			energySupplierId = numConfigLab010IdexSupplier.getIdexEnergySupplierId();
			session.put(FrontalKey.SITE_ID_010_IDEX, energySupplierId);
		}
		return SUCCESS;
	}
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String save() {
		if(myFile == null || myFileFileName == null){
			return "error";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		energySupplierId = request.getParameter("idexSiteSupplier");
		listAllConfig = lab010IdexEnergySupplierService.getAllIdexEnergySupplier();
		String path = PropertiesReader.getValue(ConfigEnum.LOGO_LAB010_PATH);

		Boolean result = lab010IdexService.uploadFile(path, myFileFileName, myFile);
		if (result) {
			IdexEnergySupplier idexEnergySupplier =  idexEnergySupplierDAO.findById(Integer.parseInt(energySupplierId));
			idexEnergySupplier.setLogo(myFileFileName);
			idexEnergySupplierDAO.save(idexEnergySupplier);
		}
		listAllConfig = lab010IdexEnergySupplierService.getAllIdexEnergySupplier();
		if (listAllConfig != null && !listAllConfig.isEmpty()) {
			for (IdexEnergySupplier idexEnergySupplier : listAllConfig) {
				NumConfigLab010IdexSupplier numIdexSupplier= new NumConfigLab010IdexSupplier();
				Integer idexSiteSupplier = idexEnergySupplier.getIdexEnergySupplierId();
				numIdexSupplier.setIdexEnergySupplierId(idexSiteSupplier.toString());
				numIdexSupplier.setName(idexEnergySupplier.getName());
				numIdexSupplier.setLogo(idexEnergySupplier.getLogo());
				numConfigLab010IdexSupplierLst.add(numIdexSupplier);
			}
		}
		return SUCCESS;
	}
	
	public String delete(){
		String path = PropertiesReader.getValue(ConfigEnum.LOGO_LAB010_PATH);
		IdexEnergySupplier idexEnergySupplier = idexEnergySupplierDAO.findById(Integer.parseInt(energySupplierId));
		if(idexEnergySupplier != null){
			String fileName = idexEnergySupplier.getLogo();
			if(fileName != null && fileName != ""){
				if(lab010IdexService.deleteFile(path, fileName)){
					idexEnergySupplier.setLogo("");
					idexEnergySupplierDAO.save(idexEnergySupplier);
				}
			}
		}
		return SUCCESS;
	}
	
	public NumConfigLab010IdexSupplier getNumConfigLab010IdexSupplier() {
		return numConfigLab010IdexSupplier;
	}
	public void setNumConfigLab010IdexSupplier(NumConfigLab010IdexSupplier numConfigLab010IdexSupplier) {
		this.numConfigLab010IdexSupplier = numConfigLab010IdexSupplier;
	}
	public Lab010IdexEnergySupplierService getLab010IdexEnergySupplierService() {
		return lab010IdexEnergySupplierService;
	}
	public void setLab010IdexEnergySupplierService(Lab010IdexEnergySupplierService lab010IdexEnergySupplierService) {
		this.lab010IdexEnergySupplierService = lab010IdexEnergySupplierService;
	}
	public IdexEnergySupplier getIdexEnergySupplier() {
		return idexEnergySupplier;
	}
	public void setIdexEnergySupplier(IdexEnergySupplier idexEnergySupplier) {
		this.idexEnergySupplier = idexEnergySupplier;
	}
	
	public String getEnergySupplierId() {
		return energySupplierId;
	}
	public void setEnergySupplierId(String energySupplierId) {
		this.energySupplierId = energySupplierId;
	}
	public List<IdexEnergySupplier> getListAllConfig() {
		return listAllConfig;
	}
	public void setListAllConfig(List<IdexEnergySupplier> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}
	public List<NumConfigLab010IdexSupplier> getNumConfigLab010IdexSupplierLst() {
		return numConfigLab010IdexSupplierLst;
	}
	public void setNumConfigLab010IdexSupplierLst(List<NumConfigLab010IdexSupplier> numConfigLab010IdexSupplierLst) {
		this.numConfigLab010IdexSupplierLst = numConfigLab010IdexSupplierLst;
	}
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	
	
}
