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

    /* Declare Variables */
    private String reg_ids;
    private String reg_pws;
    private String reg_names;
    private String reg_phones;
    private String cite_name;
    private Button register_complete_button;
    private EditText reg_id;
    private EditText reg_pw;
    private EditText reg_name;
    private EditText reg_phone;
    private ReadFileClass readFileClass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        getData();
        setInit();

        register_complete_button.setOnClickListener(view -> {
            reg_ids = reg_id.getText().toString();
            reg_pws = reg_pw.getText().toString();
            reg_names = reg_name.getText().toString();
            reg_phones = reg_phone.getText().toString();

            OkHttpClient client = new OkHttpClient();

            cite_name = readFileClass.readLoginAddressText(view);
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

    private void getData() {
        Intent intent = getIntent();
    }

    private void setInit() {
        register_complete_button = findViewById(R.id.register_complete);
        reg_id = (EditText) findViewById(R.id.reg_id);
        reg_pw = (EditText) findViewById(R.id.reg_pw);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_phone = (EditText) findViewById(R.id.reg_phone);
        readFileClass = new ReadFileClass();
    }
}

