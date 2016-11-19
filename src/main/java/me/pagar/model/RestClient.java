package me.pagar.model;

import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import me.pagar.util.JSONUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    public final static String API_KEY = "api_key";

    public final static String AMOUNT = "amount";

    private HttpsURLConnection httpClient;

    private String method;

    private String url;

    private Map<String, Object> parameters;

    private int count;

    private boolean live;

    private InputStream is;

    private void setupSecureConnection(final HttpsURLConnection httpClient) throws KeyStoreException,
            IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        final URL certFile = Thread.currentThread().getContextClassLoader()
                .getResource("pagarme.crt");

        if (null == certFile) {
            return;
        }

        final Certificate cert = CertificateFactory.getInstance("X.509")
                .generateCertificate(certFile.openStream());

        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("pagarme", cert);

        final TrustManagerFactory tmf = TrustManagerFactory.getInstance("X.509");
        tmf.init(keyStore);

        final SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, tmf.getTrustManagers(), null);

        httpClient.setSSLSocketFactory(ctx.getSocketFactory());
    }

    public RestClient(final String method, final String url) throws PagarMeException {
        this(method, url, null, null);
    }

    public RestClient(final String method, final String url, Map<String, Object> parameters) throws PagarMeException {
        this(method, url, parameters, null);
    }

    @SuppressWarnings("unchecked")
    public RestClient(final String method, final String url, Map<String, Object> parameters,
                      Map<String, String> headers) throws PagarMeException {
        this.method = method;
        this.url = url;
        this.parameters = parameters;

        if (null == headers) {
            headers = new HashMap<String, String>();
        }

        if (null == this.parameters) {
            this.parameters = new HashMap<String, Object>();
        }


        headers.put("User-Agent", "pagarme-java 1.0.0");
        headers.put("Accept", "application/json");

        if (Strings.isNullOrEmpty(url)) {
            throw new PagarMeException("You must set the URL to make a request.");
        }

        if (!Strings.isNullOrEmpty(method)) {

            try {
                final UriBuilder builder = UriBuilder.fromPath(this.url);
                builder.queryParam(API_KEY, PagarMe.getApiKey());

                if (this.parameters.containsKey(AMOUNT) && this.parameters.size() == 1) {
                    builder.queryParam(AMOUNT, this.parameters.remove(AMOUNT));
                }

                if (method.equalsIgnoreCase(HttpMethod.GET)) {

                    for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
                        builder.queryParam(entry.getKey(), entry.getValue());
                    }

                }

                httpClient = (HttpsURLConnection) builder
                        .build(this)
                        .toURL()
                        .openConnection();
                httpClient.setRequestMethod(this.method.toUpperCase());
                httpClient.setDoInput(true);
                httpClient.setDoOutput(false);

                setupSecureConnection(httpClient);

                if (headers.size() > 0) {

                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        httpClient.addRequestProperty(entry.getKey(), entry.getValue());
                    }

                }

            } catch (Exception e) {
                throw PagarMeException.buildWithError(e);
            }

        }

    }

    public PagarMeResponse execute() throws PagarMeException {
        final StringBuilder builder = new StringBuilder();
        int responseCode = -1;

        try {

            if (method.equalsIgnoreCase(HttpMethod.POST) ||
                    method.equalsIgnoreCase(HttpMethod.PUT) ||
                    method.equalsIgnoreCase(HttpMethod.DELETE)) {
                httpClient.setDoOutput(true);

                if (parameters.size() >= 1) {
                    final byte[] payload = JSONUtils.getInterpreter().toJson(parameters).getBytes();
                    httpClient.addRequestProperty("Content-Type", "application/json");
                    httpClient.addRequestProperty("Content-Length", String.valueOf(payload.length));

                    final OutputStream os = httpClient.getOutputStream();
                    os.write(payload);
                    os.flush();
                }

            }

            try {
                is = httpClient.getInputStream();
                responseCode = httpClient.getResponseCode();
            } catch (IOException e) {
                is = httpClient.getErrorStream();
                responseCode = httpClient.getResponseCode();
            }

            final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\r');
            }

            reader.close();
            httpClient.disconnect();

            return new PagarMeResponse(responseCode,
                    JSONUtils.getInterpreter().fromJson(builder.toString(), JsonElement.class));
        } catch (Exception e) {

            if (e instanceof JsonSyntaxException) {
                throw new PagarMeException(responseCode, url, method, builder.toString());
            }

            throw PagarMeException.buildWithError(e);
        }

    }
}
