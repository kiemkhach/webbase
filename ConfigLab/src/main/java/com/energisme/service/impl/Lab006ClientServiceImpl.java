/**
 * 
 */
package com.energisme.service.impl;

import com.energisme.bean.NumConfigLab006Client;
import com.energisme.service.Lab006ClientService;
import com.energisme.util.Utils;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab006ClientDAO;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * @author hmtri
 *
 */
public class Lab006ClientServiceImpl implements Lab006ClientService {

	private static final Logger logger = LoggerFactory.getLogger(Lab006ClientServiceImpl.class);

	@Autowired
	private ConfigLab006ClientDAO lab006ClientDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.energisme.service.Lab006ClientService#getClientsBySubscriptionId(java
	 * .lang.Integer)
	 */
	@Override
	public List<ConfigLab006Client> getClientsBySubscriptionId(Integer subscriptionId) {

		List<ConfigLab006Client> listClients = lab006ClientDAO.getBySubscription(subscriptionId);

		return listClients;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.energisme.service.Lab006ClientService#saveClient(com.ifi.lab.LabDAO.
	 * model.ConfigLab006Client)
	 */
	@Override
	public boolean saveClient(NumConfigLab006Client obj) {
	    ConfigLab006Client client = null;
		if (obj.getId() != null){
			client = lab006ClientDAO.getClientById(obj.getId());
		}else {
			client = new ConfigLab006Client();
		}
		String reportClientName = obj.getReportClientName();
		String imageClientName = obj.getImgClientName();
		if (reportClientName != null){
			client.setReportClientName(reportClientName.trim());
		}
		if (imageClientName != null){
			client.setImageClientName(imageClientName.trim());
		}
		else if(imageClientName == null) {
			client.setImageClientName(imageClientName);
		}
        client.setClientName(obj.getClientName());
        client.setSubscriptionId(obj.getSubscriptionId());

        String listModuleHCE = obj.getListModuleHCE();
        String listModuleHPE = obj.getListModuleHPE();
        String listModuleHCH = obj.getListModuleHCH();
        String listModuleHPH = obj.getListModuleHPH();

        GetCSMDataAction csm = new GetCSMDataAction();

        String listModuleIdHCE = "";
        String listModuleIdHPE = "";
        String listModuleIdHCH = "";
        String listModuleIdHPH = "";

        if (listModuleHCE != null) {
            if (!listModuleHCE.trim().isEmpty()) {
                String[] arr = listModuleHCE.split(FrontalKey.PLUS_SPECIAL);
                for (int i = 0; i < arr.length; i++) {
                    Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
                    if (number != null) {
                        listModuleIdHCE += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
                    } else {
                        return false;
                    }
                }
            }
        }
        if (listModuleHPE != null) {
            if (!listModuleHPE.trim().isEmpty()) {
                String[] arr = listModuleHPE.split(FrontalKey.PLUS_SPECIAL);
                for (int i = 0; i < arr.length; i++) {
                    Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
                    if (number != null) {
                        listModuleIdHPE += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
                    } else {
                        return false;
                    }
                }
            }
        }
        if (listModuleHCH != null) {
            if (!listModuleHCH.trim().isEmpty()) {
                String[] arr = listModuleHCH.split(FrontalKey.PLUS_SPECIAL);
                for (int i = 0; i < arr.length; i++) {
                    Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
                    if (number != null) {
                        listModuleIdHCH += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
                    } else {
                        return false;
                    }
                }
            }
        }
        if (listModuleHPH != null) {
            if (!listModuleHPH.trim().isEmpty()) {
                String[] arr = listModuleHPH.split(FrontalKey.PLUS_SPECIAL);
                for (int i = 0; i < arr.length; i++) {
                    Integer number = csm.getModuleCSMIDByNumber(Utils.getModuleNumberFromArray(arr[i].trim()));
                    if (number != null) {
                        listModuleIdHPH += Utils.getModuleIndexRatio(arr[i].trim())+number.toString() + FrontalKey.PLUS;
                    } else {
                        return false;
                    }
                }
            }
        }
        
		/*if (listModuleHCE != null && !"".equals(listModuleHCE)){
			client.setModuleIdHCE(csm.getModuleCSMIDByNumber(listModuleHCE).toString());
		}
		if (listModuleHCH != null && !"".equals(listModuleHCH)){
			client.setModuleIdHCH(csm.getModuleCSMIDByNumber(listModuleHCH).toString());
		}
		if (listModuleHPE != null && !"".equals(listModuleHPE)){
			client.setModuleIdHPE(csm.getModuleCSMIDByNumber(listModuleHPE).toString());
		}
		if (listModuleHPH != null && !"".equals(listModuleHPH)){
			client.setModuleIdHPH(csm.getModuleCSMIDByNumber(listModuleHPH).toString());
		}*/
        if (listModuleIdHCE != null && !"".equals(listModuleIdHCE)) {
			if (FrontalKey.PLUS.equals(listModuleIdHCE.charAt(listModuleIdHCE.length()-1))) {
				listModuleIdHCE = listModuleIdHCE.substring(0,listModuleIdHCE.length()-1);
			}
		}
        if (listModuleIdHPE != null && !"".equals(listModuleIdHPE)) {
			if (FrontalKey.PLUS.equals(listModuleIdHPE.charAt(listModuleIdHPE.length()-1))) {
				listModuleIdHPE = listModuleIdHPE.substring(0,listModuleIdHPE.length()-1);
			}
		}
        if (listModuleIdHCH != null && !"".equals(listModuleIdHCH)) {
			if (FrontalKey.PLUS.equals(listModuleIdHCH.charAt(listModuleIdHCH.length()-1))) {
				listModuleIdHCH = listModuleIdHCH.substring(0,listModuleIdHCH.length()-1);
			}
		}
        if (listModuleIdHPH != null && !"".equals(listModuleIdHPH)) {
			if (FrontalKey.PLUS.equals(listModuleIdHPH.charAt(listModuleIdHPH.length()-1))) {
				listModuleIdHPH = listModuleIdHPH.substring(0,listModuleIdHPH.length()-1);
			}
		}
        client.setModuleIdHCE(listModuleIdHCE);
        client.setModuleIdHPE(listModuleIdHPE);
        client.setModuleIdHCH(listModuleIdHCH);
        client.setModuleIdHPH(listModuleIdHPH);
        
        return lab006ClientDAO.saveOrUpdate(client);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.energisme.service.Lab006ClientService#deleteClient(com.ifi.lab.LabDAO
	 * .model.ConfigLab006Client)
	 */
	@Override
	public boolean deleteClient(Integer id) {
		return lab006ClientDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.energisme.service.Lab006ClientService#getClientById(java.lang.
	 * Integer)
	 */
	@Override
	public ConfigLab006Client getClientById(Integer id) {
		ConfigLab006Client client = null;
		client = lab006ClientDAO.getClientById(id);
		return client;
	}

	@Override
	public boolean uploadFileReport(String clientId, File file, String fileName, int type) {
		try {
//			logger.info("hmtri3");
			String path = "";
			if (FrontalKey.TYPE_UPLOAD_REPORT == type){
				path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB006_CLIENT_LINK);
			}else if (FrontalKey.TYPE_UPLOAD_IMAGE == type){
				path = PropertiesReader.getValue(ConfigEnum.IMAGE_LAB006_CLIENT_LINK);
			}
//			logger.info(path);
			File folder = new File(path);
			if (!folder.exists()) {
				if (!folder.mkdir()) {
					return false;
				}
			}
//			logger.error("hmtri4");
			String pathSiteId = path + FrontalKey.WINDOWS + clientId;
			File subFolder = new File(pathSiteId);
//			logger.error(subFolder.getPath());
			if (!subFolder.exists()) {
				logger.error("folder not exist");
				if (!subFolder.mkdir()) {
					logger.error("create folder failed");
					return false;
				}
			}
			File newFile = new File(pathSiteId + FrontalKey.WINDOWS + fileName);
			logger.error(newFile.getPath());
			FileUtils.copyFile(file, newFile);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Integer getNewestId() {
		return lab006ClientDAO.getNewestClient().getId();
	}
}
