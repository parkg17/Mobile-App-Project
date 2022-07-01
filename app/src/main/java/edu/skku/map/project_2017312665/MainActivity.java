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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.skku.map.project_2017312665.Data.LoginData;
import edu.skku.map.project_2017312665.Register.RegisterActivity;
import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    /* Declare Variables */
    private String input_ids;
    private String input_pws;
    private String cite_name;
    private Button guest_login_button;
    private Button register_button;
    private EditText input_id;
    private EditText input_pw;
    private ReadFileClass readFileClass;
    private boolean isNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInit();

        guest_login_button.setOnClickListener(view -> {
            input_ids = input_id.getText().toString();
            input_pws = input_pw.getText().toString();

            OkHttpClient client = new OkHttpClient();

            cite_name = readFileClass.readLoginAddressText(view) + "/login";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(cite_name).newBuilder();
            urlBuilder.addQueryParameter("id", input_ids);
            urlBuilder.addQueryParameter("password", input_pws);
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

                    boolean login_success = loginData.isSuccess();
                    String login_resultDetail = loginData.getResult_detail();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(login_success == false) {
                                if(login_resultDetail.equals("wrong_id")) {
                                    Toast.makeText(getApplicationContext(), "등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "잘못된 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                isNewActivity = true;
                            }
                            if(isNewActivity)  {
                                isNewActivity = false;
                                Intent intent = new Intent(MainActivity.this, ShoppingMallActivity.class);
                                intent.putExtra("User_ID", input_ids);
                                intent.putExtra("User_PW", input_pws);
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

    private void setInit() {
        guest_login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.Register);
        input_id = findViewById(R.id.input_id);
        input_pw = findViewById(R.id.input_pw);
        readFileClass = new ReadFileClass();
        isNewActivity = false;
    }
}

