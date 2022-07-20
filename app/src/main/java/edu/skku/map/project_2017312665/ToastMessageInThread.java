package edu.skku.map.project_2017312665;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastMessageInThread {
    public void ToastMessage(Context context, String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show(), 0);
    }
}
