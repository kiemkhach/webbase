package com.ifi.common.bean;

import java.util.Map;

/**
 * Bean of CarreFour
 * 
 * @author ndlong
 * 
 */
public class CarreFourBean {
	// Site ID
	private int siteId;
	// Site Name
	private String siteStr;

	public String getSiteStr() {
		return siteStr;
	}

	public void setSiteStr(String siteStr) {
		this.siteStr = siteStr;
	}

	// Path of site logo
	private String logoPath;
	// value of froid;
	private Double froid;
	private Double bureau;
	private Double eclairage;
	private Double boulangerie;
	private Double cvc;
	// template in house
	private Integer temperateIn;
	// template out house
	private Integer temperateOut;
	// sous congteur electric out
	private Double drive;
	// Total of number electric
	private Double compteurElec;
	// Total of number gaz
	private Double compteurGaz;
	private String currentYear;
	private String pastYear;
	private Integer discount;

	private Integer currentYearEnergy;
	private Integer pastYearEnergy;
	private Float discountEnergy;
	// index 1 -> 8
	private Integer indexCurrentYear;
	private Integer indexPastYear;
	private Integer indexDiscount;

	private Double froidNeg;
	private Double froidPos;
	private Double froidRes;

	private Map<String, Integer> mapFroidCap;
	private Map<String, Double> mapBureauCap;
	private Map<String, Integer> mapEclairageCap;
	private Map<String, Double> mapBoulangerieCap;
	private Map<String, Double> mapCvcCap;

	private int range_APlus_Min = 0;
	private int range_APlus_Max = 300;
	private int range_A_Min = 301;
	private int range_A_Max = 350;
	private int range_B_Min = 351;
	private int range_B_Max = 400;
	private int range_C_Min = 401;
	private int range_C_Max = 450;
	private int range_D_Min = 451;
	private int range_D_Max = 500;
	private int range_E_Min = 501;
	private int range_E_Max = 550;
	private int range_F_Min = 551;
	private int range_F_Max = 600;
	private int range_G_Min = 601;
	private int range_G_Max = 650;
	private int range_H_Min = 651;
	private int range_H_Max = 700;
	private int range_I_Min = 701;
	
	private String siteName;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getRange_APlus_Min() {
		return range_APlus_Min;
	}

	public void setRange_APlus_Min(int range_APlus_Min) {
		this.range_APlus_Min = range_APlus_Min;
	}

	public int getRange_APlus_Max() {
		return range_APlus_Max;
	}

	public void setRange_APlus_Max(int range_APlus_Max) {
		this.range_APlus_Max = range_APlus_Max;
	}

	public int getRange_A_Min() {
		return range_A_Min;
	}

	public void setRange_A_Min(int range_A_Min) {
		this.range_A_Min = range_A_Min;
	}

	public int getRange_A_Max() {
		return range_A_Max;
	}

	public void setRange_A_Max(int range_A_Max) {
		this.range_A_Max = range_A_Max;
	}

	public int getRange_B_Min() {
		return range_B_Min;
	}

	public void setRange_B_Min(int range_B_Min) {
		this.range_B_Min = range_B_Min;
	}

	public int getRange_B_Max() {
		return range_B_Max;
	}

	public void setRange_B_Max(int range_B_Max) {
		this.range_B_Max = range_B_Max;
	}

	public int getRange_C_Min() {
		return range_C_Min;
	}

	public void setRange_C_Min(int range_C_Min) {
		this.range_C_Min = range_C_Min;
	}

	public int getRange_C_Max() {
		return range_C_Max;
	}

	public void setRange_C_Max(int range_C_Max) {
		this.range_C_Max = range_C_Max;
	}

	public int getRange_D_Min() {
		return range_D_Min;
	}

	public void setRange_D_Min(int range_D_Min) {
		this.range_D_Min = range_D_Min;
	}

	public int getRange_D_Max() {
		return range_D_Max;
	}

	public void setRange_D_Max(int range_D_Max) {
		this.range_D_Max = range_D_Max;
	}

	public int getRange_E_Min() {
		return range_E_Min;
	}

	public void setRange_E_Min(int range_E_Min) {
		this.range_E_Min = range_E_Min;
	}

	public int getRange_E_Max() {
		return range_E_Max;
	}

	public void setRange_E_Max(int range_E_Max) {
		this.range_E_Max = range_E_Max;
	}

	public int getRange_F_Min() {
		return range_F_Min;
	}

	public void setRange_F_Min(int range_F_Min) {
		this.range_F_Min = range_F_Min;
	}

	public int getRange_F_Max() {
		return range_F_Max;
	}

	public void setRange_F_Max(int range_F_Max) {
		this.range_F_Max = range_F_Max;
	}

	public int getRange_G_Min() {
		return range_G_Min;
	}

	public void setRange_G_Min(int range_G_Min) {
		this.range_G_Min = range_G_Min;
	}

	public int getRange_G_Max() {
		return range_G_Max;
	}

	public void setRange_G_Max(int range_G_Max) {
		this.range_G_Max = range_G_Max;
	}

