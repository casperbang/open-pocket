package com.bangbits;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 * Utils wrapping SHA512 hashing and AES256 cipher
 * 
 * CBC: Each block of plaintext is XORed with the previous ciphertext block before being 
 * encrypted. This way, each ciphertext block is dependent on all plaintext blocks processed 
 * up to that point. Also, to make each message unique, an initialization vector must be used 
 * in the first block.
 *
 * IV: An initialization vector (IV) is a block of bits that is used by several modes to randomize 
 * the encryption and hence to produce distinct ciphertexts even if the same plaintext is encrypted 
 * multiple times, without the need for a slower re-keying process.
 * 
 */
public class CryptUtils {

    private static final String HEXSTRING = "0123456789ABCDEF";

    // Used for IV seed and cipher
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    public static final int IV_BYTE_LENGTH = 16;

    // Used by cipher
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    private static final int PBE_KEY_LENGTH = 256;
    public static final int PBE_ITERATION_COUNT = 100;
    
    public static final int SALT_BYTE_LENGTH = 20;
    public static final int SHA512_BYTE_LENGTH = 64;
    //private static final String KEY_ALGORITHM = "AES";
    
   
    // Even when directly using bouncycastle light-weight stuff, apparently we
    // still need to register as security provider
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public String decrypt(String password, String salt, String encString) {
        try {
            byte[] ivData = toByte(encString.substring(0, IV_BYTE_LENGTH << 1));
            byte[] encData = toByte(encString.substring(IV_BYTE_LENGTH << 1));

            // Get raw key from password and salt
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), toByte(salt), PBE_ITERATION_COUNT, PBE_KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(keyFactory.generateSecret(pbeKeySpec).getEncoded(), CIPHER_ALGORITHM);
            byte[] key = secretKey.getEncoded();

            // Setup cipher parameters with key and IV
            CipherParameters params = new ParametersWithIV(new KeyParameter(key), ivData);

            // Setup AES cipher in CBC mode with PKCS7 padding
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher( new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
            cipher.reset();
            cipher.init(false, params);

            // Create a temporary buffer to decode into (it'll include padding)
            byte[] buf = new byte[cipher.getOutputSize(encData.length)];
            int len = cipher.processBytes(encData, 0, encData.length, buf, 0);
            len += cipher.doFinal(buf, len);

            // Remove padding
            byte[] out = new byte[len];
            System.arraycopy(buf, 0, out, 0, len);

            // Return string representation of decoded bytes
            return new String(out, "UTF-8");
        } catch (NoSuchAlgorithmException ex) {             // Holy fuck, Java's checked exception madness!
            throw new CryptException(ex.getMessage());
        } catch (InvalidKeySpecException ex) {
            throw new CryptException(ex.getMessage());
        } catch (InvalidCipherTextException ex) {
            throw new CryptException(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            throw new CryptException(ex.getMessage());
        }
    }

    /*
    private byte[] createIV() throws NoSuchAlgorithmException {
        SecureRandom localSecureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
        byte[] arrayOfByte = new byte[IV_BYTE_LENGTH];
        localSecureRandom.nextBytes(arrayOfByte);
        return arrayOfByte;
    }*/
    
    /*
    public String encrypt(SecretKey paramSecretKey, String paramString) {
        try {
            byte[] arrayOfByte1 = createIV();
            String str1 = toHex(arrayOfByte1);
            IvParameterSpec localIvParameterSpec = new IvParameterSpec(arrayOfByte1);
            Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            localCipher.init(1, paramSecretKey, localIvParameterSpec);
            byte[] arrayOfByte2 = paramString.getBytes();
            String str2 = toHex(localCipher.doFinal(arrayOfByte2));
            String str3 = String.valueOf(str1);
            String str4 = str3 + str2;
            return str4;
        } catch (Exception ex) {
            throw new CryptException("Unable to encrypt");
        }
    }*/

    /*
    public String generateSalt()
            throws CryptException {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
            byte[] arrayOfByte = new byte[SALT_BYTE_LENGTH];
            localSecureRandom.nextBytes(arrayOfByte);
            String str = toHex(arrayOfByte);
            return str;
        } catch (Exception ex) {
            throw new CryptException("Unable to generate salt");
        }
    }*/

    public String getHash(String stringToHash, String salt) {
        try {
            String composite = String.valueOf(stringToHash) + salt;
            byte[] compositeArrayOfByte = composite.getBytes();

            SHA512Digest md = new SHA512Digest();
            md.update(compositeArrayOfByte, 0, compositeArrayOfByte.length);
            byte[] result = new byte[SHA512_BYTE_LENGTH];
            md.doFinal(result, 0);

            return toHex(result);
        } catch (Exception ex) {
            throw new CryptException("Unable to get hash");
        }
    }

    /*
    public SecretKey getSecretKey(String password, String salt) {
        try {
            PBEKeySpec localPBEKeySpec = new PBEKeySpec(password.toCharArray(), HexEncoder.toByte(salt), PBE_ITERATION_COUNT, PBE_KEY_LENGTH);
            SecretKey secretKey = SecretKeyFactory.getInstance(PBE_ALGORITHM).generateSecret(localPBEKeySpec);
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }*/

    public static byte[] toByte(String paramString) {
        int i = paramString.length() >> 1;
        byte[] arrayOfByte = new byte[i];
        int j = 0;
        while (j < i) {
            
            int k = j << 1;
            int m = k + 2;
            byte n = Integer.valueOf(paramString.substring(k, m), 16).byteValue();
            arrayOfByte[j] = n;
            j += 1;
        }
        return arrayOfByte;
    }
    
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        
        StringBuffer result = new StringBuffer(buf.length << 1);
        for (int i = 0; i < buf.length; i++)
            appendHex(result, buf[i]);
       return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEXSTRING.charAt((b >> 4) & 0x0f)).append(HEXSTRING.charAt(b & 0x0f));
    }
    
    class CryptException extends RuntimeException{
        public CryptException(String msg){
            super(msg);
        }
    }
    
}
