package minhna.android.minhimageloader.remote

import java.util.concurrent.Callable

/**
 * The abstract design for all types of download execution.
 */
abstract class DownloadTask<T> : Callable<T> {
    abstract fun download(url: String): T
}