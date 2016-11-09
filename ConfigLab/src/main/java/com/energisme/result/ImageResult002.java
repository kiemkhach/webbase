package com.energisme.result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.energisme.action.BouyguesAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ImageResult002 implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ActionInvocation invocation) throws Exception {
		// BouyguesAction action = (BouyguesAction) invocation.getAction();
		// HttpServletResponse respone = ServletActionContext.getResponse();
		// respone.getOutputStream().write(action.getImageLogoInBytes());
		// respone.getOutputStream().flush();
	}
}
