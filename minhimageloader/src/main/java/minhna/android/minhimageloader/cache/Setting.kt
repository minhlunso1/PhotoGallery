package minhna.android.minhimageloader.cache

/**
 * Memory size setting.
 */
class Setting() {
    companion object {
        val maxMemory = Runtime.getRuntime().maxMemory()
        val defaultCacheSize = (maxMemory / 2).toInt()
    }
}