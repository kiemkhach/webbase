package com.energisme.action;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.UserBean;
import com.energisme.service.CaloonService;
import com.energisme.service.LoginService;
import com.energisme.util.Constant;
import com.google.gson.Gson;
import com.ifi.common.bean.Idex.IdexConstant.IdexConstant;
import com.ifi.common.bean.caloon.CaloonConstant;
import com.ifi.common.bean.caloon.CaloonResidentBean;
import com.ifi.common.bean.caloon.CaloonSyndicBean;
import com.ifi.common.bean.caloon.CaloonUserBean;
import com.ifi.common.util.FrontalKey;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

public class LoginAction extends AbstractBaseAction implements ModelDriven<UserBean> {

	private static final long serialVersionUID = 1L;

	private String token;

	private Integer status;
	private Integer caloonSyndicId;

	private CaloonUserBean caloonUserBean;

	@Autowired
	private UserBean userBean;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CaloonService caloonService;

	private String mess;

	@SuppressWarnings({ "unused", "resource" })
	@PostConstruct
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
	}

	public String execute() throws Exception {

		ActionContext.getContext().setLocale(getServletRequest().getLocale());
		session.put(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, getServletRequest().getLocale());
		logOut();
		return Constant.LOGIN_FORM;
	}

	public String logOut() throws Exception {
		session.clear();
		return SUCCESS;
	}

	public String doCheckLogin() {
		session.put(FrontalKey.USER_NAME, userBean.getUserName());
		Gson gSon = new Gson();
		String jsonCaloonUser = loginService.getCaloonUserStatusByUser(userBean.getUserName(), userBean.getPassword());
		if (jsonCaloonUser != null) {
			try {
				caloonUserBean = gSon.fromJson(jsonCaloonUser, CaloonUserBean.class);
				caloonSyndicId = caloonUserBean.getCaloonSyndicId();
				session.put(FrontalKey.CALOON_SYNDIC_ID, caloonSyndicId);
				session.put(FrontalKey.CALOON_RESIDENT_ID, caloonUserBean.getCaloonResidentId());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			status = caloonUserBean.getStatus();
			session.put(FrontalKey.OBJECT_CALOON_USER, caloonUserBean);
			
			String fullName = "";
			if(caloonUserBean.getFirstName() != null){
				fullName +=caloonUserBean.getFirstName();
				fullName += " ";
			}
			if(caloonUserBean.getLastName() != null){
				fullName +=caloonUserBean.getLastName();
			}
			session.put(CaloonConstant.FULL_NAME_KEY, fullName);
			session.put(CaloonConstant.FIRST_NAME_KEY, caloonUserBean.getFirstName());
			session.put(CaloonConstant.LAST_NAME_KEY, caloonUserBean.getLastName());
			
			if (status != null) {
				if (status.intValue() == 0) {
					//syndic site
					session.put(CaloonConstant.ADDRESS_NAME, "Adresse immeuble");
					
					CaloonSyndicBean bean = caloonService.getCaloonSyndicBean(caloonUserBean.getCaloonSyndicId());
					
					String address = "";
					if(bean != null && bean.getAddress() != null){
						address = bean.getAddress();
					}
					session.put(CaloonConstant.ADDRESS_VALUE, address);
					session.put(CaloonConstant.POSTAL_CODE, bean.getCodePostal());
					session.put(CaloonConstant.CITY, bean.getVille());
					
					String day = caloonService.getLastDaySyndic(caloonUserBean.getCaloonSyndicId());
					if(day == null){
						day = "--";
					}
					session.put(CaloonConstant.LAST_DAY_DATA, day);
					
					return Constant.SYNDIC_SITE;
				}
				
				session.put(CaloonConstant.ADDRESS_NAME, "Votre numéro de logement");
				
				CaloonResidentBean bean = caloonService.getCaloonResidentBean(caloonUserBean.getCaloonResidentId());
				
				String address = "";
				if(bean != null & bean.getAppartmentNumber() != null){
					address = bean.getAppartmentNumber();
				}
				session.put(CaloonConstant.ADDRESS_VALUE, address);
				
				String day = caloonService.getLastDayResident(caloonUserBean.getCaloonResidentId());
				if(day == null){
					day = "--";
				}
				session.put(CaloonConstant.LAST_DAY_DATA, day);
				
				// resident site
				return Constant.RESIDENCE_SITE;
			}
		}
		
		
		return Constant.ERROR;
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

	public Integer getCaloonSyndicId() {
		return caloonSyndicId;
	}

	public void setCaloonSyndicId(Integer caloonSyndicId) {
		this.caloonSyndicId = caloonSyndicId;
	}
	
}
