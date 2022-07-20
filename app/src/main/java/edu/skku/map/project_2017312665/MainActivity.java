package edu.skku.map.project_2017312665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.skku.map.project_2017312665.Data.LoginResultData;
import edu.skku.map.project_2017312665.Data.UserInfoData;
import edu.skku.map.project_2017312665.Data.UserLoginNetworkData;
import edu.skku.map.project_2017312665.Register.RegisterActivity;
import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;


public class MainActivity extends AppCompatActivity {

    /* Declare Variables */
    private String input_ids;
    private String cite_name;
    private String cite_name_append;
    private Button guest_login_button;
    private Button register_button;
    private EditText input_id;
    private EditText input_pw;
    private ReadFileClass readFileClass;
    private boolean isNewActivity;
    private ToastMessageInThread toastMessageInThread;
    private String userInfoDataName;
    private String userInfoDataPhoneNum;
    private Thread getUserInfoThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInit();

        guest_login_button.setOnClickListener(view -> {
            cite_name = readFileClass.readText(
                    view, getApplicationContext(), "aws_login_address");
            cite_name_append = cite_name + "/login";
            new Thread(() -> {
                NetworkPost networkPost = new NetworkPost();
                String response = networkPost.post(cite_name_append, processInputJson());
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
        String input_pws = input_pw.getText().toString();
        userLoginData.setId(input_ids);
        userLoginData.setPw(input_pws);

        return gson.toJson(userLoginData, UserLoginNetworkData.class);
    }

    private void processOutputJson(String response) {
        if(response.equals("ERROR")) {
            toastMessageInThread.ToastMessage(getApplicationContext(), "네트워크 에러 발생");
            return;
        }

        Gson gson = new GsonBuilder().create();
        final LoginResultData loginData = gson.fromJson(response, LoginResultData.class);
        boolean login_success = loginData.isSuccess();
        String login_resultDetail = loginData.getResult_detail();

        if(!login_success) {
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
            getUserName();
            try {
                getUserInfoThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isNewActivity = false;
            Intent intent = new Intent(MainActivity.this, ShoppingMallActivity.class);
            intent.putExtra("User_ID", input_ids);
            intent.putExtra("User_NAME", userInfoDataName);
            intent.putExtra("User_PHONE_NUM", userInfoDataPhoneNum);
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

    private void getUserName() {
        cite_name_append = cite_name + "/getuser";
        getUserInfoThread = new Thread(() -> {
            NetworkPost networkPost = new NetworkPost();
            String response = networkPost.post(cite_name_append, processInputJson());
            processOutputJsonUserInfo(response);
        });
        getUserInfoThread.start();
    }

    private void processOutputJsonUserInfo(String response) {
        if (response.equals("ERROR")) {
            toastMessageInThread.ToastMessage(getApplicationContext(), "네트워크 에러 발생");
            userInfoDataName = "Unknown";
            userInfoDataPhoneNum = "Unknown";
            return;
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        final UserInfoData userInfoData = gson.fromJson(response, UserInfoData.class);

        boolean userInfoDataSuccess = userInfoData.isSuccess();
        userInfoDataName = userInfoData.getName();
        userInfoDataPhoneNum = userInfoData.getPhone_num();

        if(!userInfoDataSuccess) {
            userInfoDataName = "Unknown";
            userInfoDataPhoneNum = "Unknown";
        }
    }
}

