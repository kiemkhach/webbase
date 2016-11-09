package com.energisme.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.Lab010IdexService;
import com.ifi.common.bean.Idex.IdexConstant.IdexConstant;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;

public class Lab010ConfigInstallationAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer installationId;
	private File fileUpload;
	private String fileUploadFileName;
	private Integer fileType;

	private List<IdexInstallation> idexInstallationLst;

	@Autowired
	private IdexInstallationDAO idexInstallationDAO;

	@Autowired
	private Lab010IdexService lab010IdexService;

	public String getUploadPage() {
		idexInstallationLst = idexInstallationDAO.getAll();
		return SUCCESS;
	}

	public String uploadReport() {
		IdexInstallation installation = idexInstallationDAO.findById(installationId);
		if (fileType.equals(IdexConstant.TYPE_COST)) {
			installation.setCostReportFile(fileUploadFileName);
		} else {
			installation.setEnergyReportFile(fileUploadFileName);
		}
		String path = PropertiesReader.getValue(ConfigEnum.REPORT_LAB010_PATH);
		Boolean ok = lab010IdexService.uploadFile(path, fileUploadFileName, fileUpload);
		if (ok) {
			idexInstallationDAO.save(installation);
		}
		return SUCCESS;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public List<IdexInstallation> getIdexInstallationLst() {
		return idexInstallationLst;
	}

	public void setIdexInstallationLst(List<IdexInstallation> idexInstallationLst) {
		this.idexInstallationLst = idexInstallationLst;
	}

}
