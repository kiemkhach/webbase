package com.energisme.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumconfigLab011Caloon;
import com.energisme.service.Lab011UserService;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.caloon.CaloonConsommationRangeDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonSyndicDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonUserDAO;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;

public class Lab011UserServiceImpl implements Lab011UserService{
	@Autowired
	private CaloonUserDAO caloonUserDAO;
	@Autowired
	private CaloonSyndicDAO caloonSyndicDAO;
	@Autowired
	private CaloonConsommationRangeDAO caloonConsommationRangeDAO;
	@Override
	public List<CaloonUser> getAllUser() {
		List<CaloonUser> caloonUsersLst = caloonUserDAO.getAllUser();
		return caloonUsersLst;
	}
	@Override
	public CaloonUser getUserById(Integer userId) {
		CaloonUser caloonUser = caloonUserDAO.findUserById(userId);
		return caloonUser;
	}
	@Override
	public Integer createUser(CaloonUser obj) {
		return caloonUserDAO.createUser(obj);
	}
	@Override
	public Boolean saveUser(CaloonUser obj) {
		return caloonUserDAO.saveOrUpdate(obj);
	}
	@Override
	public boolean deleteUserCaloon(Integer id) {
		return caloonUserDAO.deleteUserCaloon(id);
	}

	
	@Override
	public Integer createSyndic(CaloonSyndic obj) {
		return caloonSyndicDAO.createSyndic(obj);
	}
	@Override
	public boolean deleteSyndic(Integer id) {
		return caloonSyndicDAO.deleteSyndic(id);
	}
	@Override
	public List<CaloonSyndic> getAll() {
		List<CaloonSyndic> caloonSyndicsLst = caloonSyndicDAO.getAll();
		return caloonSyndicsLst;
	}
	@Override
	public CaloonSyndic findById(Integer caloonSyndicId) {
		CaloonSyndic caloonSyndic = caloonSyndicDAO.findById(caloonSyndicId);
		return caloonSyndic;
	}
	@Override
	public boolean uploadFileReport(String syndicId, File file, String fileName) {
		try {
			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB011_PATH);
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return false;
				}
			}
			String pathSiteId = path + FrontalKey.WINDOWS + syndicId;
			File subFolder = new File(pathSiteId);
			if (!subFolder.exists()) {
				if (!subFolder.mkdir()) {
					return false;
				}
			}
			File newFile = new File(pathSiteId + FrontalKey.WINDOWS + fileName);
			FileUtils.copyFile(file, newFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean saveSyndic(CaloonSyndic obj) {
		return caloonSyndicDAO.saveOrUpdate(obj);
	}
	@Override
	public List<CaloonConsommationRange> getAllRange() {
		List<CaloonConsommationRange> rangLst = caloonConsommationRangeDAO.getAll();
		return rangLst;
	}
	@Override
	public CaloonConsommationRange getConsommationRangeById(Integer id) {
		CaloonConsommationRange caloonConsommationRange = caloonConsommationRangeDAO.getById(id);
		return caloonConsommationRange;
	}
	@Override
	public Integer createRange(CaloonConsommationRange obj) {
		// TODO Auto-generated method stub
		return caloonConsommationRangeDAO.createRange(obj);
	}
	@Override
	public boolean saveRange(CaloonConsommationRange obj) {
		// TODO Auto-generated method stub
		return caloonConsommationRangeDAO.saveOrUpdate(obj);
	}
	@Override
	public boolean deleteRange(Integer id) {
		return caloonConsommationRangeDAO.deleteRangeById(id);
	}
	@Override
	public List<CaloonUser> getUserResident() {
		List<CaloonUser> lst = caloonUserDAO.getUserResident();
		return lst;
	}
	@Override
	public List<CaloonUser> getUserSyndic() {
		List<CaloonUser> lst = caloonUserDAO.getUserSyndic();
		return lst;
	}

}
