package edu.skku.map.project_2017312665;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String EXT_ID = "User_ID";
    public static final String EXT_PW = "User_PW";
    public static final String EXT_NAME = "User_NAME";
    public static final String EXT_PHONE = "User_PHONE";
    boolean isNewActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guest_login_button = findViewById(R.id.login_button);
        Button register_button = findViewById(R.id.Register);
        EditText input_id = findViewById(R.id.input_id);
        EditText input_pw = findViewById(R.id.input_pw);

        guest_login_button.setOnClickListener(view -> {
            String input_ids = input_id.getText().toString();
            String input_pws = input_pw.getText().toString();

            OkHttpClient client = new OkHttpClient();

            String cite_name = "https://mfinzgll7f.execute-api.ap-northeast-2.amazonaws.com/dev/access";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(cite_name).newBuilder();
            urlBuilder.addQueryParameter("id", input_ids);
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    final LoginData loginData = gson.fromJson(myResponse, LoginData.class);

                    String req_id = loginData.getId();
                    String req_pw = loginData.getPw();
                    String req_name = loginData.getName();
                    String req_phone = loginData.getPhone();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (req_id.equals("NULL")) {
                                Toast.makeText(getApplicationContext(), "등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!req_pw.equals(input_pws)) {
                                    Toast.makeText(getApplicationContext(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    isNewActivity = true;
                                }
                            }
                            if(isNewActivity)  {
                                isNewActivity = false;
                                Intent intent = new Intent(MainActivity.this, ShoppingMallActivity.class);
                                intent.putExtra(EXT_ID, req_id);
                                intent.putExtra(EXT_PW, req_pw);
                                intent.putExtra(EXT_NAME, req_name);
                                intent.putExtra(EXT_PHONE, req_phone);
                                startActivity(intent);
                            }
                        }
                    });
                }
            });

        });

        register_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}