package com.energisme.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.bean.NumConfigLab008V2;
import com.energisme.service.ConstantService;
import com.energisme.service.Lab008ECSService;
import com.energisme.service.Lab008V2Service;
import com.energisme.service.LabService;
import com.energisme.util.ConvertObject;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Lab008ECS;
import com.opensymphony.xwork2.ModelDriven;

public class Lab008V2Action extends AbstractBaseAction implements ModelDriven<NumConfigLab008V2> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Lab008V2Service lab008V2Service;

	@Autowired
	private LabService labService;

	@Autowired
	private NumConfigLab008V2 numConfigLab008V2;

	@Autowired
	private Lab008ECSService lab008ECSService;

	@Autowired
	private ConstantService constantService;

	private String username_lab_service;

	private ConfigLab008V2 configLab008V2;

	private String mes;

	private ConvertObject co = new ConvertObject();

	private Integer siteId;

	private List<ConfigLab008V2> listAllConfig = new ArrayList<ConfigLab008V2>();
	private Lab lab;
	private List<String> listUsers;

	private String listUser;
	public boolean isCreate;

	@Override
	public NumConfigLab008V2 getModel() {
		return numConfigLab008V2;
	}

	public String redirect() {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (numConfigLab008V2.getSiteId() != null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String siteTmp = request.getParameter("siteId");
			if (siteTmp == null || siteTmp.isEmpty()) {
				siteId = (Integer) session.get(FrontalKey.SITE_ID_008_V2);
			} else {
				siteId = Integer.parseInt(request.getParameter("siteId"));
			}

			session.put(FrontalKey.SITE_ID_008_V2, siteId);
			configLab008V2 = lab008V2Service.getConfigLab008BySite(siteId);
			numConfigLab008V2 = co.convertIdtoMuduleNumber(configLab008V2);
			List<Lab008ECS> lab008ConsommationChauffageLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					1);
			if (lab008ConsommationChauffageLst != null && !lab008ConsommationChauffageLst.isEmpty()) {
				numConfigLab008V2.setLab008ConsommationChauffageLst(lab008ConsommationChauffageLst);
			}
			List<Lab008ECS> lab008ConsommationECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 2);
			if (lab008ConsommationECSLst != null && !lab008ConsommationECSLst.isEmpty()) {
				numConfigLab008V2.setLab008ConsommationECSLst(lab008ConsommationECSLst);
			}
			List<Lab008ECS> lab008TemperatureAmbianteLogementLst = lab008ECSService
					.getECSByType(numConfigLab008V2.getId(), 3);
			if (lab008TemperatureAmbianteLogementLst != null && !lab008TemperatureAmbianteLogementLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureAmbianteLogementLst(lab008TemperatureAmbianteLogementLst);
			}
			List<Lab008ECS> lab008TemperatureProductionChauffageLst = lab008ECSService
					.getECSByType(numConfigLab008V2.getId(), 4);
			if (lab008TemperatureProductionChauffageLst != null && !lab008TemperatureProductionChauffageLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureProductionChauffageLst(lab008TemperatureProductionChauffageLst);
			}
			List<Lab008ECS> lab008TemperatureProductionECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					5);
			if (lab008TemperatureProductionECSLst != null && !lab008TemperatureProductionECSLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureProductionECSLst(lab008TemperatureProductionECSLst);
			}
			List<Lab008ECS> lab008TemperatureRecyclageECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					6);
			if (lab008TemperatureRecyclageECSLst != null && !lab008TemperatureRecyclageECSLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureRecyclageECSLst(lab008TemperatureRecyclageECSLst);
			}

			List<Lab008ECS> moduleWaterLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 7);
			if (moduleWaterLst != null && !moduleWaterLst.isEmpty()) {
				numConfigLab008V2.setModuleWaterLst(moduleWaterLst);
			}
			List<Lab008ECS> moduleVentilationLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 8);
			if (moduleVentilationLst != null && !moduleVentilationLst.isEmpty()) {
				numConfigLab008V2.setModuleVentilationLst(moduleVentilationLst);
			}
			listAllConfig = lab008V2Service.getAllConfigLab008V2();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		} else {
			listAllConfig = lab008V2Service.getAllConfigLab008V2();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab008V2 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				List<Lab008ECS> lab008ConsommationChauffageLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 1);
				if (lab008ConsommationChauffageLst != null && !lab008ConsommationChauffageLst.isEmpty()) {
					numConfigLab008V2.setLab008ConsommationChauffageLst(lab008ConsommationChauffageLst);
				}
				List<Lab008ECS> lab008ConsommationECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 2);
				if (lab008ConsommationECSLst != null && !lab008ConsommationECSLst.isEmpty()) {
					numConfigLab008V2.setLab008ConsommationECSLst(lab008ConsommationECSLst);
				}
				List<Lab008ECS> lab008TemperatureAmbianteLogementLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 3);
				if (lab008TemperatureAmbianteLogementLst != null && !lab008TemperatureAmbianteLogementLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureAmbianteLogementLst(lab008TemperatureAmbianteLogementLst);
				}
				List<Lab008ECS> lab008TemperatureProductionChauffageLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 4);
				if (lab008TemperatureProductionChauffageLst != null
						&& !lab008TemperatureProductionChauffageLst.isEmpty()) {
					numConfigLab008V2
							.setLab008TemperatureProductionChauffageLst(lab008TemperatureProductionChauffageLst);
				}
				List<Lab008ECS> lab008TemperatureProductionECSLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 5);
				if (lab008TemperatureProductionECSLst != null && !lab008TemperatureProductionECSLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureProductionECSLst(lab008TemperatureProductionECSLst);
				}
				List<Lab008ECS> lab008TemperatureRecyclageECSLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 6);
				if (lab008TemperatureRecyclageECSLst != null && !lab008TemperatureRecyclageECSLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureRecyclageECSLst(lab008TemperatureRecyclageECSLst);
				}
				List<Lab008ECS> moduleWaterLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 7);
				if (moduleWaterLst != null && !moduleWaterLst.isEmpty()) {
					numConfigLab008V2.setModuleWaterLst(moduleWaterLst);
				}
				List<Lab008ECS> moduleVentilationLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 8);
				if (moduleVentilationLst != null && !moduleVentilationLst.isEmpty()) {
					numConfigLab008V2.setModuleVentilationLst(moduleVentilationLst);
				}
				siteId = Integer.parseInt(numConfigLab008V2.getSiteId());
				session.put(FrontalKey.SITE_ID_008_V2, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			}
		}
