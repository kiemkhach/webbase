package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab011Range;
import com.energisme.bean.NumConfigLab011Syndic;
import com.energisme.bean.NumConfigLab011Temperature;
import com.energisme.bean.NumconfigLab011Caloon;
import com.energisme.bean.NumconfigLab011Resident;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab011ResidentService;
import com.energisme.service.Lab011UserService;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.google.gson.Gson;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonResident;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;
import com.opensymphony.xwork2.ModelDriven;

public class Lab011CaloonAction extends AbstractBaseAction implements ModelDriven<NumconfigLab011Caloon>{
	private Integer residentId;
	private String jsonString;
	private CaloonResident resident;
	/**
	 * 
	 */
	private Integer caloonSyndicId;
	private File fileReport;
	private String fileReportName;
	
	private Integer rangeId;
	private Integer syndicId;
	private CaloonConsommationRange caloonConsommationRange;
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private NumconfigLab011Caloon numconfigLab011Caloon;
	
	@Autowired
	private Lab011UserService lab011UserService;
	@Autowired
	private Lab011ResidentService lab011ResidentService;
	@Autowired
	private LabService labService;
	@Autowired
	private ConstantService constantService;
	
	private Lab lab;
	private NumConfigLab011Range numConfigLab011Range;
	private NumConfigLab011Temperature numConfigLab011Temperature;
	private NumconfigLab011Resident numconfigLab011Resident;
	private NumConfigLab011Syndic numConfigLab011Syndic;
	
	private List<NumconfigLab011Resident> listAllResidents = new ArrayList<NumconfigLab011Resident>();
	private List<NumConfigLab011Syndic> listAllSyndic = new ArrayList<NumConfigLab011Syndic>();
	private List<NumConfigLab011Range> listAllRange = new ArrayList<NumConfigLab011Range>();
	private List<CaloonResident> listCaloonResident = new ArrayList<CaloonResident>();
	private List<CaloonUser> listUserCaloon = new ArrayList<CaloonUser>();
	private List<CaloonSyndic> listCaloonSyndic = new ArrayList<CaloonSyndic>();
	private List<CaloonResident> listResidentBySyndicId = new ArrayList<CaloonResident>();
	private CaloonUser caloonUser = new CaloonUser();
	private CaloonResident caloonResident = new CaloonResident();
	private CaloonSyndic caloonSyndic = new CaloonSyndic();
	
	private List<CaloonUser> userResidentLst = new ArrayList<CaloonUser>();
	private List<CaloonUser> userSyndicLst = new ArrayList<CaloonUser>();
	
	private Integer userId;
//	private Integer statusTest;
//	private Integer[] checkBoxId = new Integer[20]; 
	
	private Integer appartementTmp;
	private Integer meteoTmp;
	
	public String redirect() {
		listUserCaloon = lab011UserService.getAllUser();
		if(listUserCaloon != null && listUserCaloon.size()>0){
			if(userId == null){
				userId = listUserCaloon.get(0).getId();
			}
		}
		CaloonSyndic caloonSyndic = lab011ResidentService.getCaloonSyndicByUser(userId);
		caloonUser = lab011UserService.getUserById(userId);
		listCaloonResident = lab011ResidentService.getAllSyndicId();
		
		if(caloonSyndic != null){
			listResidentBySyndicId = lab011ResidentService.getAllResidentBySyndicId(caloonSyndic.getId());
		}
		if(caloonUser!=null){
			numconfigLab011Caloon.setUserName(caloonUser.getUserName());
			numconfigLab011Caloon.setFirstName(caloonUser.getFirstName());
			numconfigLab011Caloon.setLastName(caloonUser.getLastName());
			numconfigLab011Caloon.setPassword(caloonUser.getPassword());
			numconfigLab011Caloon.setStatus(caloonUser.getStatus());
//			if(caloonUser.getStatus() == 1){
//				numconfigLab011Caloon.setStatusCaloon("Résident");
//				statusTest = 1;
//			}else{
//				numconfigLab011Caloon.setStatusCaloon("Syndic");
//				statusTest = 0;
//			}
			numconfigLab011Caloon.setId(userId);
		}
		return SUCCESS;
	}
	
