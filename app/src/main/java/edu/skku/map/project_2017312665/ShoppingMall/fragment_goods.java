package edu.skku.map.project_2017312665.ShoppingMall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.skku.map.project_2017312665.Data.CoffeeItemData;
import edu.skku.map.project_2017312665.Data.Grade;
import edu.skku.map.project_2017312665.R;
import edu.skku.map.project_2017312665.ReadFileClass;

public class fragment_goods extends Fragment {
    private ListView coffee_list;
    private ShoppingMallAdapter shoppingMallAdapter;
    private ArrayList<CoffeeItemData> items;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_goods, container, false);
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
        ReadFileClass readFileClass = new ReadFileClass();
        String CoffeeItemJsonData = readFileClass.readText(view);

        try {
            JSONArray coffeeItemJsonArray = new JSONArray(CoffeeItemJsonData);

            if(!sort_criteria.equals("INIT")) {
                coffeeItemJsonArray = SortArray(coffeeItemJsonArray, sort_criteria);
            }

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

    private JSONArray SortArray(JSONArray jsonArray, String sort_criteria) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonValues.add(jsonArray.getJSONObject(i));
            }
            Collections.sort( jsonValues, new Comparator<JSONObject>() {
                String KEY_NAME = "name";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();
                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                    }
                    catch (JSONException e) {
                    }
                    return valA.compareTo(valB);
                }
            });
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    private void setInit(View view) {
        coffee_list = view.findViewById(R.id.CoffeeListview);
        spinner = view.findViewById(R.id.sort_standard_spinner);
        items = getGoods(view, "INIT");
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);
        coffee_list.setAdapter(shoppingMallAdapter);
    }
}
