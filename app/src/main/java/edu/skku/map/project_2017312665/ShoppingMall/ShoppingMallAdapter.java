package edu.skku.map.project_2017312665.ShoppingMall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.skku.map.project_2017312665.Data.CoffeeItemData;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

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

        /* Declaration Text, Image and Setting them */
        String input_coffee_name = items.get(i).getName();
        String input_coffee_description = items.get(i).getDescription();
        String input_coffee_price = String.valueOf(items.get(i).getPrice()) + "원";
        String input_coffee_image_name = items.get(i).getId();

        TextView textview_coffee_name = (TextView) view.findViewById(R.id.coffee_name);
        TextView textview_coffee_price = (TextView) view.findViewById(R.id.coffee_price);
        TextView textview_coffee_description = (TextView) view.findViewById(R.id.coffee_description);
        ImageView imageview_coffee  = (ImageView) view.findViewById(R.id.coffeeimageView);

        textview_coffee_name.setText(input_coffee_name);
        textview_coffee_price.setText(input_coffee_price);
        textview_coffee_description.setText(input_coffee_description);

        ReadFileClass readFileClass = new ReadFileClass();
        String cite_name = readFileClass.readImageAddressText(view);
        String image_path = cite_name + items.get(i).getId() + ".jpg";
        Glide.with(view).load(image_path).into(imageview_coffee);

        /* List Item Click Event */
        View view_array[] = new View[] {textview_coffee_name, textview_coffee_price,
                                        textview_coffee_description, imageview_coffee};

        for (int view_idx = 0; view_idx < 4; ++view_idx) {
            view_array[view_idx].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ShoppingMallActivity)ShoppingMallActivity.mContext).open_CoffeeGoodsActivity(
                            input_coffee_name, input_coffee_price, input_coffee_description, input_coffee_image_name);
                }
            });
        }

        return view;
    }
}
