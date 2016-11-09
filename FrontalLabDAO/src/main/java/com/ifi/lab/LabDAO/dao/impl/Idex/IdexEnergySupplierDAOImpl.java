package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergyType;


@Repository("idexEnergySupplierDAO")
@Transactional
public class IdexEnergySupplierDAOImpl implements IdexEnergySupplierDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<IdexEnergySupplier> findByInstallation(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexEnergySupplier i LEFT JOIN fetch i.idexEnergyType WHERE i.idexInstallation.idexInstallationId = :installationId ORDER BY i.orderBy";
		Query query = session.createQuery(hql);
		query.setInteger("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexEnergySupplier> list = query.list();
		return list;
	}

	public boolean save(IdexEnergySupplier obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "update idex_energy_supplier set logo = :logo where idex_energy_supplier_id = :idex_energy_supplier_id";
		Query query = session.createSQLQuery(hql);
		query.setParameter("logo", obj.getLogo());
		query.setParameter("idex_energy_supplier_id", obj.getIdexEnergySupplierId());
		int rowsAffected = query.executeUpdate();
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}

	public Lab findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab u WHERE u.name = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		List result = query.list();
		if (result != null && result.size() > 0) {
			Lab obj = (Lab) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public List<IdexEnergySupplier> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM  com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexEnergySupplier> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}

	public IdexEnergySupplier getConfigBySite(Integer IdexEnergySupplierId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM idex_energy_supplier c WHERE c.idex_energy_supplier_id = :idex_energy_supplier_id";
		Query query = session.createQuery(hql);
		query.setInteger("idex_site_id", IdexEnergySupplierId);
		@SuppressWarnings("unchecked")
		List<IdexEnergySupplier> listlab = (List<IdexEnergySupplier>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public List<IdexEnergyType> getAllEnergyType(){
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexEnergyType c";
		Query query = session.createQuery(hql);
		return (List<IdexEnergyType>) query.list();
	}

	public IdexEnergySupplier findById(Integer idexEnergySupplierId){
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexEnergySupplier c WHERE c.idexEnergySupplierId = :idexEnergySupplierId";
		Query query = session.createQuery(hql);
		query.setInteger("idexEnergySupplierId", idexEnergySupplierId);
		@SuppressWarnings("unchecked")
		List<IdexEnergySupplier> listlab = (List<IdexEnergySupplier>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexEnergySupplier c WHERE c.idexInstallation.idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}
}
