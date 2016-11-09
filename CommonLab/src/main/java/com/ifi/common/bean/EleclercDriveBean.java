package com.ifi.common.bean;

public class EleclercDriveBean {
	private int siteId;
	// Site Name
	private String siteStr;
	// Path of site logo
	private String logoPath;
	// sous congteur electric out
	private Double drive;
	private String currentYear;
	private String pastYear;
	private Integer discount;
	// template out house
	private Integer temperateOut;
	private Integer currentYearEnergy;
	private Integer pastYearEnergy;
	private Float discountEnergy;
	// index 1 -> 8
	private Integer indexCurrentYear;
	private Integer indexPastYear;
	private Integer indexDiscount;
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
	//index progress bar
	private int maxValueDrive;
	private int range_Normal_Min;
	private int range_Normal_Max;
	private int range_High_Min;
	private int range_High_Max;
	private int range_Warning_Min;
	private int range_Warning_Max;
	private int level_Drive;
	
	private String siteName;
	
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getLevel_Drive() {
		return level_Drive;
	}

	public void setLevel_Drive(int level_Drive) {
		this.level_Drive = level_Drive;
	}

	public int getMaxValueDrive() {
		return maxValueDrive;
	}

	public void setMaxValueDrive(int maxValueDrive) {
		this.maxValueDrive = maxValueDrive;
	}

	public int getRange_Normal_Min() {
		return range_Normal_Min;
	}

	public void setRange_Normal_Min(int range_Normal_Min) {
		this.range_Normal_Min = range_Normal_Min;
	}

	public int getRange_Normal_Max() {
		return range_Normal_Max;
	}

	public void setRange_Normal_Max(int range_Normal_Max) {
		this.range_Normal_Max = range_Normal_Max;
	}

	public int getRange_High_Min() {
		return range_High_Min;
	}

	public void setRange_High_Min(int range_High_Min) {
		this.range_High_Min = range_High_Min;
	}

	public int getRange_High_Max() {
		return range_High_Max;
	}

	public void setRange_High_Max(int range_High_Max) {
		this.range_High_Max = range_High_Max;
	}

	public int getRange_Warning_Min() {
		return range_Warning_Min;
	}

	public void setRange_Warning_Min(int range_Warning_Min) {
		this.range_Warning_Min = range_Warning_Min;
	}

	public int getRange_Warning_Max() {
		return range_Warning_Max;
	}

	public void setRange_Warning_Max(int range_Warning_Max) {
		this.range_Warning_Max = range_Warning_Max;
	}

	public Integer getTemperateOut() {
		return temperateOut;
	}

	public void setTemperateOut(Integer temperateOut) {
		this.temperateOut = temperateOut;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSiteStr() {
		return siteStr;
	}

	public void setSiteStr(String siteStr) {
		this.siteStr = siteStr;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Double getDrive() {
		return drive;
	}

	public void setDrive(Double drive) {
		this.drive = drive;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getPastYear() {
		return pastYear;
	}

	public void setPastYear(String pastYear) {
		this.pastYear = pastYear;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getCurrentYearEnergy() {
		return currentYearEnergy;
	}

	public void setCurrentYearEnergy(Integer currentYearEnergy) {
		this.currentYearEnergy = currentYearEnergy;
	}

	public Integer getPastYearEnergy() {
		return pastYearEnergy;
	}

	public void setPastYearEnergy(Integer pastYearEnergy) {
		this.pastYearEnergy = pastYearEnergy;
	}

	public Float getDiscountEnergy() {
		return discountEnergy;
	}

	public void setDiscountEnergy(Float discountEnergy) {
		this.discountEnergy = discountEnergy;
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

}
