package org.ricky.admin.realm;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.ricky.admin.api.common.CtrlInfo;
import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.api.service.UserService;
import org.ricky.admin.controler.UserController;
import org.ricky.admin.util.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdminCaseRealm extends AuthorizingRealm {  
	
	private static final Logger logger = LoggerFactory.getLogger(AdminCaseRealm.class);
	
    /** 
     * 为当前登录的Subject授予角色和权限 
     * @see  经测试:本例中该方法的调用时机为需授权资源被访问时 
     * @see  经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
    	logger.info("doGetAuthorizationInfo"); 
    	
        try
		{
	        UserService userService = (UserService)ServiceFactory.getInstance().GetService("UserService");
        
	        List<String> roles = new ArrayList<String>();  
	        Set<String> permissions = new HashSet<String>();
	        
	        String name = (String) getAvailablePrincipal(principals);
	        
	        CtrlInfo ctrlInfo = new CtrlInfo();
	        UserPo userPo = userService.getUserByName(ctrlInfo, name);
	        if (userPo != null)
	        {
	        	if (userPo.getRole() == 1)
	        	{
	        		logger.error("###admin login");
	        		roles.add("admin");
	        		permissions.add("admin:manage");
	        	}
	        	else
	        	{
	        		roles.add("member");
	        	}
		        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
		        // 增加角色  
		        info.addRoles(roles); 
		        info.addStringPermissions(permissions);
		        return info;	
	        }
		} 
		catch(Exception e)
		{
			logger.error(e.toString());
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			throw new AuthorizationException();  
		}
    	
        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址  
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置  
        return null;  
    }  
   
       
    /** 
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(
    		AuthenticationToken authcToken) throws AuthenticationException { 
    	logger.info("doGetAuthenticationInfo");  
    	
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
    	
        try
		{
        	UserService userService = (UserService)ServiceFactory.getInstance().GetService("UserService");
	        	        
	        CtrlInfo ctrlInfo = new CtrlInfo();
	        int iRet = userService.CheckPassword(ctrlInfo, 
	        		token.getUsername(), new String(token.getPassword())); // 执行远程方法
	        
	        logger.info("##########check password Ret:" + iRet);
	        
	        if (iRet != 0)
	        {
	        	throw new AuthorizationException("invalid password");
	        }
		} 
		catch(Exception e)
		{
			logger.error(e.toString());
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			
			throw new AuthorizationException();  
		}
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
        		token.getUsername(), token.getPassword(), getName()); 

        return info; 
     
    }  
       
       
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    protected void setSession(Object key, Object value){  
    	logger.info("setSession");  
    	
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  
}
