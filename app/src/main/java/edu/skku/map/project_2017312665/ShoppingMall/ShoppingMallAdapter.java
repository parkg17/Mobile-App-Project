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
import edu.skku.map.project_2017312665.Data.Grade;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class ShoppingMallAdapter extends BaseAdapter {

    private final ArrayList<CoffeeItemData> items;
    private final Context mContext;

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
        String input_coffee_id = items.get(i).getId();
        String input_coffee_name = items.get(i).getName();
        Integer input_coffee_stock = items.get(i).getStock();
        double input_coffee_price = items.get(i).getPrice();
        String input_coffee_price_str = input_coffee_price + "원";
        double input_coffee_rating = items.get(i).getRating();
        Grade input_coffee_grade = items.get(i).getGrade();
        String input_coffee_expiredDate = items.get(i).getExpiredDate();
        String input_coffee_description = items.get(i).getDescription();

        TextView textview_coffee_name = (TextView) view.findViewById(R.id.coffee_name);
        TextView textview_coffee_price = (TextView) view.findViewById(R.id.coffee_price);
        TextView textview_coffee_description = (TextView) view.findViewById(R.id.coffee_description);
        ImageView imageview_coffee  = (ImageView) view.findViewById(R.id.coffeeimageView);

        textview_coffee_name.setText(input_coffee_name);
        textview_coffee_price.setText(input_coffee_price_str);
        textview_coffee_description.setText(input_coffee_description);

        ReadFileClass readFileClass = new ReadFileClass();
        String cite_name = readFileClass.readText(view, mContext, "aws_image_address");
        String image_path = cite_name + items.get(i).getId() + ".jpg";
        Glide.with(view).load(image_path).into(imageview_coffee);

        /* List Item Click Event */
        View[] view_array = new View[] {textview_coffee_name, textview_coffee_price,
                                        textview_coffee_description, imageview_coffee};

        for (int view_idx = 0; view_idx < 4; ++view_idx) {
            view_array[view_idx].setOnClickListener(v ->
                    ((ShoppingMallActivity)ShoppingMallActivity.mContext).open_CoffeeGoodsActivity(
                        input_coffee_id, input_coffee_name, input_coffee_stock, input_coffee_price,
                        input_coffee_rating, input_coffee_grade, input_coffee_expiredDate,
                        input_coffee_description));
        }

        return view;
    }
}
