package org.ricky.admin.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.ricky.admin.annotation.ConrollerLogger;
import org.ricky.admin.api.common.CtrlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(ControllerAspect.class);

	//环绕通知方法
    @Around("execution(* org.ricky.admin.controler.*.*(..))")
    public Object doWriteLog(ProceedingJoinPoint pjp) throws Throwable {
 		CtrlInfo ctrlInfo = new CtrlInfo();
 		ctrlInfo.setRemoteIp(Utils.getIpAddr());
 		Utils.setSessionCtrlInfo(ctrlInfo);
        
    	String className = pjp.getThis().toString();
        String methodName = pjp.getSignature().getName();   //获得方法名
        
        logger.info("=====================================" );
        logger.info("====位于：" +className);
        logger.info("====客户端IP:" + ctrlInfo.getRemoteIp());
        logger.info("====调用" +methodName+"方法-开始！");
        Object[] args = pjp.getArgs();  //获得参数列表
         if(args.length <=0){
              logger.info("====" +methodName+"方法没有参数");
        } else{
               for(int i=0; i<args.length; i++){
              logger.info("====参数  " +(i+1)+"："+args[i]);
          }
        }
         
		TimeMonitor timeMonitor = new TimeMonitor();
        try {
        	Object object = pjp.proceed(); // 执行该方法
        	logger.info("====执行成功,耗时:" + timeMonitor.stop() + "ms");
            logger.info("=====================================");
        	return object;
        } catch (Throwable e) {
        	logger.info("====执行异常:[" + e.getMessage() + "], 耗时:" + timeMonitor.stop() + "ms");
            logger.info("=====================================");
            
            RetInfo retInfo = new RetInfo();
            retInfo.setRetcode(1000);
            retInfo.setRetMsg("系统繁忙");            
            return retInfo;
        }      
    }
    
    private class TimeMonitor {
    	public long startTimeMillis;
    	public long stopTimeMillis;
    	
    	public TimeMonitor() {
    		startTimeMillis = System.currentTimeMillis();
    		stopTimeMillis = 0;
    	}
    	
    	public long stop() {
    		stopTimeMillis = System.currentTimeMillis();
    		return getWaste();
    	}
    	
    	public long getWaste() {
    		return stopTimeMillis - startTimeMillis;
    	}
    }

}
