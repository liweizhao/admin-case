package org.ricky.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RetInfo {
	protected int ret;
	protected String msg;
	
	public RetInfo()
	{
		this.ret = 0;
		this.msg = "ok";
	}
	
	public void WrapException(Exception e)
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bs);
		e.printStackTrace(ps);
		ps.flush();

		this.ret = 1;
		this.msg = bs.toString();
	}

	public int getRetcode() {
		return ret;
	}

	public void setRetcode(int retcode) {
		this.ret = retcode;
	}

	public String getRetMsg() {
		return msg;
	}

	public void setRetMsg(String retMsg) {
		this.msg = retMsg;
	}
	
	public String toJson()
	{
		return "{ret:" + this.ret + ",msg:\"" +  this.msg + "\"}";
	}
}
