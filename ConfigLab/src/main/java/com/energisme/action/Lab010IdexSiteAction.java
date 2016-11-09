package com.energisme.action;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab010IdexCost;
import com.energisme.bean.NumConfigLab010IdexSite;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab010IdexService;
import com.energisme.service.Lab010IdexSiteService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;
import com.opensymphony.xwork2.ModelDriven;

public class Lab010IdexSiteAction extends AbstractBaseAction implements ModelDriven<NumConfigLab010IdexSite> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NumConfigLab010IdexSite numConfigLab010IdexSite;
	@Autowired
	private Lab010IdexSiteService lab010IdexSiteService;
	@Autowired
	private Lab010IdexService lab010IdexService;
	@Autowired
	private NumConfigLab010IdexCost numConfigLab010IdexCost;
	
	@Autowired
	private IdexSiteDAO idexSiteDAO;
	
	private IdexSite idexSite;
//	private String username_lab_service;
    private String siteIdIdex;
    private ConvertObject co = new ConvertObject();
	private List<IdexSite> listAllConfig = new ArrayList<IdexSite>();
	private Lab lab;
	private File myFile;
	private String myFileFileName;
	private List<IdexCounter> idexCounterList = new ArrayList<IdexCounter>();
	private List<IdexCost> idexCostList = new ArrayList<IdexCost>();
	private List<IdexInstallation> idexInstallationList = new ArrayList<IdexInstallation>();
	private Integer idexInstallationId;
	private Integer idexCostId;
	private Integer idexCounterId;
	private InputStream fileInputStream;
	private File importCSVFile;
	private File importRelevesCSVFile;
	private File importDJUCSVFile;
	private String message;
	private String messageReleves;
	@Override
	public NumConfigLab010IdexSite getModel() {
		return numConfigLab010IdexSite;
	}
	private List<NumConfigLab010IdexSite> numConfigLab010IdexSiteLst = new ArrayList<NumConfigLab010IdexSite>();

	public String redirect() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = lab010IdexSiteService.getLabinfo(FrontalKey.LAB_NAME_010_IDEX);
