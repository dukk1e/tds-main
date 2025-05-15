package com.vnh.apptds.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.vnh.apptds.databinding.FragmentDashboardBinding;
import com.vnh.apptds.utils.PreferenceUtils;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Context context = requireContext();

        // Load các giá trị đã lưu từ SharedPreferences, gán vào UI
        binding.inputRandomMin.setText(String.valueOf(PreferenceUtils.getRandomMin(context)));
        binding.inputRandomMax.setText(String.valueOf(PreferenceUtils.getRandomMax(context)));
        binding.inputJobToGetCoin.setText(String.valueOf(PreferenceUtils.getJobToGetCoin(context)));
        binding.inputSwitchAccountDelay.setText(String.valueOf(PreferenceUtils.getSwitchAccountDelay(context)));
        binding.inputDelayBetweenTasks.setText(String.valueOf(PreferenceUtils.getDelayBetweenTasks(context)));
        switch (PreferenceUtils.getTaskType(context)) {
            case "Follow":
                binding.radioFollow.setChecked(true);
                break;
            case "Tym":
                binding.radioTym.setChecked(true);
                break;
            case "Comment":
                binding.radioComment.setChecked(true);
                break;
            default:
                binding.radioFollow.setChecked(true);
        }

        // Nút lưu
        binding.btnSaveSettings.setOnClickListener(v -> {
            try {
                int randomMin = Integer.parseInt(binding.inputRandomMin.getText().toString().trim());
                int randomMax = Integer.parseInt(binding.inputRandomMax.getText().toString().trim());
                int jobToGetCoin = Integer.parseInt(binding.inputJobToGetCoin.getText().toString().trim());
                int switchAccountDelay = Integer.parseInt(binding.inputSwitchAccountDelay.getText().toString().trim());
                int delayBetweenTasks = Integer.parseInt(binding.inputDelayBetweenTasks.getText().toString().trim());
                String taskType;
                if (binding.radioFollow.isChecked()) {
                    taskType = "Follow";
                } else if (binding.radioTym.isChecked()) {
                    taskType = "Tym";
                } else if (binding.radioComment.isChecked()) {
                    taskType = "Comment";
                } else {
                    taskType = "Follow";
                }

                // Kiểm tra số âm (trừ switchAccountDelay có thể = 0)
                if (randomMin < 0 || randomMax < 0 || jobToGetCoin < 0 || delayBetweenTasks < 0) {
                    Toast.makeText(getContext(), "Các trường không được nhập số âm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (switchAccountDelay < 0) {
                    Toast.makeText(getContext(), "Trường 'Đổi acc sau số job' không được nhập số âm!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (randomMin > randomMax) {
                    Toast.makeText(getContext(), "Giá trị Min không được lớn hơn Max!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu dữ liệu vào SharedPreferences (ví dụ)
                PreferenceUtils.saveRandomMin(getContext(), randomMin);
                PreferenceUtils.saveRandomMax(getContext(), randomMax);
                PreferenceUtils.saveJobToGetCoin(getContext(), jobToGetCoin);
                PreferenceUtils.saveSwitchAccountDelay(getContext(), switchAccountDelay);
                PreferenceUtils.saveDelayBetweenTasks(getContext(), delayBetweenTasks);
                PreferenceUtils.saveTaskType(getContext(), taskType);

                Toast.makeText(getContext(), "Lưu cài đặt thành công!", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ và đúng định dạng số!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private int parseIntOrDefault(String str, int def) {
        if (TextUtils.isEmpty(str)) return def;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}