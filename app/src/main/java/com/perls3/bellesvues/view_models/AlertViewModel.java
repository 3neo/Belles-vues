package com.perls3.bellesvues.view_models;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.perls3.bellesvues.App;
import com.perls3.bellesvues.model.db.RequestsDao;
import com.perls3.bellesvues.model.db.RequestsEntity;
import com.perls3.bellesvues.repositories.BelleVueRepository;


public class AlertViewModel extends AndroidViewModel {
    private BelleVueRepository belleVueRepository;
    private LiveData<RequestsEntity[]> mAllRequests;


    public AlertViewModel(@NonNull Application application) {
        super(application);
        belleVueRepository = new BelleVueRepository(application);
        mAllRequests=belleVueRepository.getmAllRequests();

    }

    // TODO: Implement the ViewModel
    public void insertAllRequests(RequestsEntity requestsEntity){
        belleVueRepository.InsertRepo(requestsEntity);
    }

    public LiveData<RequestsEntity[]> getmAllRequests() {
        return mAllRequests;
    }
}