package org.ricky.admin.provider;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ricky.admin.dao.service.IUserDaoService;
import org.ricky.admin.ddo.UserDo;
import org.ricky.admin.exception.RickyException;
import org.ricky.admin.util.EncryptUtil;
import org.ricky.admin.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.ricky.admin.api.pojo.LoginInfo;
import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.api.common.CtrlInfo;
import org.ricky.admin.api.service.UserService;

public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
    private IUserDaoService userDaoService; 

	public UserPo getUserById(CtrlInfo ctrlInfo, int userId) {
		try {
			UserDo userDo = this.userDaoService.getUserById(userId); 
			userDo.setPassword("");
			
			UserPo userPo = new UserPo();
			BeanUtils.copyProperties(userDo, userPo);
			
			return userPo;
		} catch (Exception e)
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			return null;
		}
	}

	public int AddUser(CtrlInfo ctrlInfo, UserPo userPo) {
		try {
			String passwordEncrypted = EncryptUtil.encrypt(userPo.getPassword());
			userPo.setPassword(passwordEncrypted);
			
			UserDo userDo = new UserDo();
			BeanUtils.copyProperties(userPo, userDo);
			
			this.userDaoService.addUser(userDo);	
			return 0;
		} catch (Exception e)
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			return 1;
		}
	}

	public int Login(CtrlInfo ctrlInfo, LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		return 1;
	}

	public int UpdateUser(CtrlInfo ctrlInfo, UserPo user) {
		// TODO Auto-generated method stub
		return 1;
	}

	public UserPo getUserByName(CtrlInfo ctrlInfo, String username) {
		try {
			UserDo userDo = this.userDaoService.getUserByName(username);
			userDo.setPassword("");
			
			UserPo userPo = new UserPo();
			BeanUtils.copyProperties(userDo, userPo);
			
			return userPo;
		} catch (Exception e)
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			return null;
		}
	}

	public List<UserPo> getUserList(CtrlInfo ctrlInfo, int page, int pagesize) {
		try {
			List<UserDo> userDoList = this.userDaoService.getUserList(page, pagesize);
			List<UserPo> userPoList = new ArrayList<UserPo>();
			for (UserDo userDo : userDoList)
			{
				UserPo userPo = new UserPo();
				BeanUtils.copyProperties(userDo, userPo);
				userPoList.add(userPo);
			}
			return userPoList;
		} 
		catch (Exception e)
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			return null;
		}
	}

	public int CheckPassword(CtrlInfo ctrlInfo, String username, String rawPassword) {
		try
		{
			UserDo user = this.userDaoService.getUserByName(username);
			String passwd = user.getPassword();
        
	        // 验证加密后的密码  
			logger.info("raw:'" + rawPassword + "', psw:'" +passwd + "'");
	        if (!EncryptUtil.match(rawPassword, passwd)) {  
	            return ErrorCode.RET_ERR_INVALID_PSW.value();
	        } 
		} catch (RickyException e)
		{
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			return ErrorCode.RET_ERR_NO_SUCH_USER.value();
		}
        
        return ErrorCode.RET_SUCCESS.value();
	}
	
}
