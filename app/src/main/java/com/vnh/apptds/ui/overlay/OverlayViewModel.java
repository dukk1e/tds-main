package com.vnh.apptds.ui.overlay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OverlayViewModel extends ViewModel {

    private final OverlayDataManager dataManager = OverlayDataManager.getInstance();

    public LiveData<String> getUsername() {
        return dataManager.getUsername();
    }

    public LiveData<Integer> getJobCount() {
        return dataManager.getJobCount();
    }

    public LiveData<Integer> getXu() {
        return dataManager.getXu();
    }

    public LiveData<Integer> getCoinEarned() {
        return dataManager.getCoinEarned();
    }

    public LiveData<String> getLog() {
        return dataManager.getLog();
    }
}
