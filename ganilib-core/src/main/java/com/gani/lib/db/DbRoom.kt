package com.gani.lib.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.gani.lib.utils.Res

@Database(entities = arrayOf(DbMap::class), version = 1)
abstract class DbRoom : RoomDatabase() {
    abstract fun map(): DbMap.Accessor

    companion object {
        val INSTANCE = Room.databaseBuilder(Res.context,
                DbRoom::class.java, "gdatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}