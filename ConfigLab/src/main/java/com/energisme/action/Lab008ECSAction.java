package com.energisme.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab008ECS;
import com.energisme.service.Lab008ECSService;
import com.energisme.util.ConvertObject;
import com.google.gson.Gson;
import com.ifi.lab.LabDAO.model.Lab008ECS;

/**
 * Created by Kim Anh
 */
public class Lab008ECSAction extends AbstractBaseAction {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	// private static final Logger LOGGER =
	// Logger.getLogger(Lab008ECSAction.class);

	private int ecsId;
	private boolean isCreate;

	@Autowired
	private Lab008ECSService lab008ECSService;

	private NumConfigLab008ECS ecs;

	private String jsonString;

	public String getECSById() {
		Lab008ECS ecs = lab008ECSService.getECSById(ecsId);
		ConvertObject convertObject = new ConvertObject();
		NumConfigLab008ECS numEcs = convertObject.convertIdtoModuleNumber(ecs);
		Gson gson = new Gson();
		jsonString = gson.toJson(numEcs);
		return SUCCESS;
	}

	public String deleteECS() {
		boolean isDeleted = lab008ECSService.deleteECS(ecsId);
		if (isDeleted) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"failed\"}";
		}
		return SUCCESS;
	}

	public String saveECS() {
		String msg = lab008ECSService.saveECSList(ecs);
		if (msg == null) {
			jsonString = "{\"result\":\"success\"}";
		} else {
			jsonString = "{\"result\":\"" + msg + "\"}";
		}
		return SUCCESS;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean create) {
		isCreate = create;
	}

	public int getEcsId() {
		return ecsId;
	}

	public void setEcsId(int ecsId) {
		this.ecsId = ecsId;
	}

	public Lab008ECSService getLab008ECSService() {
		return lab008ECSService;
	}

	public void setLab008ECSService(Lab008ECSService lab008ecsService) {
		lab008ECSService = lab008ecsService;
	}

	public NumConfigLab008ECS getEcs() {
		return ecs;
	}

	public void setEcs(NumConfigLab008ECS ecs) {
		this.ecs = ecs;
	}

}
