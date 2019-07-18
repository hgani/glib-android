package com.glib.core.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.glib.core.utils.Res

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