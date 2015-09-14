package org.ricky.admin.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter{
    
    protected final Logger logger = Logger.getLogger(LoginFormAuthenticationFilter.class);
     
 
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
         
        subject.getSession().setAttribute(SecurityUtils.getSubject()., subject.getPrincipal());    //设置用户身份进session属性
         
        logger.info("用户 "+ SecurityUtils.getShiroUser(subject).toString() + " 登陆成功");
         
        String url = this.getSuccessUrl();
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);    //页面跳转
        return false;
    }

}
