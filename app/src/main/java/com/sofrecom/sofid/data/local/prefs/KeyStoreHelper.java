package com.sofrecom.sofid.data.local.prefs;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.x500.X500Principal;

import androidx.annotation.RequiresApi;

/**
 * Created by oibnchahdia on 18/04/2019
 */
@Singleton
public class KeyStoreHelper {

    private static final String TAG = "KEYSTORE";

    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";

    private static final String PREF_KEY_AES = "J77IrUmqPB";
    private static final String PREF_KEY_IV = "n18t8bFHcH";

    private static String KEYSTORE_ALIAS;

    private KeyStore keyStore;
    private SharedPreferencesHelper prefsHelper;

    @Inject
    public KeyStoreHelper(Context context, SharedPreferencesHelper sharedPreferencesHelper) {
        try {
            KEYSTORE_ALIAS = context.getApplicationContext().getApplicationInfo().packageName;
            prefsHelper = sharedPreferencesHelper;
            keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
            keyStore.load(null);

            if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
                prefsHelper.putStringPlainText(PREF_KEY_IV,"");
                genKeyStoreKey(context);
                genAESKey();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genKeyStoreKey(Context context) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateRSAKey_AboveApi23();
        } else {
            generateRSAKey_BelowApi23(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateRSAKey_AboveApi23() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                .Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build();

        keyPairGenerator.initialize(keyGenParameterSpec);
        keyPairGenerator.generateKeyPair();
    }

    private void generateRSAKey_BelowApi23(Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 30);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(KEYSTORE_ALIAS)
                .setSubject(new X500Principal("CN=" + KEYSTORE_ALIAS))
                .setSerialNumber(BigInteger.TEN)
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        keyPairGenerator.initialize(spec);
        keyPairGenerator.generateKeyPair();
    }

    public String encrypt(String plainText) {
        try {
            return encryptAES(plainText);

        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
            return "";
        }
    }

    public String decrypt(String encryptedText) {
        try {
            return decryptAES(encryptedText);

        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
            return "";
        }
    }

    private String encryptRSA(byte[] plainText) throws Exception {
        PublicKey publicKey = keyStore.getCertificate(KEYSTORE_ALIAS).getPublicKey();

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedByte = cipher.doFinal(plainText);
        return Base64.encodeToString(encryptedByte, Base64.NO_WRAP);
    }

    private byte[] decryptRSA(String encryptedText) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, null);

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.NO_WRAP);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return decryptedBytes;
    }

    private void genAESKey() throws Exception {
        // Generate AES-Key
        byte[] aesKey = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(aesKey);

        // Generate 12 bytes iv then save to SharedPrefs
        byte[] generated = secureRandom.generateSeed(12);
        String iv = Base64.encodeToString(generated, Base64.NO_WRAP);
        prefsHelper.putStringPlainText(PREF_KEY_IV,iv);

        // Encrypt AES-Key with RSA Public Key then save to SharedPrefs
        String encryptAESKey = encryptRSA(aesKey);
        prefsHelper.putStringPlainText(PREF_KEY_AES,encryptAESKey);
    }

    /**
     * AES Encryption
     * @param plainText: A string which needs to be encrypted.
     * @return A base64's string after encrypting.
     */
    private String encryptAES(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getAESKey(), new IvParameterSpec(getIV()));

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
    }

    private String decryptAES(String encryptedText) throws Exception {
        byte[] decodedBytes = Base64.decode(encryptedText.getBytes(), Base64.NO_WRAP);

        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, getAESKey(), new IvParameterSpec(getIV()));

        return new String(cipher.doFinal(decodedBytes));
    }

    private byte[] getIV() {
        String prefIV = prefsHelper.getStringPlainText(PREF_KEY_IV);
        return Base64.decode(prefIV, Base64.NO_WRAP);
    }

    private SecretKeySpec getAESKey() throws Exception {
        String encryptedKey = prefsHelper.getStringPlainText(PREF_KEY_AES);
        byte[] aesKey = decryptRSA(encryptedKey);

        return new SecretKeySpec(aesKey, AES_MODE);
    }
}
