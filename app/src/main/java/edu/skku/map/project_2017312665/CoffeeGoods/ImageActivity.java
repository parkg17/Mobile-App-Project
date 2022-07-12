package edu.skku.map.project_2017312665.CoffeeGoods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class ImageActivity extends AppCompatActivity {

    /* Declare Variables */
    private int iResId;
    private Context mContext;
    private String coffee_id;
    private String image_path;
    private String cite_name;
    private ImageView imageView;
    private ReadFileClass readFileClass;
    private View view;

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setInit();
        mContext = this;
    }

    private void getData() {
        Intent intent = getIntent();
        coffee_id = intent.getStringExtra("COFFEE_ID");
    }

    private void setInit() {
        imageView = (ImageView) findViewById(R.id.image_full);
        view = getLayoutInflater().inflate(R.layout.activity_image, null);
        cite_name = readFileClass.readImageAddressText(view);
        image_path = cite_name + coffee_id + ".jpg";
        Glide.with(view).load(image_path).into(imageView);

        imageView.setOnClickListener(view -> {
            supportFinishAfterTransition();
        });
    }
}