//		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
//				FrontalKey.CONFIG_LAB_KEY_USERNAME);
		listAllConfig = lab010IdexSiteService.getAllIdexSite();
		if (listAllConfig != null && listAllConfig.size() > 0) {
			for (IdexSite idexSite : listAllConfig) {
				NumConfigLab010IdexSite numIdexSite = new NumConfigLab010IdexSite();
				Integer idexSiteId = idexSite.getIdexSiteId();
				numIdexSite.setIdexSiteId(idexSiteId.toString());
				numIdexSite.setName(idexSite.getName());
				numIdexSite.setLogo(idexSite.getLogo());
				numConfigLab010IdexSiteLst.add(numIdexSite);
			}
			numConfigLab010IdexSite = co.convertIdtoModuleNumber((listAllConfig.get(0)));
			siteIdIdex = numConfigLab010IdexSite.getIdexSiteId();
			session.put(FrontalKey.SITE_ID_010_IDEX, siteIdIdex);
		}
		idexInstallationList = lab010IdexSiteService.getAllIdexInstallation();
		return SUCCESS;
	}
	public String redirectLab010SubCategory() {
		idexInstallationList = lab010IdexService.getAllIdexInstallation();
/*		HttpServletRequest request = ServletActionContext.getRequest();
		String idexInstallationStr = request.getParameter("idexInstallationId");*/
		if (idexInstallationId == null) {
			idexInstallationId = idexInstallationList.get(0).getIdexInstallationId();
		}
/*		if(idexInstallationStr.equals(null) || idexInstallationStr.equals("")){
			idexInstallationId = idexInstallationList.get(0).getIdexInstallationId();
		}else{
			idexInstallationId = Integer.parseInt(idexInstallationStr);		
		}*/
		idexCostList = lab010IdexService.getGroupIdexCostByInstallation(idexInstallationId);
		numConfigLab010IdexCost.setIdexEnergySupplierList(lab010IdexService.getGroupIdexEnergySupplierByInstallation(idexInstallationId));
		numConfigLab010IdexCost.setIdexSiteList(lab010IdexService.getGroupIdexSiteByInstallation(idexInstallationId));
		return SUCCESS;
		
	}
	public String redirectLab010Releves(){
		idexInstallationList = lab010IdexService.getAllIdexInstallation();
/*		HttpServletRequest request = ServletActionContext.getRequest();
		String idexInstallationStr = request.getParameter("idexInstallationId");
		if(idexInstallationStr.equals(null) || idexInstallationStr.equals("")){
			idexInstallationId = idexInstallationList.get(0).getIdexInstallationId();
		}else{
			idexInstallationId = Integer.parseInt(idexInstallationStr);		
		}*/
		if (idexInstallationId == null) {
			idexInstallationId = idexInstallationList.get(0).getIdexInstallationId();
		}
		idexCounterList = lab010IdexService.getGroupIdexCounterByInstallation(idexInstallationId);
		return SUCCESS;
	}
	public String createLab010SubCategory() {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 idexInstallationId = Integer.parseInt(request.getParameter("idexInstallationId"));
		 IdexCost idexCost = new IdexCost();
		 IdexEnergySupplier idexEnergySupplier = new IdexEnergySupplier();
		 IdexSite idexSite = new IdexSite();
		 idexCost.setIdexInstallationId(idexInstallationId);
		 idexCost.setName(numConfigLab010IdexCost.getIdexCostName());
		 Integer energySupplierId = numConfigLab010IdexCost.getIdexEnergySupplierId();
		 Integer siteId = numConfigLab010IdexCost.getIdexSiteId();
		 if(energySupplierId != null && energySupplierId > 0){
			 idexEnergySupplier.setIdexEnergySupplierId(energySupplierId);
			 idexCost.setIdexEnergySupplier(idexEnergySupplier);
		 }
		 if(siteId != null && siteId > 0) {
			 idexSite.setIdexSiteId(numConfigLab010IdexCost.getIdexSiteId());
			 idexCost.setIdexSiteIn(idexSite);
		 }
		 lab010IdexService.saveIdexCost(idexCost);
		return SUCCESS;
	}
	public String deleteLab010SubCategory(){
		boolean isDeleted = lab010IdexService.deleteIdexCost(idexCostId);
		if(isDeleted){
			message = "Delete success!";
		}else{
			message = "Delete Failed!";
		}
		return SUCCESS;
	}
	public String exportSubCategoryDetailByIdToCSV(){
		String[] dataHeader = {"Date (MM-yyyy)", "Cost", "Consumption"};
		fileInputStream = lab010IdexService.exportLab010CostDetailByIdToCSV(dataHeader, idexCostId);
		return SUCCESS;
	}
	public String exportAllSubCategoryDetailToCSV(){
		String[] dataHeader = {"SubCategoryId", "Date (MM-yyyy)", "Cost", "Consumption"};
		fileInputStream = lab010IdexService.exportAllLab010CostDetailByIdToCSV(dataHeader);
		return SUCCESS;
	}
	
	public String importLab010SubCategoryDetailCSV() {
		if (importCSVFile != null) {
			message = lab010IdexService.importSubCategoryByCSV(importCSVFile, idexCostId);
		} else {
			message = "import file is null!";
		}
		return SUCCESS;
	}
	
	public String exportLab010DJUByInstallationToCSV(){
		String[] dataHeader = {"Date (dd-MM-yyyy)", "Reel"};
		fileInputStream = lab010IdexService.exportIdexMeteoByInstallationToCSV(dataHeader, idexInstallationId);
		return SUCCESS;
	}
	public String exportAllLab010DJUToCSV(){
		String[] dataHeader = {"InstallationId", "Date (dd-MM-yyyy)", "Reel"};
		fileInputStream = lab010IdexService.exportAllIdexMeteoToCSV(dataHeader);
		return SUCCESS;
	}
	
	public String importLab010DJUByInstallation() {
		if (importDJUCSVFile != null) {
			messageReleves = lab010IdexService.importMeteoCSVByInstallation(importDJUCSVFile, idexInstallationId);
		} else {
			messageReleves = "import file is null!";
		}
		return SUCCESS;
	}
	
	public String exportRelevesByCounterIdToCSV(){
		String[] dataHeader = {"Date (MM-yyyy)", "Value"};
		fileInputStream = lab010IdexService.exportLab010RelevesByIdToCSV(dataHeader, idexCounterId);
		return SUCCESS;
	}
	public String exportAllRelevesToCSV(){
		String[] dataHeader = {"CounterId", "Date (MM-yyyy)", "Value"};
		fileInputStream = lab010IdexService.exportAllLab010RelevesToCSV(dataHeader);
		return SUCCESS;
	}
	
	public String importRelevesCSV() {
		if (importRelevesCSVFile != null) {
			messageReleves = lab010IdexService.importRelevesByCSV(importRelevesCSVFile, idexCounterId);
		} else {
			messageReleves = "import file is null!";
		}
		return SUCCESS;
	}

	public String configLabIdexEnergySupplier(){
		return SUCCESS;
	}
	public String create() {
		lab = lab010IdexSiteService.getLabinfo(FrontalKey.LAB_NAME_010_IDEX);
		numConfigLab010IdexSite = new NumConfigLab010IdexSite();
		return SUCCESS;
	}
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String save() {
		if(myFile == null || myFileFileName == null){
			return "error";
		}
		String path = PropertiesReader.getValue(ConfigEnum.LOGO_LAB010_PATH);
		Boolean rs = lab010IdexService.uploadFile(path,myFileFileName,myFile);
		if(rs){
			IdexSite idexSite = idexSiteDAO.findById(Integer.parseInt(siteIdIdex));
			idexSite.setLogo(myFileFileName);
			idexSiteDAO.save(idexSite);
		}
		listAllConfig = lab010IdexSiteService.getAllIdexSite();
		if (listAllConfig != null && !listAllConfig.isEmpty()) {
			for (IdexSite idexSite : listAllConfig) {
				NumConfigLab010IdexSite numIdexSite = new NumConfigLab010IdexSite();
				Integer idexSiteId = idexSite.getIdexSiteId();
				numIdexSite.setIdexSiteId(idexSiteId.toString());
				numIdexSite.setName(idexSite.getName());
				numIdexSite.setLogo(idexSite.getLogo());
				numConfigLab010IdexSiteLst.add(numIdexSite);
			}
		}
		return SUCCESS;
	}
	public String delete(){
		String path = PropertiesReader.getValue(ConfigEnum.LOGO_LAB010_PATH);
		IdexSite idexSite = idexSiteDAO.findById(Integer.parseInt(siteIdIdex));
		if(idexSite != null){
			String fileName = idexSite.getLogo();
			if(fileName != null && fileName != ""){
				if(lab010IdexService.deleteFile(path, fileName)){
					idexSite.setLogo("");
					idexSiteDAO.save(idexSite);
				}
			}
		}
		return SUCCESS;
	}
	
	public Lab010IdexSiteService getLab010IdexSiteService() {
		return lab010IdexSiteService;
	}
	public void setLab010IdexSiteService(Lab010IdexSiteService lab010IdexSiteService) {
		this.lab010IdexSiteService = lab010IdexSiteService;
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
	public String getSiteIdIdex() {
		return siteIdIdex;
	}
	public void setSiteIdIdex(String siteIdIdex) {
		this.siteIdIdex = siteIdIdex;
	}
	public List<IdexCounter> getIdexCounterList() {
		return idexCounterList;
	}
	public void setIdexCounterList(List<IdexCounter> idexCounterList) {
		this.idexCounterList = idexCounterList;
	}
	public List<IdexInstallation> getIdexInstallationList() {
		return idexInstallationList;
	}
	public void setIdexInstallationList(List<IdexInstallation> idexInstallationList) {
		this.idexInstallationList = idexInstallationList;
	}
	public List<IdexSite> getListAllConfig() {
		return listAllConfig;
	}
	public void setListAllConfig(List<IdexSite> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}
	public IdexSite getIdexSite() {
		return idexSite;
	}
	public void setIdexSite(IdexSite idexSite) {
		this.idexSite = idexSite;
	}
	public List<NumConfigLab010IdexSite> getNumConfigLab010IdexSiteLst() {
		return numConfigLab010IdexSiteLst;
	}
	public void setNumConfigLab010IdexSiteLst(List<NumConfigLab010IdexSite> numConfigLab010IdexSiteLst) {
		this.numConfigLab010IdexSiteLst = numConfigLab010IdexSiteLst;
	}
	public void setIdexCostList(List<IdexCost> idexCostList) {
		this.idexCostList = idexCostList;
	}
	public int getIdexInstallationId() {
		return idexInstallationId;
	}
	public void setIdexInstallationId(int idexInstallationId) {
		this.idexInstallationId = idexInstallationId;
	}
	public List<IdexCost> getIdexCostList() {
		return idexCostList;
	}
	public NumConfigLab010IdexCost getNumConfigLab010IdexCost() {
		return numConfigLab010IdexCost;
	}
	public void setNumConfigLab010IdexCost(
			NumConfigLab010IdexCost numConfigLab010IdexCost) {
		this.numConfigLab010IdexCost = numConfigLab010IdexCost;
	}
	public int getIdexCostId() {
		return idexCostId;
	}
	public void setIdexCostId(int idexCostId) {
		this.idexCostId = idexCostId;
	}
	public NumConfigLab010IdexSite getNumConfigLab010IdexSite() {
		return numConfigLab010IdexSite;
	}
	public void setNumConfigLab010IdexSite(
			NumConfigLab010IdexSite numConfigLab010IdexSite) {
		this.numConfigLab010IdexSite = numConfigLab010IdexSite;
	}
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public File getImportCSVFile() {
		return importCSVFile;
	}
	public void setImportCSVFile(File importCSVFile) {
		this.importCSVFile = importCSVFile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getIdexCounterId() {
		return idexCounterId;
	}
	public void setIdexCounterId(Integer idexCounterId) {
		this.idexCounterId = idexCounterId;
	}
	public File getImportRelevesCSVFile() {
		return importRelevesCSVFile;
	}
	public void setImportRelevesCSVFile(File importRelevesCSVFile) {
		this.importRelevesCSVFile = importRelevesCSVFile;
	}
	public String getMessageReleves() {
		return messageReleves;
	}
	public void setMessageReleves(String messageReleves) {
		this.messageReleves = messageReleves;
	}
	public void setIdexInstallationId(Integer idexInstallationId) {
		this.idexInstallationId = idexInstallationId;
	}
	public void setIdexCostId(Integer idexCostId) {
		this.idexCostId = idexCostId;
	}
	public File getImportDJUCSVFile() {
		return importDJUCSVFile;
	}
	public void setImportDJUCSVFile(File importDJUCSVFile) {
		this.importDJUCSVFile = importDJUCSVFile;
	}
	

}
