package com.perls3.bellesvues.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.perls3.bellesvues.App;
import com.perls3.bellesvues.model.db.AppDataBase;
import com.perls3.bellesvues.model.db.RequestsDao;
import com.perls3.bellesvues.model.db.RequestsEntity;

import java.util.List;

public class BelleVueRepository {
    RequestsDao requestsDao;
    LiveData<RequestsEntity[]> mAllRequests;

    public BelleVueRepository(Application app) {
       requestsDao= App.db.userDao();
       mAllRequests=requestsDao.loadAllRequests();
    }

    /**
     * this method should run on other thread ,thats why we used the ExecutorService to run the io instruction not in the main thread
     * @param requestsEntity
     */
    public void InsertRepo(RequestsEntity requestsEntity){
        AppDataBase.databaseWriteExecutor.execute(()-> requestsDao.insert(requestsEntity));

    }

    public LiveData<RequestsEntity[]> getmAllRequests() {
        return mAllRequests;
    }
}
