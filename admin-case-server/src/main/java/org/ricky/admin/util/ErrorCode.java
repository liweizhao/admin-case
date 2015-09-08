package org.ricky.admin.util;

public enum ErrorCode {
    RET_SUCCESS(0), 
    RET_ERR_INVALID_PSW(1001),
	RET_ERR_ACCESS_DB(1003),
	RET_ERR_NO_SUCH_USER(1004);

    private int value = 0;

    private ErrorCode(int value) {  
        this.value = value;
    }

    public int value() {
        return this.value;
    }
			
}
