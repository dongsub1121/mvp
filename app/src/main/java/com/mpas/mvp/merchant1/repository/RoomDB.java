package com.mpas.mvp.merchant1.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MerchantEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static volatile RoomDB INSTANCE = null;
    private static final String DATABASE = "ROOM";

    public abstract MerchantDao merchantDao();

    public static RoomDB getInstance(Context context) {
        if(INSTANCE == null){
            synchronized (RoomDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE).build();
                }
            }
        }
        return INSTANCE;
    }
}
