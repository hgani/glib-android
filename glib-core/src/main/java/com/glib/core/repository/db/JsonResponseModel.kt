package com.glib.core.repository.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity
data class JsonResponseModel(
    @PrimaryKey val url: String,
    val response: JSONObject,
    val timeStamp: Long
)