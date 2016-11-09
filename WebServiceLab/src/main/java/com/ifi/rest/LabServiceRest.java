package com.ifi.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.LabBean;
import com.ifi.common.bean.LabService;
import com.ifi.common.bean.LabServiceBean;
import com.ifi.common.bean.LabSiteBean;
import com.ifi.common.bean.LabSubcription;
import com.ifi.common.csm.SiteCSM;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.BouyguesDAO;
import com.ifi.lab.LabDAO.dao.ConfigLab001DAO;
import com.ifi.lab.LabDAO.dao.ConfigLab003DAO;
import com.ifi.lab.LabDAO.dao.ConfigLab004Dao;
import com.ifi.lab.LabDAO.dao.ConfigLab005Dao;
import com.ifi.lab.LabDAO.dao.ConfigLab006Dao;
import com.ifi.lab.LabDAO.dao.ConfigLab007DAO;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.Bouygues;
import com.ifi.lab.LabDAO.model.ConfigLab001;
import com.ifi.lab.LabDAO.model.ConfigLab003;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab005;
import com.ifi.lab.LabDAO.model.ConfigLab006;
import com.ifi.lab.LabDAO.model.ConfigLab007;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.UserLab;

@Controller
@RequestMapping("labservice")
public class LabServiceRest {
	@Autowired
	private BouyguesDAO bouyguesDAO;
	@Autowired
	private ConfigLab001DAO configLab001DAO;
	@Autowired
	private ConfigLab003DAO configLab003DAO;
	@Autowired
	private ConfigLab004Dao lab004DAO;
	@Autowired
	private ConfigLab005Dao lab005DAO;
	@Autowired
	private ConfigLab006Dao lab006DAO;
	@Autowired
	private ConfigLab007DAO lab007DAO;
	@Autowired
	private LabDAO labDAO;
	@Autowired
	private UserLabDAO userLabDAO;
	@Autowired
	private ConstantDAO constantDAO;

	private static final String LOAD_LOGO_ACTION = "/LabService/public/loadLogo.action?labId=";

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getSitesByLabId", method = RequestMethod.GET)
	public @ResponseBody JSONArray getSitesByLabName(String labName) {
		Lab lab = labDAO.findByName(labName);
		JSONArray array = null;
		if (lab != null) {
			array = new JSONArray();
			if (FrontalKey.LAB_NAME_001.equals(labName)) {
				List<ConfigLab001> list1 = configLab001DAO.getAllConfig();
				array.addAll(list1);
			} else if (FrontalKey.LAB_NAME_002.equals(labName)) {
				List<Bouygues> list2 = bouyguesDAO.getAllBouygues();
				array.addAll(list2);
			} else if (FrontalKey.LAB_NAME_003.equals(labName)) {
				List<ConfigLab003> list3 = configLab003DAO.getAllConfig();
				array.addAll(list3);
			} else if (FrontalKey.LAB_NAME_004.equals(labName)) {
				List<ConfigLab004> list4 = lab004DAO.getAllConfig();
				array.addAll(list4);
			} else if (FrontalKey.LAB_NAME_005.equals(labName)) {
				List<ConfigLab005> list5 = lab005DAO.getAllConfig();
				array.addAll(list5);
			} else if (FrontalKey.LAB_NAME_006.equals(labName)) {
				List<ConfigLab006> list6 = lab006DAO.getAllConfig();
				array.addAll(list6);
			} else if (FrontalKey.LAB_NAME_007.equals(labName)) {
				List<ConfigLab007> list7 = lab007DAO.getAllConfig();
				array.addAll(list7);
			}
		}
		return array;
	}

