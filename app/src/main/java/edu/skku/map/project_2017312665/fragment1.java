package edu.skku.map.project_2017312665;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment1 extends Fragment {
    private ListView coffee_list;
    private ShoppingMallAdapter shoppingMallAdapter;
    private ArrayList<CoffeeItemData> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment1, container, false);

        coffee_list = view.findViewById(R.id.CoffeeListview);
        items = new ArrayList<CoffeeItemData>();
        shoppingMallAdapter = new ShoppingMallAdapter(view.getContext(), items);

        items.add(new CoffeeItemData("케냐 스페셜 카투리리 AA", "풍부한 아로마, 청량감이 느껴지는 인산이 강한 산미," +
                "달콤함과 균형 잡힌 밸런스, 풍부한 바디감과 긴 여운이 일품인 고급 커피.", "kenya", 9900));
        items.add(new CoffeeItemData("엘살바도르 마이크로랏 스페셜", "생두가 큰 편이지만 무게는 가벼운 편이며 부드러운 아로마와 가벼운 산미" +
                "깨끗한 맛과 달콤한 밸런스가 특징으로 블렌딩에도 많이 사용됨.",
                "elsalvador", 19900));
        items.add(new CoffeeItemData("이디오피아 리무 워시드", "대체로 풍미, 오묘한 신맛이 좋으며 꽃, 과일 또는 와인" +
                " 향이 나기로 유명. 대표적인 커피인 이디오피아 리무는 서부지역에서 재배됨.", "ethiopia", 9500));
        items.add(new CoffeeItemData("과테말라 메디나허니", "산미의 깊은 맛에서부터 올라오는 단향을 느낄 수 있는 커피." +
                " 초콜릿풍의 단맛, 꽈찬 풍부함, 와인 느낌의 과일향.", "guatemala", 19900));
        items.add(new CoffeeItemData("카메룬 블루마운틴", "도드라지지 않는 은은한 맛과 산미, 그리고 초콜릿 맛의 향미를" +
                " 느낄 수 있는 특별한 커피. 장미향, 산미, 캐러멜, 부드러움.", "cameroon", 8900));
        items.add(new CoffeeItemData("브라질 비뉴", "달콤하고 강렬한 풀바디감. 레드와인을 마시는 듯한 아로마의 풍미." +
                " 숙성된 포도의 맛과 혀끝에 길게 남는 달콤한 향기.", "brazil", 14900));
        coffee_list.setAdapter(shoppingMallAdapter);

        ShoppingMallActivity activity = (ShoppingMallActivity) getActivity();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST","현재 포커스=>"+activity.getCurrentFocus());
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    //do something
                }
                return true;
            }
        });

        coffee_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TEST","현재 포커스=>"+activity.getCurrentFocus());
                CoffeeItemData coffeeData = (CoffeeItemData) shoppingMallAdapter.getItem(position);
                Toast.makeText(view.getContext(), coffeeData.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
