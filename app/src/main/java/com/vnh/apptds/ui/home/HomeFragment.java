package com.vnh.apptds.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vnh.apptds.databinding.FragmentHomeBinding;
import com.vnh.apptds.services.OverlayService;

import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String user;
    private String xu;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        // Gửi token
        binding.btnSubmit.setOnClickListener(view -> {
            String token = binding.editToken.getText().toString().trim();

            if (token.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập token!", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "https://traodoisub.com/api/?fields=profile&access_token=" + token;
            RequestQueue queue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    response -> {
                        if (response.has("error")) {
                            String error = response.optString("error");
                            Toast.makeText(getContext(), "Lỗi: " + error, Toast.LENGTH_LONG).show();
                        } else {
                            JSONObject data = response.optJSONObject("data");
                            if (data != null) {
                                user = data.optString("user");
                                xu = data.optString("xu");

                                // Hiện thông tin
                                binding.textUsername.setText("Tài khoản: " + user);
                                binding.textXu.setText("Xu: " + xu);

                                binding.layoutUserInfo.setVisibility(View.VISIBLE);
                                binding.btnStart.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    error -> Toast.makeText(getContext(), "Lỗi mạng: " + error.getMessage(), Toast.LENGTH_LONG).show()
            );

            queue.add(request);
        });

        binding.btnStart.setOnClickListener(view -> {
            try {
                // Danh sách các package name TikTok (ưu tiên theo thứ tự)
                // ⚠️ Đoạn này liên quan đến việc mở TikTok - có thể tạm bỏ qua nếu không cần
        /*
        String[] tiktokPackages = {
                "com.ss.android.ugc.trill",    // TikTok phiên bản quốc tế mới
                "com.zhiliaoapp.musically"     // TikTok tên cũ
        };

        boolean appFound = false;
        */

                // 👉 Khởi động OverlayService trước
                if (Settings.canDrawOverlays(getContext())) {
                    Intent overlayService = new Intent(getContext(), OverlayService.class);

                    // huynh tham khảo cái chỗ này nha, em k nhớ huynh làm hay em làm nữa :)))
                    overlayService.putExtra("user", user);
                    overlayService.putExtra("xu", xu);

                    getContext().startService(overlayService);
                } else {
                    Toast.makeText(getContext(), "Ứng dụng chưa được cấp quyền overlay", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ⚠️ Tạm thời không cần mở TikTok, có thể ẩn đoạn này
        /*
        // 👉 Thử mở TikTok
        for (String pkg : tiktokPackages) {
            try {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(pkg);
                if (intent != null) {
                    startActivity(intent);
                    appFound = true;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }

        if (!appFound) {
            Toast.makeText(getContext(), "TikTok chưa được cài đặt", Toast.LENGTH_SHORT).show();

            // 👉 Mở CH Play để cài
            try {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
                playStoreIntent.setData(Uri.parse("market://details?id=" + tiktokPackages[0]));
                startActivity(playStoreIntent);
            } catch (Exception e) {
                // 👉 Nếu không mở được CH Play thì mở trình duyệt
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + tiktokPackages[0]));
                startActivity(browserIntent);
            }
        }
        */

            } catch (Exception e) {
                Toast.makeText(getContext(), "Lỗi khi hiển thị overlay: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
