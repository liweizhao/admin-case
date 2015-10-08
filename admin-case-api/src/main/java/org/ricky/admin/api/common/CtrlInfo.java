package org.ricky.admin.api.common;

import java.io.Serializable;

public class CtrlInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String uin;
	protected String token;
	protected String remoteIp;
	
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRemoteIp() {
		return remoteIp;
	}
	
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
}
