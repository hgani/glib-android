package com.glib.core.repository.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.glib.core.utils.Res

@Database(entities = [JsonResponseModel::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun dao(): Dao
    companion object {
        private const val DATABASE_NAME: String = "team_app_local_db"
        val INSTANCE = Room.databaseBuilder(Res.context, LocalDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}