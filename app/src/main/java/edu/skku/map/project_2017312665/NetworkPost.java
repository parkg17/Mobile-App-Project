package edu.skku.map.project_2017312665;

import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkPost {

    /* Variable Declaration */
    private String response_string = "ERROR";

    public String post(String requestURL, String json) {
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(requestURL)).newBuilder();
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            response_string =  Objects.requireNonNull(response.body()).string();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response_string;
    }
}
