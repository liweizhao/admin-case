package org.ricky.admin.controler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ricky.admin.api.common.CtrlInfo;
import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.api.service.UserService;
import org.ricky.admin.util.RetInfo;
import org.ricky.admin.util.ServiceFactory;
import org.ricky.admin.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add_user", method = RequestMethod.POST)  
	public @ResponseBody RetInfo addUser(HttpServletRequest request) {	
		RetInfo retInfo = new RetInfo();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int role = 0;
		if ("管理员".equals(request.getParameter("role")))
		{ 
			role = 1; 
		} else {
			role = 0; 
		}
		
		logger.info("name is:" + username);
		
        UserPo user = new UserPo();        
        user.setUserName(username);
        user.setPassword(password);
        user.setRole(role);
        
		try
		{
			UserService userService = (UserService)ServiceFactory.getInstance().GetService("UserService");
	        
			CtrlInfo ctrlInfo = new CtrlInfo();
	        int ret = userService.AddUser(ctrlInfo, user);
        
	        logger.info("ret is:" + ret);        
		} 
		catch(Exception e)
		{
			Utils.flushException(logger, e);	
			retInfo.WrapException(e);
		}
	    
	    return retInfo; 
	}
	
	@RequestMapping(value = "/get_user_list", method = RequestMethod.GET)  
	public @ResponseBody Object getUserList(HttpServletRequest request) {	
		
		String sPage = request.getParameter("page");
		String sPageSize = request.getParameter("pagesize");
		
		int page = 0;
		int pagesize = 10; 
		if ((sPage != null) && (sPageSize != null))
		{
			page = Integer.parseInt(sPage);
			pagesize = Integer.parseInt(sPageSize);
		}
		
		logger.info("page is:" + page + ", pagesize:" + pagesize);
		        
		try
		{
			UserService userService = (UserService)ServiceFactory.getInstance().GetService("UserService");
	        
	        CtrlInfo ctrlInfo = new CtrlInfo();
	        List<UserPo> userList = userService.getUserList(ctrlInfo, page, pagesize);
        
	        return userList;  
		} 
		catch(Exception e)
		{
			Utils.flushException(logger, e);	
			return "error";
		}
	}
	
	@RequestMapping(value = "/get_user_info", method = RequestMethod.GET)  
	public @ResponseBody Object getUserInfo(HttpServletRequest request) {	
		return "";
	}
	
}