	/**
	 * Find all lab of user
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "getLabByUser", method = RequestMethod.GET)
	public @ResponseBody List<LabBean> getBSList(@RequestParam String userName) {

		List<String> userLst = new ArrayList<String>();
		String[] userArr = userName.split(FrontalKey.COMMA);
		for (String uTmp : userArr) {
			if (!uTmp.isEmpty()) {
				userLst.add(uTmp);
			}
		}
		List<Lab> labDaoLst = labDAO.getAllLabByLstUser(userLst);
		List<LabBean> labBeanLst = new ArrayList<LabBean>();

		for (Lab lab : labDaoLst) {
			LabBean bsLab = new LabBean();
			bsLab.setLabId(lab.getId());
			bsLab.setPath(lab.getLogoPath());

			// get all site
			List<UserLab> userLabLst = userLabDAO.getByUserAndLab(userLst, lab.getId());
			for (UserLab ul : userLabLst) {
				LabSiteBean labSite = new LabSiteBean();
				String siteName = labDAO.getSiteName(lab.getTableSite(), lab.getColumnSite(), lab.getColumnKey(),
						ul.getSiteId());
				labSite.setName(siteName);
				labSite.setSiteId(String.valueOf(ul.getSiteId()));
				String url = lab.getUrl().trim();
				if (ul.getSiteId() > 0) {
					url += "?siteId=" + ul.getSiteId();
				}
				labSite.setUrl(url);
				List<LabSiteBean> lst = bsLab.getLabSiteLst();
				if (lst == null) {
					lst = new ArrayList<LabSiteBean>();
				}
				lst.add(labSite);
				bsLab.setLabSiteLst(lst);
			}
			labBeanLst.add(bsLab);
		}

		return labBeanLst;
	}

	@RequestMapping(value = "getlogoPath", method = RequestMethod.GET)
	public @ResponseBody String getlogoPath(@RequestParam String labId) {
		return labDAO.getlogoPath(labId);
	}

	/**
	 * Get path of LabService
	 */
	@RequestMapping(value = "getServiceLab", method = RequestMethod.GET)
	public @ResponseBody String getServiceLab(@RequestParam String token) {

		GetCSMDataAction csm = new GetCSMDataAction();

		UserCSM userCSM = csm.checkLoginByToken(token);
		if (userCSM == null) {
			return "";
		}
		List<String> userNameLst = new ArrayList<String>();
		userNameLst.add(userCSM.getUserName());
		if (userCSM.getLevel() > 1) {
			userNameLst = csm.getListUserCSMByClient(userCSM.getClientId());
		}
		Boolean ok = labDAO.checkLabAccess(userNameLst);
		if (ok) {
			Lab lab = labDAO.findByName(FrontalKey.LAB_NAME_SERVICE);
			if (lab != null) {
				return lab.getUrl() + "?token=" + token;
			}
		}
		return "";
	}

	/**
	 * Lab service for display cartographie type 0: there are no lab service
	 * type 1: there are only one subcription type 2: there are only one lab
	 * service with multi subcription type 3: there are many lab services with
	 * multil subcription
	 * 
	 */

