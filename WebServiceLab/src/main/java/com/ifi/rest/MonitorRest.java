package com.ifi.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.MonitorConfigBean;
import com.ifi.common.bean.MonitorGatewayBean;
import com.ifi.common.bean.MonitorModuleBean;
import com.ifi.common.bean.MonitorViewBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.MViewDAO;
import com.ifi.lab.LabDAO.dao.MViewGatewayDAO;
import com.ifi.lab.LabDAO.dao.MonitorGatewayDAO;
import com.ifi.lab.LabDAO.dao.MonitorModuleDAO;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.MView;
import com.ifi.lab.LabDAO.model.MViewGateway;
import com.ifi.lab.LabDAO.model.MonitorGateway;
import com.ifi.lab.LabDAO.model.MonitorModule;

@Controller
@RequestMapping("monitor")
public class MonitorRest {
	private static final Logger log = Logger.getLogger(MonitorRest.class);

	// private static final String LAB_NAME = "monitoring";

	@Autowired
	private MonitorGatewayDAO monitorGateWayDAO;

	@Autowired
	private MonitorModuleDAO monitorModuleDAO;

	@Autowired
	private MViewGatewayDAO mViewGatewayDAO;

	@Autowired
	private MViewDAO mViewDAO;

	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;

	private SimpleDateFormat sDateFormat = new SimpleDateFormat(FrontalKey.DATE_DATA_FORMAT);

