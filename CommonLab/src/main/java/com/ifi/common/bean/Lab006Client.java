package com.ifi.common.bean;

public class Lab006Client {
    private Integer id;
    private Integer subscriptionId;
    private String clientName;
    private Double numberHCE;
    private Double numberHPE;
    private Double numberHCH;
    private Double numberHPH;
    private Double globalConsumptionClient;
    private Double percentClient;
    private String reportClientLink;
    private String imgClientLink;
    private String imgClientBase64;


    public String getReportClientLink() {
        return reportClientLink;
    }

    public void setReportClientLink(String reportClientLink) {
        this.reportClientLink = reportClientLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    public Double getNumberHCE() {
		return numberHCE;
	}

	public void setNumberHCE(Double numberHCE) {
		this.numberHCE = numberHCE;
	}

	public Double getNumberHPE() {
		return numberHPE;
	}

	public void setNumberHPE(Double numberHPE) {
		this.numberHPE = numberHPE;
	}

	public Double getNumberHCH() {
		return numberHCH;
	}

	public void setNumberHCH(Double numberHCH) {
		this.numberHCH = numberHCH;
	}

	public Double getNumberHPH() {
		return numberHPH;
	}

	public void setNumberHPH(Double numberHPH) {
		this.numberHPH = numberHPH;
	}

	public Double getGlobalConsumptionClient() {
        return globalConsumptionClient;
    }

    public void setGlobalConsumptionClient(Double globalConsumptionClient) {
        this.globalConsumptionClient = globalConsumptionClient;
    }

    public Double getPercentClient() {
        return percentClient;
    }

    public void setPercentClient(Double percentClient) {
        this.percentClient = percentClient;
    }

    public String getImgClientLink() {
        return imgClientLink;
    }

    public void setImgClientLink(String imgClientLink) {
        this.imgClientLink = imgClientLink;
    }

    public String getImgClientBase64() {
        return imgClientBase64;
    }

    public void setImgClientBase64(String imgClientBase64) {
        this.imgClientBase64 = imgClientBase64;
    }
}