	public Integer[] getListResidentChecked() {
		Integer[] checkBoxId = new Integer[listResidentBySyndicId.size()];
		for (int i = 0; i < listResidentBySyndicId.size(); i++) {
			checkBoxId[i] = listResidentBySyndicId.get(i).getId();
		}
		return checkBoxId;
	}
	public String saveNewUserCaloon(){
		caloonUser.setFirstName(numconfigLab011Caloon.getFirstName());
		caloonUser.setLastName(numconfigLab011Caloon.getLastName());
		caloonUser.setStatus(numconfigLab011Caloon.getStatus());
		caloonUser.setUserName(numconfigLab011Caloon.getUserName());
		caloonUser.setPassword(numconfigLab011Caloon.getPassword());
		Integer userID = lab011UserService.createUser(caloonUser);
		if(numconfigLab011Caloon.getStatus() == 1){
			caloonResident.setAppartementNumber(numconfigLab011Caloon.getAppartementNumber());
			caloonResident.setLogements(true);
			caloonResident.setCaloonUserId(userID);
			Integer residentId = lab011ResidentService.createResident(caloonResident);
//			caloonUser.setCaloonResidentId(residentId);
		}else{
			caloonSyndic.setIsDefaultSyndic(true);
			caloonSyndic.setCaloonUserId(userID);
			Integer caloonSyndicId = lab011UserService.createSyndic(caloonSyndic);
//			caloonUser.setCaloonSyndicId(caloonSyndicId);
			if(numconfigLab011Caloon.getResidents()!= null && numconfigLab011Caloon.getResidents() != ""){
				String result[] = numconfigLab011Caloon.getResidents().split(",");
				List<Integer> idLst = new ArrayList<>();
				for(String i : result){
					String id = i.trim();
					idLst.add(Integer.parseInt(id));
				}
				
				lab011ResidentService.updateSyndicId(caloonSyndicId, idLst);
			}
		}

		return SUCCESS;
	}
	public String create(){
		listCaloonResident = lab011ResidentService.getAllSyndicId();
		numconfigLab011Caloon = new NumconfigLab011Caloon();
		return SUCCESS;
	}
	public String saveUserCaloon(){
		userId = numconfigLab011Caloon.getId();
		caloonUser = lab011UserService.getUserById(userId);
		caloonUser.setFirstName(numconfigLab011Caloon.getFirstName());
		caloonUser.setLastName(numconfigLab011Caloon.getLastName());
		caloonUser.setUserName(numconfigLab011Caloon.getUserName());
		caloonUser.setPassword(numconfigLab011Caloon.getPassword());
		if (caloonUser.getStatus() == 0) {

			CaloonSyndic caloonSyndic = lab011ResidentService.getCaloonSyndicByUser(userId);
			if (caloonSyndic != null) {
				 lab011ResidentService.updateSyndicId(caloonSyndic.getId());
				if (numconfigLab011Caloon.getResidents() != null && numconfigLab011Caloon.getResidents() != "") {
					String[] result = numconfigLab011Caloon.getResidents().split(",");
					List<Integer> idLst = new ArrayList<>();
					for (String i : result) {
						String id = i.trim();
						idLst.add(Integer.parseInt(id));
					}
					
					lab011ResidentService.updateSyndicId(caloonSyndic.getId(), idLst);
				}
				if (numconfigLab011Caloon.getIscheck() != null && numconfigLab011Caloon.getIscheck() != "") {
					String result[] = numconfigLab011Caloon.getIscheck().split(",");
					List<Integer> idLst = new ArrayList<>();
					for (String i : result) {
						Integer residentId = Integer.parseInt(i.trim());
						idLst.add(residentId);
					}
					
					lab011ResidentService.updateSyndicId(caloonSyndic.getId(), idLst);
				}

			}
		}
		lab011UserService.saveUser(caloonUser);
		return SUCCESS;
	}
	public String deleteUserCaloon(){
//		userId = numconfigLab011Caloon.getId();
		if(userId != null){	
			caloonUser = lab011UserService.getUserById(userId);
			if(caloonUser.getStatus() == 1){
//				Integer residentId = caloonUser.getCaloonResidentId();
				lab011ResidentService.deleteResident(residentId);
			}else{
				CaloonSyndic caloonSyndic = lab011ResidentService.getCaloonSyndicByUser(userId);
				if(caloonSyndic != null){
					lab011UserService.deleteSyndic(caloonSyndic.getId());
					lab011ResidentService.updateSyndicId(caloonSyndic.getId());
				}
			}
			lab011UserService.deleteUserCaloon(userId);
		}
		return SUCCESS;
	}
	public List<CaloonResident> getListCaloonResident() {
		return listCaloonResident;
	}
	public void setListCaloonResident(List<CaloonResident> listCaloonResident) {
		this.listCaloonResident = listCaloonResident;
	}
	public CaloonUser getCaloonUser() {
		return caloonUser;
	}
	public void setCaloonUser(CaloonUser caloonUser) {
		this.caloonUser = caloonUser;
	}
	public CaloonResident getCaloonResident() {
		return caloonResident;
	}
	public void setCaloonResident(CaloonResident caloonResident) {
		this.caloonResident = caloonResident;
	}
	@Override
	public NumconfigLab011Caloon getModel() {
		// TODO Auto-generated method stub
		return numconfigLab011Caloon;
	}
	public NumconfigLab011Caloon getNumconfigLab011Caloon() {
		return numconfigLab011Caloon;
	}
	public void setNumconfigLab011Caloon(NumconfigLab011Caloon numconfigLab011Caloon) {
		this.numconfigLab011Caloon = numconfigLab011Caloon;
	}
	public CaloonSyndic getCaloonSyndic() {
		return caloonSyndic;
	}
	public void setCaloonSyndic(CaloonSyndic caloonSyndic) {
		this.caloonSyndic = caloonSyndic;
	}
	public List<CaloonUser> getListUserCaloon() {
		return listUserCaloon;
	}
	public void setListUserCaloon(List<CaloonUser> listUserCaloon) {
		this.listUserCaloon = listUserCaloon;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public List<NumconfigLab011Resident> getListAllResidents() {
		return listAllResidents;
	}
	public void setListAllResidents(List<NumconfigLab011Resident> listAllResidents) {
		this.listAllResidents = listAllResidents;
	}
	public Lab011ResidentService getLab011ResidentService() {
		return lab011ResidentService;
	}
	public void setLab011ResidentService(Lab011ResidentService lab011ResidentService) {
		this.lab011ResidentService = lab011ResidentService;
	}
	
	public Integer getResidentId() {
		return residentId;
	}
	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}
	
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public CaloonResident getResident() {
		return resident;
	}
	public void setResident(CaloonResident resident) {
		this.resident = resident;
	}
	public String residentConfig() {
		List<CaloonResident> residents = lab011ResidentService.getAllResident();
		userResidentLst = lab011UserService.getUserResident();
		
        if(residents != null && residents.size() > 0){
        	 for (CaloonResident res : residents) {
        		 NumconfigLab011Resident numResident = new NumconfigLab011Resident();
        		 numResident.setAppartementNumber(res.getAppartementNumber());
        		 numResident.setCaloonSyndicId(res.getCaloonSyndicId());
        		 numResident.setChauffage(res.getChauffage());
        		 numResident.setEauChaude(res.getEauChaude());
        		 numResident.setEauFroide(res.getEauFroide());
        		 numResident.setResidentId(res.getId());
        		 numResident.setClientName(res.getClientName());
        		 numResident.setSuperficie(res.getSuperficie());
        		 if(res.getLogements()){
        			 numResident.setTxtLogements("Occupied");
        		 }else{
        			 numResident.setTxtLogements("Empty");
        		 }
        		 for(CaloonUser user : userResidentLst){
        			 if(user.getId().equals(res.getCaloonUserId())){
        				 numResident.setUserName(user.getUserName());
        			 }
        		 }
        		 listAllResidents.add(numResident);
     		}
        }
        return SUCCESS;
    }
	public String getResidentById() {
        CaloonResident resident = lab011ResidentService.getLab011ResidentById(residentId);
        ConvertObject convertObject = new ConvertObject();
        NumconfigLab011Resident numResident = convertObject.convertIdtoModuleNumber(resident);
        Gson gson = new Gson();
        jsonString = gson.toJson(numResident);
        return SUCCESS;
    }
	 public String saveResident() {
		   boolean isSaved = lab011ResidentService.saveResident(resident);
		   if (isSaved) {
		       jsonString = "{\"result\":\"success\"}";
		   } else {
		       jsonString = "{\"result\":\"failed\"}";
		       }
		       return SUCCESS;
	   }
	 public String deleteLab011Resident(){
		 boolean isDeleted = lab011ResidentService.deleteResident(residentId);
			if (isDeleted) {
				jsonString = "{\"result\":\"success\"}";
			} else {
				jsonString = "{\"result\":\"failed\"}";
			}
		 return SUCCESS;
	 }
	 
