package org.ricky.admin.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{
    
	private static final Logger logger = LoggerFactory.getLogger(LoginFormAuthenticationFilter.class);
     
 
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, 
    		Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
    	logger.info("onLoginSuccess");
    	
        HttpServletRequest rq = (HttpServletRequest)request;  
        HttpServletResponse rs = (HttpServletResponse)response;  
        Cookie cookie = new SimpleCookie("sid");  
        cookie.setValue("1");
        cookie.saveTo(rq, rs);
        
        return false;
    }

}
