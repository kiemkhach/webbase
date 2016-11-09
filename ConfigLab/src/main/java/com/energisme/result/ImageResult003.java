package com.energisme.result;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.energisme.action.ELeclercAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class ImageResult003 implements Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ActionInvocation invocation) throws Exception {
		// ELeclercAction action = (ELeclercAction) invocation.getAction();
		// HttpServletResponse respone = ServletActionContext.getResponse();
		// respone.getOutputStream().write(action.getImageLogoInBytes());
		// respone.getOutputStream().flush();
	}
}
