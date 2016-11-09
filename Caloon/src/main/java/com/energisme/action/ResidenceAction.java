package com.energisme.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifi.common.bean.caloon.CaloonUserBean;
import com.ifi.common.util.FrontalKey;

/**
 * Created by NHANH
 */
public class ResidenceAction extends AbstractBaseAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ResidenceAction.class);
	// CaloonUserBean caloonUserBean;

	private Integer residentId;

	@Override
	public String execute() throws Exception {
		if (residentId != null) {
			session.put(FrontalKey.CALOON_RESIDENT_ID, residentId);
		}
		return SUCCESS;
	}

	// public CaloonUserBean getCaloonUserBean() {
	// return caloonUserBean;
	// }
	//
	// public void setCaloonUserBean(CaloonUserBean caloonUserBean) {
	// this.caloonUserBean = caloonUserBean;
	// }

	public Integer getResidentId() {
		return residentId;
	}

	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}

}
