package com.mpas.mvp.merchant.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MerchantInfoData.class}, version = 1, exportSchema = false)
public abstract class MerchantInfoDataBase extends RoomDatabase {

    public abstract MerchantInfoDao merchantInfoDao();
    private static  MerchantInfoDataBase INSTANCE;


    public synchronized static MerchantInfoDataBase getInstance(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MerchantInfoDataBase.class,
                    "merchantInfo")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
