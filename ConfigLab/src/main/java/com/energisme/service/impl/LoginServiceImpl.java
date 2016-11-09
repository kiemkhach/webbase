package com.energisme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.LoginService;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.dao.ConstantDAO;

public class LoginServiceImpl implements LoginService {
	@Autowired
	private ConstantDAO constantDao;

	@Override
	public boolean checkLogin(String user_name, String password) {
		String userName = constantDao.getByLabIdnKey(FrontalKey.CONFIG_LAB_ID,
				FrontalKey.CONFIG_LAB_KEY_USERNAME).getValue();
		String passWord = constantDao.getByLabIdnKey(FrontalKey.CONFIG_LAB_ID,
				FrontalKey.CONFIG_LAB_KEY_PASSWORD).getValue();
		if (userName == null || passWord == null || user_name == null
				|| password == null || userName.trim().equals("")
				|| passWord.trim().equals("") || user_name.trim().equals("")
				|| password.trim().equals("")) {
			return false;
		}

		if (userName.equals(user_name) && passWord.equals(password)) {
			return true;
		}
		return false;
	}
}
