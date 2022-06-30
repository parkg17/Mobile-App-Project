package edu.skku.map.project_2017312665.CoffeeGoods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import edu.skku.map.project_2017312665.R;

public class ImageActivity extends AppCompatActivity {
    private Context mContext;
    private String coffee_img_name;
    private ImageView imageView;

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        getData();
        setInit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mContext = this;

    }

    private void getData() {
        Intent intent = getIntent();
        coffee_img_name = intent.getStringExtra("COFFEE_IMG_NAME");
    }

    private void setInit() {
        imageView = (ImageView) findViewById(R.id.image_full);
        String image_path = "@drawable/" + coffee_img_name;
        int iResId = this.getResources().getIdentifier( image_path, "drawable", this.getPackageName() );
        imageView.setImageResource(iResId);

        imageView.setOnClickListener(view -> {
            supportFinishAfterTransition();
        });
    }
}
