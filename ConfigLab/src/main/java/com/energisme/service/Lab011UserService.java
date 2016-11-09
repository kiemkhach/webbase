package com.energisme.service;

import java.io.File;
import java.util.List;

import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;

public interface Lab011UserService {
	List<CaloonUser> getAllUser();
	CaloonUser getUserById(Integer userId);
	Integer createUser(CaloonUser obj);
	Boolean saveUser(CaloonUser obj);
	boolean deleteUserCaloon(Integer id);
	List<CaloonUser> getUserResident();
	List<CaloonUser> getUserSyndic();
	
	Integer createSyndic(CaloonSyndic obj);
	boolean deleteSyndic(Integer id);
	List<CaloonSyndic> getAll();
	CaloonSyndic findById(Integer caloonSyndicId);
	boolean uploadFileReport(String syndicId, File file, String fileName);
	boolean saveSyndic(CaloonSyndic obj);
	
	List<CaloonConsommationRange> getAllRange();
	CaloonConsommationRange getConsommationRangeById(Integer id);
	Integer createRange(CaloonConsommationRange obj);
	boolean saveRange(CaloonConsommationRange obj);
	boolean deleteRange(Integer id);
}
