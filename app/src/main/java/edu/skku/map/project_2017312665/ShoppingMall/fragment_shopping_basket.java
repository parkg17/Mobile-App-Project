package edu.skku.map.project_2017312665.ShoppingMall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import edu.skku.map.project_2017312665.Data.CoffeeItemData;
import edu.skku.map.project_2017312665.R;

public class fragment_shopping_basket extends Fragment {
    private ListView coffee_list;
    private ShoppingMallAdapter shoppingMallAdapter;
    private ArrayList<CoffeeItemData> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shopping_basket, container, false);

        coffee_list = view.findViewById(R.id.Coffeebasket);
        items = new ArrayList<CoffeeItemData>();
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);

        items.add(new CoffeeItemData("케냐 스페셜 카투리리 AA", "풍부한 아로마, 청량감이 느껴지는 인산이 강한 산미," +
                "달콤함과 균형 잡힌 밸런스, 풍부한 바디감과 긴 여운이 일품인 고급 커피.", "kenya", 9900));
        coffee_list.setAdapter(shoppingMallAdapter);

        return view;
    }
}