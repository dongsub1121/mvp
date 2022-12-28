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

    @Query("SELECT * FROM MerchantEntity WHERE   master =:value")
    Single<List<MerchantEntity>> loadByIMaster(Integer value);

    @Query("Update MerchantEntity set master = :set where sitename = :site")
    Completable update(Integer set, String site);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MerchantEntity... merchants);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable replace(MerchantEntity... merchants);

    @Query("DELETE FROM MerchantEntity WHERE sitename =:sitename")
    Completable delete(String... sitename);

    @Query("DELETE FROM MerchantEntity")
    Completable deleteAll();
}
