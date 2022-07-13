package edu.skku.map.project_2017312665;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadFileClass {

    /* Communicate with AWS Address By raw_file_name */
    public String readText(View view, Context context, String raw_file_name) {
        int idx;
        String data = null;
        int rawNum = view.getResources().getIdentifier(raw_file_name, "raw", context.getPackageName());
        InputStream inputStream = view.getResources().openRawResource(rawNum);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            idx = inputStream.read();
            while (idx != -1) {
                byteArrayOutputStream.write(idx);
                idx = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
