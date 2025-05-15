package com.vnh.apptds.services;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vnh.apptds.ui.overlay.OverlayDataManager;

public class TikTokAccessibilityService extends AccessibilityService {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean hasClicked = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        // Gửi dữ liệu lên Overlay
        OverlayDataManager.getInstance().update(
                "dongocduc",
                5,
                250,
                30,
                "Xem hồ sơ"
        );
        Log.d("TikTokService", "Gửi dữ liệu Overlay xong");
        if (!hasClicked && event.getPackageName() != null &&
                event.getPackageName().toString().contains("com.ss.android.ugc.trill")) {

            handler.postDelayed(() -> {
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                if (rootNode != null) {
                    boolean clicked = clickByViewId(rootNode, "com.ss.android.ugc.trill:id/k6f");

                }
            }, 3000);

            hasClicked = true;
        }
    }

    private boolean clickByViewId(AccessibilityNodeInfo node, String viewId) {
        if (node == null) return false;

        if (viewId.equals(node.getViewIdResourceName()) && node.isClickable()) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            return true;
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
