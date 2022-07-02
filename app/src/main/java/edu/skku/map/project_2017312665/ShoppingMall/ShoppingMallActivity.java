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

    /* Declare Variables */
    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_phone;
    public static Context mContext;
    public Fragment fragment_goods;
    public Fragment fragment_information;
    public Fragment fragment_shopping_cart;
    public BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_mall);
        getData();
        setInit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment_goods).commit();
                        break;
                    case R.id.item2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment_information).commit();
                        break;
                    case R.id.item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment_shopping_cart).commit();
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

    private void getData() {
        Intent intent = getIntent();
        user_id = intent.getStringExtra("User_ID");
        user_pw = intent.getStringExtra("User_PW");
        user_name = "x";
        user_phone = "x";
    }

    private void setInit() {
        Bundle bundle = new Bundle();
        bundle.putString("id", user_id);
        bundle.putString("name", user_name);
        bundle.putString("phone", user_phone);

        fragment_goods = new fragment_goods();
        fragment_information = new fragment_information();
        fragment_shopping_cart = new fragment_shopping_basket();
        fragment_information.setArguments(bundle);

        mContext = this;
        bottomNavigationView = findViewById(R.id.nav_bar);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, new fragment_goods())
                .commit();

    }
}


