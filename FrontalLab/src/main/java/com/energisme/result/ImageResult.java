package com.energisme.result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.energisme.action.CarreFourAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ImageResult implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ActionInvocation invocation) throws Exception {
		CarreFourAction action = (CarreFourAction) invocation.getAction();
		HttpServletResponse respone = ServletActionContext.getResponse();
		if (CarreFourAction.RESULT_LOGO.equals(invocation.getResultCode())) {
			respone.getOutputStream().write(action.getImageLogoInBytes());
		} else {
			respone.getOutputStream().write(action.getImageIconInBytes());
		}
		// respone.setContentType(action.getCustomContentType());
		respone.getOutputStream().flush();
	}
}
