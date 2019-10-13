package minhna.android.photogallery.remote.pojo

import android.content.res.Resources
import minhna.android.photogallery.R
import minhna.android.photogallery.model.Const

data class ErrorApi(val code: Int = -1, val message: String) {

    fun getLocalizedMessage(resources: Resources) =
        when (code) {
            Const.HTTP_CODE.TIME_OUT -> resources.getString(R.string.network_error)
            else -> resources.getString(R.string.error)
        }
}