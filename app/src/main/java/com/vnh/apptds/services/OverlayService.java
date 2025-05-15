package com.vnh.apptds.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.vnh.apptds.R;
import com.vnh.apptds.ui.overlay.OverlayDataManager;

public class OverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;
//    private TextView txtUsername;
//    private TextView txtXu;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Khởi tạo WindowManager
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Inflate layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        // Cấu hình params cho overlay window
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );

        // Hiển thị ở góc trên bên trái
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 24;
        params.y = 48;

        // Thêm view vào màn hình
        windowManager.addView(overlayView, params);
//        txtUsername = overlayView.findViewById(R.id.txtUsername);
//        txtXu = overlayView.findViewById(R.id.txtXu);
//
//
//
//        // Chố này em bảo huynh nè
//        if (intent != null) {
//            String username = intent.getStringExtra("username");
//            String xu = intent.getStringExtra("xu");
//
//            Log.d("OverlayTest", "Received user: " + username + ", xu: " + xu);
//            if (txtUsername != null) {
//                txtUsername.setText("Username: " + username);
//            }
//
//           if (txtXu != null) {
//              txtXu.setText("Xu: " + xu);
//            }
//        }

        // Xử lý nút đóng (X)
            ImageButton btnClose = overlayView.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(v -> {
            if (windowManager != null && overlayView != null) {
                windowManager.removeView(overlayView);
                overlayView = null;
            }
            stopSelf();
            android.os.Process.killProcess(android.os.Process.myPid());
        });

        OverlayDataManager.getInstance().update("test_user", 5, 100, 50, "Đã cập nhật dữ liệu");


        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }
}
