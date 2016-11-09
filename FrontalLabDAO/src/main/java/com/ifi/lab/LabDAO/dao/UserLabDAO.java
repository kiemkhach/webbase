package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.UserLab;

public interface UserLabDAO {
	public boolean save(UserLab obj);
	public boolean saveAll(List<UserLab> listObj);
	public boolean update(UserLab obj);
	public boolean delete(UserLab obj);
	public boolean deleteAll(List<UserLab> obj);
	public List<UserLab> getAllUserLab();
	public UserLab findById(int userId, int labId, int siteId);
	public int findByUserAndLab(String userName, String labName);
	public List<UserLab> findByLabAndSite(int labId, int siteId);
	public List<UserLab> getByUserAndLab(List<String> userLst, int labId);
	public boolean updateSiteId(Integer labId, Integer oldSiteId, Integer newSiteId);
	List<UserLab> getByUserAndLab(int userId, int labId);
	List<UserLab> getByUserAndLab(String userName, String labName);
}
