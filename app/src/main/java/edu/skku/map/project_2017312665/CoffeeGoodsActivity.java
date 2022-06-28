package edu.skku.map.project_2017312665;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoffeeGoodsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffee_goods);

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

    }
}
