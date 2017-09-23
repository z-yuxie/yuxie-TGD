/*
 * 文件名：SessionUtils.java
 * 版权： Copyright 2014-2017 北京思源政务通科技有限公司.
 * 描述：〈描述〉
 * 修改人：137127
 * 修改时间：2017年3月7日
 * 修改单号：2017年3月7日
 * 修改内容：〈修改内容〉
 */
package com.yuxie.tgd.common.util;


/**
 * 向ThreadLocal保存或从中获取SessionInfo
 * @author    147356
 * @version   [1.0.0, 2017年3月7日]
 */
public class SessionUtils {
	
	public static ThreadLocal<String> sessionHolder = new ThreadLocal<String>();

	public static void removeSessionId(){
		 sessionHolder.remove();
	}
	
	public static String getSessionId(){
		 return sessionHolder.get();
	}
	
	public static void setSessionId(String sessionId){
		sessionHolder.set(sessionId);
	}
}
