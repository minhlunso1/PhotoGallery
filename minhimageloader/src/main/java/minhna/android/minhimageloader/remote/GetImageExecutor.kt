package minhna.android.minhimageloader.remote

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import minhna.android.minhimageloader.cache.MemoryCache
import java.net.HttpURLConnection
import java.net.URL

class GetImageExecutor<T>(private val url: String, private val imageView: ImageView,
                       private val cache: MemoryCache): DownloadTask<Bitmap?>() {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun download(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(url)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    override fun call(): Bitmap? {
        val bitmap = download(url)
        bitmap?.let {
            if (imageView.tag == url)
                updateImageView(imageView, it)
            cache.put(url, it)
        }
        return bitmap
    }

    fun updateImageView(imageview: ImageView, bitmap: Bitmap) {
        uiHandler.post { imageview.setImageBitmap(bitmap) }
    }

}

