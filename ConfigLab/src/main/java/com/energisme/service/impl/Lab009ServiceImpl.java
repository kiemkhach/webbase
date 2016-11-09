package com.energisme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab009Energy;
import com.energisme.bean.NumConfigLab009LotConsommation;
import com.energisme.bean.NumConfigLab009Perial;
import com.energisme.bean.NumConfigLab009Provider;
import com.energisme.service.Lab009Service;
import com.ifi.lab.LabDAO.dao.ConfigLab009DAO;
import com.ifi.lab.LabDAO.dao.Lab009EnergyTypeDao;
import com.ifi.lab.LabDAO.dao.Lab009LotConsommationDAO;
import com.ifi.lab.LabDAO.dao.Lab009ModuleDAO;
import com.ifi.lab.LabDAO.dao.Lab009ProviderDAO;
import com.ifi.lab.LabDAO.model.ConfigLab009;
import com.ifi.lab.LabDAO.model.Lab008ECS;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.Lab009Module;
import com.ifi.lab.LabDAO.model.Lab009Provider;

public class Lab009ServiceImpl implements Lab009Service{
	@Autowired
	private Lab009EnergyTypeDao lab009EnergyTypeDao;
	@Autowired
	private Lab009LotConsommationDAO lab009LotConsommationDAO;
	@Autowired
	private ConfigLab009DAO configLab009DAO;
	@Autowired
	private Lab009ProviderDAO lab009ProviderDAO; 
	@Autowired
	private Lab009ModuleDAO lab009ModuleDAO; 
	
//	.............................EnerTypes.............................
	@Override
	public List<Lab009EnergyType> getAllNumConfigLab009Energy() {
		List<Lab009EnergyType> listLab009EnergyType = lab009EnergyTypeDao.getAll();
		return listLab009EnergyType;
	}

	@Override
	public boolean deleteLab009EnergyType(int id) {
		return lab009EnergyTypeDao.delete(id);
	}

	@Override
	public Integer createLab009EnergyType(Lab009EnergyType obj) {
		return lab009EnergyTypeDao.createEnergy(obj);
	}
	
	@Override
	public Lab009EnergyType getEnerTypesById(Integer id) {
		Lab009EnergyType enerTypes = null;
		enerTypes = lab009EnergyTypeDao.findByID(id);
		return enerTypes;
	}

	@Override
	public String saveLab009EnergyType(NumConfigLab009Energy obj) {
		Lab009EnergyType lab009EnergyType = null;
		if (obj.getEnergyId() != null) {
			lab009EnergyType = lab009EnergyTypeDao.findByID(obj.getEnergyId());
		} else {
			lab009EnergyType = new Lab009EnergyType();
		}
		if (obj.getEnergyName() != null && !obj.getEnergyName().isEmpty()) {
			lab009EnergyType.setEnergyName(obj.getEnergyName());
		} else {
			return "Energy Name must be not null";
		}
//		if(obj.getEnergyEmissions()!=null && obj.getEnergyEmissions().isNaN()){
			lab009EnergyType.setEnergyEmissions(obj.getEnergyEmissions());
//		}else{
//			return "Energy Emissions must be a number";
//		}
//			
		if (obj.getEnergyCode() != null && !obj.getEnergyCode().isEmpty()) {
			lab009EnergyType.setEnergyCode(obj.getEnergyCode());
		} else {
			return "Energy Code must be not null";
		}
		if (obj.getColorCode().startsWith("#")) {
			lab009EnergyType.setColorCode(obj.getColorCode());
		} else {
			lab009EnergyType.setColorCode("#" + obj.getColorCode());
		}
		if (lab009EnergyTypeDao.updateEnergyType(lab009EnergyType)) {
			return "success";
		} else {
			return "Error when insert DB";
		}
	}

	
//	........................Config ClientId..............................
	@Override
	public boolean saveOrUpdate(ConfigLab009 obj) {
		NumConfigLab009Perial numConfigLab009Perial = new NumConfigLab009Perial();
		if(numConfigLab009Perial.getClientId()!=null){
			obj = configLab009DAO.findByClient(numConfigLab009Perial.getClientId());
		}
		return configLab009DAO.saveOrUpdate(obj);
	}
	
