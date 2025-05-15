package com.vnh.apptds.ui.overlay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vnh.apptds.databinding.OverlayLayoutBinding;

public class OverlayFragment extends Fragment {

    private OverlayLayoutBinding binding;
    private OverlayViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = OverlayLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(OverlayViewModel.class);

        // Quan sát và cập nhật dữ liệu UI
        viewModel.getUsername().observe(getViewLifecycleOwner(), username ->
                binding.txtUsername.setText("Username: " + username));

        viewModel.getJobCount().observe(getViewLifecycleOwner(), jobCount ->
                binding.txtJobCount.setText("Đã tìm thấy: " + jobCount + " job"));

        viewModel.getXu().observe(getViewLifecycleOwner(), xu ->
                binding.txtXu.setText("Xu hiện tại: " + xu));

        viewModel.getCoinEarned().observe(getViewLifecycleOwner(), coinEarned ->
                binding.txtCoinEarned.setText("Xu kiếm: " + coinEarned));

        viewModel.getLog().observe(getViewLifecycleOwner(), log ->
                binding.txtLog.setText(log));

        OverlayDataManager.getInstance().update("test_user", 5, 100, 50, "Đã cập nhật dữ liệu");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
