package edu.skku.map.project_2017312665.ShoppingMall;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import edu.skku.map.project_2017312665.R;

public class fragment_information extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        String print_id = bundle.getString("id");
        String print_name = bundle.getString("name");
        String print_phone = bundle.getString("phone");

        TextView textView_id = (TextView) view.findViewById(R.id.textView_id);
        TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
        TextView textView_phone = (TextView) view.findViewById(R.id.textView_phone);
        Button logout_btn = (Button) view.findViewById(R.id.logout);

        textView_id.setText(print_id);
        textView_name.setText(print_name);
        textView_phone.setText(print_phone);

        Activity activity = getActivity();
        if (activity == null || getContext() == null) {
            return view;
        }

        logout_btn.setOnClickListener(v -> activity.finish());

        return view;
    }
}
