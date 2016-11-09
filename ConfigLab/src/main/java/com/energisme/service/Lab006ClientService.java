package com.energisme.service;

import java.io.File;
import java.util.List;

import com.energisme.bean.NumConfigLab006Client;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;

public interface Lab006ClientService {
	List<ConfigLab006Client> getClientsBySubscriptionId(Integer subscriptionId);
	boolean saveClient(NumConfigLab006Client obj);
	boolean deleteClient(Integer id);
	ConfigLab006Client getClientById(Integer id);
	boolean uploadFileReport(String clientId, File file, String fileName, int type);
	Integer getNewestId();
}
