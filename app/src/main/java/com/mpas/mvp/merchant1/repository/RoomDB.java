package com.mpas.mvp.merchant1.repository;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//@Database(entities = {MerchantEntity.class}, version = 1, exportSchema = false)
@Database(entities = {MerchantEntity.class}, version = 3,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static volatile RoomDB INSTANCE = null;
    private static final String DATABASE = "ROOM";

    public abstract MerchantDao merchantDao();

    static final Migration MIGRATION_2_3 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase a_database) {
            a_database.execSQL(
                    "ALTER TABLE MerchantEntity   ADD COLUMN master2 INTIGER NOT NULL DEFAULT 0");
        }
    };

    public static RoomDB getInstance(Context context) {
        if(INSTANCE == null){
            synchronized (RoomDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.
                            databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE)
                            .addMigrations(MIGRATION_2_3) //.fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
