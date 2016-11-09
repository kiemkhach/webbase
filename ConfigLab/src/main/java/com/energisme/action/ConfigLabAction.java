package com.energisme.action;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.ConfigLabService;
import com.ifi.lab.LabDAO.model.Lab;

public class ConfigLabAction extends AbstractBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Lab> listLabs;
	private String jsonString;
	private Integer labId;
	private Lab lab;
	
	@Autowired
	private ConfigLabService configLabService;
	
	@Override
	public String execute() throws Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		listLabs = configLabService.getAllConfigLabs();
		return SUCCESS;
	}
	
	public String getLabById(){
		if (labId != null) {
			Lab obj = configLabService.getConfigLabById(labId);
			if (obj != null) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					jsonString = mapper.writeValueAsString(obj);
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}
	
	public String updateLab(){
		boolean isSaved = false;
		if (lab != null) {
			isSaved = configLabService.update(lab);
		}
		if (isSaved) {
            jsonString = "{\"result\":\"success\"}";
        } else {
            jsonString = "{\"result\":\"failed\"}";
        }
		return SUCCESS;
	}
	
	public String saveLab(){
		boolean isSaved = false;
		if (lab != null) {
			isSaved = configLabService.save(lab);
		}
		if (isSaved) {
            jsonString = "{\"result\":\"success\"}";
        } else {
            jsonString = "{\"result\":\"failed\"}";
        }
		return SUCCESS;
	}
	
	public String deleteLab(){
		boolean isDeleted = false;
		if (labId != null) {
			isDeleted = configLabService.delete(labId);
		}
        if (isDeleted) {
            jsonString = "{\"result\":\"success\"}";
        } else {
            jsonString = "{\"result\":\"failed\"}";
        }
        return SUCCESS;
	}
	
	public List<Lab> getListLabs() {
		return listLabs;
	}

	public void setListLabs(List<Lab> listLabs) {
		this.listLabs = listLabs;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public Integer getLabId() {
		return labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}
	
}
