package org.ricky.admin.service;

import org.ricky.admin.pojo.LoginInfo;
import org.ricky.admin.pojo.User;

public interface UserService {
    
	User getUserById(int userId);
	
	int AddUser(User user);
	
	int Login(LoginInfo loginInfo);
	
	int CheckLogin();
	
	int UpdateUser(User user);
	
	User getUserByName(String username);
 
}