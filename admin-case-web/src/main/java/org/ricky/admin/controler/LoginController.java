package org.ricky.admin.controler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.util.RetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)  
	public String login(HttpServletRequest request) {	
		RetInfo retInfo = new RetInfo();
		
		logger.info("login2");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Subject user = SecurityUtils.getSubject();		
        UsernamePasswordToken token = new UsernamePasswordToken(
        		username, password);
        token.setRememberMe(true);
        
        try {
            user.login(token);
            
            String url = "/protected/index.html";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null)
            {
                String tmpUrl = savedRequest.getRequestUrl();
                if (null != tmpUrl && "" != tmpUrl)
                	url = tmpUrl;
            }
            	
            return "redirect:" + url;
        }catch (AuthenticationException e) {
        	logger.error("登录失败错误信息:"+e);
            token.clear();
            return "redirect:/public/login.html";
        }
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)  
	public String logout(HttpServletRequest request) {	
		logger.error("logout");
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		
		return "redirect:/public/login.html";
	}
}
