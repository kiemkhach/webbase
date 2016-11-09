package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Bouygues;

/**
 * Interface define a BouyguesDAO
 * @author Hoang Minh Tri
 *
 */
public interface BouyguesDAO {
	public boolean save(Bouygues obj);
	public boolean update(Bouygues obj);
	public boolean delete(int id);
	public List<Bouygues> getAllBouygues();
	public Bouygues findById(int id);
	public Bouygues findBySiteId(int siteId);
	public boolean saveOrUpdate(Bouygues obj);
	public Integer getMaxSiteId();
}
