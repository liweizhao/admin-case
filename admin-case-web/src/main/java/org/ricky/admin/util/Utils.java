package org.ricky.admin.util;

import java.util.HashMap;
import java.util.Map;

public class Utils {
	public static Map<String, String> GetDefaultResultMap()
	{
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put("retcode", "0");
		retMap.put("retmsg", "ok");
		return retMap;
	}
}
