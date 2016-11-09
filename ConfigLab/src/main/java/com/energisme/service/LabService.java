package com.energisme.service;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab;

public interface LabService {
	Lab getLabinfo(String name);

	List<Lab> getAllLab();

	String registerUserForLab(String lab_name, String siteId, String listUsr);

	String unregisterUserForLab(String lab_name, String siteId, String listUsr);

	List<String> getUsersInLab(String labName, String siteId);

	boolean deleteUserForSite(Integer labId, Integer siteId);

	boolean updateSiteId(Integer labId, Integer oldSiteId, Integer newSiteId);

}
