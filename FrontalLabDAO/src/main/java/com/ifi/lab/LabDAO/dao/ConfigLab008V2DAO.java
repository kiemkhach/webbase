package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab008ECS;

/**
 * Created by Kim Anh
 */
public interface ConfigLab008V2DAO {
	ConfigLab008V2 findBySiteId(Integer siteId);
	List<Lab008ECS> findECSBySiteId(Integer configLab008V2ID);
	List<Lab008ECS> getAllECSBySiteId(Integer siteId);
	boolean delete(Integer id);
	Integer getMaxSiteId();
	public ConfigLab008V2 findById(int id);
	public boolean saveOrUpdate(ConfigLab008V2 obj);
	public List<ConfigLab008V2> getAllConfig();
	List<Lab008ECS> findECSBySiteIdAndType(Integer siteId,Integer type);
	List<Lab008ECS> findECSBySiteIdAndType(Integer siteId,List<Integer> typeLst);
	Integer getBoilType (Integer siteId);
	List<ConfigLab008V2> getConfigByUserExcept(String userName,int configLabID);
	List<Lab008ECS> findECSByType(Integer type);
}
