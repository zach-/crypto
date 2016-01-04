import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @version 1.0
 * @author Zach Bricker
 */
public class crypto {
	public static String encrypt(String value) {
		String encryptedString = null;
		try {

			byte[] raw = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
			Key skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = new byte[cipher.getBlockSize()];

			IvParameterSpec ivParams = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParams);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			// System.out.println("encrypted string length:" +
			// encrypted.length);
			encryptedString = Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encryptedString;
	}

	public static String decrypt(String base64String) {
		byte[] encrypted = Base64.decodeBase64(base64String);
		String originalString = null;
		try {
			byte[] raw = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
			Key key = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// the block size (in bytes), or 0 if the underlying algorithm is
			// not a block cipher
			byte[] ivByte = new byte[cipher.getBlockSize()];
			// This class specifies an initialization vector (IV). Examples
			// which use
			// IVs are ciphers in feedback mode, e.g., DES in CBC mode and RSA
			// ciphers with OAEP encoding operation.
			IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
			cipher.init(Cipher.DECRYPT_MODE, key, ivParamsSpec);
			byte[] decryptedData = cipher.doFinal(encrypted);
			originalString = new String(decryptedData, "UTF8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return originalString;
	}

	private static void printUsage() {
		System.out.println("Invalid arguments passed.");
		System.out.println("Usage: java crypto [ENCRYPT|DECRYPT] [String to encrypt or decrypt]");
		System.exit(1);
	}

	public static void main(String args[]) {
		if (args.length < 2) {
			printUsage();
		}
		if ("ENCRYPT".equalsIgnoreCase(args[0])) {
			String encryptedString = encrypt(args[1]);
			System.out.println("Encrypted base64 string is: " + encryptedString);
		} else if ("DECRYPT".equalsIgnoreCase(args[0])) {
			String decryptedString = decrypt(args[1]);
			System.out.println("Decrypted String is: " + decryptedString);
		} else {
			printUsage();
		}
	}
}
