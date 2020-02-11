package sjz.sgy.lb.util;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5Util {
	
	public static void main(String[] args) {
		System.out.println(strMd5("123456"));
	}
	public static String strMd5(String str) {
		String md5Hex = DigestUtils.md5Hex(str);
		return md5Hex;
	}
    
}
