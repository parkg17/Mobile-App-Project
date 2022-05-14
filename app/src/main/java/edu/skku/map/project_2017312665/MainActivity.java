package edu.skku.map.project_2017312665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String EXT_ID = "User_ID";
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guest_login_button = findViewById(R.id.guest_login_button);

        guest_login_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShoppingMallActivity.class);
            intent.putExtra(EXT_ID, user_id);
            startActivity(intent);
        });
    }
}