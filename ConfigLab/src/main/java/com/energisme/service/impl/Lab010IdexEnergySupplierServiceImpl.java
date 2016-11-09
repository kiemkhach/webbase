package com.energisme.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab010IdexSupplier;
import com.energisme.service.Lab010IdexEnergySupplierService;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;

public class Lab010IdexEnergySupplierServiceImpl implements Lab010IdexEnergySupplierService{
	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;

	@Override
	public String uploadFile(File file, String filename) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.LOGO_LAB010_PATH);
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return "error";
				}
			}
			File newFile = new File(path  + FrontalKey.WINDOWS + filename);
			FileUtils.copyFile(file, newFile);
			IdexEnergySupplier idexEnergySupplier = new IdexEnergySupplier();
			if (idexEnergySupplierDAO.save(idexEnergySupplier)) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.getMessage();	
		}
		return "error";
	}

	@Override
	public String saveConfig(NumConfigLab010IdexSupplier numConfigLab010IdexSupplier) {
		IdexEnergySupplier idexEnergySupplier = new IdexEnergySupplier();
		String logo = numConfigLab010IdexSupplier.getLogo();
		if (logo != null) {
			idexEnergySupplier.setLogo(logo);
		}
		String idexEnergySupplierId = numConfigLab010IdexSupplier.getIdexEnergySupplierId();
		if (idexEnergySupplierId != null) {
			idexEnergySupplier.setIdexEnergySupplierId(Integer.parseInt(idexEnergySupplierId));
		}
		
		if (idexEnergySupplierDAO.save(idexEnergySupplier)) {
			return "success";
		} else {
			return "fail";
		}
	}
	@Override
	public List<IdexEnergySupplier> getAllIdexEnergySupplier() {
		List<IdexEnergySupplier> listIdexEnergySupplier = idexEnergySupplierDAO.getAllConfig();
		return listIdexEnergySupplier;
	}

	@Override
	public Lab getLab010IdexEnergySupplierinfo(String name) {
		return idexEnergySupplierDAO.findByName(name);
	}

	@Override
	public Lab getLabinfo(String name) {
		return idexEnergySupplierDAO.findByName(name);
	}

	

}
