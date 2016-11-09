package com.ifi.lab.LabDAO.dao.impl.caloon;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.caloon.CaloonUserBean;
import com.ifi.lab.LabDAO.dao.caloon.CaloonUserDAO;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;

@Repository("caloonUserDAO")
@Transactional
public class CaloonUserDAOImpl implements CaloonUserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public CaloonUserBean checkLogin(String userName, String password) {
		Session session = sessionFactory.getCurrentSession();
		CaloonUserBean caloonUserBean = new CaloonUserBean();
		String hql = "FROM CaloonUser u WHERE u.userName = :userName AND u.password = :password ";
		Query query = session.createQuery(hql);
		query.setString("userName", userName);
		query.setString("password", password);
		@SuppressWarnings("unchecked")
		List<CaloonUser> listlab = (List<CaloonUser>) query.list();
		if (listlab != null && listlab.size() > 0) {
			caloonUserBean.setId(listlab.get(0).getId());
			caloonUserBean.setUserName(listlab.get(0).getUserName());
			caloonUserBean.setPassword("");
			caloonUserBean.setFirstName(listlab.get(0).getFirstName());
			caloonUserBean.setLastName(listlab.get(0).getLastName());
			caloonUserBean.setStatus(listlab.get(0).getStatus());
//			caloonUserBean.setCaloonResidentId(listlab.get(0).getCaloonResidentId());
//			caloonUserBean.setCaloonSyndicId(listlab.get(0).getCaloonSyndicId());
		}
		return caloonUserBean;
	}

	public Integer createUser(CaloonUser obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}

	public List<CaloonUser> getAllUser() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonUser";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonUser> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public CaloonUser findUserById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonUser c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<CaloonUser> listlab = (List<CaloonUser>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public Boolean saveOrUpdate(CaloonUser obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		// LOGGER.info("Insert ConfigPerial success");
		return true;
	}

	public Boolean deleteUserCaloon(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM CaloonUser c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		if (query.executeUpdate() < 0) {
			return false;
		}
		return true;
	}

	public List<CaloonUser> getUserResident() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonUser c WHERE c.status = 1";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonUser> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public List<CaloonUser> getUserSyndic() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonUser c WHERE c.status = 0";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonUser> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

}
