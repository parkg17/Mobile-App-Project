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
import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;

public class CoffeeGoodsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_goods);

        /* Taking Data from Intent */
        Intent intent = getIntent();
        String coffee_name = intent.getStringExtra("COFFEE_NAME");
        String coffee_price = intent.getStringExtra("COFFEE_PRICE");
        String coffee_description = intent.getStringExtra("COFFEE_DESCRIPTION");
        String coffee_img_name = intent.getStringExtra("COFFEE_IMG_NAME");

        TextView textview_coffee_name = (TextView) findViewById(R.id.coffee_goods_name);
        TextView textview_coffee_price = (TextView) findViewById(R.id.coffee_goods_price);
        TextView textview_coffee_description = (TextView) findViewById(R.id.coffee_goods_description);
        ImageView imageview_coffee  = (ImageView) findViewById(R.id.coffee_goods_image);

        textview_coffee_name.setText(coffee_name);
        textview_coffee_price.setText(coffee_price);
        textview_coffee_description.setText(coffee_description);

        String image_path = "@drawable/" + coffee_img_name;
        int iResId = this.getResources().getIdentifier( image_path, "drawable", this.getPackageName() );
        imageview_coffee.setImageResource(iResId);

        /* Setting Button Function */
        ImageButton btn_back = (ImageButton) findViewById(R.id.Btn_back);
        ImageButton btn_shopping_cart = (ImageButton) findViewById(R.id.Btn_shopping_cart);
        Button btn_basket = (Button) findViewById(R.id.Btn_basket);
        Button btn_purchase = (Button) findViewById(R.id.Btn_purchase);

        imageview_coffee.setOnClickListener(view -> {
            Intent put_intent = new Intent(CoffeeGoodsActivity.this, ImageActivity.class);
            put_intent.putExtra("COFFEE_IMG_NAME", coffee_img_name);

            ActivityOptions opt =
                    ActivityOptions.makeSceneTransitionAnimation(CoffeeGoodsActivity.this, view, "img_trans");

            //startActivity(put_intent, optionsCompat.toBundle());
            startActivity(put_intent, opt.toBundle());
        });

        btn_back.setOnClickListener(view -> {
            finish();
        });

        btn_shopping_cart.setOnClickListener(view -> {
            finish();
        });
    }
}
