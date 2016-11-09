/**
 * 
 */
package com.energisme.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.bean.NumConfigLab008ECS;
import com.energisme.service.Lab008ECSService;
import com.ifi.common.ws.GetCSMDataAction;
import com.ifi.lab.LabDAO.dao.ConfigLab008ECSDAO;
import com.ifi.lab.LabDAO.model.Lab008ECS;

/**
 * @author Kim Anh
 *
 */
public class Lab008ECSServiceImpl implements Lab008ECSService {

//	private static final Logger logger = LoggerFactory.getLogger(Lab008ECSServiceImpl.class);

	@Autowired
	private ConfigLab008ECSDAO configLab008ECSDAO;

	@Override
	public List<Lab008ECS> getListECSBySiteId(Integer siteId) {

		List<Lab008ECS> listECSs = configLab008ECSDAO.getBySubscription(siteId);

		return listECSs;
	}

	@Override
	public List<Lab008ECS> getECSByType(int configlab8Id, int type) {
		List<Lab008ECS> listECSByType = configLab008ECSDAO.getECSByType(configlab8Id, type);
		return listECSByType;
	}

	@Override
	public String saveECSList(NumConfigLab008ECS obj) {
		Lab008ECS ecs = null;
		if (obj.getId() != null) {
			ecs = configLab008ECSDAO.getECSById(obj.getId());
		} else {
			ecs = new Lab008ECS();
		}
		ecs.setConfigLab008V2ID(obj.getConfiglab008v2id());
		String name = obj.getName();
		if (name != null) {
			ecs.setName(name.trim());
		}
		String moduleNoCSM = obj.getModuleNumberCSM();

		GetCSMDataAction csm = new GetCSMDataAction();

		String listModuleCSM = "";

		if (moduleNoCSM != null && !moduleNoCSM.isEmpty()) {
			listModuleCSM = csm.getCalculateModuleByName(moduleNoCSM);
			if (listModuleCSM == null) {
				return "Module is not exists:" + moduleNoCSM;
			}
		}
		ecs.setModuleCSM(listModuleCSM);
		ecs.setType(obj.getType());
		boolean ok = configLab008ECSDAO.saveOrUpdate(ecs);
		if (ok) {
			return null;
		}
		return "Error when insert DB";
	}

	@Override
	public boolean deleteECS(Integer id) {
		return configLab008ECSDAO.delete(id);
	}

	@Override
	public Integer getNewstId() {
		return configLab008ECSDAO.getNewestECS().getId();
	}

	@Override
	public Lab008ECS getECSById(Integer id) {
		Lab008ECS ecs = null;
		ecs = configLab008ECSDAO.getECSById(id);
		return ecs;
	}
}
