package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab010IdexSupplier;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;

public interface Lab010IdexEnergySupplierService {
	Lab getLab010IdexEnergySupplierinfo(String name);
	String uploadFile(File file, String filename);
	String saveConfig(NumConfigLab010IdexSupplier numConfigLab010IdexSupplier);
	Lab getLabinfo(String name);
	List<IdexEnergySupplier> getAllIdexEnergySupplier();
}
