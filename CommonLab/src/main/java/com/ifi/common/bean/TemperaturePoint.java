package com.ifi.common.bean;

public class TemperaturePoint {
	private Integer minTemp;
	private Integer maxTemp;
	private Integer sumTemp;
	private Integer countTemp;
	private Integer avgTemp;

	public Integer getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(Integer minTemp) {
		this.minTemp = minTemp;
	}

	public Integer getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Integer maxTemp) {
		this.maxTemp = maxTemp;
	}

	public Integer getSumTemp() {
		return sumTemp;
	}

	public void setSumTemp(Integer sumTemp) {
		this.sumTemp = sumTemp;
	}

	public Integer getCountTemp() {
		return countTemp;
	}

	public void setCountTemp(Integer countTemp) {
		this.countTemp = countTemp;
	}

	public Integer getAvgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(Integer avgTemp) {
		this.avgTemp = avgTemp;
	}

}
