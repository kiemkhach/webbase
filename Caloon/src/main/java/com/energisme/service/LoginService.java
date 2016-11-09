package com.energisme.service;

import com.ifi.common.bean.caloon.CaloonUserBean;

public interface LoginService {
	int getSiteByUser(String userName);
	
	String getCaloonUserStatusByUser(String userName, String passWord);
}
