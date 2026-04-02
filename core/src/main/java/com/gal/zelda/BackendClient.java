package com.gal.zelda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class BackendClient {
    private final String baseUrl;
    private final HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

    public BackendClient(String baseUrl) {
        this.baseUrl = trimTrailingSlash(baseUrl);
    }

    public void fetchPlayerById(int playerId, ResponseHandler handler) {
        Net.HttpRequest request = requestBuilder
            .newRequest()
            .method(Net.HttpMethods.GET)
            .url(baseUrl + "/api/players/" + playerId)
            .build();

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                String responseBody = httpResponse.getResultAsString();
                if (statusCode < 200 || statusCode >= 300) {
                    handler.onFailure("HTTP " + statusCode + " Response: " + responseBody);
                    return;
                }

                try {
                    handler.onSuccess(parsePlayerProfile(responseBody));
                } catch (RuntimeException exception) {
                    handler.onFailure("Failed to parse player JSON: " + exception.getMessage());
                }
            }

            @Override
            public void failed(Throwable t) {
                handler.onFailure(t.getMessage() == null ? "Unknown network error" : t.getMessage());
            }

            @Override
            public void cancelled() {
                handler.onFailure("Request cancelled");
            }
        });
    }

    private String trimTrailingSlash(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }

    private PlayerProfile parsePlayerProfile(String responseBody) {
        JsonValue json = new JsonReader().parse(responseBody);
        return new PlayerProfile(
            json.getInt("id"),
            json.getString("username"),
            json.getInt("totalScore"),
            json.getInt("totalKills")
        );
    }

    public interface ResponseHandler {
        void onSuccess(PlayerProfile playerProfile);

        void onFailure(String errorMessage);
    }
}
