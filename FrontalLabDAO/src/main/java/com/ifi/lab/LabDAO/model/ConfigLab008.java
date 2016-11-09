package com.ifi.lab.LabDAO.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hmtri on 2/29/2016.
 */
@Entity
@Table(name="config_lab008")
public class ConfigLab008 {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "site_id")
    private Integer siteId;
    @Column(name = "site_name")
    private String siteName;
    @Column(name = "avgAirTempModuleId")
    private String avgAirTempModuleId;
    @Column(name = "avgExtTempModuleId")
    private String avgExtTempModuleId;
    @Column(name = "boilerModel")
    private String boilerModel;
    @Column(name = "boilerYear")
    private Integer boilerYear;
    @Column(name = "boilerTheoryPower")
    private Double boilerTheoryPower;
    @Column(name = "gasNaturalModuleId")
    private String gasNaturalModuleId;
    @Column(name = "productionModuleId")
    private String productionModuleId;
    @Column(name = "coeff1", columnDefinition = "double default 1")
    private Double coeff1;
    @Column(name = "coeff2", columnDefinition = "double default 1")
    private Double coeff2;
    @Column(name = "coeff3", columnDefinition = "double default 1")
    private Double coeff3;
    @Column(name = "coeff4", columnDefinition = "double default 1")
    private Double coeff4;
    @Column(name = "coeff5", columnDefinition = "double default 1")
    private Double coeff5;
    @Column(name = "coeff6", columnDefinition = "double default 1")
    private Double coeff6;
    @Column(name = "coeffRadnConvection", columnDefinition = "double default 1")
    private Double coeffRadnConvection;
    @Column(name = "uri_icon")
    private String uriIcon;
    @Column(name = "logo")
    private String logo;
    @Column(name = "report_name")
    private String reportName;    
    @Column(name = "fromDate")
    private Date fromDate;    
    @Column(name = "modelPrecision")
    private Double modelPrecision;
    @Column(name = "chauffage")
    private String chauffageModuleId;
    @Column(name = "ecsZoneBasse")
    private String ecsZoneBasse;
    @Column(name = "ecsZoneHaute")
    private String ecsZoneHaute;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAvgAirTempModuleId() {
        return avgAirTempModuleId;
    }

    public void setAvgAirTempModuleId(String avgAirTempModuleId) {
        this.avgAirTempModuleId = avgAirTempModuleId;
    }

    public String getAvgExtTempModuleId() {
        return avgExtTempModuleId;
    }

    public void setAvgExtTempModuleId(String avgExtTempModuleId) {
        this.avgExtTempModuleId = avgExtTempModuleId;
    }

    public String getBoilerModel() {
        return boilerModel;
    }

    public void setBoilerModel(String boilerModel) {
        this.boilerModel = boilerModel;
    }

    public Integer getBoilerYear() {
        return boilerYear;
    }

    public void setBoilerYear(Integer boilerYear) {
        this.boilerYear = boilerYear;
    }

    public Double getBoilerTheoryPower() {
        return boilerTheoryPower;
    }

    public void setBoilerTheoryPower(Double boilerTheoryPower) {
        this.boilerTheoryPower = boilerTheoryPower;
    }

    public String getGasNaturalModuleId() {
        return gasNaturalModuleId;
    }

    public void setGasNaturalModuleId(String gasNaturalModuleId) {
        this.gasNaturalModuleId = gasNaturalModuleId;
    }

    public String getProductionModuleId() {
        return productionModuleId;
    }

    public void setProductionModuleId(String productionModuleId) {
        this.productionModuleId = productionModuleId;
    }

    public Double getCoeff1() {
        return coeff1;
    }

    public void setCoeff1(Double coeff1) {
        this.coeff1 = coeff1;
    }

    public Double getCoeff2() {
        return coeff2;
    }

    public void setCoeff2(Double coeff2) {
        this.coeff2 = coeff2;
    }

    public Double getCoeff3() {
        return coeff3;
    }

    public void setCoeff3(Double coeff3) {
        this.coeff3 = coeff3;
    }

    public Double getCoeff4() {
        return coeff4;
    }

    public void setCoeff4(Double coeff4) {
        this.coeff4 = coeff4;
    }

    public Double getCoeff5() {
        return coeff5;
    }

    public void setCoeff5(Double coeff5) {
        this.coeff5 = coeff5;
    }

    public Double getCoeff6() {
        return coeff6;
    }

    public void setCoeff6(Double coeff6) {
        this.coeff6 = coeff6;
    }

    public Double getCoeffRadnConvection() {
        return coeffRadnConvection;
    }

    public String getUriIcon() {
        return uriIcon;
    }

    public void setUriIcon(String uriIcon) {
        this.uriIcon = uriIcon;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setCoeffRadnConvection(Double coeffRadnConvection) {
        this.coeffRadnConvection = coeffRadnConvection;
    }
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Double getModelPrecision() {
		return modelPrecision;
	}

	public void setModelPrecision(Double modelPrecision) {
		this.modelPrecision = modelPrecision;
	}

	public String getChauffageModuleId() {
		return chauffageModuleId;
	}

	public void setChauffageModuleId(String chauffageModuleId) {
		this.chauffageModuleId = chauffageModuleId;
	}

	public String getEcsZoneBasse() {
		return ecsZoneBasse;
	}

	public void setEcsZoneBasse(String ecsZoneBasse) {
		this.ecsZoneBasse = ecsZoneBasse;
	}

	public String getEcsZoneHaute() {
		return ecsZoneHaute;
	}

	public void setEcsZoneHaute(String ecsZoneHaute) {
		this.ecsZoneHaute = ecsZoneHaute;
	}

}
