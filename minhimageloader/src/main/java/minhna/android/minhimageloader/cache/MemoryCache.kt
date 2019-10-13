package minhna.android.minhimageloader.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * @property newMaxSize
 * If the cache size specified @param newMaxSize larger than the memory of JVM,
 * set it to the default cache size.
 */
class MemoryCache (newMaxSize: Int) {
    private val cache : LruCache<String, Bitmap>

    init {
        val cacheSize : Int =
        if (newMaxSize > Setting.maxMemory)
            Setting.defaultCacheSize
        else
            newMaxSize

        cache = object : LruCache<String, Bitmap>(cacheSize) {
          override fun sizeOf(key: String, value: Bitmap): Int {
              return (value.rowBytes) * (value.height) / 1024
          }
      }
    }

    fun put(url: String, bitmap: Bitmap) {
        cache.put(url,bitmap)
    }

    fun get(url: String): Bitmap? = cache.get(url)

    fun evict() {
        cache.evictAll()
    }
}