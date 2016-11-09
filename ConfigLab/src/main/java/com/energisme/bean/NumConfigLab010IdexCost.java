package com.energisme.bean;

import java.util.ArrayList;
import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public class NumConfigLab010IdexCost {
	private List<IdexEnergySupplier> idexEnergySupplierList = new ArrayList<IdexEnergySupplier>(0);
	private List<IdexSite> idexSiteList = new ArrayList<IdexSite>(0);
	private Integer idexEnergySupplierId;
	private Integer idexSiteId;
	private String idexCostName;
	public List<IdexEnergySupplier> getIdexEnergySupplierList() {
		return idexEnergySupplierList;
	}
	public void setIdexEnergySupplierList(
			List<IdexEnergySupplier> idexEnergySupplierList) {
		this.idexEnergySupplierList = idexEnergySupplierList;
	}
	public List<IdexSite> getIdexSiteList() {
		return idexSiteList;
	}
	public void setIdexSiteList(List<IdexSite> idexSiteList) {
		this.idexSiteList = idexSiteList;
	}
	public Integer getIdexEnergySupplierId() {
		return idexEnergySupplierId;
	}
	public void setIdexEnergySupplierId(Integer idexEnergySupplierId) {
		this.idexEnergySupplierId = idexEnergySupplierId;
	}
	public Integer getIdexSiteId() {
		return idexSiteId;
	}
	public void setIdexSiteId(Integer idexSiteId) {
		this.idexSiteId = idexSiteId;
	}
	public String getIdexCostName() {
		return idexCostName;
	}
	public void setIdexCostName(String idexCostName) {
		this.idexCostName = idexCostName;
	}
	
}
