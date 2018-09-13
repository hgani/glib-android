package com.gani.lib.db

import android.arch.persistence.room.*
import com.gani.lib.json.GJsonObject

@Entity(indices = arrayOf(Index("name", unique = true)))
class DbMap {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    var name: String? = null  // "key" is a reserved word, so we use "name" instead
    var value: String? = null

    fun with(key: String, value: String): DbMap {
        this.name = key
        this.value = value
        return this
    }



    companion object {
        private fun row(key: String): DbMap? {
            return DbRoom.INSTANCE.map().where(key)
        }

        fun put(key: String, obj: GJsonObject.Default) {
            put(key, obj.stringValue)
        }

        fun put(key: String, value: String) {
            val record = row(key)
            if (record == null) {
                DbRoom.INSTANCE.map().insert(DbMap().with(key, value))
            }
            else {
                record.value = value
                DbRoom.INSTANCE.map().update(record)
            }
        }

        fun get(key: String): GJsonObject.Default {
            return GJsonObject.Default(row(key)?.value)
        }

        fun remove(key: String) {
            val record = row(key)
            if (record != null) {
                DbRoom.INSTANCE.map().delete(record)
            }
        }
    }



    @Dao
    interface Accessor {
        @get:Query("SELECT * FROM DbMap")
        val all: List<DbMap>

        @Query("SELECT * FROM DbMap where name = :key")
        fun where(key: String): DbMap?

        @Query("SELECT COUNT(*) from DbMap")
        fun count(): Int

        @Insert
        fun insert(vararg records: DbMap)

        @Update
        fun update(record: DbMap): Int

        @Delete
        fun delete(record: DbMap)
    }
}