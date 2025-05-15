package com.vnh.apptds.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vnh.apptds.entity.Account;

import org.json.JSONObject;

import java.math.BigDecimal;

public class ApiUtils {
    private final RequestQueue requestQueue;

    public ApiUtils(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public interface ApiCallback {
        void onSuccess(Account account);
        void onError(String errorMessage);
    }

    public void getTdsInfo(String token, ApiCallback callback) {
        String url = "https://traodoisub.com/api/?fields=profile&access_token=" + token;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    if (response.has("error")) {
                        String error = response.optString("error");
                        callback.onError(error);
                    } else {
                        JSONObject data = response.optJSONObject("data");
                        if (data != null) {
                            Account account = new Account();
                            account.setUsername(data.optString("user"));
                            account.setTotalCoin(new BigDecimal(data.optString("xu", "0")));
                            callback.onSuccess(account);
                        } else {
                            callback.onError("Không có dữ liệu");
                        }
                    }
                },
                error -> callback.onError(error.getMessage())
        );

        requestQueue.add(request);
    }
}
