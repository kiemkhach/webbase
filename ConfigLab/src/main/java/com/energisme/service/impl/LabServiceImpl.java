package com.energisme.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.LabService;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.User;
import com.ifi.lab.LabDAO.model.UserLab;

public class LabServiceImpl implements LabService {

	@Autowired
	private LabDAO labDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserLabDAO userLabDAO;

	@Override
	public Lab getLabinfo(String name) {
		return labDAO.findByName(name);
	}

	@Override
	public List<Lab> getAllLab() {
		return labDAO.getAllLab();
	}

	@Override
	public String registerUserForLab(String name, String siteId, String listUsr) {
		try {
			Lab lab = labDAO.findByName(name);
			if (lab == null) {
				return "Can not find lab's name";
			}
			if (listUsr == null || listUsr.trim().equals("")
					|| listUsr.trim().equals(",")) {
				return "Username invalid";
			}
			List<User> listUsers = new ArrayList<User>();
			String[] arrUsrNameStr = listUsr.split(",");
			List<User> listNewObj = new ArrayList<User>();

			for (String username : arrUsrNameStr) {
				username = username.trim();
				User obj = userDAO.findByUserName(username);
				if (obj == null) {
					obj = new User(username);
					listNewObj.add(obj);
				} else {
					listUsers.add(obj);
				}
			}
			if (listNewObj != null && listNewObj.size() > 0) {
				if (!userDAO.saveAll(listNewObj)) {
					return "Failed";
				}
				for (User obj : listNewObj) {
					listUsers.add(obj);
				}
			}

			List<UserLab> listUserLabs = new ArrayList<UserLab>();
			for (User user : listUsers) {
				UserLab userLab = new UserLab(user.getId(), lab.getId(),
						Integer.parseInt(siteId));
				listUserLabs.add(userLab);
			}

			if (userLabDAO.saveAll(listUserLabs)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	@Override
	public String unregisterUserForLab(String name, String siteId,
			String listUsr) {
		try {
			Lab lab = labDAO.findByName(name);
			if (lab == null) {
				return "Can not find lab's name";
			}
			List<User> listUsers = new ArrayList<User>();
			String[] arrUsrNameStr = listUsr.split(",");
			String listObjNull = "";
			for (String username : arrUsrNameStr) {
				username = username.trim();
				User obj = userDAO.findByUserName(username);
				if (obj == null) {
					listObjNull += username + ", ";
				}
				listUsers.add(obj);
			}
			if (!listObjNull.equals("")) {
				return "Username: " + listObjNull + " not exist";
			}
			List<UserLab> listUserLabs = new ArrayList<UserLab>();
			for (User user : listUsers) {
				UserLab userLab = userLabDAO.findById(user.getId(),
						lab.getId(), Integer.parseInt(siteId));
				listUserLabs.add(userLab);
			}
			if (userLabDAO.deleteAll(listUserLabs)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	@Override
	public List<String> getUsersInLab(String labName, String siteId) {
		List<String> listUser = userDAO.getUsersInLab(labName,
				Integer.parseInt(siteId));
		return listUser;
	}
	
	@Override
	public boolean updateSiteId(Integer labId, Integer oldSiteId, Integer newSiteId) {
		return userLabDAO.updateSiteId(labId, oldSiteId, newSiteId);
	}

	@Override
	public boolean deleteUserForSite(Integer labId, Integer siteId) {
		try {
			List<UserLab> listUserLabs = userLabDAO.findByLabAndSite(labId,
					siteId);
			if (listUserLabs != null && listUserLabs.size() > 0) {
				if (userLabDAO.deleteAll(listUserLabs)) {
					return true;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
