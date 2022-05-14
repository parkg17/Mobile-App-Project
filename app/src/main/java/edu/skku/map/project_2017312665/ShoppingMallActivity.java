package edu.skku.map.project_2017312665;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingMallActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_mall);

        // MainActivity로부터 UserID를 Intent로 전달
        Intent intent = getIntent();
        String user_id = intent.getStringExtra(MainActivity.EXT_ID);
        //TextView textView_userName = (TextView) findViewById(R.id.textView_username);
        //textView_userName.setText(user_id);
    }

}
