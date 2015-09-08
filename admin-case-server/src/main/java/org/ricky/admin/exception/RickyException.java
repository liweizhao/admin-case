package org.ricky.admin.exception;

public class RickyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5263443445159383537L;
	
	protected int errCode;
	
	public RickyException(int errCode, String errMsg)
	{
		super(errMsg);
		this.errCode = errCode;
	}
}