	 public String createLab011Resident(){
			CaloonResident obj = new CaloonResident();
			obj.setAppartementNumber(numconfigLab011Resident.getAppartementNumber());
			obj.setClientName(numconfigLab011Resident.getClientName());
			obj.setChauffage(numconfigLab011Resident.getChauffage());
			obj.setEauChaude(numconfigLab011Resident.getEauChaude());
			obj.setEauFroide(numconfigLab011Resident.getEauFroide());
			obj.setSuperficie(numconfigLab011Resident.getSuperficie());
			if(numconfigLab011Resident.getLogements() == null){
				obj.setLogements(true);
			}else{
				obj.setLogements(numconfigLab011Resident.getLogements());
			}
			obj.setCaloonUserId(numconfigLab011Resident.getUserId());
			lab011ResidentService.createResident(obj);
			return SUCCESS;
		}
	 
	 public String syndicConfig(){
		 listCaloonSyndic = lab011UserService.getAll();
		 userSyndicLst = lab011UserService.getUserSyndic();
		 if(listCaloonSyndic!=null && listCaloonSyndic.size()>0){
			 for(CaloonSyndic obj : listCaloonSyndic){
				 NumConfigLab011Syndic numConfigLab011Syndic = new NumConfigLab011Syndic();
				 numConfigLab011Syndic.setId(obj.getId());
				 numConfigLab011Syndic.setAddress(obj.getAddress());
				 numConfigLab011Syndic.setChauffage(obj.getChauffage());
				 numConfigLab011Syndic.setEauChaude(obj.getEauChaude());
				 numConfigLab011Syndic.setEauFroide(obj.getEauFroide());
				 numConfigLab011Syndic.setChauffageCommunes(obj.getChauffageCommunes());
				 numConfigLab011Syndic.setEauChaudeCommunes(obj.getEauChaudeCommunes());
				 numConfigLab011Syndic.setEauFroideCommunes(obj.getEauFroideCommunes());
				 numConfigLab011Syndic.setReportPath(obj.getReportPath());
				 numConfigLab011Syndic.setCodePostal(obj.getCodePostal());
				 numConfigLab011Syndic.setName(obj.getName());
				 numConfigLab011Syndic.setVille(obj.getVille());
				 numConfigLab011Syndic.setCoeffUnit(obj.getCoeffUnit());
				 numConfigLab011Syndic.setCoeffTotal(obj.getCoeffTotal());
				 numConfigLab011Syndic.setCoeffEcs(obj.getCoeffEcs());
				 if(userSyndicLst != null){
					 for(CaloonUser caloonUser: userSyndicLst){
						 if(obj.getCaloonUserId() != null && obj.getCaloonUserId().equals(caloonUser.getId())){
							 numConfigLab011Syndic.setUserName(caloonUser.getUserName());
						 }
					 }
				 }
				 listAllSyndic.add(numConfigLab011Syndic);
			 }
		 }
		 return SUCCESS;
	 }
	 public String getSyndicById() {
	        CaloonSyndic syndic = lab011UserService.findById(caloonSyndicId);
//	        ConvertObject convertObject = new ConvertObject();
//	        NumconfigLab011Resident numResident = convertObject.convertIdtoModuleNumber(resident);
	        Gson gson = new Gson();
	        jsonString = gson.toJson(syndic);
	        return SUCCESS;
	    }
	 public String uploadFiles() {
		 if (fileReport != null) {
	            boolean isUploaded = lab011UserService.uploadFileReport( caloonSyndicId+ "", fileReport, fileReportName);
	            if (isUploaded) {
	                jsonString = "{\"result\":\"success\"}";
	            } else {
	                jsonString = "{\"result\":\"failed\"}";
	            }
	        }
		 return SUCCESS;
	 }
	 public String saveSyndic() {
		   boolean isSaved = lab011UserService.saveSyndic(caloonSyndic);
		   if (isSaved) {
		       jsonString = "{\"result\":\"success\"}";
		   } else {
		       jsonString = "{\"result\":\"failed\"}";
		       }
		       return SUCCESS;
	   }
	 public String createLab011Syndic(){
			CaloonSyndic obj = new CaloonSyndic();
			obj.setAddress(numConfigLab011Syndic.getAddress());
			obj.setChauffage(numConfigLab011Syndic.getChauffage());
			obj.setChauffageCommunes(numConfigLab011Syndic.getChauffageCommunes());
			obj.setEauChaude(numConfigLab011Syndic.getEauChaude());
			obj.setEauChaudeCommunes(numConfigLab011Syndic.getEauChaudeCommunes());
			obj.setEauFroide(numConfigLab011Syndic.getEauFroide());
			obj.setEauFroideCommunes(numConfigLab011Syndic.getEauFroideCommunes());
			obj.setCodePostal(numConfigLab011Syndic.getCodePostal());
			obj.setName(numConfigLab011Syndic.getName());
			obj.setVille(numConfigLab011Syndic.getVille());
			obj.setCaloonUserId(numConfigLab011Syndic.getUserId());
			lab011UserService.createSyndic(obj);
			return SUCCESS;
		}
	 public String deleteLab011Syndic(){
		 boolean isDeleted = lab011UserService.deleteSyndic(syndicId);
			if (isDeleted) {
				jsonString = "{\"result\":\"success\"}";
			} else {
				jsonString = "{\"result\":\"failed\"}";
			}
		 return SUCCESS;
	 }
	 
	 
	 public String rangeConfig(){
		 List<CaloonConsommationRange> rangeLst = lab011UserService.getAllRange();
	        if(rangeLst != null && rangeLst.size() > 0){
	        	 for (CaloonConsommationRange range : rangeLst) {
	        		 NumConfigLab011Range numRange = new NumConfigLab011Range();
	        		 numRange.setRangeId(range.getId());
	        		 numRange.setFromNumber(range.getFromNumber());
	        		 numRange.setToNumber(range.getToNumber());
	        		 numRange.setOrderBy(range.getOrderBy());
	        		 listAllRange.add(numRange);
	     		}
	        }
		 return SUCCESS;
	 }
	public String getRangeById() {
        CaloonConsommationRange range = lab011UserService.getConsommationRangeById(rangeId);
        ConvertObject convertObject = new ConvertObject();
        NumConfigLab011Range numRange = convertObject.convertIdtoModuleNumber(range);
        Gson gson = new Gson();
        jsonString = gson.toJson(numRange);
        return SUCCESS;
    }
	
