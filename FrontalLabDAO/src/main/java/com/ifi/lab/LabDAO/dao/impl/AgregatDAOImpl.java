package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.AgregatDAO;
import com.ifi.lab.LabDAO.model.Agregat;

@Repository("agregatDAO")
@Transactional
public class AgregatDAOImpl implements AgregatDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AgregatDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ifi.lab.LabDAO.dao.AgregatDAO#save(com.ifi.lab.LabDAO.model.Agregat)
	 */
	public boolean save(Agregat obj) {
		// Session session = null;
		// Transaction tx = null;
		// try {
		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		session.persist(obj);
//		tx.commit();
		LOGGER.info("Insert Agregat success");
		return true;
		// } catch (Exception e) {
		// tx.rollback();
		// LOGGER.debug("Insert error transaction rollback");
		// return false;
		// }finally{
		// session.close();
		// }
	}

	public boolean saveList(List<Agregat> lst) {
//		Session session = null;
//		Transaction tx = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			for (Agregat a : lst) {
				session.persist(a);
			}
//			tx.commit();
			LOGGER.info("Insert Agregat success");
			return true;
//		} catch (Exception e) {
//			tx.rollback();
//			LOGGER.debug("Insert error transaction rollback");
//			return false;
//		} finally {
//			session.close();
//		}
	}
}
