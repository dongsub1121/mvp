package com.mpas.mvp.merchant.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MerchantInfoDao {

    @Insert
    void insert(MerchantInfoData merchantInfoData);

    @Update
    void update(MerchantInfoData merchantInfoData);

    @Delete
    void delete(MerchantInfoData merchantInfoData);

    @Query("SELECT * FROM MerchantInfoTable")
    LiveData<List<MerchantInfoData>> getAll();

}
