package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.User;

public interface UserDAO {
	public boolean save(User obj);
	public boolean saveAll(List<User> listObj);
	public boolean update(User obj);
	public boolean delete(int userId);
	public List<User> getAllUser();
	public User findById(int id);
	public User findByUserName(String username);
	public List<String> getUsersInLab(String labName, Integer siteId);
}
