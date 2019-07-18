package com.glib.core.io

import android.content.Context
import com.glib.core.logging.GLog
import com.glib.core.utils.Res
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI
import java.util.*

class PersistentCookieStore(private val delegate: CookieStore) : CookieStore {
    init {
        deserializeCookieMap()
    }

    override fun add(uri: URI, cookie: HttpCookie) {
        delegate.add(uri, cookie)
        serializeCookieMap()
    }

    override fun get(uri: URI): List<HttpCookie> {
        return delegate.get(uri)
    }

    override fun getCookies(): List<HttpCookie> {
        return delegate.cookies
    }

    override fun getURIs(): List<URI> {
        return delegate.urIs
    }

    override fun remove(uri: URI, cookie: HttpCookie): Boolean {
        val removed = delegate.remove(uri, cookie)
        serializeCookieMap()
        return removed
    }

    override fun removeAll(): Boolean {
        val hadCookies = delegate.removeAll()
        serializeCookieMap()
        return hadCookies
    }

    private fun deserializeCookieMap() {
        try {
            val fileIn = Res.context.openFileInput(COOKIE_FILE)

            val objectIn = ObjectInputStream(fileIn)
            importFromDelegate(objectIn.readObject() as Map<URI, List<SerializableCookie>>)
            objectIn.close()
        } catch (e: FileNotFoundException) {
            // This is very possible and not problem at all.
        } catch (e: IOException) {
            GLog.e(javaClass, "Failed deserializing cookies", e)
        } catch (e: ClassNotFoundException) {
            GLog.e(javaClass, "Failed deserializing cookies", e)
        }

    }

    private fun importFromDelegate(map: Map<URI, List<SerializableCookie>>) {
        for ((key, value) in map) {
            for (cookie in value) {
                delegate.add(key, cookie.toHttpCookie())
            }
        }
    }

    private fun exportFromDelegate(): Map<URI, List<SerializableCookie>> {
        val cookieMap = HashMap<URI, List<SerializableCookie>>()
        for (uri in delegate.urIs) {
            cookieMap[uri] = SerializableCookie.from(delegate.get(uri))
        }
        return cookieMap
    }

    private fun serializeCookieMap() {
        //    if (true) {
        //      throw new RuntimeException("SERIALIZE COOKIE");
        //    }
        try {
            val fileOut = Res.context.openFileOutput(COOKIE_FILE, Context.MODE_PRIVATE)
            val objectOut = ObjectOutputStream(fileOut)
            objectOut.writeObject(exportFromDelegate())
            objectOut.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    companion object {
        val COOKIE_FILE = "cookies"
    }
}
