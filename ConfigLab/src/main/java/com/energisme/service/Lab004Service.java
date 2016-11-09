package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab004;
import com.energisme.bean.NumConfigLab004Line;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab004Line;

public interface Lab004Service {

	ConfigLab004 getConfigLab004BySite(Integer siteId);

	List<ConfigLab004> getAllConfigLab004();

	String saveConfig(NumConfigLab004 numConfigLab);

	ConfigLab004Line getLineById(Integer id);

	List<ConfigLab004Line> getAllLine(Integer configLab004Id);

	String savaAllLine(List<ConfigLab004Line> listConfig);

	String deleteConfig(String id);

	String uploadFile(String siteId, File file1, String file1name, File file2, String file2name, File file3,
			String file3name);

	Integer getMaxSiteId();

}
