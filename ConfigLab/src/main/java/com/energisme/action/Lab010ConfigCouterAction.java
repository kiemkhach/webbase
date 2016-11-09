package com.energisme.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;

public class Lab010ConfigCouterAction extends AbstractBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer installationId;
	private Integer counterId;
	private String coefficient;
	private List<IdexCounter> couteurLst;
	private List<IdexInstallation> installationLst;

	@Autowired
	private IdexCounterDAO idexCounterDAO;
	@Autowired
	private IdexInstallationDAO idexInstallationDAO;

	public String execute() throws Exception {
		installationLst = idexInstallationDAO.getAll();
		if (installationId != null) {
			couteurLst = idexCounterDAO.findByInstallation(installationId);
		} else {
			if (installationLst.size() > 0) {
				couteurLst = idexCounterDAO.findByInstallation(installationLst.get(0).getIdexInstallationId());
			}
		}
		if (counterId != null) {
			IdexCounter idexCounter = idexCounterDAO.findById(counterId);
			if (idexCounter != null) {
				coefficient = String.valueOf(idexCounter.getCoefficientEnergetique());
			}
		}
		return SUCCESS;
	}

	public String saveCounter() throws Exception {
		if(counterId != null && coefficient != null){
			Float val = Float.parseFloat(coefficient);
			idexCounterDAO.updateCoefficient(counterId, val);
		}
		return SUCCESS;
	}

	public Integer getCounterId() {
		return counterId;
	}

	public void setCounterId(Integer counterId) {
		this.counterId = counterId;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}

	public Integer getInstallationId() {
		return installationId;
	}

	public void setInstallationId(Integer installationId) {
		this.installationId = installationId;
	}

	public List<IdexCounter> getCouteurLst() {
		return couteurLst;
	}

	public void setCouteurLst(List<IdexCounter> couteurLst) {
		this.couteurLst = couteurLst;
	}

	public List<IdexInstallation> getInstallationLst() {
		return installationLst;
	}

	public void setInstallationLst(List<IdexInstallation> installationLst) {
		this.installationLst = installationLst;
	}

}
