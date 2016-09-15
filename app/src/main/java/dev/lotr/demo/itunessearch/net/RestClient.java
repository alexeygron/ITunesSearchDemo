package dev.lotr.demo.itunessearch.net;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Contain methods to work with iTunes Search API.
 */
public class RestClient {

    private static final String URL = "https://itunes.apple.com/search?term=";

    /**
     * @return response json
     */
    public static String getTracks(String query) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL + query)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}
