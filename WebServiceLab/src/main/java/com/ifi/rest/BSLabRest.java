package com.ifi.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.bean.BSLab;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.User;
import com.ifi.lab.LabDAO.model.UserLab;

@Controller
@RequestMapping("BSLab")
public class BSLabRest {

	private static final Logger log = Logger.getLogger(BSLabRest.class);
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private LabDAO labDAO;
	@Autowired
	private UserLabDAO userLabDAO;

	@RequestMapping(value = "getBSList", method = RequestMethod.GET)
	public @ResponseBody String getBSList(@RequestParam String token) {

		GetCSMDataAction csm = new GetCSMDataAction();

		UserCSM userCSM = csm.checkLoginByToken(token);
		if (userCSM == null) {
			return "User not valid";
		}

		List<String> useLst = new ArrayList<String>();
		if (userCSM.getLevel() == 2) {
			useLst = csm.getListUserCSMByClient(userCSM.getClientId());
		} else {
			useLst.add(userCSM.getUserName());
		}
		if (useLst == null) {
			return "Can't find client id of user";
		}
		// save database

		// ServiceFactory serviceFactory = new ServiceFactory();
		// @SuppressWarnings("static-access")
		// ApplicationContext context = serviceFactory.getAppCtx();
		// LabDAO labDAO = context.getBean(LabDAO.class);
		List<Lab> labDaoLst = labDAO.findByUserName(useLst);

		// csm.getListUserCSMByToken(token);

		List<BSLab> labLst = new ArrayList<BSLab>();
		for (Lab lab : labDaoLst) {
			BSLab bsLab = new BSLab();
			String url = lab.getUrl().trim();
			String uriIcon = lab.getUri().trim();
			if (url.contains("?")) {
				url += "&token=";
			} else {
				url += "?token=";
			}
			url += token;
			if (lab.getSiteId() != null && lab.getSiteId() > 0) {
				url += "&siteId=" + lab.getSiteId();
				uriIcon += "?siteId=" + lab.getSiteId();
			}
			bsLab.setUrl(url);
			bsLab.setUriIcon(uriIcon);
			labLst.add(bsLab);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(labLst);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * getUserByLab
	 * 
	 * @param userName
	 * @param labName
	 * @return -1 not config, 0: siteid = null, siteid default
	 */
	@RequestMapping(value = "getUserByLab", method = RequestMethod.GET)
	public @ResponseBody String getUserByLab(@RequestParam String labName,
			@RequestParam String userName) {

		// ServiceFactory serviceFactory = new ServiceFactory();
		// @SuppressWarnings("static-access")
		// ApplicationContext context = serviceFactory.getAppCtx();
		// UserLabDAO userLabDAO = context.getBean(UserLabDAO.class);
		return String.valueOf(userLabDAO.findByUserAndLab(userName, labName));
	}

	@RequestMapping(value = "addLab", method = RequestMethod.GET)
	public @ResponseBody String addLab(@RequestParam String url,
			@RequestParam String uri, @RequestParam String name) {
		try {
			if (name.trim().equals("")) {
				return "labName not emty";
			}
			// ApplicationContext context = ServiceFactory.getAppCtx();
			// LabDAO labDAO = context.getBean(LabDAO.class);
			Lab checklab = labDAO.findByName(name);
			if (checklab != null) {
				return "labName has exist";
			}
			Lab lab = new Lab();
			lab.setUrl(url);
			lab.setUri(uri);
			lab.setName(name);
			if (labDAO.save(lab)) {
				return "success";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "failed";
		}
		return "failed";
	}

	@RequestMapping(value = "editLab", method = RequestMethod.GET)
	public @ResponseBody String editLab(@RequestParam String old_name,
			@RequestParam String url, @RequestParam String uri,
			@RequestParam(required = false) String new_name) {
		try {
			if (old_name.trim().equals("") || new_name.trim().equals("")) {
				return "labName not emty";
			}

			// ServiceFactory serviceFactory = new ServiceFactory();
			// @SuppressWarnings("static-access")
			// ApplicationContext context = serviceFactory.getAppCtx();
			// LabDAO labDAO = context.getBean(LabDAO.class);
			Lab lab = labDAO.findByName(old_name);
			if (lab == null) {
				return "labName(old_name) not exist";
			}
			if (new_name != null && !new_name.isEmpty()) {
				Lab checklab = labDAO.findByName(new_name);
				if (checklab != null) {
					return "labName(new_name) has exist";
				}
			}
			lab.setUrl(url);
			lab.setUri(uri);
			if (new_name != null && !new_name.isEmpty()) {
				lab.setName(new_name);
			}
			if (labDAO.update(lab)) {
				return "success";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "failed";
		}
		return "failed";
	}

	@RequestMapping(value = "deleteLab", method = RequestMethod.GET)
	public @ResponseBody String deleteLab(@RequestParam String name) {
		try {

			// ServiceFactory serviceFactory = new ServiceFactory();
			// @SuppressWarnings("static-access")
			// ApplicationContext context = serviceFactory.getAppCtx();
			// LabDAO labDAO = context.getBean(LabDAO.class);

			Lab lab = labDAO.findByName(name);
			if (lab == null) {
				return "labName not exist";
			}
			if (labDAO.delete(lab.getId())) {
				return "success";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "failed";
		}
		return "failed";
	}

	/**
	 * Method register user for Lab
	 * 
	 * @param name
	 * @param listUsr
	 * @return success if register success
	 */
	@RequestMapping(value = "registerUserForLab", method = RequestMethod.GET)
	public @ResponseBody String registerUserForLab(@RequestParam String name,
			@RequestParam int siteId, @RequestParam String listUsr) {
		try {
			// ServiceFactory serviceFactory = new ServiceFactory();
			// @SuppressWarnings("static-access")
			// ApplicationContext context = serviceFactory.getAppCtx();
			// LabDAO labDAO = context.getBean(LabDAO.class);

			Lab lab = labDAO.findByName(name);
			if (lab == null) {
				return "Can not find lab's name";
			}
			List<User> listUsers = new ArrayList<User>();
			String[] arrUsrNameStr = listUsr.split(",");
			List<User> listNewObj = new ArrayList<User>();

			// UserDAO userDAO = context.getBean(UserDAO.class);

			for (String username : arrUsrNameStr) {
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
				UserLab userLab = new UserLab(user.getId(), lab.getId(), siteId);
				listUserLabs.add(userLab);
			}

			// UserLabDAO userLabDAO = context.getBean(UserLabDAO.class);
			if (userLabDAO.saveAll(listUserLabs)) {
				return "success";
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			return "failed";
		}
		return "failed";
	}

	/**
	 * Method unregister user from Lab
	 * 
	 * @param name
	 * @param listUsr
	 * @return success if unregister success
	 */
	@RequestMapping(value = "unregisterUserFromLab", method = RequestMethod.GET)
	public @ResponseBody String unregisterUserFromLab(
			@RequestParam String name, @RequestParam int siteId,
			@RequestParam String listUsr) {
		try {

			// ServiceFactory serviceFactory = new ServiceFactory();
			// @SuppressWarnings("static-access")
			// ApplicationContext context = serviceFactory.getAppCtx();
			// LabDAO labDAO = context.getBean(LabDAO.class);
			Lab lab = labDAO.findByName(name);
			if (lab == null) {
				return "Can not find lab's name";
			}
			List<User> listUsers = new ArrayList<User>();
			String[] arrUsrNameStr = listUsr.split(",");

			String listObjNull = "";

			// UserDAO userDAO = context.getBean(UserDAO.class);

			for (String username : arrUsrNameStr) {
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

			// UserLabDAO userLabDAO = context.getBean(UserLabDAO.class);
			for (User user : listUsers) {
				UserLab userLab = userLabDAO.findById(user.getId(),
						lab.getId(), siteId);
				listUserLabs.add(userLab);
			}
			if (userLabDAO.deleteAll(listUserLabs)) {
				return "success";
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			return "failed";
		}
		return "failed";
	}
}
