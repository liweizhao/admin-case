package org.ricky.admin.security;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import org.ricky.admin.api.pojo.UserPo;
import org.ricky.admin.api.service.UserService;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);
    
	public LoginAuthenticationProvider() {
        super();
    }

    // API

    @Override
    public Authentication authenticate(final Authentication authentication) 
    		throws AuthenticationException {
    	final String name = authentication.getName();
        
		try
		{
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]
					{"dubboConsumer.xml"});
			
	        context.start();
	        	        
	        UserService userService = (UserService)context.getBean("UserService"); // 获取远程服务代理
	        logger.info("get service sucessfully");
	        
	        final String rawPassword = (String) authentication.getCredentials();  
	       
	        logger.info("raw:'" + rawPassword );
	        
	        int iRet = userService.CheckPassword(name, rawPassword); // 执行远程方法
	        
	        logger.info("##########check password Ret:" + iRet);
	        
	        if (iRet != 0)
	        {
	        	throw new BadCredentialsException("invalid password");
	        }	 
	        
	        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
	        
	        UserPo userPo = userService.getUserByName(name);
	        if (userPo != null)
	        {
		        if (userPo.getRole() == 0)
		        {
		        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		        } else if (userPo.getRole() == 1) {
		        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		        } 
	        }
	        
	        Authentication authen = new UsernamePasswordAuthenticationToken(
	        		name, rawPassword, grantedAuths);

	        return authen;
		} 
		catch(Exception e)
		{
			logger.error(e.toString());
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bs);
			e.printStackTrace(ps);
			ps.flush();
			logger.error(bs.toString());
			return null;
		}
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}