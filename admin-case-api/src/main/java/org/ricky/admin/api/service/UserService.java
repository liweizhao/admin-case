package org.ricky.admin.api.service;

import java.util.List;

import org.ricky.admin.api.pojo.LoginInfo;
import org.ricky.admin.api.pojo.UserPo;

public interface UserService {
    
	UserPo getUserById(int userId);
	
	int AddUser(UserPo user);
	
	int UpdateUser(UserPo user);
	
	UserPo getUserByName(String username);
	
	List<UserPo> getUserList(int page, int pagesize);
	
	int CheckPassword(String username, String rawPassword);
 
}