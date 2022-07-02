package edu.skku.map.project_2017312665;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkPost {

    /* Variable Declaration */
    private OkHttpClient client;
    private Response response;
    private String url;
    private String response_string = "ERROR";

    public String post(String requestURL, String json) {
        try {
            client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(requestURL).newBuilder();
            url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

            response = client.newCall(request).execute();
            response_string =  response.body().string();

        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            return response_string;
        }
    }
}
