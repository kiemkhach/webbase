package com.ifi.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.lab.LabDAO.dao.ConfigRangeDAO;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.ConfigRange;
import com.ifi.lab.LabDAO.model.Constant;
import com.ifi.lab.LabDAO.model.Lab;

@Controller
@RequestMapping("config")
public class ConfigRest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigRest.class);
	@Autowired
	private ConfigRangeDAO configRangeDAO;
	@Autowired
	private LabDAO labDAO;
	@Autowired
	private ConstantDAO constantDAO;

	@RequestMapping(value = "getConfigRange", method = RequestMethod.GET)
	public @ResponseBody List<ConfigRange> getConfigRange(
			@RequestParam String labName, @RequestParam int pageId,
			@RequestParam String type) {
		Lab lab = labDAO.findByName(labName);
		List<ConfigRange> ranges = null;
		if (lab != null) {
			ranges = configRangeDAO.getRangeByLabIdnPageIdnType(lab.getId(),
					pageId, type);
			try {
				if (ranges.size() > 0) {
					return ranges;
				} else {
					LOGGER.debug("ranges null");
				}
			} catch (NullPointerException e) {
				LOGGER.debug("ranges null");
			}
		} else {
			LOGGER.debug("lab null");
		}
		return null;
	}

	@RequestMapping(value = "getConstant", method = RequestMethod.GET)
	public @ResponseBody Constant getConstant(@RequestParam String labName,
			@RequestParam String key) {
		Lab lab = labDAO.findByName(labName);
		Constant constant = null;
		if (lab != null) {
			constant = constantDAO.getByLabIdnKey(lab.getId(), key);
		}
		return constant;
	}
}
