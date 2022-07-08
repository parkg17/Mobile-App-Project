package edu.skku.map.project_2017312665.ShoppingMall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortJsonArrayClass {
    public List<JSONObject> sortJsonArrayByString (List<JSONObject> jsonValues, String key_name) {
        String KEY_NAME = key_name;
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
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
        return jsonValues;
    }

    public List<JSONObject> sortJsonArrayByDouble (List<JSONObject> jsonValues, String key_name, int isReverse) {
        String KEY_NAME = key_name;

        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a, JSONObject b) {
                Double valA = 0.0;
                Double valB = 0.0;
                try {
                    valA = Double.parseDouble(String.valueOf(a.get(KEY_NAME)));
                    valB = Double.parseDouble(String.valueOf(b.get(KEY_NAME)));
                }
                catch (JSONException e) {
                }
                if (valA > valB) {
                    return isReverse;
                }
                else if (valA < valB) {
                    return -isReverse;
                }
                else {
                    return 0;
                }
            }
        });
        return jsonValues;
    }
}
