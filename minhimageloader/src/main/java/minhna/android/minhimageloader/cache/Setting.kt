package minhna.android.minhimageloader.cache

/**
 * Default cache size is 5MB
 */
class Setting() {
    companion object {
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024
        val defaultCacheSize = (maxMemory / 5).toInt()
    }
}