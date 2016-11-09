package com.energisme.result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.energisme.action.BouyguesAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ImageResult implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ActionInvocation invocation) throws Exception {
		BouyguesAction action = (BouyguesAction) invocation.getAction();
		HttpServletResponse respone = ServletActionContext.getResponse();
		if (BouyguesAction.RESULT_LOGO.equals(invocation.getResultCode())) {
			respone.getOutputStream().write(action.getImageLogoInBytes());
		} else {
			respone.getOutputStream().write(action.getImageIconInBytes());
		}
		respone.getOutputStream().flush();
	}
}
