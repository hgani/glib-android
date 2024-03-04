package com.glib.core.repository.network.helpers

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.glib.core.utils.Res
import java.io.IOException

@TargetApi(Build.VERSION_CODES.M)
class NetworkHelper(private val context: Context = Res.context) {
    /**
     * Checks whether or not the device
     * is online and able to communicate
     * with the outside world.
     *
     * @return whether the device is online
     */
    fun isOnline() = runWithNetworkCapabilities(false) {
        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI,
        ) || it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

    private fun <T> runWithNetworkCapabilities(
        defaultValue: T,
        action: (networkCapabilities: NetworkCapabilities) -> T,
    ): T {
        val connectivityManager =
            context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        return if (network != null) {
            connectivityManager.getNetworkCapabilities(network)?.let(action) ?: defaultValue
        } else {
            defaultValue
        }
    }

    /**
     * Returns network type "wifi" or "mobile"
     */
    fun getNetworkType() = runWithNetworkCapabilities("offline") {
        if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) "wifi" else "mobile"
    }
}

class NoConnectivityException : IOException() {
    override val message: String = "No Internet Connection"
}