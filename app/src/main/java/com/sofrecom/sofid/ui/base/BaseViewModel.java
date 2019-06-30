package com.sofrecom.sofid.ui.base;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by oibnchahdia on 11/04/2019
 */
public abstract class BaseViewModel extends ViewModel {

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public MutableLiveData<ApiResponse> getResponseLiveData() {
        return responseLiveData;
    }
}
