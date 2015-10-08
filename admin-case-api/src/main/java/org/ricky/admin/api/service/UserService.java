package org.ricky.admin.api.service;

import java.util.List;

import org.ricky.admin.api.common.CtrlInfo;
import org.ricky.admin.api.pojo.LoginInfo;
import org.ricky.admin.api.pojo.UserPo;

public interface UserService {
    
	UserPo getUserById(CtrlInfo ctrlInfo, int userId);
	
	int AddUser(CtrlInfo ctrlInfo, UserPo user);
	
	int UpdateUser(CtrlInfo ctrlInfo, UserPo user);
	
	UserPo getUserByName(CtrlInfo ctrlInfo, String username);
	
	List<UserPo> getUserList(CtrlInfo ctrlInfo, int page, int pagesize);
	
	int CheckPassword(CtrlInfo ctrlInfo, String username, String rawPassword);
 
}