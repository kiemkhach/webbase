package com.ifi.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.MGatewayFileBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.dao.MGatewayFileDao;
import com.ifi.lab.LabDAO.dao.MonitorGatewayDAO;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.MGatewayFile;
import com.ifi.lab.LabDAO.model.MonitorGateway;

@Controller
@RequestMapping("mGatewayFile")
public class MGatewayFileRest {

	@Autowired
	private MGatewayFileDao mGatewayFileDao;

	@Autowired
	private MonitorGatewayDAO monitorGatewayDAO;

	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;

	@RequestMapping(value = "saveFile", method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody String saveFile(@RequestBody MGatewayFileBean bean) {
		MGatewayFile f = new MGatewayFile();
		MonitorGateway gateway = monitorGatewayDAO.findBySerialNo(bean.getGatewayNo());
		if (gateway != null && gateway.getId() != null) {
			f.setGatewayId(gateway.getId());
		} else {
			return FrontalKey.ERROR;
		}
		f.setDateData(bean.getDateData());
		f.setContent(bean.getContent());
		f.setFileName(bean.getFileName());
		f.setFileSize(bean.getFileSize());
		if (mGatewayFileDao.save(f)) {
			return FrontalKey.SUCCESS;
		} else {
			return FrontalKey.ERROR;
		}

	}

	@RequestMapping(value = "getByGateway", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody List<MGatewayFileBean> getByGateway(@RequestParam Integer gatewayId) {
		List<MGatewayFile> modelLst = mGatewayFileDao.getByGateway(gatewayId);
		List<MGatewayFileBean> beanLst = new ArrayList<MGatewayFileBean>();
		for (MGatewayFile model : modelLst) {
			MGatewayFileBean bean = new MGatewayFileBean();
			bean.setId(model.getId());
			bean.setFileName(model.getFileName());
			bean.setDateData(model.getDateData());
			bean.setGatewayId(model.getGatewayId());
			bean.setContent(model.getContent());
			bean.setFileSize(model.getFileSize());
			if (model.getFileSize() != null) {
				bean.setFileSizeKB((double) model.getFileSize() / 1024);
			}
			beanLst.add(bean);
		}
		return beanLst;
	}

	@RequestMapping(value = "findById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody MGatewayFileBean findById(@RequestParam Integer id) {
		MGatewayFile model = mGatewayFileDao.findByID(id);
		MGatewayFileBean bean = new MGatewayFileBean();
		bean.setId(model.getId());
		bean.setFileName(model.getFileName());
		bean.setDateData(model.getDateData());
		bean.setGatewayId(model.getGatewayId());
		bean.setContent(model.getContent());
		bean.setFileSize(model.getFileSize());
		return bean;
	}

	/**
	 * Delete old file in DB the Records of file have data_date greater than
	 * constant [timeout_gateway_file]
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteOldFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody String deleteOldFile() {
		Lab l = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_MONITOR));
		Constant c = constantDAO.getByLabIdnKey(l.getId(), FrontalKey.TIMEOUT_GATEWAY_FILE);
		int timeout_gateway_file = 72;
		if (c != null) {
			try {
				timeout_gateway_file = Integer.parseInt(c.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -timeout_gateway_file);
		int rs = mGatewayFileDao.deleteBeforeDay(cal.getTime());
		if(rs >= 0){
			return FrontalKey.SUCCESS;
		}else{
			return FrontalKey.ERROR;
		}
	}
}