	public String createLab011Range(){
		CaloonConsommationRange obj = new CaloonConsommationRange();
		obj.setFromNumber(numConfigLab011Range.getFromNumber());
		obj.setToNumber(numConfigLab011Range.getToNumber());
		obj.setOrderBy(numConfigLab011Range.getOrderBy());
		lab011UserService.createRange(obj);
		return SUCCESS;
	}
	public String saveRange() {
		   boolean isSaved = lab011UserService.saveRange(caloonConsommationRange);
		   if (isSaved) {
		       jsonString = "{\"result\":\"success\"}";
		   } else {
		       jsonString = "{\"result\":\"failed\"}";
		       }
		       return SUCCESS;
	   }
	public String deleteLab011Range(){
		boolean isDeleted = lab011UserService.deleteRange(rangeId);
		if (isDeleted) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"failed\"}";
		}
		return SUCCESS;
	}
	
	
	public String configTemp(){
		numConfigLab011Temperature = new NumConfigLab011Temperature();
		Constant constant = new Constant();
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_011_CALOON);
		constant = constantService.getConstant(lab.getId(), FrontalKey.CALOON_TEMP_APPARTEMENT);
		if(constant!= null){
			if(constant.getValue()!=null && !constant.getValue().equals("")){
				numConfigLab011Temperature.setTempAppartement(constant.getValue());
			}
		}
		constant = constantService.getConstant(lab.getId(), FrontalKey.CALOON_TEMP_METEO);
		if(constant != null){
			if(constant.getValue() != null && !constant.getValue().equals("")){
				numConfigLab011Temperature.setTempMeteo(constant.getValue());
			}
		}
		return SUCCESS;
	}
	public String saveConfigTmp(){
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_011_CALOON);
		List<Constant> lst = new ArrayList<Constant>();
		lst = constantService.getConstantByLabId(lab.getId());
		boolean isTmpApp = false;
		boolean isTmpMeteo = false;
		if(lst != null && lst.size()>0){
			for(Constant cst : lst){
				if(cst.getKey().equals(FrontalKey.CALOON_TEMP_APPARTEMENT)){
					if(numConfigLab011Temperature.getTempAppartement() != null){
						constantService.updateValue(lab.getId(), FrontalKey.CALOON_TEMP_APPARTEMENT, numConfigLab011Temperature.getTempAppartement().trim());
					}else{
						constantService.updateValue(lab.getId(), FrontalKey.CALOON_TEMP_APPARTEMENT, "");
					}
					isTmpApp = true;
				}
				if(cst.getKey().equals(FrontalKey.CALOON_TEMP_METEO)){
					if(numConfigLab011Temperature.getTempMeteo()!=null){
						constantService.updateValue(lab.getId(), FrontalKey.CALOON_TEMP_METEO, numConfigLab011Temperature.getTempMeteo().trim());
					}else{
						constantService.updateValue(lab.getId(), FrontalKey.CALOON_TEMP_METEO, "");
					}
					isTmpMeteo = true;
				}
			}
		}
		if(isTmpApp == false){
			Constant constant = new Constant();
			constant.setLabId(lab.getId());
			constant.setKey(FrontalKey.CALOON_TEMP_APPARTEMENT);
			if(numConfigLab011Temperature.getTempAppartement() != null){
				constant.setValue(numConfigLab011Temperature.getTempAppartement().trim());
			}else{
				constant.setValue("");
			}
			constantService.save(constant);
		}
		if(isTmpMeteo == false){
			Constant constant = new Constant();
			constant.setLabId(lab.getId());
			constant.setKey(FrontalKey.CALOON_TEMP_METEO);
			if(numConfigLab011Temperature.getTempMeteo() != null){
				constant.setValue(numConfigLab011Temperature.getTempMeteo().trim());
			}else {
				constant.setValue("");
			}
			constantService.save(constant);
		}
			
		return SUCCESS;
	}
	
	public List<NumConfigLab011Syndic> getListAllSyndic() {
		return listAllSyndic;
	}
	public void setListAllSyndic(List<NumConfigLab011Syndic> listAllSyndic) {
		this.listAllSyndic = listAllSyndic;
	}
	public Lab011UserService getLab011UserService() {
		return lab011UserService;
	}
	public void setLab011UserService(Lab011UserService lab011UserService) {
		this.lab011UserService = lab011UserService;
	}
	public Integer getCaloonSyndicId() {
		return caloonSyndicId;
	}
	public void setCaloonSyndicId(Integer caloonSyndicId) {
		this.caloonSyndicId = caloonSyndicId;
	}
	public List<CaloonSyndic> getListCaloonSyndic() {
		return listCaloonSyndic;
	}
	public void setListCaloonSyndic(List<CaloonSyndic> listCaloonSyndic) {
		this.listCaloonSyndic = listCaloonSyndic;
	}
	public File getFileReport() {
		return fileReport;
	}
	public void setFileReport(File fileReport) {
		this.fileReport = fileReport;
	}
	public String getFileReportName() {
		return fileReportName;
	}
	public void setFileReportName(String fileReportName) {
		this.fileReportName = fileReportName;
	}
	public List<NumConfigLab011Range> getListAllRange() {
		return listAllRange;
	}
	public void setListAllRange(List<NumConfigLab011Range> listAllRange) {
		this.listAllRange = listAllRange;
	}
	public CaloonConsommationRange getCaloonConsommationRange() {
		return caloonConsommationRange;
	}
	public void setCaloonConsommationRange(CaloonConsommationRange caloonConsommationRange) {
		this.caloonConsommationRange = caloonConsommationRange;
	}
	
	public NumConfigLab011Range getNumConfigLab011Range() {
		return numConfigLab011Range;
	}
	public void setNumConfigLab011Range(NumConfigLab011Range numConfigLab011Range) {
		this.numConfigLab011Range = numConfigLab011Range;
	}
	public Integer getRangeId() {
		return rangeId;
	}
	public void setRangeId(Integer rangeId) {
		this.rangeId = rangeId;
	}

	public List<CaloonResident> getListResidentBySyndicId() {
		return listResidentBySyndicId;
	}

	public void setListResidentBySyndicId(List<CaloonResident> listResidentBySyndicId) {
		this.listResidentBySyndicId = listResidentBySyndicId;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public LabService getLabService() {
		return labService;
	}

	public void setLabService(LabService labService) {
		this.labService = labService;
	}

	public NumConfigLab011Temperature getNumConfigLab011Temperature() {
		return numConfigLab011Temperature;
	}

	public void setNumConfigLab011Temperature(NumConfigLab011Temperature numConfigLab011Temperature) {
		this.numConfigLab011Temperature = numConfigLab011Temperature;
	}

	public ConstantService getConstantService() {
		return constantService;
	}

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	public Integer getAppartementTmp() {
		return appartementTmp;
	}

	public void setAppartementTmp(Integer appartementTmp) {
		this.appartementTmp = appartementTmp;
	}

	public Integer getMeteoTmp() {
		return meteoTmp;
	}

	public void setMeteoTmp(Integer meteoTmp) {
		this.meteoTmp = meteoTmp;
	}

	public List<CaloonUser> getUserResidentLst() {
		return userResidentLst;
	}

	public void setUserResidentLst(List<CaloonUser> userResidentLst) {
		this.userResidentLst = userResidentLst;
	}

	public List<CaloonUser> getUserSyndicLst() {
		return userSyndicLst;
	}

	public void setUserSyndicLst(List<CaloonUser> userSyndicLst) {
		this.userSyndicLst = userSyndicLst;
	}

	public NumconfigLab011Resident getNumconfigLab011Resident() {
		return numconfigLab011Resident;
	}

	public void setNumconfigLab011Resident(NumconfigLab011Resident numconfigLab011Resident) {
		this.numconfigLab011Resident = numconfigLab011Resident;
	}

	public NumConfigLab011Syndic getNumConfigLab011Syndic() {
		return numConfigLab011Syndic;
	}

	public void setNumConfigLab011Syndic(NumConfigLab011Syndic numConfigLab011Syndic) {
		this.numConfigLab011Syndic = numConfigLab011Syndic;
	}

	public Integer getSyndicId() {
		return syndicId;
	}

	public void setSyndicId(Integer syndicId) {
		this.syndicId = syndicId;
	}

}
