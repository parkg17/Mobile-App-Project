package edu.skku.map.project_2017312665.CoffeeGoods;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import edu.skku.map.project_2017312665.Data.Grade;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class CoffeeGoodsActivity extends AppCompatActivity {

    /* Declare Variables */
    private int iResId;
    private String coffee_id;
    private String coffee_name;
    private Integer coffee_stock;
    private Double coffee_price;
    private Double coffee_rating;
    private Grade coffee_grade;
    private String coffee_expiredDate;
    private String coffee_description;
    private String image_path;
    private TextView textview_coffee_name;
    private TextView textview_coffee_price;
    private TextView textview_coffee_description;
    private ImageView imageview_coffee;
    private ImageButton btn_back;
    private ImageButton btn_shopping_cart;
    private Button btn_basket;
    private Button btn_purchase;
    private ReadFileClass readFileClass;
    private View goods_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        getData();
        setInit();
        LoadCoffeeImage(goods_view);


        /* Click Event : Coffee Image */
        imageview_coffee.setOnClickListener(view -> {
            Intent put_intent = new Intent(CoffeeGoodsActivity.this, ImageActivity.class);
            put_intent.putExtra("COFFEE_ID", coffee_id);

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
        coffee_id = intent.getStringExtra("COFFEE_ID");
        coffee_name = intent.getStringExtra("COFFEE_NAME");
        coffee_stock = intent.getIntExtra("COFFEE_STOCK", 0);
        coffee_price = intent.getDoubleExtra("COFFEE_PRICE", 0);
        coffee_rating = intent.getDoubleExtra("COFFEE_RATING", 0);
        coffee_grade = (Grade) getIntent().getSerializableExtra("COFFEE_GRADE");
        coffee_expiredDate = intent.getStringExtra("COFFEE_EXPIRED_DATE");
        coffee_description = intent.getStringExtra("COFFEE_DESCRIPTION");
    }

    private void setInit() {
        textview_coffee_name = (TextView) findViewById(R.id.coffee_goods_name);
        textview_coffee_price = (TextView) findViewById(R.id.coffee_goods_price);
        textview_coffee_description = (TextView) findViewById(R.id.coffee_goods_description);
        imageview_coffee  = (ImageView) findViewById(R.id.coffee_goods_image);

        textview_coffee_name.setText(coffee_name);
        textview_coffee_price.setText(String.valueOf(coffee_price) + "원");
        textview_coffee_description.setText(coffee_description);

        btn_back = (ImageButton) findViewById(R.id.Btn_back);
        btn_shopping_cart = (ImageButton) findViewById(R.id.Btn_shopping_cart);
        btn_basket = (Button) findViewById(R.id.Btn_basket);
        btn_purchase = (Button) findViewById(R.id.Btn_purchase);

        readFileClass = new ReadFileClass();
        goods_view = getLayoutInflater().inflate(R.layout.activity_goods, null);
    }

    private void LoadCoffeeImage(View view) {
        String cite_name = readFileClass.readImageAddressText(view);
        String image_path = cite_name + coffee_id + ".jpg";
        Glide.with(view).load(image_path).into(imageview_coffee);
    }
}