	@Override
	public List<ConfigLab009> getAllConfigLab009() {
		List<ConfigLab009> listAllConfigLab009 = configLab009DAO.getAll();
		return listAllConfigLab009;
	}
	@Override
	public ConfigLab009 getConfigLab009ByClient(Integer clientID) {
		ConfigLab009 configLab009 = configLab009DAO.findByClient(clientID);
		return configLab009;
	}

//...................................Lot Consommation.......................
	@Override
	public List<Lab009LotConsommation> getAllDataLotConsommation() {
		List<Lab009LotConsommation> listLab009LotConsommations = lab009LotConsommationDAO.getAllData();
		return listLab009LotConsommations;
	}
	@Override
	public Integer createLab009LotConsommation(Lab009LotConsommation obj) {
		return lab009LotConsommationDAO.createLot(obj);
	}


	@Override
	public boolean deleteLab009LotConsommation(int id) {
		return lab009LotConsommationDAO.delete(id);
	}

	@Override
	public Lab009LotConsommation getLotById(Integer id) {
		Lab009LotConsommation lotConsommation = null;
		lotConsommation = lab009LotConsommationDAO.findById(id);
		return lotConsommation;
	}
	@Override
	public String saveLab009LotConsommation(NumConfigLab009LotConsommation obj) {
		Lab009LotConsommation lab009LotConsommation = null;
		if (obj.getLotId() != null) {
			lab009LotConsommation = lab009LotConsommationDAO.findById(obj.getLotId());
		} else {
			lab009LotConsommation = new Lab009LotConsommation();
		}
		if (obj.getLotName() != null && !obj.getLotName().isEmpty()) {
			lab009LotConsommation.setLotName(obj.getLotName());
		} else {
			return "Lot Name must be not null";
		}
		if (obj.getLotCode() != null && !obj.getLotCode().isEmpty()) {
			lab009LotConsommation.setLotCode(obj.getLotCode());
		}else{
			return "Lot Code must be not null";
		}
		if (obj.getColorCode().startsWith("#")) {
			lab009LotConsommation.setColorCode(obj.getColorCode());
		} else {
			lab009LotConsommation.setColorCode("#" + obj.getColorCode());
		}
		if (lab009LotConsommationDAO.updateLotConsommation(lab009LotConsommation)) {
			return "success";
		} else {
			return "Error when insert DB";
		}
	}

//	.......................................Provider..............................
	@Override
	public List<Lab009Provider> getAllDataProvider() {
		List<Lab009Provider> listLab009Provider = lab009ProviderDAO.getAllData();
		return listLab009Provider;
	}

	@Override
	public Integer createLab009Provider(Lab009Provider obj) {
		return lab009ProviderDAO.createPro(obj);
	}

	@Override
	public boolean deleteLab009Provider(int id) {
		return lab009ProviderDAO.delete(id);
	}

	@Override
	public Lab009Provider getProviderById(Integer id) {
		Lab009Provider lab009Provider = null;
		lab009Provider = lab009ProviderDAO.findById(id);
		return lab009Provider;
	}

	@Override
	public String saveLab009Provider(NumConfigLab009Provider obj) {
		Lab009Provider lab009Provider = null;
		if (obj.getProviderId() != null) {
			lab009Provider = lab009ProviderDAO.findById(obj.getProviderId());
		} else {
			lab009Provider = new Lab009Provider();
		}
		if (obj.getProviderName() != null && !obj.getProviderName().isEmpty()) {
			lab009Provider.setProviderName(obj.getProviderName());
		} else {
			return "Provider Name must be not null";
		}
		if (obj.getProviderCode() != null && !obj.getProviderCode().isEmpty()) {
			lab009Provider.setProviderCode(obj.getProviderCode());
		}else{
			return "Lot Code must be not null";
		}
		if (obj.getColorCode().startsWith("#")) {
			lab009Provider.setColorCode(obj.getColorCode());
		} else {
			lab009Provider.setColorCode("#" + obj.getColorCode());
		}
		if (lab009ProviderDAO.updateProvider(lab009Provider)) {
			return "success";
		} else {
			return "Error when insert DB";
		}
	}

	@Override
	public List<Lab009Module> getAllDataModule() {
		return lab009ModuleDAO.getAllData();
	}

	@Override
	public Lab009Module getConfigModuleById(int id) {
		return lab009ModuleDAO.findById(id);
	}

	@Override
	public Integer createLab009Module(Lab009Module obj) {
		return lab009ModuleDAO.create(obj);
	}

	@Override
	public boolean deleteLab009Module(int id) {
		return lab009ModuleDAO.delete(id);
	}

	@Override
	public boolean updateLab009Module(Lab009Module obj) {
		return lab009ModuleDAO.update(obj);
	}

}