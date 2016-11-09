package com.energisme.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.CaloonService;
import com.ifi.common.bean.caloon.CaloonConstant;
import com.ifi.common.bean.caloon.CaloonSyndicBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;

/**
 * Created by NHANH
 */
public class SyndicAction extends AbstractBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SyndicAction.class);

	private Integer syndicId;

	private InputStream fileInputStream;
	
	private String fileName;

	@Autowired
	private CaloonService caloonService;

	@Override
	public String execute() throws Exception {
		if(syndicId != null){
			session.put(FrontalKey.CALOON_SYNDIC_ID, syndicId);
			CaloonSyndicBean bean = caloonService.getCaloonSyndicBean(syndicId);
			
			String address = "";
			if(bean != null && bean.getAddress() != null){
				address = bean.getAddress();
			}
			session.put(CaloonConstant.ADDRESS_VALUE, address);
			session.put(CaloonConstant.POSTAL_CODE, bean.getCodePostal());
			session.put(CaloonConstant.CITY, bean.getVille());
			
			String day = caloonService.getLastDaySyndic(syndicId);
			if(day == null){
				day = "--";
			}
			session.put(CaloonConstant.LAST_DAY_DATA, day);
		}
		return SUCCESS;
	}

	public String downloadBackLog() {
		try {

			CaloonSyndicBean bean = caloonService.getCaloonSyndicBean(syndicId);

			String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB011_PATH);
			if (bean.getReportPath() != null) {
				fileName = bean.getReportPath();
				String src = path + "/" + bean.getCaloonSyndicId() + "/" + bean.getReportPath();
				fileInputStream = new FileInputStream(new File(src));
			} else {
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}

	public Integer getSyndicId() {
		return syndicId;
	}

	public void setSyndicId(Integer syndicId) {
		this.syndicId = syndicId;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
