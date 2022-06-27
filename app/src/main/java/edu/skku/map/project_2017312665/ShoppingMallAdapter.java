package edu.skku.map.project_2017312665;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingMallAdapter extends BaseAdapter {

    private ArrayList<CoffeeItemData> items;
    private Context mContext;

    ShoppingMallAdapter(Context mContext, ArrayList<CoffeeItemData> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }
        
        String input_coffee_name = items.get(i).getName();
        String input_coffee_description = items.get(i).getDescription();
        String input_coffee_price = String.valueOf(items.get(i).getPrice()) + "원";
        String input_coffee_image_name = items.get(i).getImage_name();

        TextView textview_coffee_name = (TextView) view.findViewById(R.id.coffee_name);
        TextView textview_coffee_price = (TextView) view.findViewById(R.id.coffee_price);
        TextView textview_coffee_description = (TextView) view.findViewById(R.id.coffee_description);
        ImageView imageview_coffee  = (ImageView) view.findViewById(R.id.coffeeimageView);

        textview_coffee_name.setText(input_coffee_name);
        textview_coffee_price.setText(input_coffee_price);
        textview_coffee_description.setText(input_coffee_description);

        String image_path = "@drawable/" + input_coffee_image_name;
        int iResId = mContext.getResources().getIdentifier( image_path, "drawable", mContext.getPackageName() );
        imageview_coffee.setImageResource(iResId);

        imageview_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, i + "번째 이미지 선택", Toast.LENGTH_SHORT).show();
            }
        });

        textview_coffee_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, i + "번째 커피 이름 선택", Toast.LENGTH_SHORT).show();
            }
        });

        textview_coffee_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, i + "번째 커피 설명 선택", Toast.LENGTH_SHORT).show();
            }
        });

        textview_coffee_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, i + "번째 커피 가격 선택", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}