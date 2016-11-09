package com.energisme.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab008ECS;
import com.energisme.bean.NumConfigLab009Energy;
import com.energisme.bean.NumConfigLab009LotConsommation;
import com.energisme.bean.NumConfigLab009Module;
import com.energisme.bean.NumConfigLab009Perial;
import com.energisme.bean.NumConfigLab009Provider;
import com.energisme.bean.NumLab008BoilerInfo;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab009Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.google.gson.Gson;
import com.ifi.common.csm.ModuleCSM;
import com.ifi.common.csm.SiteCSM;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.common.ws.GetCSMDataActionOLD;
import com.ifi.lab.LabDAO.model.ConfigLab009;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Lab008ECS;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.Lab009Module;
import com.ifi.lab.LabDAO.model.Lab009Provider;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class Lab009Action extends AbstractBaseAction implements ModelDriven<NumConfigLab009Energy> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NumConfigLab009Energy numConfigLab009Energy;
	@Autowired
	private Lab009Service lab009Service;
	@Autowired
	private NumConfigLab009Perial numConfigLab009Perial;
	@Autowired
	private NumConfigLab009LotConsommation numConfigLab009LotConsommation;
	@Autowired
	private NumConfigLab009Provider numConfigLab009Provider;
	@Autowired
	private NumConfigLab009Module numConfigLab009Module;
	@Autowired
	private LabService labService;
	private List<ModuleCSM> moduleCSMLst = new ArrayList<ModuleCSM>();
	private List<NumConfigLab009Module> numConfigLab009ModuleLst = new ArrayList<NumConfigLab009Module>();
	private List<ConfigLab009> listAllConfigLab009 = new ArrayList<ConfigLab009>();
	private Integer clientId;
	private Integer energyId;
	private Integer lotId;
	private Integer providerId;
	private String jsonString;
	private Integer id;
	private String mes;
	private ConfigLab009 configLab009;

	@Override
	public NumConfigLab009Energy getModel() {
		return numConfigLab009Energy;
	}

	public String redirect() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		listAllConfigLab009 = lab009Service.getAllConfigLab009();
		List<Lab009EnergyType> lab009EnergyTypesLst = lab009Service.getAllNumConfigLab009Energy();
		if (lab009EnergyTypesLst != null && !lab009EnergyTypesLst.isEmpty()) {
			numConfigLab009Energy.setLab009EnergyTypesLst(lab009EnergyTypesLst);
		}
		List<Lab009LotConsommation> lab009LotConsommationsLst = lab009Service.getAllDataLotConsommation();
		if (lab009LotConsommationsLst != null && !lab009LotConsommationsLst.isEmpty()) {
			numConfigLab009LotConsommation.setLab009LotConsommationsLst(lab009LotConsommationsLst);
		}
		List<Lab009Provider> lab009ProvidersLst = lab009Service.getAllDataProvider();
		if (lab009ProvidersLst != null && !lab009ProvidersLst.isEmpty()) {
			numConfigLab009Provider.setLab009ProviderLst(lab009ProvidersLst);
		}
		if (listAllConfigLab009 != null && listAllConfigLab009.size() > 0) {
			numConfigLab009Perial.setId(listAllConfigLab009.get(0).getId());
			numConfigLab009Perial.setClientName(listAllConfigLab009.get(0).getClientName());
			numConfigLab009Perial.setClientId(listAllConfigLab009.get(0).getClientId());
			numConfigLab009Perial.setUnitEmissions(listAllConfigLab009.get(0).getUnitEmissions());
			numConfigLab009Perial.setUnitConsommation(listAllConfigLab009.get(0).getUnitConsommation());
			numConfigLab009Perial.setUnitMontal(listAllConfigLab009.get(0).getUnitMontal());
		}
		return SUCCESS;
	}

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		clientId = Integer.parseInt(request.getParameter("clientId"));
		listAllConfigLab009 = lab009Service.getAllConfigLab009();
		configLab009 = lab009Service.getConfigLab009ByClient(clientId);
		if (configLab009 != null) {
			numConfigLab009Perial.setId(configLab009.getId());
			numConfigLab009Perial.setClientName(configLab009.getClientName());
			numConfigLab009Perial.setClientId(configLab009.getClientId());
			numConfigLab009Perial.setUnitEmissions(configLab009.getUnitEmissions());
			numConfigLab009Perial.setUnitConsommation(configLab009.getUnitConsommation());
			numConfigLab009Perial.setUnitMontal(configLab009.getUnitMontal());
		}
		List<Lab009EnergyType> lab009EnergyTypesLst = lab009Service.getAllNumConfigLab009Energy();
		if (lab009EnergyTypesLst != null && !lab009EnergyTypesLst.isEmpty()) {
			numConfigLab009Energy.setLab009EnergyTypesLst(lab009EnergyTypesLst);
		}
		List<Lab009LotConsommation> lab009LotConsommationsLst = lab009Service.getAllDataLotConsommation();
		if (lab009LotConsommationsLst != null && !lab009LotConsommationsLst.isEmpty()) {
			numConfigLab009LotConsommation.setLab009LotConsommationsLst(lab009LotConsommationsLst);
		}
		List<Lab009Provider> lab009ProvidersLst = lab009Service.getAllDataProvider();
		if (lab009ProvidersLst != null && !lab009ProvidersLst.isEmpty()) {
			numConfigLab009Provider.setLab009ProviderLst(lab009ProvidersLst);
		}

		return SUCCESS;
	}

	// ..........................................Energy
	// Types.....................................
	public String deleteLab009EnergyType() {
		boolean isDeleted = lab009Service.deleteLab009EnergyType(id);
		if (isDeleted) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"failed\"}";
		}
		return SUCCESS;
	}

	public String create() {
		Lab009EnergyType obj = new Lab009EnergyType();
		obj.setEnergyName(numConfigLab009Energy.getEnergyName());
		obj.setEnergyCode(numConfigLab009Energy.getEnergyCode());
		obj.setEnergyEmissions(numConfigLab009Energy.getEnergyEmissions());
		obj.setColorCode("#" + numConfigLab009Energy.getColorCode());
		lab009Service.createLab009EnergyType(obj);
		return SUCCESS;
	}

	public String getEnergyTypesById() {
		Lab009EnergyType enerTypes = lab009Service.getEnerTypesById(id);
		NumConfigLab009Energy numEnerTypes = new NumConfigLab009Energy();
		numEnerTypes.setEnergyId(enerTypes.getId());
		numEnerTypes.setEnergyName(enerTypes.getEnergyName());
		numEnerTypes.setEnergyCode(enerTypes.getEnergyCode());
		numEnerTypes.setEnergyEmissions(enerTypes.getEnergyEmissions());
		numEnerTypes.setColorCode(enerTypes.getColorCode());
		Gson gson = new Gson();
		jsonString = gson.toJson(numEnerTypes);
		return SUCCESS;
	}

	public String saveLab009Energy() {
		String msg = lab009Service.saveLab009EnergyType(numConfigLab009Energy);
		if (msg == null) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"" + msg + "\"}";
		}
		return SUCCESS;
	}

	// .....................................LotConsommation..............................
	public String createLotConsommation() {
		Lab009LotConsommation obj = new Lab009LotConsommation();
		obj.setLotName(numConfigLab009LotConsommation.getLotName());
		obj.setLotCode(numConfigLab009LotConsommation.getLotCode());
		obj.setColorCode("#" + numConfigLab009LotConsommation.getColorCode());
		lab009Service.createLab009LotConsommation(obj);
		return SUCCESS;
	}

	public String getLotById() {
		Lab009LotConsommation lotConsommation = lab009Service.getLotById(id);
		NumConfigLab009LotConsommation numLot = new NumConfigLab009LotConsommation();
		numLot.setLotId(lotConsommation.getId());
		numLot.setLotName(lotConsommation.getLotName());
		numLot.setLotCode(lotConsommation.getLotCode());
		numLot.setColorCode(lotConsommation.getColorCode());
		Gson gson = new Gson();
		jsonString = gson.toJson(numLot);
		return SUCCESS;
	}

	public String deleteLab009LotConsommation() {
		boolean isDeleted = lab009Service.deleteLab009LotConsommation(id);
		if (isDeleted) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"failed\"}";
		}
		return SUCCESS;
	}

	public String saveLab009LotConsommation() {
		String msg = lab009Service.saveLab009LotConsommation(numConfigLab009LotConsommation);
		if (msg == null) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"" + msg + "\"}";
		}
		return SUCCESS;
	}

	// ..........................Config ClientId
	// ...................................
	public String registerPerial() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				.get(ServletActionContext.HTTP_REQUEST);
		String clientId = request.getParameter("clientId");
		String clientName = request.getParameter("clientName");
		String unitEmissions = request.getParameter("unitEmissions");
		Integer unitConsommation = Integer.parseInt(request.getParameter("unitConsommation"));
		Integer unitMontal = Integer.parseInt(request.getParameter("unitmontal"));
		ConfigLab009 configLab009 = new ConfigLab009();
		configLab009.setClientId(Integer.parseInt(clientId));
		configLab009.setClientName(clientName);
		configLab009.setUnitEmissions(unitEmissions);
		configLab009.setUnitConsommation(unitConsommation);
		configLab009.setUnitMontal(unitMontal);
		lab009Service.saveOrUpdate(configLab009);
		return SUCCESS;
	}

	public String saveConfigLab009() {
		clientId = numConfigLab009Perial.getClientId();
		ConfigLab009 configLab009 = new ConfigLab009();
		configLab009 = lab009Service.getConfigLab009ByClient(clientId);
		if(numConfigLab009Perial.getClientName()!=null && !numConfigLab009Perial.getClientName().isEmpty() )
			{
				configLab009.setClientName(numConfigLab009Perial.getClientName());
				mes = "success";
			}else{
				mes = "error";
			}
		configLab009.setUnitEmissions(numConfigLab009Perial.getUnitEmissions());
		configLab009.setUnitConsommation(numConfigLab009Perial.getUnitConsommation());
		configLab009.setUnitMontal(numConfigLab009Perial.getUnitMontal());
		lab009Service.saveOrUpdate(configLab009);
		return SUCCESS;
	}

	public String createConfigLab() {
		labService.getLabinfo(FrontalKey.LAB_NAME_009);
		numConfigLab009Perial = new NumConfigLab009Perial();
		return SUCCESS;
	}

	// .......................................Provider......................................
	public String createProvider() {
		Lab009Provider obj = new Lab009Provider();
		obj.setProviderName(numConfigLab009Provider.getProviderName());
		obj.setProviderCode(numConfigLab009Provider.getProviderCode());
		obj.setColorCode("#" + numConfigLab009Provider.getColorCode());
		lab009Service.createLab009Provider(obj);
		return SUCCESS;
	}

	public String deleteLab009Provider() {
		boolean isDeleted = lab009Service.deleteLab009Provider(id);
		if (isDeleted) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"failed\"}";
		}
		return SUCCESS;
	}

	public String getProviderById() {
		Lab009Provider lab009Provider = lab009Service.getProviderById(id);
		NumConfigLab009Provider numProvider = new NumConfigLab009Provider();
		numProvider.setProviderId(lab009Provider.getId());
		numProvider.setProviderName(lab009Provider.getProviderName());
		numProvider.setProviderCode(lab009Provider.getProviderCode());
		numProvider.setColorCode(lab009Provider.getColorCode());
		Gson gson = new Gson();
		jsonString = gson.toJson(numProvider);
		return SUCCESS;
	}

	public String saveLab009Provider() {
		String msg = lab009Service.saveLab009Provider(numConfigLab009Provider);
		if (msg == null) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"" + msg + "\"}";
		}
		return SUCCESS;
	}
	 // ------------------Config Module------------------
	 public String redirectLab009Module() {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 String clientId = request.getParameter("clientId");
		 GetCSMDataActionOLD getCSMDataAction = new GetCSMDataActionOLD();
		 Map<Integer, SiteCSM> siteCSMMap = getCSMDataAction.getMapSiteByClient(clientId);
		 Map<Integer, ModuleCSM> moduleCSMMap = getCSMDataAction.getMapModuleSiteByClient(clientId);
		 if (moduleCSMMap != null && !moduleCSMMap.isEmpty()){
			 moduleCSMLst.addAll(moduleCSMMap.values());
		 }
	 	 List<Lab009Module> lab009ModuleLst = lab009Service.getAllDataModule();
	 		if (lab009ModuleLst != null && !lab009ModuleLst.isEmpty()) {
	 			for (Lab009Module lab009Module : lab009ModuleLst) {
	 			 	NumConfigLab009Module module = new NumConfigLab009Module();
	 				Integer moduleId = lab009Module.getModuleId();
	 				if (moduleCSMMap.containsKey(moduleId)) {
	 					module.setModuleNumber(moduleCSMMap.get(moduleId).getNumberModule());
	 					module.setModuleId(moduleCSMMap.get(moduleId).getId());
	 					Integer siteId = moduleCSMMap.get(moduleId).getSiteId();
	 					module.setSiteId(siteId);
	 					if (siteCSMMap != null && !siteCSMMap.isEmpty()
	 							&& siteCSMMap.containsKey(siteId)) {
	 						module.setSiteName(siteCSMMap.get(siteId).getSiteName());
	 					}
	 				}
	 				module.setLotId(lab009Module.getLotConsommation().getId());
	 				module.setLotName(lab009Module.getLotConsommation().getLotName());
	 				module.setEnergyId(lab009Module.getEnergyType().getId());
	 				module.setEnergyName(lab009Module.getEnergyType().getEnergyName());
	 				module.setLab009ModuleId(lab009Module.getId());
	 				numConfigLab009ModuleLst.add(module);
	 			}
	 		}
		List<Lab009EnergyType> lab009EnergyTypesLst = lab009Service.getAllNumConfigLab009Energy();
			if (lab009EnergyTypesLst != null && !lab009EnergyTypesLst.isEmpty()) {
				numConfigLab009Energy.setLab009EnergyTypesLst(lab009EnergyTypesLst);
			}
		List<Lab009LotConsommation> lab009LotConsommationsLst = lab009Service.getAllDataLotConsommation();
			if (lab009LotConsommationsLst != null && !lab009LotConsommationsLst.isEmpty()) {
				numConfigLab009LotConsommation.setLab009LotConsommationsLst(lab009LotConsommationsLst);
			}
	 		return SUCCESS;
	 }

	 public String createLab009Module() {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 clientId = Integer.parseInt(request.getParameter("clientId"));
		 Lab009Module module = new Lab009Module();
		 Lab009EnergyType energy = new Lab009EnergyType();
		 Lab009LotConsommation lot = new Lab009LotConsommation();
		 module.setModuleId(numConfigLab009Module.getModuleId());
		 energy.setId(numConfigLab009Module.getEnergyId());
		 lot.setId(numConfigLab009Module.getLotId());
		 module.setEnergyType(energy);
		 module.setLotConsommation(lot);
		 lab009Service.createLab009Module(module);
		return SUCCESS;
		 
	 }
	 public String updateLab009Module() {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 clientId = Integer.parseInt(request.getParameter("clientId"));
		 Lab009Module module = new Lab009Module();
		 Lab009EnergyType energy = new Lab009EnergyType();
		 Lab009LotConsommation lot = new Lab009LotConsommation();
		 module.setId(numConfigLab009Module.getLab009ModuleId());
		 module.setModuleId(numConfigLab009Module.getModuleId());
		 energy.setId(numConfigLab009Module.getEnergyId());
		 lot.setId(numConfigLab009Module.getLotId());
		 module.setEnergyType(energy);
		 module.setLotConsommation(lot);
		 lab009Service.updateLab009Module(module);
		 return SUCCESS;
		 
	 }
		public String deleteLab009Module() {
			boolean isDeleted = lab009Service.deleteLab009Module(id);
			if (isDeleted) {
				jsonString = "{\"result\":\"success\"}";
			} else {
				jsonString = "{\"result\":\"failed\"}";
			}
			return SUCCESS;
		}
	 
	public NumConfigLab009Module getNumConfigLab009Module() {
		return numConfigLab009Module;
	}

	public void setNumConfigLab009Module(NumConfigLab009Module numConfigLab009Module) {
		this.numConfigLab009Module = numConfigLab009Module;
	}

	public List<ModuleCSM> getModuleCSMLst() {
		return moduleCSMLst;
	}

	public void setModuleCSMLst(List<ModuleCSM> moduleCSMLst) {
		this.moduleCSMLst = moduleCSMLst;
	}

	public List<NumConfigLab009Module> getNumConfigLab009ModuleLst() {
		return numConfigLab009ModuleLst;
	}

	public void setNumConfigLab009ModuleLst(
			List<NumConfigLab009Module> numConfigLab009ModuleLst) {
		this.numConfigLab009ModuleLst = numConfigLab009ModuleLst;
	}

	public NumConfigLab009Energy getNumConfigLab009Energy() {
		return numConfigLab009Energy;
	}

	public void setNumConfigLab009Energy(NumConfigLab009Energy numConfigLab009Energy) {
		this.numConfigLab009Energy = numConfigLab009Energy;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public NumConfigLab009Perial getNumConfigLab009Perial() {
		return numConfigLab009Perial;
	}

	public void setNumConfigLab009Perial(NumConfigLab009Perial numConfigLab009Perial) {
		this.numConfigLab009Perial = numConfigLab009Perial;
	}

	public NumConfigLab009LotConsommation getNumConfigLab009LotConsommation() {
		return numConfigLab009LotConsommation;
	}

	public void setNumConfigLab009LotConsommation(NumConfigLab009LotConsommation numConfigLab009LotConsommation) {
		this.numConfigLab009LotConsommation = numConfigLab009LotConsommation;
	}

	public NumConfigLab009Provider getNumConfigLab009Provider() {
		return numConfigLab009Provider;
	}

	public void setNumConfigLab009Provider(NumConfigLab009Provider numConfigLab009Provider) {
		this.numConfigLab009Provider = numConfigLab009Provider;
	}

	public List<ConfigLab009> getListAllConfigLab009() {
		return listAllConfigLab009;
	}

	public void setListAllConfigLab009(List<ConfigLab009> listAllConfigLab009) {
		this.listAllConfigLab009 = listAllConfigLab009;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public ConfigLab009 getConfigLab009() {
		return configLab009;
	}

	public void setConfigLab009(ConfigLab009 configLab009) {
		this.configLab009 = configLab009;
	}

	public Integer getEnergyId() {
		return energyId;
	}

	public void setEnergyId(Integer energyId) {
		this.energyId = energyId;
	}

	public Integer getLotId() {
		return lotId;
	}

	public void setLotId(Integer lotId) {
		this.lotId = lotId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
