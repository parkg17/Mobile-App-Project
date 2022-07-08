package edu.skku.map.project_2017312665.Register;

import android.content.Intent;
import android.os.Bundle;
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
    private ToastMessageInThread toastMessageInThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getData();
        setInit();

        register_complete_button.setOnClickListener(view -> {
            cite_name = readFileClass.readLoginAddressText(view) + "/adduser";
            new Thread(() -> {
                NetworkPost networkPost = new NetworkPost();
                String response = networkPost.post(cite_name, processInputJson());
                processOutputJson(response);
            }).start();
        });
    }

    private String processInputJson() {
        UserNetworkData userData = new UserNetworkData();
        Gson gson = new Gson();

        reg_ids = reg_id.getText().toString();
        reg_pws = reg_pw.getText().toString();
        reg_names = reg_name.getText().toString();
        reg_phones = reg_phone.getText().toString();

        userData.setId(reg_ids);
        userData.setPassword(reg_pws);
        userData.setName(reg_names);
        userData.setPhone_num(reg_phones);

        String input_json = gson.toJson(userData, UserNetworkData.class);
        return input_json;
    }

    private void processOutputJson(String response) {
        if(response == "ERROR") {
            toastMessageInThread.ToastMessage(getApplicationContext(), "네트워크 에러 발생");
            return;
        }

        Gson gson = new GsonBuilder().create();
        final LoginResultData loginData = gson.fromJson(response, LoginResultData.class);

        boolean login_success = loginData.isSuccess();
        String login_resultDetail = loginData.getResult_detail();

        String toast_message = "";

        if(login_success == true) {
            FlushEditText();
            toast_message = "회원 가입 성공";
        }
        else {
            if(login_resultDetail.equals("id_duplicated")) {
                toast_message = "이미 존재하는 회원 ID입니다.";
            }
            else if(login_resultDetail.equals("name_empty")) {
                toast_message = "이름을 입력해야 합니다.";
            }
            else if(login_resultDetail.equals("password_empty")) {
                toast_message = "비밀번호를 입력해야 합니다.";
            }
            else if(login_resultDetail.equals("phonenum_empty")) {
                toast_message = "전화번호를 입력해야 합니다.";
            }
        }
        toastMessageInThread.ToastMessage(getApplicationContext(), toast_message);
    }

    private void FlushEditText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reg_id.setText(null);
                reg_pw.setText(null);
                reg_name.setText(null);
                reg_phone.setText(null);
            }
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
        toastMessageInThread = new ToastMessageInThread();
    }
}