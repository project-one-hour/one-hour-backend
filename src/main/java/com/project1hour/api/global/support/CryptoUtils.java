package com.project1hour.api.global.support;

import com.project1hour.api.global.advice.ErrorCode;
import com.project1hour.api.global.advice.InfraStructureException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoUtils {

    public static String encrypt(String plainText) {
        IvParameterSpec ivParameterSpec = CryptoContext.getIvParameterSpec();
        SecretKeySpec secretKeySpec = CryptoContext.getSecretKeySpec();
        Cipher cipher = CryptoContext.getCipher();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("암호화 중 알 수 없는 오류가 발생했습니다. ");
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String cipherText) {
        IvParameterSpec ivParameterSpec = CryptoContext.getIvParameterSpec();
        SecretKeySpec secretKeySpec = CryptoContext.getSecretKeySpec();
        Cipher cipher = CryptoContext.getCipher();
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decoded = Base64.getDecoder().decode(cipherText.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new InfraStructureException("잘못된 입력값입니다. 입력 데이터를 확인하세요.", ErrorCode.INVALID_DATA);
        } catch (Exception e) {
            log.error("복호화 중 알 수 없는 오류가 발생했습니다. ");
            throw new RuntimeException(e);
        }
    }

    @Component
    private static class CryptoContext {

        private static final String ALGORITHM = "AES";
        private static final String INSTANCE_TYPE = ALGORITHM + "/CBC/PKCS5Padding";

        private static IvParameterSpec ivParameterSpec;
        private static SecretKeySpec secretKeySpec;
        private static Cipher cipher;

        public CryptoContext(@Value("${crypto.key}") final String secretKey,
                             @Value("${crypto.iv}") final String initializationVector)
                throws NoSuchPaddingException, NoSuchAlgorithmException {
            ivParameterSpec = new IvParameterSpec(initializationVector.getBytes());
            secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher = Cipher.getInstance(INSTANCE_TYPE);
        }

        private static IvParameterSpec getIvParameterSpec() {
            return ivParameterSpec;
        }

        private static SecretKeySpec getSecretKeySpec() {
            return secretKeySpec;
        }

        private static Cipher getCipher() {
            return cipher;
        }
    }
}
