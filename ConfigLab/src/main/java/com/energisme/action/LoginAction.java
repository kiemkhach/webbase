package com.energisme.action;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.energisme.bean.UserBean;
import com.energisme.service.LoginService;
import com.ifi.common.util.FrontalKey;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

public class LoginAction extends AbstractBaseAction implements
		ModelDriven<UserBean> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserBean userBean;

	@Autowired
	private LoginService loginService;

	private String mess;

	@SuppressWarnings({ "unused", "resource" })
	@PostConstruct
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"constantConfig.xml");
	}

	public String execute() throws Exception {

		ActionContext.getContext().setLocale(getServletRequest().getLocale());
		session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE,
				getServletRequest().getLocale());
		logOut();
		return "loginForm";
	}

	public String logOut() throws Exception {
		session.clear();
		return SUCCESS;
	}

	public String home() {
		return SUCCESS;
	}

	public String doCheckLogin() {
		if (!loginService.checkLogin(userBean.getUserName(),
				userBean.getPassword())) {
			return loginFail();
		}
		session.put(FrontalKey.USER_NAME, userBean.getUserName());
		return SUCCESS;
	}

	private String loginFail() {
		mess = ERROR;
		return mess;
	}

	@Override
	public UserBean getModel() {
		return userBean;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

}
