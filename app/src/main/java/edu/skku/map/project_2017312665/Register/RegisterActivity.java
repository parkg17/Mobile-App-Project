package edu.skku.map.project_2017312665.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import edu.skku.map.project_2017312665.Data.LoginData;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    ReadFileClass readFileClass = new ReadFileClass();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        Intent intent = getIntent();

        Button register_complete_button = findViewById(R.id.register_complete);
        EditText reg_id = findViewById(R.id.reg_id);
        EditText reg_pw = findViewById(R.id.reg_pw);
        EditText reg_name = findViewById(R.id.reg_name);
        EditText reg_phone = findViewById(R.id.reg_phone);

        register_complete_button.setOnClickListener(view -> {
            String reg_ids = reg_id.getText().toString();
            String reg_pws = reg_pw.getText().toString();
            String reg_names = reg_name.getText().toString();
            String reg_phones = reg_phone.getText().toString();

            OkHttpClient client = new OkHttpClient();

            String cite_name = readFileClass.readLoginAddressText(view);
            HttpUrl.Builder urlBuilder = HttpUrl.parse(cite_name).newBuilder();
            urlBuilder.addQueryParameter("id", reg_ids);
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

                    String id = loginData.getId();
                    String pw = loginData.getPw();
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!id.equals("NULL")) {
                                Toast.makeText(getApplicationContext(), "이미 등록된 회원입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                finish();
                            }
                        }
                    });
                }
            });
        });
    }
}

