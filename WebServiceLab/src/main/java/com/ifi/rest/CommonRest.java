package com.ifi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.lab.LabDAO.dao.ConstantDAO;

@Controller
@RequestMapping("common")
public class CommonRest {

	@Autowired
	private ConstantDAO constantDao;

	@RequestMapping(value = "getConstant", method = RequestMethod.GET)
	public @ResponseBody String getConstant(@RequestParam Integer labId,
			@RequestParam String key) {		
		return constantDao.getByLabIdnKey(labId, key).getValue();
	}
}
