package com.energisme.interceptor;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LocaleInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;

	private static final String LOCALE_PARAMETER = "locale";

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionMapping mapping = (ActionMapping) invocation
				.getInvocationContext()
				.get(ServletActionContext.ACTION_MAPPING);
		System.out.println("ABCCCCCC:"
				+ invocation.getInvocationContext().getLocale());
		ActionContext.getContext().setLocale(
				invocation.getInvocationContext().getLocale());

		return invocation.invoke();
	}
}
