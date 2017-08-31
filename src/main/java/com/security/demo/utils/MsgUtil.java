package com.security.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class MsgUtil {

  public static Map<String, String> getMsg(String msg){
	Map<String, String> result = new HashMap<>();
	result.put("result", msg);

	return result;
  }
}
