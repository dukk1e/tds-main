package com.vnh.apptds.ui.overlay;

import androidx.lifecycle.MutableLiveData;

public class OverlayDataManager {

    private static OverlayDataManager instance;

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<Integer> jobCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> xu = new MutableLiveData<>();
    private final MutableLiveData<Integer> coinEarned = new MutableLiveData<>();
    private final MutableLiveData<String> log = new MutableLiveData<>();

    public static synchronized OverlayDataManager getInstance() {
        if (instance == null) {
            instance = new OverlayDataManager();
        }
        return instance;
    }

    public void update(String username, int jobCount, int xu, int coinEarned, String log) {
        this.username.postValue(username);
        this.jobCount.postValue(jobCount);
        this.xu.postValue(xu);
        this.coinEarned.postValue(coinEarned);
        this.log.postValue(log);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<Integer> getJobCount() {
        return jobCount;
    }

    public MutableLiveData<Integer> getXu() {
        return xu;
    }

    public MutableLiveData<Integer> getCoinEarned() {
        return coinEarned;
    }

    public MutableLiveData<String> getLog() {
        return log;
    }
}
