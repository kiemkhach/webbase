package com.energisme.action;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.UserBean;
import com.energisme.service.LoginService;
import com.ifi.common.csm.UserCSM;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.ws.GetCSMDataAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

public class LoginAction extends AbstractBaseAction implements
		ModelDriven<UserBean> {

	private static final long serialVersionUID = 1L;

	private String token;

	private String siteId;

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

	public String doCheckLogin() {

		GetCSMDataAction csm = new GetCSMDataAction();
		UserCSM userCSM;
		if (token != null) {
			userCSM = csm.checkLoginByToken(token.trim());
		} else {
			String username = userBean.getUserName();
			String password = userBean.getPassword();
			if (username == null || username.equals("")) {
				username = "unknown";
			}
			if (password == null || password.equals("")) {
				password = "unknown";
			}
			userCSM = csm.checkLogin(username, password);
			if (userCSM != null && userCSM.getToken() != null) {
				userCSM = csm.checkLoginByToken(userCSM.getToken());
			}
		}

		if (userCSM == null) {
			return loginFail();
		}

		int siteTmp = loginService.getSiteByUser(userCSM.getUserName());

		if (siteTmp < 0) {
			if (userCSM.getLevel() > 1) {
				List<String> useLst = csm.getListUserCSMByClient(userCSM
						.getClientId());
				boolean isOk = false;
				for (int i = 0; i < useLst.size(); i++) {
					siteTmp = loginService.getSiteByUser(useLst.get(i));
					if (siteTmp >= 0) {
						isOk = true;
						break;
					}
				}
				if (!isOk) {
					return loginFail();
				}
			} else {
				return loginFail();
			}
		}

		session.put(FrontalKey.USER_NAME, userCSM.getUserName());
		if (siteId != null) {
			session.put(FrontalKey.SITE_ID, Integer.parseInt(siteId));
		} else {
			siteId = String.valueOf(siteTmp);
			session.put(FrontalKey.SITE_ID, siteTmp);
		}
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
