package org.ricky.admin.controler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.annotation.Before;
import org.ricky.admin.api.common.CtrlInfo;
import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.model.LoginBean;
import org.ricky.admin.util.RetInfo;
import org.ricky.admin.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody RetInfo login(@RequestBody LoginBean loginBean, 
			HttpServletRequest request) {
		RetInfo retInfo = new RetInfo();
				
		Subject user = SecurityUtils.getSubject();		
        UsernamePasswordToken token = new UsernamePasswordToken(loginBean.getUsername(), loginBean.getPassword());
        token.setRememberMe(true);
        
        try {
            user.login(token);            	
            return retInfo;
        } catch (AuthenticationException e) {
        	logger.error("登录失败错误信息:"+e.getMessage());
            token.clear();
            
            retInfo.setRetcode(1001);
            retInfo.setRetMsg("登陆失败");
            
            return retInfo;
        }
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)  
	public String logout(HttpServletRequest request) {	
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		
		return "redirect:/public/login.html";
	}
}
