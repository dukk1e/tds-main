package com.vnh.apptds.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.vnh.apptds.databinding.FragmentHomeBinding;
import com.vnh.apptds.entity.Account;
import com.vnh.apptds.services.OverlayService;
import com.vnh.apptds.utils.ApiUtils;
import com.vnh.apptds.utils.PreferenceUtils;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ApiUtils apiUtils;
    private Account account;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        apiUtils = new ApiUtils(requireContext());

        String savedToken = PreferenceUtils.getAccessToken(requireContext());
        if (savedToken != null && !savedToken.isEmpty()) {
            binding.editToken.setText(savedToken);
            getProfile(savedToken);
        }

        binding.btnSubmit.setOnClickListener(v -> {
            String token = binding.editToken.getText().toString().trim();
            if (token.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập token!", Toast.LENGTH_SHORT).show();
                return;
            }
            getProfile(token);
//            Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        });

        binding.btnStart.setOnClickListener(v -> {
            if (account == null) {
                Toast.makeText(getContext(), "Vui lòng đăng nhập trước!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String[] tiktokPackages = {
                        "com.ss.android.ugc.trill",
                        "com.zhiliaoapp.musically"
                };

                if (android.provider.Settings.canDrawOverlays(getContext())) {
                    // Khởi chạy OverlayService và truyền user, xu
                    Intent overlayService = new Intent(getContext(), OverlayService.class);
                    overlayService.putExtra("user", account.getUsername());
                    overlayService.putExtra("xu", account.getTotalCoin().toString());
                    getContext().startService(overlayService);
                } else {
                    Toast.makeText(getContext(), "Ứng dụng chưa được cấp quyền overlay", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean appFound = false;
                for (String pkg : tiktokPackages) {
                    try {
                        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(pkg);
                        if (intent != null) {
                            startActivity(intent);
                            appFound = true;
                            break;
                        }
                    } catch (Exception ignored) {}
                }

                if (!appFound) {
                    Toast.makeText(getContext(), "TikTok chưa được cài đặt", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Lỗi khi hiển thị overlay: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void getProfile(String token) {
        apiUtils.getTdsInfo(token, new ApiUtils.ApiCallback() {
            @Override
            public void onSuccess(Account acc) {
                account = acc;
                PreferenceUtils.saveAccessToken(requireContext(), binding.editToken.getText().toString().trim());

                getActivity().runOnUiThread(() -> {
                    binding.textUsername.setText("Tài khoản: " + acc.getUsername());
                    binding.textXu.setText("Xu: " + acc.getTotalCoin().toString());
                    binding.layoutUserInfo.setVisibility(View.VISIBLE);
                    binding.btnStart.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onError(String errorMessage) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Lỗi: " + errorMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
