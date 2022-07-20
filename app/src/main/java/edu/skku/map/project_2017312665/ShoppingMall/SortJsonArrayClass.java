package edu.skku.map.project_2017312665.ShoppingMall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SortJsonArrayClass {
    public List<JSONObject> sortJsonArrayByString (List<JSONObject> jsonValues, String KEY_NAME) {
        jsonValues.sort((a, b) -> {
            String valA = "";
            String valB = "";
            try {
                valA = (String) a.get(KEY_NAME);
                valB = (String) b.get(KEY_NAME);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return valA.compareTo(valB);
        });
        return jsonValues;
    }

    public List<JSONObject> sortJsonArrayByDouble (List<JSONObject> jsonValues, String KEY_NAME, int isReverse) {
        jsonValues.sort((a, b) -> {
            double valA = 0.0;
            double valB = 0.0;
            try {
                valA = Double.parseDouble(String.valueOf(a.get(KEY_NAME)));
                valB = Double.parseDouble(String.valueOf(b.get(KEY_NAME)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (valA > valB) {
                return isReverse;
            } else if (valA < valB) {
                return -isReverse;
            } else {
                return 0;
            }
        });
        return jsonValues;
    }
}
