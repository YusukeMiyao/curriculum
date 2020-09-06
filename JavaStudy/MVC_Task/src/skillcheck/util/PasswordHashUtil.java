package skillcheck.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import skillcheck.logger.Logger;

/**
 * パスワードハッシュユーティリティ
 *
 * @author y.sato
 * @since 2019/01/02 *
 */
public final class PasswordHashUtil {

    /** パスワードを安全にするためのアルゴリズム */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    /** ストレッチング回数 */
    private static final int ITERATION_COUNT = 10000;
    /** 生成される鍵の長さ */
    private static final int KEY_LENGTH = 256;

    /**
     * 平文のパスワードとソルトから安全なパスワードを生成し返却
     * <pre>※ハッシュ化のため、復号は不可</pre>
     *
     * @param password 平文のパスワード
     * @param salt ソルト
     * @return 安全なパスワード
     */
    public static String getSafetyPassword(String password, String salt) {

        char[] passCharAry = password.toCharArray();
        byte[] hashedSalt = getHashedSalt(salt);

        PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        SecretKey secretKey;
        try {
            secretKey = skf.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        byte[] passByteAry = secretKey.getEncoded();

        // 生成されたバイト配列を16進数の文字列に変換
        StringBuilder sb = new StringBuilder(64);
        for (byte b : passByteAry) {
            sb.append(String.format("%02x", b & 0xff));
        }
        Logger.log(new Throwable(), "HASHED: " + sb.toString());
        return sb.toString();
    }

    /**
     * ソルトをハッシュ化して返却
     * ※ハッシュアルゴリズムはSHA-256を使用
     *
     * @param salt ソルト
     * @return ハッシュ化されたバイト配列のソルト
     */
    private static byte[] getHashedSalt(String salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(salt.getBytes());
        return messageDigest.digest();
    }
}