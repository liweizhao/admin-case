package org.ricky.admin.dao.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ricky.admin.dao.mapper.UserCustomMapper;
import org.ricky.admin.dao.mapper.UserDoMapper;
import org.ricky.admin.dao.service.IUserDaoService;
import org.ricky.admin.ddo.UserDo;
import org.ricky.admin.ddo.UserDoExample;
import org.ricky.admin.exception.RickyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("userDaoService")
public class UserDaoServiceImpl implements IUserDaoService {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoServiceImpl.class);
	
	@Resource
	private UserDoMapper userDao;
	
	@Resource
	private UserCustomMapper userCustomDao;
	
	//@Override
	public UserDo getUserById(int userId)  throws RickyException {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

	public UserDo getUserByName(String userName) throws RickyException {
		// TODO Auto-generated method stub
		UserDoExample example = new UserDoExample();
		example.createCriteria().andUserNameEqualTo(userName);
		List<UserDo> userList = this.userDao.selectByExample(example);
		if (userList.size() == 0)
		{
			throw new RickyException(1, "no such user");
		}
		
		if (userList.size() > 1)
		{
			throw new RickyException(1, "more than one user found");
		}
		
		return userList.get(0);
	}

	public void addUser(UserDo user) throws RickyException {
		logger.info("addUser");
		
		int affected = this.userDao.insert(user);
		if (affected != 1)
		{
			throw new RickyException(1, "insert user record failed");
		}
	}

	public List<UserDo> getUserList(int page, int pagesize) throws RickyException {
		logger.info("getUserList");
		
		int limit = pagesize;
		int offset = page * pagesize;
		
		List<UserDo> userList = this.userCustomDao.selectUserList(limit, offset);
		
		return userList;
	}

}
