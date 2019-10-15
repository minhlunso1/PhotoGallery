package minhna.android.photogallery.remote.pojo

import android.content.res.Resources
import minhna.android.photogallery.R
import minhna.android.photogallery.model.Const

/**
 * The error api wrapper to store error info and customize the output info.
 */
data class ErrorApi(val code: Int = -1, val message: String) {

    /**
     * Get the localized the error display text.
     * @param resources The resource from Android context.
     * @return Error message.
     */
    fun getLocalizedMessage(resources: Resources) =
        when (code) {
            Const.HTTP_CODE.TIME_OUT -> resources.getString(R.string.network_error)
            else -> resources.getString(R.string.error)
        }
}