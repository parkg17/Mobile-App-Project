package edu.skku.map.project_2017312665;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class fragment2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        Bundle bundle = getArguments();
        String print_id = bundle.getString("id");
        String print_name = bundle.getString("name");
        String print_phone = bundle.getString("phone");

        TextView Stringid = (TextView) view.findViewById(R.id.textView_id);
        TextView Stringname = (TextView) view.findViewById(R.id.textView_name);
        TextView Stringphone = (TextView) view.findViewById(R.id.textView_phone);
        Button logout_btn = (Button) view.findViewById(R.id.logout);

        Stringid.setText(print_id);
        Stringname.setText(print_name);
        Stringphone.setText(print_phone);

        logout_btn.setOnClickListener(v -> {
            getActivity().finish();
        });

        return view;
    }
}
