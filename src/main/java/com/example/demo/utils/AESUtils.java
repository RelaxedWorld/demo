package com.example.demo.utils;

import com.example.demo.constants.BaseConstants;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
public class AESUtils {
    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */

    public static byte[] encrypt(String content, String password) {
        try {
            long start = System.currentTimeMillis();

            Cipher cipher = Cipher.getInstance(BaseConstants.AES_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes(BaseConstants.UTF8);

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密
            long end = System.currentTimeMillis();
            log.info("AES加密字符串耗时：{}ms", end - start);
            return result;

        } catch (Exception e) {
            log.error("AES加密字符串异常", e);
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            long start = System.currentTimeMillis();
            Cipher cipher = Cipher.getInstance(BaseConstants.AES_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            long end = System.currentTimeMillis();
            log.info("AES解密字符串耗时：{}ms", end - start);
            return result; // 明文
        } catch (Exception e) {
            log.error("AES解密字符串异常", e);
        }
        return null;
    }

    /**
     * 生成AES加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(BaseConstants.AES_ALGORITHM);//返回生成指定算法密钥生成器的 KeyGenerator 对象
            kgen.init(128, new SecureRandom(password.getBytes()));//AES 要求密钥长度为 128
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            return new SecretKeySpec(enCodeFormat, BaseConstants.AES_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException e) {
            log.error("生成AES加密秘钥异常", e);
        }
        return null;
    }
}
