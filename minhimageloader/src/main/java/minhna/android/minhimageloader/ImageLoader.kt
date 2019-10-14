package minhna.android.minhimageloader

import android.graphics.Bitmap
import android.widget.ImageView
import minhna.android.minhimageloader.cache.MemoryCache
import minhna.android.minhimageloader.cache.Setting
import minhna.android.minhimageloader.remote.GetImageExecutor
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ImageLoader private constructor(cacheSize: Int) {
    private var cache = MemoryCache(cacheSize)
    private val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList:HashMap<String,Future<Bitmap?>> = hashMapOf()

    companion object {
        private var instance: ImageLoader? = null

        @Synchronized
        fun getInstance(memoryCacheSize: Int = Setting.defaultCacheSize): ImageLoader {
            if (instance == null)
                instance = ImageLoader(memoryCacheSize)
            return instance!!
        }
    }

    fun displayImage(url: String, imageView: ImageView, placeholder: Int?) {
        val bitmap= cache.get(url)
        bitmap?.let {
            imageView.setImageBitmap(it)
            return
        }
        ?: run {
            imageView.tag = url
            if (placeholder != null)
                imageView.setImageResource(placeholder)
            addGetImageExecutor(url, GetImageExecutor(url, imageView, cache))
        }
    }

    private fun addGetImageExecutor(url: String, task: GetImageExecutor<Bitmap?>) {
        mRunningDownloadList[url] = executorService.submit(task)
    }


    fun clearCache() {
        cache.evict()
    }

    fun cancelTask(url: String){
        synchronized(this){
            mRunningDownloadList.forEach {
                if (it.key == url &&  !it.value.isDone)
                    it.value.cancel(true)
            }
        }
    }

    fun cancelAll() {
        synchronized (this) {
            mRunningDownloadList.forEach{
                if ( !it.value.isDone)
                    it.value.cancel(true)
            }
            mRunningDownloadList.clear()
        }
    }
}