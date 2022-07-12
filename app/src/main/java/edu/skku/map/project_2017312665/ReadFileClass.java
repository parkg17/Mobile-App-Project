package edu.skku.map.project_2017312665;

import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadFileClass {

    /* Communicate with AWS Login Address */
    public String readLoginAddressText(View view) {
        int idx;
        String data = null;
        InputStream inputStream = view.getResources().openRawResource(R.raw.aws_login_address);
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

    /* Communicate with AWS Login Address */
    public String readImageAddressText(View view) {
        int idx;
        String data = null;
        InputStream inputStream = view.getResources().openRawResource(R.raw.aws_image_address);
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

    /* Read Common txt File */
    public String readText(View view) {
        int idx;
        String data = null;
        InputStream inputStream = view.getResources().openRawResource(R.raw.coffee_goods_info);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            idx = inputStream.read();
            while (idx != -1) {
                byteArrayOutputStream.write(idx);
                idx = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(),"UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
