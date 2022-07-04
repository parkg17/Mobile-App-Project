package edu.skku.map.project_2017312665.ShoppingMall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.skku.map.project_2017312665.Data.CoffeeItemData;
import edu.skku.map.project_2017312665.Data.Grade;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class fragment_goods extends Fragment {
    private ListView coffee_list;
    private ShoppingMallAdapter shoppingMallAdapter;
    private ArrayList<CoffeeItemData> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_goods, container, false);

        coffee_list = view.findViewById(R.id.CoffeeListview);
        items = getGoods(view);
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);
        coffee_list.setAdapter(shoppingMallAdapter);

        return view;
    }

    public ArrayList<CoffeeItemData> getGoods(View view) {
        ArrayList<CoffeeItemData> items = new ArrayList<CoffeeItemData>();
        ReadFileClass readFileClass = new ReadFileClass();
        String CoffeeItemJsonData = readFileClass.readText(view);

        try {
            JSONArray coffeeItemJsonArray = new JSONArray(CoffeeItemJsonData);
            for(int idx = 0; idx < coffeeItemJsonArray.length(); ++idx) {
                JSONObject coffeeItemJsonObj = coffeeItemJsonArray.getJSONObject(idx);

                String coffee_id = coffeeItemJsonObj.getString("Id");
                String coffee_name = coffeeItemJsonObj.getString("name");
                double coffee_price = coffeeItemJsonObj.getDouble("price");
                double coffee_rating = coffeeItemJsonObj.getDouble("rating");
                Grade coffee_grade = Grade.valueOf(coffeeItemJsonObj.getString("grade"));
                String coffee_expiredDate = coffeeItemJsonObj.getString("expiredDate");
                String coffee_description = coffeeItemJsonObj.getString("description");

                items.add(new CoffeeItemData(coffee_id, coffee_name, coffee_price,
                        coffee_rating, coffee_grade, coffee_expiredDate, coffee_description));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return items;
    }
}
