package edu.skku.map.project_2017312665.CoffeeGoods;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;
import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;

public class CoffeeGoodsActivity extends AppCompatActivity {

    /* Declare Variables */
    private int iResId;
    private String coffee_name;
    private String coffee_price;
    private String coffee_description;
    private String coffee_img_name;
    private String image_path;
    private TextView textview_coffee_name;
    private TextView textview_coffee_price;
    private TextView textview_coffee_description;
    private ImageView imageview_coffee;
    private ImageButton btn_back;
    private ImageButton btn_shopping_cart;
    private Button btn_basket;
    private Button btn_purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_goods);
        getData();
        setInit();

        /* Click Event : Coffee Image */
        imageview_coffee.setOnClickListener(view -> {
            Intent put_intent = new Intent(CoffeeGoodsActivity.this, ImageActivity.class);
            put_intent.putExtra("COFFEE_IMG_NAME", coffee_img_name);

            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(
                    CoffeeGoodsActivity.this, view, "img_trans");

            startActivity(put_intent, opt.toBundle());
        });

        /* Click Event : Back Button */
        btn_back.setOnClickListener(view -> {
            finish();
        });

        /* Click Event : Shopping Cart Button */
        btn_shopping_cart.setOnClickListener(view -> {
            finish();
        });
    }

    private void getData() {
        Intent intent = getIntent();
        coffee_name = intent.getStringExtra("COFFEE_NAME");
        coffee_price = intent.getStringExtra("COFFEE_PRICE");
        coffee_description = intent.getStringExtra("COFFEE_DESCRIPTION");
        coffee_img_name = intent.getStringExtra("COFFEE_IMG_NAME");
    }

    private void setInit() {
        textview_coffee_name = (TextView) findViewById(R.id.coffee_goods_name);
        textview_coffee_price = (TextView) findViewById(R.id.coffee_goods_price);
        textview_coffee_description = (TextView) findViewById(R.id.coffee_goods_description);
        imageview_coffee  = (ImageView) findViewById(R.id.coffee_goods_image);

        textview_coffee_name.setText(coffee_name);
        textview_coffee_price.setText(coffee_price);
        textview_coffee_description.setText(coffee_description);

        btn_back = (ImageButton) findViewById(R.id.Btn_back);
        btn_shopping_cart = (ImageButton) findViewById(R.id.Btn_shopping_cart);
        btn_basket = (Button) findViewById(R.id.Btn_basket);
        btn_purchase = (Button) findViewById(R.id.Btn_purchase);

        image_path = "@drawable/" + coffee_img_name;
        iResId = this.getResources().getIdentifier( image_path, "drawable", this.getPackageName() );
        imageview_coffee.setImageResource(iResId);
    }
}
