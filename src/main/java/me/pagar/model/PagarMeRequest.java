package me.pagar.model;

import com.google.common.base.Strings;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;
import me.pagar.util.JSONUtils;

public class PagarMeRequest extends PagarMe {

    private final String path;

    private final String method;

    private Map<String, Object> parameters;

    private Map<String, String> headers;

    public PagarMeRequest(String method, String path) {
        this.path = path;
        this.method = method;
        this.parameters = new HashMap<String, Object>();
    }

    @Deprecated
    public PagarMeRequest(String method, String path, boolean live) {
        this(method, path);
    }

    @SuppressWarnings("unchecked")
    public <T> T execute() throws PagarMeException {

        if (Strings.isNullOrEmpty(getApiKey())) {
            throw new PagarMeException("You need to configure API key before performing requests.");
        }

        final RestClient client = new RestClient(method, fullApiUrl(path), parameters, headers);
        final PagarMeResponse response = client.execute();

        final JsonElement decoded = JSONUtils.getInterpreter().fromJson(response.getBody(), JsonElement.class);

        if (response.getCode() == 200) {
            return (T) decoded;
        } else {
            throw PagarMeException.buildWithError(response);
        }

    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
