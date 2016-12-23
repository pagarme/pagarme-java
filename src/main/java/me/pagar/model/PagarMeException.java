package me.pagar.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;

import me.pagar.util.JSONUtils;

public class PagarMeException extends Exception {
    private int returnCode;

    private String url;

    private String method;

    Collection<PagarMeError> errors = new ArrayList<PagarMeError>();

    public static PagarMeException buildWithError(final Exception error) {
        return new PagarMeException(error.getMessage(), null);
    }

    public static PagarMeException buildWithError(final PagarMeResponse response) {

        if (null == response) {
            return null;
        }

        final JsonObject responseError = new JSONUtils().getInterpreter().fromJson(response.getBody(), JsonObject.class);

        final JsonArray errors = responseError.getAsJsonArray("errors");

        final StringBuilder joinedMessages = new StringBuilder();

        int i;
        for (i = 0; i < errors.size(); i++) {
            final JsonObject error = errors.get(i).getAsJsonObject();
            joinedMessages
                    .append(error.get("message").getAsString())
                    .append("\n");
        }

        final PagarMeException exception = new PagarMeException(joinedMessages.toString(), responseError);
        exception.returnCode = response.getCode();

        return exception;
    }

    public PagarMeException(int returnCode, String url, String method, String message) {
        super(message);
        this.returnCode = returnCode;
        this.url = url;
        this.method = method;
    }

    public PagarMeException(final String message) {
        this(message, null);
    }

    public PagarMeException(final String message, final JsonObject responseError) {
        super(message);

        if (null != responseError) {
            this.url = responseError.get("url").getAsString();
            this.method = responseError.get("method").getAsString();

            if (responseError.has("errors")) {
                final JsonArray errors = responseError.get("errors").getAsJsonArray();

                int i;
                for (i = 0; i < errors.size(); i++) {
                    final JsonObject error = errors.get(i).getAsJsonObject();
                    this.errors.add(new PagarMeError(error));
                }

            }

        }

    }

    public Collection<PagarMeError> getErrors() {
        return errors;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public int getReturnCode() {
        return returnCode;
    }
}
