package com.wanandroid.kotlin.data.api.cookie

import android.content.Context
import android.util.Base64
import android.util.Log
import com.wanandroid.kotlin.MyApp
import okhttp3.Cookie
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class PersistentCookieStore {
    companion object {
        private const val COOKIE_PREFS = "cookie_prefs"
        private const val TAG = "PersistentCookieStore"
    }
    private val prefs = MyApp.context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
    private val cache = HashMap<String, List<Cookie>>()

    /**
     * 存储 Cookies
     * 首先将 Cookies 存入当前缓存对象 cache 中，然后在将序列化后的数据存入 SharedPreferences 文件。
     *
     * @param host  站点域名(或IP地址)
     * @param cookies Cookie列表
     */
    operator fun set(host: String, cookies: List<Cookie>) {
        cache[host] = cookies

        val set = HashSet<String>()
        cookies.map { encodeBase64(it) }
            .forEach { set.add(it) }

        prefs.edit().apply {
            putStringSet(host, set)
            apply()
        }
    }


    /**
     * 获取 Cookies
     * 首先，从缓存中查询是否有可用的 Cookies ，如果没有再从 SharedPreferences 文件中查找。
     *
     * @param host 站点域名(或IP地址)
     * @return Cookies
     */
    operator fun get(host: String): List<Cookie>? {
        val cookies = cache[host]
        return if (cookies != null && cookies.isNotEmpty()) {
            cookies
        } else {
            val set = prefs.getStringSet(host, null)
            if (set == null) {
                null
            } else {
                val list = ArrayList<Cookie>()
                set.map { decodeBase64(it) }
                    .forEach { list.add(it) }
                cache[host] = list
                list
            }
        }
    }

    /**
     * 移除某一个站点的 Cookies
     * 将其从缓存和 SharedPreferences 文件中删除
     *
     * @param host 站点域名(或IP地址)
     */
    fun remove(host: String) {
        cache.remove(host)
        prefs.edit().remove(host).apply()
    }

    /**
     * 清空全部站点的 Cookies
     * 清空缓存和 SharedPreferences 。
     *
     */
    fun clear() {
        cache.clear()
        prefs.edit().clear().apply()
    }

    /**
     * 将一个 Cookie 对象序列化为字符串
     *
     * 1，将 Cookie 对象转换为可序列化的 SerializableCookie 对象
     * 2，将 SerializableCookie 序列化为 ByteArray
     * 3，将 ByteArray 使用 Base64 编码并生成字符串
     *
     * @param cookie 需要序列化的 Cookie 对象
     * @return 序列化之后的字符串
     */
    private fun encodeBase64(cookie: Cookie): String {
        var objectBuffer: ObjectOutputStream? = null
        try {
            val buffer = ByteArrayOutputStream()
            objectBuffer = ObjectOutputStream(buffer)
            objectBuffer.writeObject(SerializableCookie(cookie))
            val bytes = buffer.toByteArray()
            val code = Base64.encode(bytes, Base64.DEFAULT)
            return String(code)
        } catch (e: Exception) {
            throw e
        } finally {
            if (objectBuffer != null)
                try {
                    objectBuffer.close()
                } catch (e: Exception) {
                    Log.e(TAG, "encodeBase64: ${e.message}")
                }
        }
    }

    /**
     * 将一个编码后的字符串反序列化为 Cookie 对象
     *
     * 1，将该字符串使用 Base64 解码为字节数组
     * 2，将字节数据反序列化为 SerializableCookie 对象
     * 3，从 SerializableCookie 对象中获取 Cookie 对象并返回。
     *
     * @param code 被编码后的序列化数据
     * @return 解码后的 Cookie 对象
     */
    private fun decodeBase64(code: String): Cookie {
        var objectBuffer: ObjectInputStream? = null
        try {
            val bytes = Base64.decode(code, Base64.DEFAULT)
            val buffer = ByteArrayInputStream(bytes)
            objectBuffer = ObjectInputStream(buffer)
            return (objectBuffer.readObject() as SerializableCookie).cookie()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            if (objectBuffer != null)
                try {
                    objectBuffer.close()
                } catch (e: Exception) {
                    Log.e(TAG, "decodeBase64: ${e.message}")
                }
        }
    }
}