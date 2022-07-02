package edu.skku.map.project_2017312665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.skku.map.project_2017312665.Data.LoginResultData;
import edu.skku.map.project_2017312665.Data.UserLoginNetworkData;
import edu.skku.map.project_2017312665.Register.RegisterActivity;
import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;


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
    private boolean login_success;
    private String login_resultDetail;
    private ToastMessageInThread toastMessageInThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInit();

        guest_login_button.setOnClickListener(view -> {
            cite_name = readFileClass.readLoginAddressText(view) + "/login";
            new Thread(() -> {
                NetworkPost networkPost = new NetworkPost();
                String response = networkPost.post(cite_name, processInputJson());
                processOutputJson(response);
            }).start();
        });

        register_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private String processInputJson() {
        UserLoginNetworkData userLoginData = new UserLoginNetworkData();
        Gson gson = new Gson();

        input_ids = input_id.getText().toString();
        input_pws = input_pw.getText().toString();
        userLoginData.setId(input_ids);
        userLoginData.setPw(input_pws);

        String input_json = gson.toJson(userLoginData, UserLoginNetworkData.class);
        return input_json;
    }

    private void processOutputJson(String response) {
        if(response == "ERROR") {
            toastMessageInThread.ToastMessage(getApplicationContext(), "네트워크 에러 발생");
            return;
        }

        Gson gson = new GsonBuilder().create();
        final LoginResultData loginData = gson.fromJson(response, LoginResultData.class);
        login_success = loginData.isSuccess();
        login_resultDetail = loginData.getResult_detail();

        if(login_success == false) {
            if(login_resultDetail.equals("wrong_id")) {
                toastMessageInThread.ToastMessage(getApplicationContext(), "등록되지 않은 회원입니다.");
            }
            else {
                toastMessageInThread.ToastMessage(getApplicationContext(), "잘못된 비밀번호 입니다.");
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

    private void setInit() {
        guest_login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.Register);
        input_id = findViewById(R.id.input_id);
        input_pw = findViewById(R.id.input_pw);
        readFileClass = new ReadFileClass();
        toastMessageInThread = new ToastMessageInThread();
        isNewActivity = false;
    }
}

