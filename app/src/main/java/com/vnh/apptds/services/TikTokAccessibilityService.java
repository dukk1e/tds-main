package com.vnh.apptds.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class TikTokAccessibilityService extends AccessibilityService {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean hasClicked = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!hasClicked && event.getPackageName().toString().contains("com.ss.android.ugc.trill")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                    if (rootNode != null) {
                        clickByViewId(rootNode, "com.ss.android.ugc.trill:id/k6f");
                        updateOverlayUsername("dongocduc");
                    }
                }
            }, 3000);
            hasClicked = true;
        }
    }

    private void updateOverlayUsername(String newUsername) {
        Intent serviceIntent = new Intent(this, OverlayService.class);
        serviceIntent.putExtra("username", newUsername);

        // Sử dụng startService() thay vì bind
        startService(serviceIntent);

        // Hoặc nếu dùng ContextCompat cho API 26+
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private boolean clickByViewId(AccessibilityNodeInfo node, String viewId) {
        if (node == null) return false;

        if (viewId.equals(node.getViewIdResourceName())) {
            if (node.isClickable()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (clickByViewId(child, viewId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onInterrupt() {}
}
