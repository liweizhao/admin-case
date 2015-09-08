package org.ricky.admin.dao.service;

import java.util.List;

import org.ricky.admin.ddo.UserDo;
import org.ricky.admin.exception.RickyException;

public interface IUserDaoService {
	public UserDo getUserById(int userId)  throws RickyException;
	
	public UserDo getUserByName(String userName) throws RickyException;
	
	public void addUser(UserDo user) throws RickyException;
	
	public List<UserDo> getUserList(int page, int pagesize) throws RickyException;
}
