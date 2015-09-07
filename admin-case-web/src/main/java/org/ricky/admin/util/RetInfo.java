package org.ricky.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RetInfo {
	protected int retcode;
	protected String retMsg;
	
	public RetInfo()
	{
		this.retcode = 0;
		this.retMsg = "ok";
	}
	
	public void WrapException(Exception e)
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bs);
		e.printStackTrace(ps);
		ps.flush();

		this.retcode = 1;
		this.retMsg = bs.toString();
	}
}