	@RequestMapping(value = "getLabByToken", method = RequestMethod.GET)
	public @ResponseBody String getLabByToken(@RequestParam String token) {

		GetCSMDataAction csm = new GetCSMDataAction();

		UserCSM userCSM = csm.checkLoginByToken(token);
		if (userCSM == null) {
			return "";
		}
		List<String> userNameLst = new ArrayList<String>();
		userNameLst.add(userCSM.getUserName());
		if (userCSM.getLevel() > 1) {
			userNameLst = csm.getListUserCSMByClient(userCSM.getClientId());
		}

		LabServiceBean labServiceBean = new LabServiceBean();
		List<Lab> labLst = labDAO.getSubcriptionByUser(userNameLst);
		List<LabBean> labBeanLst = new ArrayList<LabBean>();
		int type = 0;
		if (!labLst.isEmpty()) {

			// Get link lay logo

			String uri_web_lab = constantDAO.getByLabIdnKey(FrontalKey.LAB_SERVICE_ID, FrontalKey.URI_WEB_LAB)
					.getValue() + LOAD_LOGO_ACTION;

			loopLab: for (Lab labTmp : labLst) {
				for (LabBean labBeanTmp : labBeanLst) {
					if (labBeanTmp.getLabId() == labTmp.getId()) {
						List<LabSiteBean> labSiteBeanLst = labBeanTmp.getLabSiteLst();
						if (labSiteBeanLst == null) {
							labSiteBeanLst = new ArrayList<LabSiteBean>();
						}
						LabSiteBean labSiteBean = new LabSiteBean();
						labSiteBean.setSiteId(String.valueOf(labTmp.getSiteId()));
						labSiteBean.setUrl(labTmp.getUrl() + "?token=" + token + "&siteId=" + labTmp.getSiteId());
						labSiteBean.setUri(labTmp.getUri() + "?siteId=" + labTmp.getSiteId());
						SiteCSM siteCSM = csm.getSiteDetailByID(labTmp.getSiteId());
						labSiteBean.setSiteName(siteCSM.getSiteName());
						labSiteBean.setLatitude(Double.parseDouble((String) siteCSM.getLatitude()));
						labSiteBean.setLongitude(Double.parseDouble(siteCSM.getLongitude()));
						labSiteBeanLst.add(labSiteBean);
						labBeanTmp.setLabSiteLst(labSiteBeanLst);
						continue loopLab;
					}
				}
				// Neu chua co thi tao moi
				LabBean labBeanTmp = new LabBean();
				labBeanTmp.setLabId(labTmp.getId());
				labBeanTmp.setPath(uri_web_lab + labTmp.getId());
				List<LabSiteBean> labSiteBeanLst = labBeanTmp.getLabSiteLst();
				if (labSiteBeanLst == null) {
					labSiteBeanLst = new ArrayList<LabSiteBean>();
				}
				LabSiteBean labSiteBean = new LabSiteBean();
				labSiteBean.setSiteId(String.valueOf(labTmp.getSiteId()));
				labSiteBean.setUrl(labTmp.getUrl() + "?token=" + token + "&siteId=" + labTmp.getSiteId());
				labSiteBean.setUri(labTmp.getUri() + "?siteId=" + labTmp.getSiteId());
				SiteCSM siteCSM = csm.getSiteDetailByID(labTmp.getSiteId());
				labSiteBean.setSiteName(siteCSM.getSiteName());
				labSiteBean.setLatitude(Double.parseDouble((String) siteCSM.getLatitude()));
				labSiteBean.setLongitude(Double.parseDouble(siteCSM.getLongitude()));
				labSiteBeanLst.add(labSiteBean);
				labBeanTmp.setLabSiteLst(labSiteBeanLst);
				labBeanLst.add(labBeanTmp);
			}
			labServiceBean.setLabBeanLst(labBeanLst);

			if (labBeanLst.size() == 1) {
				if (labBeanLst.get(0).getLabSiteLst().size() == 1) {
					type = 1;
				} else {
					type = 2;
				}
			} else {
				type = 3;
			}
		}

		labServiceBean.setType(type);
		// if (ok) {
		// Lab lab = labDAO.findByName(FrontalKey.LAB_NAME_SERVICE);
		// if (lab != null) {
		// return lab.getUrl() + "?token=" + token;
		// }
		// }
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(labServiceBean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "getLabServiceByToken", method = RequestMethod.GET)
	public @ResponseBody List<LabService> getLabServiceByToken(@RequestParam String token) {

		GetCSMDataAction csm = new GetCSMDataAction();
		UserCSM user = csm.checkLoginByToken(token);

		if(user == null){
			return null;
		}
		List<String> userNameLst = new ArrayList<String>();
		userNameLst.add(user.getUserName());
		List<Lab> labLst = labDAO.getAllLabByLstUser(userNameLst);
		Lab labService = labDAO.findByName(FrontalKey.LAB_NAME_SERVICE);
		List<LabService> labServiceLst = new ArrayList<LabService>();
		
		for (Lab lab : labLst) {
			String server = constantDAO.getByLabIdnKey(labService.getId(), FrontalKey.URI_WEB_LAB).getValue();
			LabService service = new LabService();
			service.setLabId(lab.getId());
			service.setLabName(lab.getName());
			service.setLogoUri(server + LOAD_LOGO_ACTION + lab.getId());
			String url = lab.getUrl() + "?token=" + token;
			if (lab.getColumnKey() != null && !lab.getColumnKey().isEmpty()) {
				// select table_site
				List<UserLab> userLabLst = userLabDAO.getByUserAndLab(userNameLst, lab.getId());
				if (userLabLst.isEmpty()) {
					return null;
				}

				List<Integer> siteLst = new ArrayList<Integer>();
				for (UserLab userLab : userLabLst) {
					siteLst.add(userLab.getSiteId());
				}

				List<LabSubcription> subLst = labDAO.getLabSubcription(lab.getColumnKey(), lab.getColumnSite(),
						lab.getTableSite(), siteLst);
				List<LabSubcription> subLstTmp = new ArrayList<LabSubcription>();
				for (int i = 0; i < subLst.size(); i++) {
					LabSubcription sub = subLst.get(i);
					sub.setSubUrl(url + "&siteId=" + sub.getSubID());
					subLstTmp.add(sub);
				}
				service.setSubLst(subLstTmp);
			} else {
				service.setUrl(url);
			}
			
			labServiceLst.add(service);
		}
		return labServiceLst;
	}

}