	public int getRange_H_Min() {
		return range_H_Min;
	}

	public void setRange_H_Min(int range_H_Min) {
		this.range_H_Min = range_H_Min;
	}

	public int getRange_H_Max() {
		return range_H_Max;
	}

	public void setRange_H_Max(int range_H_Max) {
		this.range_H_Max = range_H_Max;
	}

	public int getRange_I_Min() {
		return range_I_Min;
	}

	public void setRange_I_Min(int range_I_Min) {
		this.range_I_Min = range_I_Min;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Double getFroid() {
		return froid;
	}

	public void setFroid(Double froid) {
		this.froid = froid;
	}

	public Double getBureau() {
		return bureau;
	}

	public void setBureau(Double bureau) {
		this.bureau = bureau;
	}

	public Double getEclairage() {
		return eclairage;
	}

	public void setEclairage(Double eclairage) {
		this.eclairage = eclairage;
	}

	public Double getBoulangerie() {
		return boulangerie;
	}

	public void setBoulangerie(Double boulangerie) {
		this.boulangerie = boulangerie;
	}

	public Double getCvc() {
		return cvc;
	}

	public void setCvc(Double cvc) {
		this.cvc = cvc;
	}

	public Integer getTemperateIn() {
		return temperateIn;
	}

	public void setTemperateIn(Integer temperateIn) {
		this.temperateIn = temperateIn;
	}

	public Integer getTemperateOut() {
		return temperateOut;
	}

	public void setTemperateOut(Integer temperateOut) {
		this.temperateOut = temperateOut;
	}

	public Double getDrive() {
		return drive;
	}

	public void setDrive(Double drive) {
		this.drive = drive;
	}

	public Double getCompteurElec() {
		return compteurElec;
	}

	public void setCompteurElec(Double compteurElec) {
		this.compteurElec = compteurElec;
	}

	public Double getCompteurGaz() {
		return compteurGaz;
	}

	public void setCompteurGaz(Double compteurGaz) {
		this.compteurGaz = compteurGaz;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public Integer getCurrentYearEnergy() {
		return currentYearEnergy;
	}

	public void setCurrentYearEnergy(Integer currentYearEnergy) {
		this.currentYearEnergy = currentYearEnergy;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Float getDiscountEnergy() {
		return discountEnergy;
	}

	public void setDiscountEnergy(Float discountEnergy) {
		this.discountEnergy = discountEnergy;
	}

	public String getPastYear() {
		return pastYear;
	}

	public void setPastYear(String pastYear) {
		this.pastYear = pastYear;
	}

	public Integer getPastYearEnergy() {
		return pastYearEnergy;
	}

	public void setPastYearEnergy(Integer pastYearEnergy) {
		this.pastYearEnergy = pastYearEnergy;
	}

	public Integer getIndexCurrentYear() {
		return indexCurrentYear;
	}

	public void setIndexCurrentYear(Integer indexCurrentYear) {
		this.indexCurrentYear = indexCurrentYear;
	}

	public Integer getIndexPastYear() {
		return indexPastYear;
	}

	public void setIndexPastYear(Integer indexPastYear) {
		this.indexPastYear = indexPastYear;
	}

	public Integer getIndexDiscount() {
		return indexDiscount;
	}

	public void setIndexDiscount(Integer indexDiscount) {
		this.indexDiscount = indexDiscount;
	}

	public Map<String, Integer> getMapFroidCap() {
		return mapFroidCap;
	}

	public void setMapFroidCap(Map<String, Integer> mapFroidCap) {
		this.mapFroidCap = mapFroidCap;
	}

	public Map<String, Double> getMapBureauCap() {
		return mapBureauCap;
	}

	public void setMapBureauCap(Map<String, Double> mapBureauCap) {
		this.mapBureauCap = mapBureauCap;
	}

	public Map<String, Integer> getMapEclairageCap() {
		return mapEclairageCap;
	}

	public void setMapEclairageCap(Map<String, Integer> mapEclairageCap) {
		this.mapEclairageCap = mapEclairageCap;
	}

	public Map<String, Double> getMapBoulangerieCap() {
		return mapBoulangerieCap;
	}

	public void setMapBoulangerieCap(Map<String, Double> mapBoulangerieCap) {
		this.mapBoulangerieCap = mapBoulangerieCap;
	}

	public Map<String, Double> getMapCvcCap() {
		return mapCvcCap;
	}

	public void setMapCvcCap(Map<String, Double> mapCvcCap) {
		this.mapCvcCap = mapCvcCap;
	}

	public Double getFroidNeg() {
		return froidNeg;
	}

	public void setFroidNeg(Double froidNeg) {
		this.froidNeg = froidNeg;
	}

	public Double getFroidPos() {
		return froidPos;
	}

	public void setFroidPos(Double froidPos) {
		this.froidPos = froidPos;
	}

	public Double getFroidRes() {
		return froidRes;
	}

	public void setFroidRes(Double froidRes) {
		this.froidRes = froidRes;
	}

}