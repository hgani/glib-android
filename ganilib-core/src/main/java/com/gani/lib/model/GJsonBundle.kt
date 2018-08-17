package com.gani.lib.model

import android.os.Bundle
import com.gani.lib.json.GJson

class GJsonBundle(val native: Bundle) {
    companion object {
        private val ARG_JSON = "jsonArg"
    }

    val json: GJson
        get() = native.get(ARG_JSON) as GJson

    constructor() : this(Bundle()) {}

    fun value(json: GJson): GJsonBundle {
        native.putString(ARG_JSON, json.toString())
        return this
    }
}
