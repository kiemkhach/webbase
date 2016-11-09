package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexValueDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

@Repository("idexValueDAO")
@Transactional
public class IdexValueDAOImpl implements IdexValueDAO {

	@Autowired
	private SessionFactory sessionFactory;
	public List<IdexInstallation> getIdexInstallation(Integer keyId, String text) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ii "
				+ " FROM IdexValue iv, IdexInstallation ii "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ii.idexInstallationId ";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId);
		query.setParameter(1, "%" + text.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexInstallation> getIdexInstallation(Integer keyId1, String text1, Integer keyId2, String text2) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ii "
				+ " FROM IdexInstallation ii "
				+ " WHERE EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ii.idexInstallationId ) "
				+ " AND EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ii.idexInstallationId ) ";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId1);
		query.setParameter(1, "%" + text1.toLowerCase() + "%");
		query.setParameter(2, keyId2);
		query.setParameter(3, "%" + text2.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexCounter> getIdexCounter(Integer keyId, String text) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ic "
				+ " FROM IdexValue iv, IdexCounter ic "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND ic.idexCounterId = iv.idexId";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId);
		query.setParameter(1, "%" + text.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexCounter> getIdexCounter(Integer keyId1, String text1, Integer keyId2, String text2) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ic "
				+ " FROM IdexCounter ic "
				+ " WHERE EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ic.idexCounterId ) "
				+ " AND EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ic.idexCounterId ) ";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId1);
		query.setParameter(1, "%" + text1.toLowerCase() + "%");
		query.setParameter(2, keyId2);
		query.setParameter(3, "%" + text2.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexBoiler> getIdexBoiler(Integer keyId, String text) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ib "
				+ " FROM IdexValue iv, IdexBoiler ib "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND ib.idexBoilerId = iv.idexId";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId);
		query.setParameter(1, "%" + text.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexBoiler> getIdexBoiler(Integer keyId1, String text1, Integer keyId2, String text2) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ib "
				+ " FROM IdexBoiler ib "
				+ " WHERE EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ib.idexBoilerId ) "
				+ " AND EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ib.idexBoilerId ) ";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId1);
		query.setParameter(1, "%" + text1.toLowerCase() + "%");
		query.setParameter(2, keyId2);
		query.setParameter(3, "%" + text2.toLowerCase() + "%");
		return query.list();
	}
	
	public List<IdexSite> getIdexSite(Integer keyId,String text){
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ids "
				+ " FROM IdexValue iv, IdexSite ids "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND ids.idexSiteId = iv.idexId";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId);
		query.setParameter(1, "%" + text.toLowerCase() + "%");
		return query.list();
	}
	public List<IdexSite> getIdexSite(Integer keyId1, String text1, Integer keyId2, String text2) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT ids "
				+ " FROM IdexSite ids "
				+ " WHERE EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ids.idexSiteId ) "
				+ " AND EXISTS ( "
				+ " SELECT iv "
				+ " FROM IdexValue iv "
				+ " WHERE iv.idexKeyId = ? "
				+ " AND LOWER(iv.value) LIKE ? "
				+ " AND iv.idexId = ids.idexSiteId ) ";
		Query query = session.createQuery(hql);
		query.setParameter(0, keyId1);
		query.setParameter(1, "%" + text1.toLowerCase() + "%");
		query.setParameter(2, keyId2);
		query.setParameter(3, "%" + text2.toLowerCase() + "%");
		return query.list();
	}
	public List<Object[]> getIdexObject(String text) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT " +" ik.type, "
				+ " ik.name, "
				+ " iv.value, "
				+ " iv.idexId "
				+ " FROM IdexValue iv, IdexKey ik "
				+ " WHERE LOWER(iv.value) LIKE ? "
				+ " AND iv.idexKeyId = ik.idexKeyId";
		Query query = session.createQuery(hql);
		query.setParameter(0, "%" + text.toLowerCase() + "%");
		return query.list();
		
	}
	public List<Object[]> findIdexInstallation(String text) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT " + "     ii.idexInstallationId, "
				+ " 	ii.name, "
				+ " 	ii.lat, "
				+ "  	ii.lng, "
				+ "     ik.name, "
				+ "     iv.value "
				+ " FROM IdexValue iv, IdexInstallation ii, IdexKey ik "
				+ " WHERE LOWER(iv.value) LIKE ? "
				+ " AND ii.idexInstallationId = iv.idexId "
				+ " AND ik.idexKeyId = iv.idexKeyId "
				+ " ORDER BY ii.idexInstallationId ";
		Query query = session.createQuery(hql);
		query.setParameter(0, "%" + text.toLowerCase() + "%");
		return query.list();
	}
	public String findCode(Integer idexId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = " SELECT " + "		iv.value "
								+ " FROM IdexValue iv	"
								+ " WHERE iv.idexKeyId = 2 "
								+ " AND iv.idexId = ? ";
		Query query = session.createQuery(hql);
		query.setParameter(0, idexId);
		return (String) query.list().get(0);
	}
	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexValue c WHERE c.idexId = :idexId "
				+ " AND c.idexKeyId in "
				+ " ( SELECT k.idexKeyId FROM IdexKey k WHERE type = 1 ) ";
		Query query = session.createQuery(hql);
		query.setInteger("idexId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}
}

