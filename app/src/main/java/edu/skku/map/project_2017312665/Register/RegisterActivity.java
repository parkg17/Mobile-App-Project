package edu.skku.map.project_2017312665.Register;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.skku.map.project_2017312665.Data.LoginResultData;
import edu.skku.map.project_2017312665.Data.UserNetworkData;
import edu.skku.map.project_2017312665.NetworkPost;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;
import edu.skku.map.project_2017312665.ToastMessageInThread;

public class RegisterActivity extends AppCompatActivity {

    /* Declare Variables */
    private String cite_name;
    private String cite_name_append;
    private Button register_complete_button;
    private EditText reg_id;
    private EditText reg_pw;
    private EditText reg_name;
    private EditText reg_phone;
    private ReadFileClass readFileClass;
    private ToastMessageInThread toastMessageInThread;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getData();
        setInit();

        register_complete_button.setOnClickListener(view -> {
            cite_name = readFileClass.readText(view, mContext, "aws_login_address");
            cite_name_append = cite_name + "/adduser";
            Log.d("cite_name", cite_name_append);
            new Thread(() -> {
                NetworkPost networkPost = new NetworkPost();
                String response = networkPost.post(cite_name_append, processInputJson());
                processOutputJson(response);
            }).start();
        });
    }

    private String processInputJson() {
        UserNetworkData userData = new UserNetworkData();
        Gson gson = new GsonBuilder().serializeNulls().create();

        String reg_ids = String.valueOf(reg_id.getText());
        String reg_pws = String.valueOf(reg_pw.getText());
        String reg_names = String.valueOf(reg_name.getText());
        String reg_phones = String.valueOf(reg_phone.getText());

        userData.setId(reg_ids);
        userData.setPassword(reg_pws);
        userData.setName(reg_names);
        userData.setPhone_num(reg_phones);

        return gson.toJson(userData, UserNetworkData.class);
    }

    private void processOutputJson(String response) {
        if(response.equals("ERROR")) {
            toastMessageInThread.ToastMessage(getApplicationContext(), "네트워크 에러 발생");
            return;
        }

        Gson gson = new GsonBuilder().serializeNulls().create();

        final LoginResultData loginData = gson.fromJson(response, LoginResultData.class);

        boolean login_success = loginData.isSuccess();
        String login_resultDetail = loginData.getResult_detail();

        String toast_message = "";

        if(login_success) {
            FlushEditText();
            toast_message = "회원 가입 성공";
        }
        else {
            switch (login_resultDetail) {
                case "id_duplicated":
                    toast_message = "이미 존재하는 회원 ID 입니다.";
                    break;
                case "name_empty":
                    toast_message = "이름을 입력해야 합니다.";
                    break;
                case "password_empty":
                    toast_message = "비밀번호를 입력해야 합니다.";
                    break;
                case "phonenum_empty":
                    toast_message = "전화번호를 입력해야 합니다.";
                    break;
            }
        }
        toastMessageInThread.ToastMessage(getApplicationContext(), toast_message);
    }

    private void FlushEditText() {
        runOnUiThread(() -> {
            reg_id.setText(null);
            reg_pw.setText(null);
            reg_name.setText(null);
            reg_phone.setText(null);
        });
    }

    private void getData() {
        //getIntent();
    }

    private void setInit() {
        register_complete_button = findViewById(R.id.register_complete);
        reg_id = (EditText) findViewById(R.id.reg_id);
        reg_pw = (EditText) findViewById(R.id.reg_pw);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_phone = (EditText) findViewById(R.id.reg_phone);
        readFileClass = new ReadFileClass();
        toastMessageInThread = new ToastMessageInThread();
        mContext = this;
    }
}