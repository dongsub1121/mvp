package com.mpas.mvp.merchant1.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MerchantDao {
    @Query("SELECT * FROM MerchantEntity")
    Single<List<MerchantEntity>> getAll();

    @Query("SELECT * FROM MerchantEntity WHERE sitename = :sitename")
    Single<List<MerchantEntity>> loadById(String sitename);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MerchantEntity... merchants);

    @Query("DELETE FROM MerchantEntity")
    Completable deleteAll();

}
