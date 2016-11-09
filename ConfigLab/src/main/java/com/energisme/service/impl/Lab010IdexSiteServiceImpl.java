package com.energisme.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab010IdexSite;
import com.energisme.service.Lab010IdexSiteService;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCostDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.model.ConfigLab005;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public class Lab010IdexSiteServiceImpl implements Lab010IdexSiteService{
	@Autowired
	private IdexSiteDAO idexSiteDAO;
	@Autowired
//	private UserDAO userDAO;
//	@Autowired
	private IdexInstallationDAO idexInstallationDAO;
	@Autowired
	private IdexCounterDAO idexCounterDAO;
	@Autowired
	private IdexCostDAO idexCostDAO;
	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;
	@Override
	public String uploadFile(String idexSiteId, File file, String filename) {
		try {
			String path = "D:/Idex Site/";
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return "error";
				}
			}
			String pathSiteId = path  + idexSiteId;
			File subFolder = new File(pathSiteId);
			if (!subFolder.exists()) {
				if (!subFolder.mkdir()) {
					return "error";
				}
			}
			String dateUpload = LabUtils.getCurrentDate();
			File newFile = new File(pathSiteId  + FrontalKey.WINDOWS + dateUpload+"-"+filename);
			FileUtils.copyFile(file, newFile);
			IdexSite idexSite = new IdexSite();
			idexSite.setLogo(dateUpload+"-"+ filename);
			
			idexSite.setIdexSiteId(Integer.parseInt(idexSiteId));
			if (idexSiteDAO.save(idexSite)) {
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
	public String saveConfig(NumConfigLab010IdexSite numConfigLab010IdexSite) {

		IdexSite idexSite = new IdexSite();
		String logo = numConfigLab010IdexSite.getLogo();
		if (logo != null) {
			idexSite.setLogo(logo);
		}
		String idexSiteId = numConfigLab010IdexSite.getIdexSiteId();
		if (idexSiteId != null) {
			idexSite.setIdexSiteId(Integer.parseInt(idexSiteId));
		}
		
		if (idexSiteDAO.save(idexSite)) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	public Lab getLab010IdexSiteinfo(String name) {
		return idexSiteDAO.findByName(name);
	}
	@Override
	public Lab getLabinfo(String name) {
		return idexSiteDAO.findByName(name);
	}
	@Override
	public List<IdexSite> getAllIdexSite() {
		List<IdexSite> listIdexSite = idexSiteDAO.getAllConfig();
		return listIdexSite;
	}
/*	@Override
	public List<String> getUsersInLab(String labName, String siteId) {
		List<String> listUser = userDAO.getUsersInLab(labName,
				Integer.parseInt(siteId));
		return listUser;
	}*/
	@Override
	public List<IdexInstallation> getAllIdexInstallation() {
		return idexInstallationDAO.getAll();
	}

	@Override
	public IdexSite getConfigLab010ByIdIndexSite(Integer idIndexSite) {
		IdexSite configLab = idexSiteDAO.getConfigBySite(idIndexSite);
		return configLab;
	}

}