package edu.skku.map.project_2017312665;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ShoppingMallActivity extends AppCompatActivity {

    public static Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_mall);
        mContext = this;

        Intent intent = getIntent();
        String user_id = intent.getStringExtra(MainActivity.EXT_ID);
        String user_pw = intent.getStringExtra(MainActivity.EXT_PW);
        String user_name = intent.getStringExtra(MainActivity.EXT_NAME);
        String user_phone = intent.getStringExtra(MainActivity.EXT_PHONE);

        Bundle bundle = new Bundle();
        bundle.putString("id", user_id);
        bundle.putString("name", user_name);
        bundle.putString("phone", user_phone);

        Fragment fragment1 = new fragment1();
        Fragment fragment2 = new fragment2();
        Fragment fragment3 = new fragment3();

        fragment2.setArguments(bundle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, new fragment1()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment1).commit();
                        break;
                    case R.id.item2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment2).commit();
                        break;
                    case R.id.item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment3).commit();
                        break;
                }
                return true;
            }
        });
    }
    public void open_CoffeeGoodsActivity(String name, String price, String desc, String img_name) {
        Intent intent = new Intent(ShoppingMallActivity.this, CoffeeGoodsActivity.class);
        intent.putExtra("COFFEE_NAME", name);
        intent.putExtra("COFFEE_PRICE", price);
        intent.putExtra("COFFEE_DESCRIPTION", desc);
        intent.putExtra("COFFEE_IMG_NAME", img_name);
        startActivity(intent);
    }
}


