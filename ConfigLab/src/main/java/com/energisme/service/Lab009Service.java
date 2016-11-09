package com.energisme.service;

import java.util.List;

import com.energisme.bean.NumConfigLab009Energy;
import com.energisme.bean.NumConfigLab009LotConsommation;
import com.energisme.bean.NumConfigLab009Provider;
import com.ifi.lab.LabDAO.model.ConfigLab009;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.Lab009Module;
import com.ifi.lab.LabDAO.model.Lab009Provider;

public interface Lab009Service {
	List<Lab009EnergyType> getAllNumConfigLab009Energy();
	boolean deleteLab009EnergyType(int id);
	Integer createLab009EnergyType(Lab009EnergyType obj);
	Lab009EnergyType getEnerTypesById(Integer id);
	String saveLab009EnergyType(NumConfigLab009Energy obj);
	
	Integer createLab009LotConsommation(Lab009LotConsommation obj);
	boolean deleteLab009LotConsommation(int id);
	List<Lab009LotConsommation> getAllDataLotConsommation();
	Lab009LotConsommation getLotById(Integer id);
	String saveLab009LotConsommation(NumConfigLab009LotConsommation obj);
	
	List<ConfigLab009> getAllConfigLab009();
	boolean saveOrUpdate(ConfigLab009 obj);
	ConfigLab009 getConfigLab009ByClient(Integer clientId);
	
	List<Lab009Provider> getAllDataProvider();
	Integer createLab009Provider(Lab009Provider obj);
	boolean deleteLab009Provider(int id);
	Lab009Provider getProviderById(Integer id);
	String saveLab009Provider(NumConfigLab009Provider obj);
	
	List<Lab009Module> getAllDataModule();
	Lab009Module getConfigModuleById(int id);
	Integer createLab009Module(Lab009Module obj);
	boolean deleteLab009Module(int id);
	boolean updateLab009Module(Lab009Module obj);
}
