package com.energisme.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.EleclercDriveService;
import com.ifi.common.bean.EleclercDriveBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;

public class EleclercDriveAction extends AbstractBaseAction {
	@Autowired
	private EleclercDriveService eleclercDriveService;
	private EleclercDriveBean driveBean;
	private String percent;
	private InputStream fileInputStream;
	private String fileName;
	private Integer siteId;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String eleclercDrive() {
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception {
		// ELeclercBean eLeclercBean = (ELeclercBean)
		// session.get("eLeclercBean");
		driveBean = eleclercDriveService.getELeclercDrive(PropertiesReader.getValue(ConfigEnum.LAB_NAME_003), 2,
				siteId);
		// driveBean.setCurrentYear(eLeclercBean.getCurrentYear());
		// driveBean.setCurrentYearEnergy(eLeclercBean.getCurrentYearEnergy());
		// driveBean.setDiscount(eLeclercBean.getDiscount());
		// driveBean.setDiscountEnergy(eLeclercBean.getDiscountEnergy());
		// driveBean.setDrive(eLeclercBean.getDrive());
		// driveBean.setIndexCurrentYear(eLeclercBean.getIndexCurrentYear());
		// driveBean.setIndexDiscount(eLeclercBean.getIndexDiscount());
		// driveBean.setIndexPastYear(eLeclercBean.getIndexPastYear());
		// driveBean.setLogoPath(logoPath);
		driveBean.setMaxValueDrive(eleclercDriveService
				.getMaxValueDrive(PropertiesReader.getValue(ConfigEnum.LAB_NAME_003), "maxDriveValue"));
		// driveBean.setPastYear(eLeclercBean.getPastYear());
		// driveBean.setPastYearEnergy(eLeclercBean.getPastYearEnergy());
		driveBean.setSiteId(siteId);
		// driveBean.setSiteStr(eLeclercBean.getSiteStr());
		// driveBean.setTemperateOut(eLeclercBean.getTemperateOut());
		driveBean.setLevel_Drive(getLevelProgress());
		linkReport = eleclercDriveService.getDriveReportLink(siteId);
		File newfile = new File(linkReport);
		if (newfile.exists() && !newfile.isDirectory()) {
			isReport = true;
		} else {
			isReport = false;
		}
		return SUCCESS;
	}

	public String exportReport() {		
		try {
			fileName = getText("file.report.name");
			fileName += LabUtils.getCurrentDate();
			fileName += ".pdf";
			fileInputStream = new FileInputStream(new File(linkReport));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// getter settter

	public EleclercDriveBean getDriveBean() {
		return driveBean;
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

	public void setDriveBean(EleclercDriveBean driveBean) {
		this.driveBean = driveBean;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	private int getLevelProgress() {
//		Integer drive = driveBean.getDrive();
		Double drive = driveBean.getDrive();
		Integer max = driveBean.getRange_High_Max();
		if (drive != null && max != null && max != 0) {
//			Integer per = (int) ((float) drive / max * 100);
			Integer per = (int) ( drive / max * 100);
			if (per > 100) {
				per = 100;
			}
			// DecimalFormat format = new DecimalFormat("##.##");
			this.percent = String.valueOf(per);
			if (drive > driveBean.getRange_Normal_Max()) {
				if (drive > driveBean.getRange_High_Max()) {
					return FrontalKey.RANGE_WARNING;
				} else
					return FrontalKey.RANGE_HIGH;
			} else {
				return FrontalKey.RANGE_NORMAL;
			}
		}
		return 0;
	}

	private boolean isReport;
	private String linkReport;

	public boolean getIsReport() {
		return isReport;
	}

	public void setIsReport(boolean isReport) {
		this.isReport = isReport;
	}

	public String getLinkReport() {
		return linkReport;
	}

	public void setLinkReport(String linkReport) {
		this.linkReport = linkReport;
	}
}
