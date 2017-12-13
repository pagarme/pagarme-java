package me.pagar.model;

import com.google.common.base.Strings;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.util.Formatter;

public abstract class PagarMe {

    public static final String ENDPOINT = "https://api.pagar.me";

    public static final String API_VERSION = "1";

    public static final String HMAC_MD5_ALGORITHM = "HmacMD5";

    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static final String SHA1_ALGORITHM = "sha1";

    public static final String SHA256_ALGORITHM = "sha256";

    public static final String ASCII = "ASCII";

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

    public static boolean validateRequestSignature(final String payload, final String signature) {

        if (Strings.isNullOrEmpty(payload) || Strings.isNullOrEmpty(signature)) {
            return false;
        }

        final String[] parts = signature.split("=");

        try {
            // get an hmac_sha1 key from the raw key bytes
            final SecretKeySpec signingKey = new SecretKeySpec(apiKey.getBytes(ASCII), parts[0]);

            String algorithm = HMAC_MD5_ALGORITHM;

            if (parts[0].equalsIgnoreCase(SHA1_ALGORITHM)) {
                algorithm = HMAC_SHA1_ALGORITHM;
            } else if (parts[0].equalsIgnoreCase(SHA256_ALGORITHM)) {
                algorithm = HMAC_SHA256_ALGORITHM;
            }

            // get an hmac_sha1 Mac instance and initialize with the signing key
            final Mac mac = Mac.getInstance(algorithm);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            final byte[] rawHmac = mac.doFinal(payload.getBytes(ASCII));

            final Formatter formatter = new Formatter();

            // right transform into sha1 hash
            for (byte b : rawHmac) {
                formatter.format("%02x", 0xFF & b);
            }

            final String hash = formatter.toString();

            return (parts.length == 2) && (hash.equals(parts[1]));
        } catch (Exception e) {
            return false;
        }

    }
}
