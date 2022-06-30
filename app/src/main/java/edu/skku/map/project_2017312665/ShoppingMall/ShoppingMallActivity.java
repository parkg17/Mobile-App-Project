package edu.skku.map.project_2017312665.ShoppingMall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.skku.map.project_2017312665.CoffeeGoods.CoffeeGoodsActivity;
import edu.skku.map.project_2017312665.R;

public class ShoppingMallActivity extends AppCompatActivity {

    public static Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_mall);
        mContext = this;

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("User_ID");
        String user_pw = intent.getStringExtra("User_PW");
        String user_name = intent.getStringExtra("User_NAME");
        String user_phone = intent.getStringExtra("User_PHONE");

        Bundle bundle = new Bundle();
        bundle.putString("id", user_id);
        bundle.putString("name", user_name);
        bundle.putString("phone", user_phone);

        Fragment fragment1 = new fragment_goods();
        Fragment fragment2 = new fragment_information();
        Fragment fragment3 = new fragment_shopping_basket();

        fragment2.setArguments(bundle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, new fragment_goods()).commit();

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