//		if (!isCreate) {
//			isCreate = false;
//		}

		return SUCCESS;
	}

	public String create() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		numConfigLab008V2 = new NumConfigLab008V2();
		isCreate = true;
		return SUCCESS;
	}

	public String execute() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		siteId = Integer.parseInt(request.getParameter("siteId"));
		session.put(FrontalKey.SITE_ID_008_V2, siteId);
		configLab008V2 = lab008V2Service.getConfigLab008BySite(siteId);
		numConfigLab008V2 = co.convertIdtoMuduleNumber(configLab008V2);
		/*
		 * List<Lab008ECS> listECSs =
		 * lab008ECSService.getListECSBySiteId(numConfigLab008V2.getId()); if
		 * (listECSs != null && !listECSs.isEmpty()) {
		 * numConfigLab008V2.setLab008ConsommationECSLst(listECSs); }
		 */
		List<Lab008ECS> lab008ConsommationChauffageLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 1);
		if (lab008ConsommationChauffageLst != null && !lab008ConsommationChauffageLst.isEmpty()) {
			numConfigLab008V2.setLab008ConsommationChauffageLst(lab008ConsommationChauffageLst);
		}
		List<Lab008ECS> lab008ConsommationECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 2);
		if (lab008ConsommationECSLst != null && !lab008ConsommationECSLst.isEmpty()) {
			numConfigLab008V2.setLab008ConsommationECSLst(lab008ConsommationECSLst);
		}
		List<Lab008ECS> lab008TemperatureAmbianteLogementLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
				3);
		if (lab008TemperatureAmbianteLogementLst != null && !lab008TemperatureAmbianteLogementLst.isEmpty()) {
			numConfigLab008V2.setLab008TemperatureAmbianteLogementLst(lab008TemperatureAmbianteLogementLst);
		}
		List<Lab008ECS> lab008TemperatureProductionChauffageLst = lab008ECSService
				.getECSByType(numConfigLab008V2.getId(), 4);
		if (lab008TemperatureProductionChauffageLst != null && !lab008TemperatureProductionChauffageLst.isEmpty()) {
			numConfigLab008V2.setLab008TemperatureProductionChauffageLst(lab008TemperatureProductionChauffageLst);
		}
		List<Lab008ECS> lab008TemperatureProductionECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 5);
		if (lab008TemperatureProductionECSLst != null && !lab008TemperatureProductionECSLst.isEmpty()) {
			numConfigLab008V2.setLab008TemperatureProductionECSLst(lab008TemperatureProductionECSLst);
		}
		List<Lab008ECS> lab008TemperatureRecyclageECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 6);
		if (lab008TemperatureRecyclageECSLst != null && !lab008TemperatureRecyclageECSLst.isEmpty()) {
			numConfigLab008V2.setLab008TemperatureRecyclageECSLst(lab008TemperatureRecyclageECSLst);
		}

		List<Lab008ECS> moduleWaterLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 7);
		if (moduleWaterLst != null && !moduleWaterLst.isEmpty()) {
			numConfigLab008V2.setModuleWaterLst(moduleWaterLst);
		}
		List<Lab008ECS> moduleVentilationLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 8);
		if (moduleVentilationLst != null && !moduleVentilationLst.isEmpty()) {
			numConfigLab008V2.setModuleVentilationLst(moduleVentilationLst);
		}
		listAllConfig = lab008V2Service.getAllConfigLab008V2();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public String save() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (isCreate) {
			mes = lab008V2Service.saveConfig(numConfigLab008V2);
			if (mes.equals(SUCCESS)) {
				listAllConfig = lab008V2Service.getAllConfigLab008V2();
				numConfigLab008V2 = co.convertIdtoMuduleNumber(listAllConfig.get(listAllConfig.size() - 1));
				/*
				 * List<Lab008ECS> listECSs =
				 * lab008ECSService.getListECSBySiteId(numConfigLab008V2.getId()
				 * ); if (listECSs != null && !listECSs.isEmpty()) {
				 * numConfigLab008V2.setLab008ConsommationECSLst(listECSs); }
				 */
				List<Lab008ECS> lab008ConsommationChauffageLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 1);
				if (lab008ConsommationChauffageLst != null && !lab008ConsommationChauffageLst.isEmpty()) {
					numConfigLab008V2.setLab008ConsommationChauffageLst(lab008ConsommationChauffageLst);
				}
				List<Lab008ECS> lab008ConsommationECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 2);
				if (lab008ConsommationECSLst != null && !lab008ConsommationECSLst.isEmpty()) {
					numConfigLab008V2.setLab008ConsommationECSLst(lab008ConsommationECSLst);
				}
				List<Lab008ECS> lab008TemperatureAmbianteLogementLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 3);
				if (lab008TemperatureAmbianteLogementLst != null && !lab008TemperatureAmbianteLogementLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureAmbianteLogementLst(lab008TemperatureAmbianteLogementLst);
				}
				List<Lab008ECS> lab008TemperatureProductionChauffageLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 4);
				if (lab008TemperatureProductionChauffageLst != null
						&& !lab008TemperatureProductionChauffageLst.isEmpty()) {
					numConfigLab008V2
							.setLab008TemperatureProductionChauffageLst(lab008TemperatureProductionChauffageLst);
				}
				List<Lab008ECS> lab008TemperatureProductionECSLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 5);
				if (lab008TemperatureProductionECSLst != null && !lab008TemperatureProductionECSLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureProductionECSLst(lab008TemperatureProductionECSLst);
				}
				List<Lab008ECS> lab008TemperatureRecyclageECSLst = lab008ECSService
						.getECSByType(numConfigLab008V2.getId(), 6);
				if (lab008TemperatureRecyclageECSLst != null && !lab008TemperatureRecyclageECSLst.isEmpty()) {
					numConfigLab008V2.setLab008TemperatureRecyclageECSLst(lab008TemperatureRecyclageECSLst);
				}
				List<Lab008ECS> moduleWaterLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 7);
				if (moduleWaterLst != null && !moduleWaterLst.isEmpty()) {
					numConfigLab008V2.setModuleWaterLst(moduleWaterLst);
				}
				List<Lab008ECS> moduleVentilationLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 8);
				if (moduleVentilationLst != null && !moduleVentilationLst.isEmpty()) {
					numConfigLab008V2.setModuleVentilationLst(moduleVentilationLst);
				}
				siteId = Integer.parseInt(numConfigLab008V2.getSiteId());
				session.put(FrontalKey.SITE_ID_008_V2, siteId);
				// add default user
				labService.registerUserForLab(lab.getName(), siteId.toString(), username_lab_service);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
				isCreate = false;
			} else {
				lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
				isCreate = true;
				return ERROR;
			}
		} else {
			try {
				// if(numConfigLab008V2.getSiteId() != null){
				siteId = Integer.parseInt(numConfigLab008V2.getSiteId());
				// }else{
				// String site =
				// session.get(FrontalKey.SITE_ID_008_V2).toString();
				// siteId = Integer.parseInt(site);
				// }
			} catch (NumberFormatException nfe) {
				mes = "SiteID must a number:" + numConfigLab008V2.getSiteId();
			}
			isCreate = false;

			String oldSiteId = session.get(FrontalKey.SITE_ID_008_V2).toString();
			if (mes == null) {
				mes = lab008V2Service.saveConfig(numConfigLab008V2);
				if (mes.equals(SUCCESS)) {
					if (!siteId.equals(oldSiteId))
						labService.updateSiteId(lab.getId(), Integer.parseInt(oldSiteId), siteId);
					session.put(FrontalKey.SITE_ID_008_V2, siteId);
				} else {
					siteId = Integer.parseInt(oldSiteId);
				}
			} else {
				siteId = Integer.parseInt(oldSiteId);
			}
			/*
			 * List<Lab008ECS> listECSs =
			 * lab008ECSService.getListECSBySiteId(numConfigLab008V2.getId());
			 * if (listECSs != null && !listECSs.isEmpty()) {
			 * numConfigLab008V2.setLab008ConsommationECSLst(listECSs); }
			 */
			List<Lab008ECS> lab008ConsommationChauffageLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					1);
			if (lab008ConsommationChauffageLst != null && !lab008ConsommationChauffageLst.isEmpty()) {
				numConfigLab008V2.setLab008ConsommationChauffageLst(lab008ConsommationChauffageLst);
			}
			List<Lab008ECS> lab008ConsommationECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 2);
			if (lab008ConsommationECSLst != null && !lab008ConsommationECSLst.isEmpty()) {
				numConfigLab008V2.setLab008ConsommationECSLst(lab008ConsommationECSLst);
			}
			List<Lab008ECS> lab008TemperatureAmbianteLogementLst = lab008ECSService
					.getECSByType(numConfigLab008V2.getId(), 3);
			if (lab008TemperatureAmbianteLogementLst != null && !lab008TemperatureAmbianteLogementLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureAmbianteLogementLst(lab008TemperatureAmbianteLogementLst);
			}
			List<Lab008ECS> lab008TemperatureProductionChauffageLst = lab008ECSService
					.getECSByType(numConfigLab008V2.getId(), 4);
			if (lab008TemperatureProductionChauffageLst != null && !lab008TemperatureProductionChauffageLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureProductionChauffageLst(lab008TemperatureProductionChauffageLst);
			}
			List<Lab008ECS> lab008TemperatureProductionECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					5);
			if (lab008TemperatureProductionECSLst != null && !lab008TemperatureProductionECSLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureProductionECSLst(lab008TemperatureProductionECSLst);
			}
			List<Lab008ECS> lab008TemperatureRecyclageECSLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(),
					6);
			if (lab008TemperatureRecyclageECSLst != null && !lab008TemperatureRecyclageECSLst.isEmpty()) {
				numConfigLab008V2.setLab008TemperatureRecyclageECSLst(lab008TemperatureRecyclageECSLst);
			}
			List<Lab008ECS> moduleWaterLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 7);
			if (moduleWaterLst != null && !moduleWaterLst.isEmpty()) {
				numConfigLab008V2.setModuleWaterLst(moduleWaterLst);
			}
			List<Lab008ECS> moduleVentilationLst = lab008ECSService.getECSByType(numConfigLab008V2.getId(), 8);
			if (moduleVentilationLst != null && !moduleVentilationLst.isEmpty()) {
				numConfigLab008V2.setModuleVentilationLst(moduleVentilationLst);
			}
			numConfigLab008V2.setSiteId(String.valueOf((siteId)));
			listAllConfig = lab008V2Service.getAllConfigLab008V2();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		}
		if (siteId != null) {
			session.put(FrontalKey.SITE_ID_008_V2, siteId);
		}
		return SUCCESS;
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		mes = lab008V2Service.deleteConfig(id);
		siteId = Integer.parseInt(request.getParameter("siteId"));
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		if (mes.equals(SUCCESS)) {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
			labService.deleteUserForSite(lab.getId(), siteId);
			listAllConfig = lab008V2Service.getAllConfigLab008V2();
			if (listAllConfig != null && listAllConfig.size() > 0) {
				numConfigLab008V2 = co.convertIdtoMuduleNumber(listAllConfig.get(0));
				siteId = Integer.parseInt(numConfigLab008V2.getSiteId());
				session.put(FrontalKey.SITE_ID_008_V2, siteId);
				listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			}
			isCreate = false;
		} else {
			lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
			session.put(FrontalKey.SITE_ID_008_V2, siteId);
			configLab008V2 = lab008V2Service.getConfigLab008BySite(siteId);
			numConfigLab008V2 = co.convertIdtoMuduleNumber(configLab008V2);
			listAllConfig = lab008V2Service.getAllConfigLab008V2();
			listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
			isCreate = false;
		}
		return SUCCESS;
	}

	public String registerUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String listUserRegist = request.getParameter("listUserRegist");
		siteId = (Integer) session.get(FrontalKey.SITE_ID_008_V2);
		mes = labService.registerUserForLab(lab.getName(), siteId.toString(), listUserRegist);
		configLab008V2 = lab008V2Service.getConfigLab008BySite(siteId);
		numConfigLab008V2 = co.convertIdtoMuduleNumber(configLab008V2);
		listAllConfig = lab008V2Service.getAllConfigLab008V2();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public String unregisterUser() {
		lab = labService.getLabinfo(FrontalKey.LAB_NAME_008);
		username_lab_service = constantService.getConstantValue(FrontalKey.LAB_SERVICE_ID,
				FrontalKey.LAB_SERVICE_KEY_USERNAME);
		siteId = (Integer) session.get(FrontalKey.SITE_ID_008_V2);
		mes = labService.unregisterUserForLab(lab.getName(), siteId.toString(), listUser);
		configLab008V2 = lab008V2Service.getConfigLab008BySite(siteId);
		numConfigLab008V2 = co.convertIdtoMuduleNumber(configLab008V2);
		listAllConfig = lab008V2Service.getAllConfigLab008V2();
		listUsers = labService.getUsersInLab(lab.getName(), siteId.toString());
		isCreate = false;
		return SUCCESS;
	}

	public LabService getLabService() {
		return labService;
	}

	public void setLabService(LabService labService) {
		this.labService = labService;
	}

	public ConstantService getConstantService() {
		return constantService;
	}

	public void setConstantService(ConstantService constantService) {
		this.constantService = constantService;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUsername_lab_service() {
		return username_lab_service;
	}

	public void setUsername_lab_service(String username_lab_service) {
		this.username_lab_service = username_lab_service;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public String getListUser() {
		return listUser;
	}

	public void setListUser(String listUser) {
		this.listUser = listUser;
	}

	public List<String> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<String> listUsers) {
		this.listUsers = listUsers;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private String filename;

	public void setFileFileName(String filename) {
		this.filename = filename;
	}

	public Lab008V2Service getLab008V2Service() {
		return lab008V2Service;
	}

	public void setLab008V2Service(Lab008V2Service lab008v2Service) {
		lab008V2Service = lab008v2Service;
	}

	public NumConfigLab008V2 getNumConfigLab008V2() {
		return numConfigLab008V2;
	}

	public void setNumConfigLab008V2(NumConfigLab008V2 numConfigLab008V2) {
		this.numConfigLab008V2 = numConfigLab008V2;
	}

	public ConfigLab008V2 getConfigLab008V2() {
		return configLab008V2;
	}

	public void setConfigLab008V2(ConfigLab008V2 configLab008V2) {
		this.configLab008V2 = configLab008V2;
	}

	public List<ConfigLab008V2> getListAllConfig() {
		return listAllConfig;
	}

	public void setListAllConfig(List<ConfigLab008V2> listAllConfig) {
		this.listAllConfig = listAllConfig;
	}

}
