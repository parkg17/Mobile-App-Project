package edu.skku.map.project_2017312665.ShoppingMall;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.skku.map.project_2017312665.Data.CoffeeItemData;
import edu.skku.map.project_2017312665.Data.Grade;
import edu.skku.map.project_2017312665.Data.LoginResultData;
import edu.skku.map.project_2017312665.NetworkPost;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class fragment_goods extends Fragment {
    private ListView coffee_list;
    private ShoppingMallAdapter shoppingMallAdapter;
    private SortJsonArrayClass sortJsonArrayClass;
    private ArrayList<CoffeeItemData> items;
    private String CoffeeItemJsonData;
    private String cite_name_append;
    private Spinner spinner;
    private Context fragment_context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_goods, container, false);
        fragment_context = container.getContext();
        setInit(view);

        spinner.setOnItemSelectedListener (
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    sortCoffeeItemArray(view, adapterView.getItemAtPosition(position).toString());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            }
        );

        return view;
    }


    public void sortCoffeeItemArray(View view, String sort_criteria) {
        items = getGoods(view, sort_criteria);
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);
        coffee_list.setAdapter(shoppingMallAdapter);
    }

    public ArrayList<CoffeeItemData> getGoods(View view, String sort_criteria) {

        ArrayList<CoffeeItemData> items = new ArrayList<CoffeeItemData>();

        Thread CoffeeItemThread = new Thread() {
            @Override
            public void run() {
                NetworkPost networkPost = new NetworkPost();
                String response = networkPost.post(cite_name_append, "");
                CoffeeItemJsonData = response;
            }
        };

        CoffeeItemThread.start();

        try {
            CoffeeItemThread.join();
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }

        if (CoffeeItemJsonData.equals("") || CoffeeItemJsonData.equals("ERROR")) {
            return items;
        }

        try {
            JSONArray coffeeItemJsonArray = new JSONArray(CoffeeItemJsonData);

            if(!sort_criteria.equals("기본순")) {
                coffeeItemJsonArray = SortArray(coffeeItemJsonArray, sort_criteria);
            }

            for(int idx = 0; idx < coffeeItemJsonArray.length(); ++idx) {
                JSONObject coffeeItemJsonObj = coffeeItemJsonArray.getJSONObject(idx);

                String coffee_id = coffeeItemJsonObj.getString("id");
                String coffee_name = coffeeItemJsonObj.getString("name");
                Integer coffee_stock = coffeeItemJsonObj.getInt("stock");
                double coffee_price = coffeeItemJsonObj.getDouble("price");
                double coffee_rating = coffeeItemJsonObj.getDouble("rating");
                Grade coffee_grade = Grade.valueOf(coffeeItemJsonObj.getString("grade"));
                String coffee_expiredDate = coffeeItemJsonObj.getString("expiredDate");
                String coffee_description = coffeeItemJsonObj.getString("description");

                items.add(new CoffeeItemData(coffee_id, coffee_name, coffee_stock, coffee_price,
                        coffee_rating, coffee_grade, coffee_expiredDate, coffee_description));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return items;
    }

    private JSONArray SortArray(JSONArray jsonArray, String sort_criteria) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonValues.add(jsonArray.getJSONObject(i));
            }
            switch(sort_criteria) {
                case "낮은 가격순":
                    jsonValues = sortJsonArrayClass.sortJsonArrayByDouble(jsonValues, "price", 1);
                    break;
                case "높은 가격순":
                    jsonValues = sortJsonArrayClass.sortJsonArrayByDouble(jsonValues, "price", -1);
                    break;
                case "별점 높은순":
                    jsonValues = sortJsonArrayClass.sortJsonArrayByDouble(jsonValues, "rating", -1);
                    break;
                case "별점 낮은순":
                    jsonValues = sortJsonArrayClass.sortJsonArrayByDouble(jsonValues, "rating", 1);
                    break;
                case "이름순":
                    jsonValues = sortJsonArrayClass.sortJsonArrayByString(jsonValues, "name");
                    break;
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    private void CoffeeItemDataNetworkAddressProcess(View view) {
        ReadFileClass readFileClass = new ReadFileClass();
        String cite_name = readFileClass.readText(
                view, fragment_context, "aws_coffee_db_address");
        cite_name_append = cite_name + "/getAll";
    }

    private void setInit(View view) {
        coffee_list = view.findViewById(R.id.CoffeeListview);
        spinner = view.findViewById(R.id.sort_standard_spinner);
        items = getGoods(view, "기본순");
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);
        coffee_list.setAdapter(shoppingMallAdapter);
        sortJsonArrayClass = new SortJsonArrayClass();
        CoffeeItemDataNetworkAddressProcess(view);
    }
}
