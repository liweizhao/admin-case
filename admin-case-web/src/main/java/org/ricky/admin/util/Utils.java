package org.ricky.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.ricky.admin.api.common.CtrlInfo;
import org.slf4j.Logger;

public class Utils {
	public static String CTRLINFO_KEY = "CTRL_INFO";
	
	public static Map<String, String> GetDefaultResultMap()
	{
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("retcode", "0");
		retMap.put("retmsg", "ok");
		return retMap;
	}
	
	public static String getIpAddr() {
		ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();   
		return getIpAddr((HttpServletRequest)request);
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			// get first ip from proxy ip
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}
	
	public static void flushException(Logger logger, Exception e) {
		logger.error(e.toString());
		
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bs);
		e.printStackTrace(ps);
		ps.flush();
		
		logger.error(bs.toString());
	}
	
	public static HttpSession getCurrentSession() {
		ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();   
		HttpSession httpSession = ((HttpServletRequest)request).getSession(); 
		return httpSession;
	}
	
	public static CtrlInfo getSessionCtrlInfo() {
		return (CtrlInfo)getCurrentSession().getAttribute(CTRLINFO_KEY);
	}
	
	public static void setSessionCtrlInfo(CtrlInfo ctrlInfo) {
		HttpSession httpSession = getCurrentSession();
		httpSession.setAttribute(CTRLINFO_KEY, ctrlInfo);
	}
}
