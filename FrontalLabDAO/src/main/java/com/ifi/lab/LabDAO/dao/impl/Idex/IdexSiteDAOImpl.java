package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.Idex.InstallationOneExelRowData;
import com.ifi.common.bean.Idex.NodeInfo;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

@Repository("idexSiteDAO")
@Transactional
public class IdexSiteDAOImpl implements IdexSiteDAO {
	@Autowired
	private SessionFactory sessionFactory;
	public List<IdexSite> getListSite() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM  com.ifi.lab.LabDAO.model.Idex.IdexSite WHERE idexInstallationId = 1";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexSite> list = query.list();

		return list;
	}

	public List<Integer> getListCompteurBySite(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT idex_counter_id FROM  idex_counter_site WHERE idex_site_id =:siteId";
		Query query = session.createSQLQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<Integer> list = query.list();

		return list;
	}

	public NodeInfo getInstallationById(Integer installationId) {
		NodeInfo result = new NodeInfo();

		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT iK.idex_key_id, iK.`name`, iV.`value`, iV.`comment` from idex_key iK INNER JOIN idex_value iV ON iK.idex_key_id = iV.idex_key_id where iK.type='1' and iV.idex_i =:value";
		Query query = session.createSQLQuery(sql);
		if (installationId == null || installationId <= 0) {
			installationId = 1;
		}
		query.setInteger("value", installationId);

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		if (rows != null && rows.size() > 0) {
			List<InstallationOneExelRowData> listRow = new ArrayList<InstallationOneExelRowData>();

			for (Object[] row : rows) {
				InstallationOneExelRowData oneRow = new InstallationOneExelRowData();
				oneRow.setRowIndex(Integer.parseInt(row[0].toString().trim()));
				oneRow.setValue(row[1].toString());
				oneRow.setComment(row[2].toString());
				
				listRow.add(oneRow);
			}
		}

		return result;
	}

	public boolean addInstallation(IdexInstallation installation) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(installation);

		return true;
	}

	public String[] getInstallationCommentById(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT comment FROM com.ifi.lab.LabDAO.model.Idex.IdexValue WHERE idexId =:value";
		Query query = session.createQuery(hql);
		if (installationId == null || installationId <= 0) {
			installationId = 1;
		}
		query.setInteger("value", installationId);

		@SuppressWarnings("unchecked")
		List<String> list = query.list();
		if (list != null && list.size() > 0) {
			String[] comments = new String[list.size()];
			int i = 0;
			for (String s : list) {
				comments[i] = s;
				i++;
			}
			return comments;
		}

		return null;
	}

	public List<IdexSite> findByInstallation(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexSite i WHERE i.idexInstallation.idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexSite> list = query.list();

		return list;
	}

	public List<IdexSite> getListSite(Integer siteId) {
		// TODO Auto-generated method stub
		return null;
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

	public List<IdexSite> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM  com.ifi.lab.LabDAO.model.Idex.IdexSite";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexSite> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}

	public boolean save(IdexSite obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "update idex_site set logo = :logo where idex_site_id = :idex_site_id";
		Query query = session.createSQLQuery(hql);
		query.setParameter("logo", obj.getLogo());
		query.setParameter("idex_site_id", obj.getIdexSiteId());
		int rowsAffected = query.executeUpdate();
		if (rowsAffected > 0) {
			return true;
		}
		return false;
	}

	public Long getNumberSite() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM IdexSite ";
		Query query  =session.createQuery(hql);
		return (Long) query.uniqueResult();
	}
	public IdexSite getConfigBySite(Integer idexSiteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM idex_site c WHERE c.idex_site_id = :idexSiteId";
		Query query = session.createQuery(hql);
		query.setInteger("idex_site_id", idexSiteId);
		@SuppressWarnings("unchecked")
		List<IdexSite> listlab = (List<IdexSite>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public IdexSite findById(Integer idexSiteId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexSite i WHERE i.idexSiteId = :idexSiteId";
		Query query = session.createQuery(hql);
		query.setInteger("idexSiteId", idexSiteId);
		@SuppressWarnings("unchecked")
		List<IdexSite> list = query.list();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexSite c WHERE c.idexInstallation.idexInstallationId = :idexInstallationId";
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
