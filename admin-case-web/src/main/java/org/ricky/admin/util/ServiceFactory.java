package org.ricky.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {
	private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
	
	private static ServiceFactory instance;
	
	private static ClassPathXmlApplicationContext context;
	
	static {
		context = new ClassPathXmlApplicationContext(new String[]
				{"dubboConsumer.xml"});		
        context.start();     	        
	}
	
	private ServiceFactory() {		
	}

	public static ServiceFactory getInstance() {
		 if(instance == null) {
	         synchronized(ServiceFactory.class) {
	        	 ServiceFactory temp = instance;
	            if(temp == null) {
	               temp = new ServiceFactory();
	               instance = temp;
	            }
	         }
		 }
		 return instance;
	}
	
	public Object GetService(String serviceName) {
        Object service = context.getBean(serviceName); // 获取远程服务代理
        logger.info("get service sucessfully");
        return service;
	}

}
