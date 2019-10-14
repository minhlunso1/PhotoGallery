package minhna.android.minhimageloader.cache

class Setting() {
    companion object {
        val maxMemory = Runtime.getRuntime().maxMemory()
        val defaultCacheSize = (maxMemory / 2).toInt()
    }
}