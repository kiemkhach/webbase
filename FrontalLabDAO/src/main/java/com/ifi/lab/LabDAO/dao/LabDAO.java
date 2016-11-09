package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.common.bean.LabSubcription;
import com.ifi.lab.LabDAO.model.Lab;

public interface LabDAO {
	public boolean save(Lab obj);
	public boolean saveAll(List<Lab> listObj);
	public boolean update(Lab obj);
	public boolean delete(int labId);
	public List<Lab> getAllLab();
	public Lab findByName(String name);
	public List<Lab> findByUserName(List<String> listUsername);
	public Lab findById(int id);
//	public List<Lab> findByUserCSMId(List<Integer> listUserCSMId);
	public List<Lab> getAllLabByLstUser(List<String> userNameLst);
	String getlogoPath(String labId);
	String getSiteName(String tableName,String columnName,String columnKey, Integer siteId);
	boolean checkLabAccess(List<String> userNameLst);
	List<Lab> getSubcriptionByUser(List<String> listUsername);
	List<LabSubcription> getLabSubcription(String columnKey, String columnSite, String tableSite,
			List<Integer> siteIDLst) ;
}