	@RequestMapping(value = "addGatewayForView", method = RequestMethod.GET)
	public @ResponseBody String addGatewayForView(@RequestParam Integer viewId, @RequestParam String listGatewayNo) {
		try {
			Date lastUpdated = new Date();
			MView view = mViewDAO.findById(viewId);
			if (view == null) {
				return "Can not find View";
			}
			List<MonitorGateway> listGateways = new ArrayList<MonitorGateway>();
			List<MonitorGateway> listUpdateObj = new ArrayList<MonitorGateway>();
			String[] arr = listGatewayNo.split(",");

			for (String serialNo : arr) {
				serialNo = serialNo.trim();
				if (!serialNo.isEmpty()) {
					MonitorGateway obj = monitorGateWayDAO.findBySerialNo(serialNo);
					if (obj == null || obj.getId() == null) {
						obj.setSerialNo(serialNo);
						obj.setLastUpdated(lastUpdated);
						listUpdateObj.add(obj);
					} else if (obj.getActive() == FrontalKey.MONITOR_STATUS_INACTIVE) {
						obj.setActive(FrontalKey.MONITOR_STATUS_ACTIVE);
						obj.setLastUpdated(lastUpdated);
						listUpdateObj.add(obj);
					} else {
						listGateways.add(obj);
					}
				}
			}
			if (listUpdateObj != null && listUpdateObj.size() > 0) {
				if (!monitorGateWayDAO.saveAll(listUpdateObj)) {
					return "failed";
				}
				for (MonitorGateway obj : listUpdateObj) {
					listGateways.add(obj);
				}
			}

			List<MViewGateway> listMViewGateway = new ArrayList<MViewGateway>();
			for (MonitorGateway gateway : listGateways) {
				MViewGateway mViewGateway = mViewGatewayDAO.findByGatewayIdAndViewId(gateway.getId(), viewId);
				if (mViewGateway == null) {
					mViewGateway = new MViewGateway();
					mViewGateway.setGatewayId(gateway.getId());
					mViewGateway.setViewId(viewId);
					listMViewGateway.add(mViewGateway);
				}
			}

			if (mViewGatewayDAO.saveAll(listMViewGateway)) {
				return "success";
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			return "failed";
		}
		return "failed";
	}

	@RequestMapping(value = "removeGateway", method = RequestMethod.POST)
	public @ResponseBody String removeGateway(@RequestBody List<Integer> listId) {
		List<MonitorGateway> listObj = new ArrayList<MonitorGateway>();
		if (listId.size() > 0) {
			for (Integer id : listId) {
				MonitorGateway gateway = monitorGateWayDAO.findById(id);
				if (gateway != null && gateway.getId() != null) {
					gateway.setActive(FrontalKey.MONITOR_STATUS_INACTIVE);
					listObj.add(gateway);
				} else {
					return "monitorGateWay not exist : id - '" + id + "'";
				}
			}
			if (monitorGateWayDAO.saveAll(listObj)) {
				return "success";
			} else {
				return "failed";
			}
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "removeModule", method = RequestMethod.POST)
	public @ResponseBody String removeModule(@RequestBody List<Integer> listId) {
		List<MonitorModule> listObj = new ArrayList<MonitorModule>();
		if (listId.size() > 0) {
			for (Integer id : listId) {
				MonitorModule module = monitorModuleDAO.findById(id);
				if (module != null && module.getId() != null) {
					module.setActive(FrontalKey.MONITOR_STATUS_INACTIVE);
					listObj.add(module);
				} else {
					return "monitorModule not exist : id - '" + id + "'";
				}
			}
			if (monitorModuleDAO.saveAll(listObj)) {
				return "success";
			} else {
				return "failed";
			}
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "removeView", method = RequestMethod.POST)
	public @ResponseBody String removeView(@RequestBody List<Integer> listId) {
		List<MView> listObj = new ArrayList<MView>();
		if (listId.size() > 0) {
			for (Integer id : listId) {
				MView obj = mViewDAO.findById(id);
				if (obj != null && obj.getId() != null) {
					listObj.add(obj);
				} else {
					return "monitorView not exist : id - '" + id + "'";
				}
			}
			if (mViewDAO.deleteAll(listObj)) {
				return "success";
			} else {
				return "failed";
			}
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "addGateway", method = RequestMethod.GET)
	public @ResponseBody String addGateway(@RequestParam String serialNo, @RequestParam Integer viewId) {
		MonitorGateway gateway = null;
		MView view = null;
		gateway = monitorGateWayDAO.findBySerialNo(serialNo);
		if (gateway.getId() == null || gateway.getActive() == 0) {
			if (viewId == -1) {
				gateway.setSerialNo(serialNo);
				gateway.setActive(1);
				if (monitorGateWayDAO.save(gateway)) {
					return "success";
				}
			} else {
				view = mViewDAO.findById(viewId);
				if (view.getId() != null) {
					gateway.setSerialNo(serialNo);
					gateway.setActive(1);
					if (monitorGateWayDAO.save(gateway)) {
						MViewGateway viewGateway = new MViewGateway();
						viewGateway.setGatewayId(gateway.getId());
						viewGateway.setViewId(viewId);
						if (mViewGatewayDAO.save(viewGateway)) {
							return "success";
						}
					}
				}
			}
		} else {
			view = mViewDAO.findById(viewId);
			if (view.getId() != null) {
				gateway.setSerialNo(serialNo);
				gateway.setActive(1);
				MViewGateway viewGateway = mViewGatewayDAO.findByGatewayIdAndViewId(gateway.getId(), viewId);
				if (viewGateway == null) {
					if (monitorGateWayDAO.save(gateway)) {
						viewGateway = new MViewGateway();
						viewGateway.setGatewayId(gateway.getId());
						viewGateway.setViewId(viewId);
						if (mViewGatewayDAO.save(viewGateway)) {
							return "success";
						}
					}
				}
			}
		}
		return "failed";
	}

	@RequestMapping(value = "addModule", method = RequestMethod.GET)
	public @ResponseBody String addModule(@RequestParam Integer gatewayId, @RequestParam String moduleNo) {
		MonitorModule module = null;
		module = monitorModuleDAO.findByModuleNo(moduleNo);
		if (module.getId() == null || module.getActive() == 0) {
			module.setModuleNo(moduleNo);
			module.setActive(1);
			MonitorGateway gateway = monitorGateWayDAO.findById(gatewayId);
			if (gateway != null && gateway.getId() != null) {
				module.setGatewayId(gateway.getId());
				monitorModuleDAO.save(module);
				return "success";
			}
		}
		return "failed";
	}

	@RequestMapping(value = "addView", method = RequestMethod.GET)
	public @ResponseBody String addView(@RequestParam String viewName) {
		MView obj = new MView();
		obj.setName(viewName);
		if (mViewDAO.save(obj)) {
			return "success";
		}
		return "failed";
	}

	@RequestMapping(value = "saveGateway", method = RequestMethod.GET)
	public @ResponseBody String saveGateway(@RequestParam String serialNo, @RequestParam String dateData,
			@RequestParam String type, @RequestParam(required = false) String active) {
		try {
			Date lastUpdated = new Date();
			if (serialNo.trim().equals("")) {
				return "serialNo not emty";
			}
			try {
				Date dDateData = sDateFormat.parse(dateData);
				MonitorGateway mGateway = monitorGateWayDAO.findBySerialNo(serialNo);
				if (mGateway != null && mGateway.getId() != null && mGateway.getDateData() != null
						&& dDateData.before(mGateway.getDateData())) {
					return "new dateData must be after old dateData";
				}
				mGateway.setDateData(dDateData);
				mGateway.setLastUpdated(lastUpdated);
				mGateway.setType(type);
				if (active != null) {
					Integer isActive = WSCommon.getNumber(active);
					if (isActive == null) {
						return "active is not number";
					}
					mGateway.setActive(isActive);
				}
				if (monitorGateWayDAO.save(mGateway)) {
					return "success";
				} else {
					return "failed";
				}
			} catch (Exception e) {
				return "dateData is not format datetime";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "failed";
		}
	}

	@RequestMapping(value = "saveModule", method = RequestMethod.GET)
	public @ResponseBody String saveModule(@RequestParam String moduleNo, @RequestParam String dateData,
			@RequestParam Integer gatewayId, @RequestParam String index,
			@RequestParam(required = false) String active) {
		try {
			Date lastUpdated = new Date();
			if (moduleNo.trim().equals("")) {
				return "moduleNo not emty";
			}
			try {
				Date dDateData = sDateFormat.parse(dateData);
				MonitorModule mModule = monitorModuleDAO.findByModuleNo(moduleNo);
				if (mModule != null && mModule.getId() != null && mModule.getDateData() != null
						&& dDateData.before(mModule.getDateData())) {
					return "new dateData must be after old dateData";
				}
				mModule.setDateData(dDateData);
				mModule.setLastUpdated(lastUpdated);
				mModule.setGatewayId(gatewayId);
				mModule.setIndex(index);
				if (active != null) {
					Integer isActive = WSCommon.getNumber(active);
					if (isActive == null) {
						return "active is not number";
					}
					mModule.setActive(isActive);
				}
				if (monitorModuleDAO.save(mModule)) {
					return "success";
				} else {
					return "failed";
				}
			} catch (Exception e) {
				return "dateData is not format datetime";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "failed";
		}
	}

	@RequestMapping(value = "saveAllGateway", method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody String saveAllGateway(@RequestBody List<MonitorGatewayBean> listObj) {
		Date lastUpdated = new Date();
		List<MonitorGateway> listGateway = new ArrayList<MonitorGateway>();
		if (listObj != null && listObj.size() > 0) {
			for (MonitorGatewayBean mGBean : listObj) {
				if (mGBean.getSerialNo().trim().equals("")) {
					return "serialNo not emty";
				}
				try {
					Date dateData = sDateFormat.parse(mGBean.getDateData());
					MonitorGateway mGateway = monitorGateWayDAO.findBySerialNo(mGBean.getSerialNo());
					if (mGateway != null && mGateway.getId() != null && mGateway.getDateData() != null
							&& dateData.before(mGateway.getDateData())) {
						continue;
					}
					mGateway.setSerialNo(mGBean.getSerialNo());
					mGateway.setDateData(dateData);
					mGateway.setType(mGBean.getType());
					mGateway.setLastUpdated(lastUpdated);
					listGateway.add(mGateway);
				} catch (Exception e) {
					return "dateData is not format datetime";
				}
			}
		}
		if (listGateway != null && listGateway.size() > 0) {
			if (monitorGateWayDAO.saveAll(listGateway)) {
				return "success";
			} else {
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping(value = "saveAllModule", method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody String saveAllModule(@RequestBody List<MonitorModuleBean> listObj) {
		Date lastUpdated = new Date();
		List<MonitorModule> listModule = new ArrayList<MonitorModule>();
		if (listObj != null && listObj.size() > 0) {
			for (MonitorModuleBean mMBean : listObj) {
				if (mMBean.getModuleNo().trim().equals("")) {
					return "moduleNo not emty";
				}
				MonitorGateway checkGateway = monitorGateWayDAO.findBySerialNo(mMBean.getGatewayNo());
				if (checkGateway == null || checkGateway.getId() == null) {
					return "Gateway is not exist";
				}
				try {
					Date dateData = sDateFormat.parse(mMBean.getDateData());
					MonitorModule mModule = monitorModuleDAO.findByModuleNo(mMBean.getModuleNo());
					if (mModule != null & mModule.getId() != null && mModule.getDateData() != null
							&& dateData.before(mModule.getDateData())) {
						continue;
					}
					mModule.setModuleNo(mMBean.getModuleNo());
					mModule.setDateData(dateData);
					mModule.setType(mMBean.getType());
					mModule.setGatewayId(checkGateway.getId());
					mModule.setLastUpdated(lastUpdated);
					mModule.setIndex(mMBean.getIndex());
					listModule.add(mModule);
				} catch (Exception e) {
					return "dateData is not format datetime";
				}
			}
		}
		if (listModule != null && listModule.size() > 0) {
			if (monitorModuleDAO.saveAll(listModule)) {
				return "success";
			} else {
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping(value = "getAllGateWay", method = RequestMethod.GET)
	public @ResponseBody List<MonitorGateway> getAllGateway() {
		Constant constant = constantDAO.getByLabIdnKey(FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_FILE);
		int timeout_gateway_file = Integer.parseInt(constant.getValue());
		long timeoutMils = timeout_gateway_file * (60 * 60 * 1000);
		Date date = new Date();
		long time = date.getTime() - timeoutMils;
		java.sql.Timestamp dateData = new java.sql.Timestamp(time);
		List<MonitorGateway> list = monitorGateWayDAO.getAllGateways(dateData);
		return list;
	}

	@RequestMapping(value = "getGatewayByDay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<MonitorGateway> getGatewayByDay(Integer viewId) {
		if (viewId == null) {
			return null;
		}
		Constant constant = constantDAO.getByLabIdnKey(FrontalKey.MONITOR_LAB_ID, FrontalKey.TIMEOUT_GATEWAY_FILE);
		int timeout_gateway_file = Integer.parseInt(constant.getValue());
		long timeoutMils = timeout_gateway_file * (60 * 60 * 1000);
		Date date = new Date();
		long time = date.getTime() - timeoutMils;
		java.sql.Timestamp dateData = new java.sql.Timestamp(time);
		List<MonitorGateway> list = monitorGateWayDAO.getGatewaysByDay(dateData, viewId);
		return list;
	}

	@RequestMapping(value = "getModuleByDay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<MonitorModule> getModuleByDay(@RequestParam Integer gatewayId) {
		List<MonitorModule> list = monitorModuleDAO.getModuleByDay(gatewayId);
		return list;
	}

	@RequestMapping(value = "getConfig", method = RequestMethod.GET)
	public @ResponseBody String getConfig() {
		Lab lab = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_MONITOR));
		List<Constant> conLst = constantDAO.getByLabId(lab.getId());
		MonitorConfigBean bean = new MonitorConfigBean();
		for (Constant con : conLst) {
			if (FrontalKey.MONITOR_CONFIG_BRUTE.equals(con.getKey())) {
				bean.setMonitor_folder_brute(con.getValue());
			} else if (FrontalKey.MONITOR_CONFIG_CLAIRE.equals(con.getKey())) {
				bean.setMonitor_folder_claire(con.getValue());
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "getAllViews", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<MonitorViewBean> getAllViews() {
		WSCommon common = new WSCommon();
		List<MonitorViewBean> list = common.convertToMonitorBean(mViewDAO.findAll());
		List<MonitorViewBean> response = new ArrayList<MonitorViewBean>();
		for (MonitorViewBean obj : list) {
			obj.setGatewayQuantity(mViewGatewayDAO.countByViewId(obj.getId()));
			response.add(obj);
		}
		return response;
	}

	/**
	 * Delete gateway of view
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteGateWayOfView", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody String deleteGateWayOfView(Integer viewId, String lstID) {

		List<Integer> lst = new ArrayList<Integer>();
		String[] arr = lstID.split(FrontalKey.COMMA);
		for (String idStr : arr) {
			if (!idStr.isEmpty()) {
				lst.add(Integer.parseInt(idStr));
			}
		}
		int rs = mViewGatewayDAO.deleteGateWayOfView(viewId, lst);
		if (rs < 0) {
			return FrontalKey.ERROR;
		} else {
			return FrontalKey.SUCCESS;
		}
	}
}
