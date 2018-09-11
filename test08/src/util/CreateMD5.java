package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateMD5 {

	// 静态方法，便于做工具类
	public static String getMD5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 加盐
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int c;

			StringBuffer buf = new StringBuffer("");
			for (int i = 0; i < b.length; i++) {
				c = b[i];
				if (c < 0)
					c += 256;

				if (c < 16)
					buf.append("0");
				buf.append(Integer.toHexString(c));
			}
			// 32位加密
			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 实验
	public static void main(String[] args) {
		String s = CreateMD5.getMD5("123456");
		System.out.println(s);
	}

}
