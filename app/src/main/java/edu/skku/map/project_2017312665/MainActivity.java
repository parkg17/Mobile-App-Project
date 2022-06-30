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

    ReadFileClass readFileClass = new ReadFileClass();
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

            String cite_name = readFileClass.readLoginAddressText(view);
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
                                intent.putExtra("User_ID", req_id);
                                intent.putExtra("User_PW", req_pw);
                                intent.putExtra("User_NAME", req_name);
                                intent.putExtra("User_PHONE", req_phone);
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
    /*
    private String readLoginAddressText() {
        int idx;
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.aws_login_address);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            idx = inputStream.read();
            while (idx != -1) {
                byteArrayOutputStream.write(idx);
                idx = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }*/
}

