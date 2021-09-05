package com.perls3.bellesvues.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface RequestsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RequestsEntity requestsEntity);

    @Query("SELECT * FROM RequestsEntity")
    LiveData<RequestsEntity[]>  loadAllRequests();


}
