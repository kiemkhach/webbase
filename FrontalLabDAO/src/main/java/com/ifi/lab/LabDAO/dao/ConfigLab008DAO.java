package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.ConfigLab008;

/**
 * Created by hmtri on 2/29/2016.
 */
public interface ConfigLab008DAO {
//    boolean update(ConfigLab008 obj);
//    boolean delete(Integer id);
//    Integer create(ConfigLab008 obj);
//    ConfigLab008 findById(Integer id);
    ConfigLab008 findBySiteId(Integer siteId);
    public List<ConfigLab008> getAllConfig();
//	public boolean saveOrUpdate(ConfigLab008 obj);
//	public Integer getMaxSiteId();
}
