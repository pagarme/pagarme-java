package me.pagar.model;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.google.common.base.Strings;

public abstract class PagarMe {

    public static final String ENDPOINT = "https://api.pagar.me";

    public static final String API_VERSION = "1";

    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static final String SHA1_ALGORITHM = "sha1";

    public static final String SHA256_ALGORITHM = "sha256";

    private static String apiKey;

    public static String fullApiUrl(final String path) {
        return ENDPOINT.concat("/")
                .concat(API_VERSION)
                .concat(path);
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void init(String apiKey) {
        PagarMe.apiKey = apiKey;
    }

    public static boolean isRequestSignatureValid(final String payload, final String signature) {

        if (Strings.isNullOrEmpty(signature)) {
            return false;
        }

        String algorithm;
        final String[] parts = signature.split("=");

        if (parts.length != 2) // Wrong signature param
            return false;

        if (parts[0].equalsIgnoreCase(SHA1_ALGORITHM)) {
            algorithm = HMAC_SHA1_ALGORITHM;
        } else if (parts[0].equalsIgnoreCase(SHA256_ALGORITHM)) {
            algorithm = HMAC_SHA256_ALGORITHM;
        } else {
            return false; // Cannot set algorithm.
        }

        try {
            // Get hmac key from the raw key bytes
            byte[] keyBytes = apiKey.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, algorithm);

            // Get hmac Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(algorithm);
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(payload.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            String hash = new String(hexBytes, "UTF-8");
            return parts[1].equals(hash);
        } catch (Exception e) {
            return false;
        }

    }
}
