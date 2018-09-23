package com.alphasta.common.core.util;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * �ַ����
 * @author huojun
 *
 */
public class StringSecurity {

	private final static String DES = "DES";

	/**
	 * ����
	 * 
	 * @param src
	 *            ���Դ
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * @return ���ؼ��ܺ�����
	 * @throws Exception
	 */

	private static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES�㷨Ҫ����һ������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ���ݴ���DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);
		// ����һ���ܳ׹�����Ȼ�������DESKeySpecת����һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɼ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// ���ڣ���ȡ��ݲ�����
		// ��ʽִ�м��ܲ���
		return cipher.doFinal(src);
	}

	/**
	 * ����
	 * 
	 * @param src
	 *            ���Դ
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * @return ���ؽ��ܺ��ԭʼ���
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES�㷨Ҫ����һ������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);
		// ����һ���ܳ׹�����Ȼ�������DESKeySpec����ת����һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// ���ڣ���ȡ��ݲ�����
		// ��ʽִ�н��ܲ���
		return cipher.doFinal(src);
	}

	/**
	 * ��ݽ���
	 * 
	 * @param data
	 *            �������
	 * @param key
	 *            ��Կ
	 * @return ԭʼ���
	 * @throws Exception
	 */
	public final static String decrypt(String data, String key) {
		try {
			return new String(
					decrypt(hex2byte(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ݼ���
	 * 
	 * @param data
	 *            ԭʼ���
	 * @param key
	 *            ��Կ
	 * @return �������
	 * @throws Exception
	 */

	public final static String encrypt(String data, String key) {
		try {
			return byte2hex(encrypt(data.getBytes(), key.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������ת�ַ�
	 * 
	 * @param b
	 * @return
	 */

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("���Ȳ���ż��");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	public final static String MD5 = "MD5";
	public final static String SHA1 = "SHA-1";
	public final static String SHA256 = "SHA-256";

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * ��ݼ��ܣ�Ĭ��ʹ��MD5�㷨
	 * 
	 * @param data
	 *            ԭʼ���
	 * @return ����
	 */
	public static String encode(String data) {
		String resultString = null;
		try {
			resultString = new String(data);
			MessageDigest md = MessageDigest.getInstance(MD5);
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString.toUpperCase();
	}

	/**
	 * ��ݼ���
	 * 
	 * @param data
	 *            ԭʼ���
	 * @param encType
	 *            ��������
	 * @return ����
	 */
	public static String encode(String data, String encType) {
		String resultString = null;
		try {
			resultString = new String(data);
			MessageDigest md = MessageDigest.getInstance(encType);
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString.toUpperCase();
	}
}
