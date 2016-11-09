package com.ifi.lab.LabDAO.dao.caloon;

import java.util.List;


import com.ifi.common.bean.caloon.CaloonUserBean;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;

public interface CaloonUserDAO {
	CaloonUserBean checkLogin(String userName,String password);
	
	Integer createUser(CaloonUser obj);
	
	List<CaloonUser> getAllUser();
	
	CaloonUser findUserById(Integer id);
	
	Boolean saveOrUpdate(CaloonUser obj);
	Boolean deleteUserCaloon(Integer id);
	
	List<CaloonUser> getUserResident();
	List<CaloonUser> getUserSyndic();
}
