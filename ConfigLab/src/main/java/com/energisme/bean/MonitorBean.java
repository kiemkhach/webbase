package com.energisme.bean;

public class MonitorBean {
	private String userName;
	private String passWord;
	private String timeoutGatewayLcpc;
	private String timeoutGatewayDevice;
	private String timeoutModuleDevice;

	public String getTimeoutGatewayLcpc() {
		return timeoutGatewayLcpc;
	}

	public void setTimeoutGatewayLcpc(String timeoutGatewayLcpc) {
		this.timeoutGatewayLcpc = timeoutGatewayLcpc;
	}

	public String getTimeoutGatewayDevice() {
		return timeoutGatewayDevice;
	}

	public void setTimeoutGatewayDevice(String timeoutGatewayDevice) {
		this.timeoutGatewayDevice = timeoutGatewayDevice;
	}

	public String getTimeoutModuleDevice() {
		return timeoutModuleDevice;
	}

	public void setTimeoutModuleDevice(String timeoutModuleDevice) {
		this.timeoutModuleDevice = timeoutModuleDevice;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
